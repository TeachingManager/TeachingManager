import React, { useState } from 'react';
import { Box, TextField, Avatar, Checkbox, Table, TableBody, TableCell, TableContainer, TableHead, TableRow, Paper, Button, Modal } from '@mui/material';

export default function Example() {
  const students = [
    { id: 1, name: '홍길동', age: 20 },
    { id: 2, name: '김철수', age: 15 },
    { id: 3, name: '아이유', age: 28 },
  ];

  const colors = ['#f44336', '#3f51b5', '#4caf50', '#ff9800', '#9c27b0'];

  // 선택된 학생들의 ID만 저장
  const [selectedStudentIds, setSelectedStudentIds] = useState([]);
  const [open, setOpen] = useState(false);

  const handleToggle = (studentId) => {
    setSelectedStudentIds((prevSelectedIds) => {
      if (prevSelectedIds.includes(studentId)) {
        return prevSelectedIds.filter((id) => id !== studentId);
      } else {
        return [...prevSelectedIds, studentId];
      }
    });
  };

  const handleOpen = () => setOpen(true);
  const handleClose = () => setOpen(false);

  return (
    <div>
      <Button variant="contained" onClick={handleOpen}>
        학생 선택하기
      </Button>
      <Modal
        open={open}
        onClose={handleClose}
        aria-labelledby="modal-modal-title"
        aria-describedby="modal-modal-description"
      >
        <Box 
          sx={{ 
            position: 'absolute', 
            top: '50%', 
            left: '50%', 
            transform: 'translate(-50%, -50%)', 
            width: 600, 
            bgcolor: 'background.paper', 
            boxShadow: 24, 
            p: 4, 
            borderRadius: 2 
          }}
        >
          {/* 선택된 학생을 보여주는 입력 박스 */}
          <TextField
            variant="outlined"
            fullWidth
            label="선택된 학생"
            disabled
            InputProps={{
              startAdornment: (
                <Box
                  sx={{
                    display: 'flex',
                    alignItems: 'center',
                    flexWrap: 'wrap',
                    gap: 1,
                    height: '100%',
                    overflow: 'hidden',
                    p:1
                  }}
                >
                  {selectedStudentIds.map((studentId, index) => {
                    const student = students.find((s) => s.id === studentId);
                    return (
                      <Avatar
                        key={studentId}
                        sx={{
                          fontSize: 14,
                          width: 40,
                          height: 40,
                          backgroundColor: colors[index % colors.length],
                          color: '#fff',
                        }}
                      >
                        {student.name}
                      </Avatar>
                    );
                  })}
                </Box>
              ),
            }}
            sx={{
              '& .MuiOutlinedInput-root': {
                display: 'flex',
                alignItems: 'center',
                paddingTop: 0.5,
                paddingBottom: 0.5,
              },
              '& .MuiInputBase-input': {
                padding: 0,
                minWidth: 0,
                width: '1px',
                flexGrow: 0,
              },
            }}
          />

          {/* 학생 목록 테이블 */}
          <TableContainer component={Paper} sx={{ mt: 2 }}>
            <Table>
              <TableHead>
                <TableRow>
                  <TableCell align="center">체크 박스</TableCell>
                  <TableCell align="center">학생 이름</TableCell>
                  <TableCell align="center">나이</TableCell>
                </TableRow>
              </TableHead>
              <TableBody>
                {students.map((student) => (
                  <TableRow key={student.id}>
                    <TableCell align="center">
                      <Checkbox
                        checked={selectedStudentIds.includes(student.id)}
                        onChange={() => handleToggle(student.id)}
                      />
                    </TableCell>
                    <TableCell align="center">{student.name}</TableCell>
                    <TableCell align="center">{student.age}</TableCell>
                  </TableRow>
                ))}
              </TableBody>
            </Table>
          </TableContainer>
          <Box sx={{ textAlign: 'right', mt: 2 }}>
            <Button variant="contained" onClick={handleClose}>
              닫기
            </Button>
          </Box>
        </Box>
      </Modal>
    </div>
  );
}
