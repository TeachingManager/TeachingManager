import React, { useState, useEffect } from "react";
import {
  Modal,
  Box,
  Typography,
  List,
  ListItem,
  ListItemText,
  Button,
  Grid,
  Backdrop
} from "@mui/material";
import PersonAddIcon from "@mui/icons-material/PersonAdd";
import PersonRemoveIcon from "@mui/icons-material/PersonRemove";
import axios from "axios";
import { getStudent } from "../../api/student";

const style = {
  position: "absolute",
  top: "50%",
  left: "50%",
  transform: "translate(-50%, -50%)",
  width: 600,
  height: 800,
  bgcolor: "background.paper",
  border: "2px solid #000",
  boxShadow: 24,
  p: 4,
  display: 'flex',
  flexDirection: 'column',
};

const listStyle = {
    maxHeight: 200,
    overflow: "auto",
  };

export const CourseModal = ({ open, handleClose, lectureId, year, month }) => {

  const [enrolledStudents, setEnrolledStudents] = useState([]);
  const [notEnrolledStudents, setNotEnrolledStudents] = useState([]);
  const [allStudents, setAllStudents] = useState([]);
  useEffect(() => {
    const token = localStorage.getItem("token");
    
    axios
        .get(`${process.env.REACT_APP_API_BASE_URL}/api/enroll/students?lecture_id=${lectureId}&year=${year}&month=${month}`, {
            headers: {
                Authorization: `Bearer ${token}`
            }
        })
        .then((response) => {
            console.log(response.data);
            setEnrolledStudents(response.data);
        })
        .catch((error) => {
            console.error("학생 목록을 가져오는 중 오류 발생:", error);
        });

    
        const fetchStudents = async () => {
            try {
              const result = await getStudent();
              setAllStudents(result)
              console.log(result)
            } catch (error) {
              console.error(error);
            }
          };
        

        fetchStudents();

}, [lectureId]);
    
useEffect(() => {
  const enrolledStudentsId = enrolledStudents.map(student => student.student_id);
  const notEnrolled = allStudents.filter(student => !enrolledStudentsId.includes(student.id));

  setNotEnrolledStudents(notEnrolled);
}, [enrolledStudents, allStudents]);

  const handleEnroll = async (studentId) => {
    try {
        const token = localStorage.getItem("token");
        const response = await axios.post(
            `${process.env.REACT_APP_API_BASE_URL}/api/enroll/student?lecture_id=${lectureId}&student_id=${studentId}&year=${year}&month=${month}`,{},
            {
                headers: {
                    Authorization: `Bearer ${token}`
                  },
            }
          )
        console.log(response.data)
        const enroll_id = response.data.enroll_id
        if (response.status === 200) {
            const student = notEnrolledStudents.find((s) => s.id === studentId);
            console.log(student)
            setEnrolledStudents([...enrolledStudents,
                {
                    lecture_id: lectureId,
                    student_id: student.id, 
                    student_name: student.name, 
                    year: 2024, 
                    month: 8, 
                    lecture_fee: 40000, 
                    payed_fee: 0, 
                    fullPaid: false,
                    enroll_id : enroll_id
                },
    ]);
    setNotEnrolledStudents((prev) => prev.filter((s) => s.id !== studentId));
    alert("수강 신청 완료.")

        }
    } catch(error) {
        console.error("수강 신청 중 오류 발생", error);
    }
  };

  const handleUnenroll = async (studentId) => {
    try {
      const token = localStorage.getItem("token");
      
      const student = enrolledStudents.find((s) => s.student_id === studentId);
  
      if (!student) {
        console.error("학생을 찾을 수 없습니다.");
        return;
      }
      console.log(student)
      const response = await axios.put(
        `${process.env.REACT_APP_API_BASE_URL}/api/delete/enroll/student?lecture_id=${student.lecture_id}&student_id=${student.student_id}&enroll_id=${student.enroll_id}&year=${year}&month=${month}`,{},
        {
          headers: {
            Authorization: `Bearer ${token}`
          },
        }
      );
      
      if (response.status === 200) {
        setEnrolledStudents(enrolledStudents.filter((s) => s.student_id !== studentId));
        setNotEnrolledStudents([
          ...notEnrolledStudents,
          {
            id: student.student_id, 
            name: student.student_name,
          },
        ]);
        alert("수강 취소 완료.")
      } else {
        console.error("수강 취소 중 오류 발생:", response.statusText);
      }
    } catch (error) {
      console.error("수강 취소 중 오류 발생:", error);
    }
  };
  

  return (
    <Modal open={open} onClose={handleClose} slots={{
      backdrop: Backdrop,
    }}
    slotProps={{
      backdrop: {
        sx: { backgroundColor: "rgba(0, 0, 0, 0.2)" },
      },
    }}>
      <Box sx={style}>
        <Typography variant="h6" component="h2">
          수강 정정 - 강의 ID: {lectureId}
        </Typography>

        <Grid container spacing={2}>
          <Grid item xs={6}>
            <Typography variant="subtitle1">수강 중인 학생</Typography>
            <List>
              {enrolledStudents.map((student) => (
                <ListItem key={student.student_id}>
                  <ListItemText primary={student.student_name} />
                  <Button
                    variant="contained"
                    color="secondary"
                    startIcon={<PersonRemoveIcon />}
                    onClick={() => handleUnenroll(student.student_id)}
                  >
                    수강 취소
                  </Button>
                </ListItem>
              ))}
            </List>
          </Grid>

          <Grid item xs={6}>
            <Typography variant="subtitle1">수강하지 않은 학생</Typography>
            <List>
              {notEnrolledStudents.map((student) => (
                <ListItem key={student.id}>
                  <ListItemText primary={student.name} />
                  <Button
                    variant="contained"
                    color="primary"
                    startIcon={<PersonAddIcon />}
                    onClick={() => handleEnroll(student.id)}
                  >
                    수강 신청
                  </Button>
                </ListItem>
              ))}
            </List>
          </Grid>
        </Grid>
        <Box mt="auto">
          <Button onClick={handleClose} sx={{ mt: 2 }} fullWidth variant="outlined">
            닫기
          </Button>
        </Box>
      </Box>
    </Modal>
  );
};
