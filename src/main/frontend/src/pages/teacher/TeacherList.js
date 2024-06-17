import React, { useState } from 'react';
import { Button, Box, Stack, TextField, Modal, Typography } from '@mui/material';
import { DataGrid } from '@mui/x-data-grid';

export default function StudentList() {
  const [rows, setRows] = useState([
    { id: 1, name: 'Kim', age: 10, grade: '4', phone: '010-1234-5678', guardianName: 'Kim Senior', guardianContact: '010-9876-5432', gender: '남성', level: 'A', birthday: '2012-04-01' },
    { id: 2, name: 'Lee', age: 10, grade: '4', phone: '010-8765-4321', guardianName: 'Lee Senior', guardianContact: '010-1234-5678', gender: '여성', level: 'B', birthday: '2012-05-02' }
  ]);

  const [open, setOpen] = useState(false);
  const [newStudent, setNewStudent] = useState({
    id: '',
    name: '',
    age: '',
    grade: '',
    phone: '',
    guardianName: '',
    guardianContact: '',
    gender: '',
    level: '',
    birthday: ''
  });

  const [selectedRows, setSelectedRows] = useState([]);

  const handleOpen = () => setOpen(true);
  const handleClose = () => setOpen(false);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setNewStudent(prevState => ({
      ...prevState,
      [name]: value
    }));
  };

  const handleAddStudent = () => {
    const newId = rows.length > 0 ? Math.max(...rows.map(row => row.id)) + 1 : 1; // 새로운 ID 설정
    setRows(prevRows => [...prevRows, { ...newStudent, id: newId }]);
    handleClose();
    setNewStudent({
      id: '',
      name: '',
      age: '',
      grade: '',
      phone: '',
      guardianName: '',
      guardianContact: '',
      gender: '',
      level: '',
      birthday: ''
    });
  };

  const handleDeleteSelected = () => {
    const selectedIds = selectedRows.map(Number); // 선택된 행 ID를 숫자로 변환
    setRows(prevRows => prevRows.filter(row => !selectedIds.includes(row.id)));
    setSelectedRows([]); // 삭제 후 선택 상태를 초기화
  };

  const modalStyle = {
    position: 'absolute',
    top: '50%',
    left: '50%',
    transform: 'translate(-50%, -50%)',
    width: 400,
    bgcolor: 'background.paper',
    border: '2px solid #000',
    boxShadow: 24,
    p: 4,
  };

  return (
    <Box sx={{ height: 600, mt: 6 }}>
      <Stack direction="row" spacing={2} mb={2}>
        <Button variant="contained" onClick={handleOpen}>학생 추가하기</Button>
        <Button variant="contained" color="error" onClick={handleDeleteSelected}>선택된 학생 삭제</Button>
      </Stack>
      <DataGrid
        columns={[
          { field: 'id', headerName: 'ID', width: 70 },
          { field: 'name', headerName: '이름', width: 130 },
          { field: 'age', headerName: '나이', width: 90 },
          { field: 'grade', headerName: '학년', width: 90 },
          { field: 'phone', headerName: '전화번호', width: 130 },
          { field: 'guardianName', headerName: '보호자명', width: 130 },
          { field: 'guardianContact', headerName: '보호자 연락처', width: 130 },
          { field: 'gender', headerName: '성별', width: 90 },
          { field: 'level', headerName: '수준', width: 90 },
          { field: 'birthday', headerName: '생일', width: 130 }
        ]}
        rows={rows}
        pageSize={5}
        rowsPerPageOptions={5}
        checkboxSelection
        onRowSelectionModelChange={(newSelectionModel) => {
          console.log(newSelectionModel); // 디버그용 콘솔 로그
          setSelectedRows(newSelectionModel);
        }}
        
      />
      <Modal
        open={open}
        onClose={handleClose}
      >
        <Box sx={modalStyle}>
          <Typography variant="h6" component="h2">학생 정보 입력</Typography>
          <Stack spacing={2} mt={2}>
            <TextField label="이름" name="name" value={newStudent.name} onChange={handleChange} />
            <TextField label="나이" name="age" value={newStudent.age} onChange={handleChange} />
            <TextField label="학년" name="grade" value={newStudent.grade} onChange={handleChange} />
            <TextField label="전화번호" name="phone" value={newStudent.phone} onChange={handleChange} />
            <TextField label="보호자명" name="guardianName" value={newStudent.guardianName} onChange={handleChange} />
            <TextField label="보호자 연락처" name="guardianContact" value={newStudent.guardianContact} onChange={handleChange} />
            <TextField label="성별" name="gender" value={newStudent.gender} onChange={handleChange} />
            <TextField label="수준" name="level" value={newStudent.level} onChange={handleChange} />
            <TextField label="생일" name="birthday" value={newStudent.birthday} onChange={handleChange} />
            <Button variant="contained" onClick={handleAddStudent}>추가</Button>
          </Stack>
        </Box>
      </Modal>
      <Typography variant="h6" component="h3">선택된 행 ID: {selectedRows.join(', ')}</Typography>
    </Box>
  );
}
