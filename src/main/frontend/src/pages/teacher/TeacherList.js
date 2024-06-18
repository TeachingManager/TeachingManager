import React, { useState } from 'react';
import { Button, Box, Stack, TextField, Modal, Typography } from '@mui/material';
import { DataGrid } from '@mui/x-data-grid';

export default function StudentList() {
  const [rows, setRows] = useState([
    { id: 1, name: 'Kim', age: 10, phone: '010-1234-5678',  gender: '남성', account: '937702', birthday: '2012-04-01' },
  ]);

  const [open, setOpen] = useState(false);
  const [newStudent, setNewStudent] = useState({
    id: '',
    name: '',
    age: '',
    phone: '',
    gender: '',
    account: '',
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
        phone: '',
        gender: '',
        account: '',
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
        <Button variant="contained" onClick={handleOpen}>선생님 추가하기</Button>
        <Button variant="contained" color="error" onClick={handleDeleteSelected}>선택된 선생님 삭제</Button>
      </Stack>
      <DataGrid
         columns={[
            { field: 'id', headerName: 'ID'},
            { field: 'name', headerName: '이름', flex: 1 },
            { field: 'age', headerName: '나이', flex: 1 },
            { field: 'phone', headerName: '전화번호', flex: 1 },
            { field: 'gender', headerName: '성별', flex: 1 },
            { field: 'account', headerName: '계좌번호', flex: 1 },
            { field: 'birthday', headerName: '생일', flex: 1 }
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
          <Typography variant="h6" component="h2">선생님 정보 입력</Typography>
          <Stack spacing={2} mt={2}>
            <TextField label="이름" name="name" value={newStudent.name} onChange={handleChange} />
            <TextField label="나이" name="age" value={newStudent.age} onChange={handleChange} />
            <TextField label="전화번호" name="phone" value={newStudent.phone} onChange={handleChange} />

            <TextField label="성별" name="gender" value={newStudent.gender} onChange={handleChange} />
            <TextField label="계좌번호" name="account" value={newStudent.account} onChange={handleChange} />
            <TextField label="생일" name="birthday" value={newStudent.birthday} onChange={handleChange} />
            <Button variant="contained" onClick={handleAddStudent}>추가</Button>
          </Stack>
        </Box>
      </Modal>
     
    </Box>
  );
}
