import React from 'react';
import { Box, TextField, Avatar, Checkbox, Button, Modal, Typography } from '@mui/material';
import { DataGrid } from '@mui/x-data-grid';

export default function StudentListModal({ open, handleClose, students, selectedStudentIds, handleToggle }) {
  const colors = ['#f44336', '#3f51b5', '#4caf50', '#ff9800', '#9c27b0'];

  const columns = [
    {
      field: 'checkbox',
      headerName: '체크 박스',
      width: 150,
      renderCell: (params) => (
        <Checkbox
          checked={selectedStudentIds.includes(params.row.id)}
          onChange={() => handleToggle(params.row.id)}
        />
      ),
    },
    { field: 'name', headerName: '학생 이름', width: 150 },
    { field: 'age', headerName: '나이', width: 110 },
  ];

  const rows = students.map((student) => ({
    id: student.id,
    name: student.name,
    age: student.age,
  }));

  return (
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
        <Typography sx = {{
            mb: 1,
            pb: 1,
            fontFamily: 'Roboto', fontSize: '18px', fontWeight: 500, 
            textAlign: 'center',
        }}>
            수강 학생을 선택해주세요.
        </Typography>
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
                  p: 1,
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

        {/* 학생 목록 DataGrid */}
        <Box sx={{ height: 400, mt: 2 }}>
          <DataGrid
            rows={rows}
            columns={columns}
            pageSize={5}
            checkboxSelection={false}
          />
        </Box>

        <Box sx={{ textAlign: 'right', mt: 2 }}>
          <Button sx={{
                fontSize: '14px', // 폰트 크기 설정
                fontWeight: 'bold', // 폰트 굵기 설정
            }}>
            강의 개설
          </Button>
          <Button onClick={handleClose} 
                sx={{
                    fontSize: '14px', // 폰트 크기 설정
                    fontWeight: 'bold', // 폰트 굵기 설정
                }}>
            닫기
          </Button>
        </Box>
      </Box>
    </Modal>
  );
}
