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
  width: 400,
  bgcolor: "background.paper",
  border: "2px solid #000",
  boxShadow: 24,
  p: 4,
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
    const token = localStorage.getItem("token"); // 토큰을 localStorage에서 가져온다고 가정
    
    axios
        .get(`http://localhost:8080/api/enroll/students?lecture_id=${lectureId}&year=${year}&month=${month}`, {
            headers: {
                Authorization: `Bearer ${token}`  // Authorization 헤더에 Bearer 토큰 설정
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
              // getStudent는 학생들의 리스트를 반환한다.
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

    setNotEnrolledStudents(notEnrolled); // 상태 업데이트
    console.log(notEnrolled); // 필터링된 결과를 로그로 출력

}, [enrolledStudents, allStudents]);










  // 수강 신청

  const handleEnroll = async (studentId) => {
    try {
        const token = localStorage.getItem("token");
        const response = await axios.post(
            `http://localhost:8080/api/enroll/student?lecture_id=35&student_id=${studentId}&year=${year}&month=${month}`,{},
            {
                headers: {
                    Authorization: `Bearer ${token}`,  // Bearer 토큰 설정
                  },
            }
          )
        
        if (response.status === 200) {
            const student = notEnrolledStudents.find((s) => s.id === studentId);
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
                },
    ]);
    setNotEnrolledStudents(notEnrolledStudents.filter((s) => s.id !== studentId));

        }
    } catch(error) {
        console.error("수강 신청 중 오류 발생", error);
    }


  };

  const handleUnenroll = (studentId) => {
    const student = enrolledStudents.find((s) => s.id === studentId);
    setEnrolledStudents(enrolledStudents.filter((s) => s.student_id !== studentId));
    setNotEnrolledStudents([...notEnrolledStudents,
        {
            id: student.student_id, 
            name: student.student_name,
            // you may add other fields like age, grade if needed
          },
    ]);
  };

  return (
    <Modal open={open} onClose={handleClose}>
      <Box sx={style}>
        <Typography variant="h6" component="h2">
          수강 정정 - 강의 ID: {lectureId}
        </Typography>

        <Grid container spacing={2}>
          {/* 수강 중인 학생 목록 */}
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

          {/* 수강하지 않은 학생 목록 */}
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
        <Button onClick={handleClose} sx={{ mt: 2 }} fullWidth variant="outlined">
          닫기
        </Button>
      </Box>
    </Modal>
  );
};


