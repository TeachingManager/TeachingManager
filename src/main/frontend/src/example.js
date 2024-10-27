import React, { useState } from "react";
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

const style = {
  position: "absolute",
  top: "50%",
  left: "50%",
  transform: "translate(-50%, -50%)",
  width: 600, // 크기를 넉넉히 조정
  bgcolor: "background.paper",
  border: "2px solid #000",
  boxShadow: 24,
  p: 4,
};

const listStyle = {
  maxHeight: 200, // 스크롤을 위한 최대 높이 설정
  overflow: 'auto', // 내용이 넘칠 경우 스크롤 활성화
};

export default function Example() {
  const dummyEnrolledStudents = [
    { id: 1, name: "학생 A" },
    { id: 2, name: "학생 B" },
    { id: 3, name: "학생 C" },
  ];

  const dummyNotEnrolledStudents = [
    { id: 4, name: "학생 D" },
    { id: 5, name: "학생 E" },
    { id: 6, name: "학생 F" },
    { id: 7, name: "학생 G" },
    { id: 8, name: "학생 H" },
  ];

  const [enrolledStudents, setEnrolledStudents] = useState(dummyEnrolledStudents);
  const [notEnrolledStudents, setNotEnrolledStudents] = useState(dummyNotEnrolledStudents);

  const handleEnroll = (studentId) => {
    const student = notEnrolledStudents.find((s) => s.id === studentId);
    setEnrolledStudents([...enrolledStudents, student]);
    setNotEnrolledStudents(notEnrolledStudents.filter((s) => s.id !== studentId));
  };

  const handleUnenroll = (studentId) => {
    const student = enrolledStudents.find((s) => s.id === studentId);
    setEnrolledStudents(enrolledStudents.filter((s) => s.id !== studentId));
    setNotEnrolledStudents([...notEnrolledStudents, student]);
  };

  return (
    <Modal open={true} onClose={() => {}}>
      <Box sx={style}>
        <Typography variant="h6" component="h2">
          수강 정정 - 강의 ID: 101
        </Typography>

        <Grid container spacing={2}>
          {/* 수강 중인 학생 목록 */}
          <Grid item xs={6}>
            <Typography variant="subtitle1">수강 중인 학생</Typography>
            <List sx={listStyle}>
              {enrolledStudents.map((student) => (
                <ListItem key={student.id}>
                  <ListItemText primary={student.name} />
                  <Button
                    variant="contained"
                    color="secondary"
                    startIcon={<PersonRemoveIcon />}
                    onClick={() => handleUnenroll(student.id)}
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
            <List sx={listStyle}>
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
        <Button onClick={() => {}} sx={{ mt: 2 }} fullWidth variant="outlined">
          닫기
        </Button>
      </Box>
    </Modal>
  );

}
