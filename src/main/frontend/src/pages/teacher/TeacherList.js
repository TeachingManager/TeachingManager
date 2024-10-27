import React, { useState, useEffect } from 'react';
import { Button, Box, Stack, TextField, Modal, Typography } from '@mui/material';
import { DataGrid } from '@mui/x-data-grid';
import { inviteTeacher, getTeachersInfo } from '../../api/institute';
import { teacherListState } from '../../common/Auth/recoilAtom';
import { useRecoilState } from 'recoil';


export default function StudentList() {
  const [teachers, setTeachers] = useRecoilState(teacherListState);
  
  const [rows, setRows] = useState([
    
  ]);

  const [open, setOpen] = useState(false);
  const [newTeacher, setNewTeacher] = useState({
    email: ''
  });

  const [selectedRows, setSelectedRows] = useState([]);

  useEffect(() => {
    const fetchTeachers = async () => {
      try {
        const fetchResult = await getTeachersInfo();
        const teacherdata = fetchResult.data.teacherList;
        setTeachers(teacherdata);
   
      } catch (error) {
        console.error(error);
      }
    };

    fetchTeachers();
  }, []);




  const handleOpen = () => setOpen(true);
  const handleClose = () => setOpen(false);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setNewTeacher(prevState => ({
      ...prevState,
      [name]: value
    }));
  };

  const handleDeleteSelected = () => {
    // const selectedIds = selectedRows.map(Number); // 선택된 행 ID를 숫자로 변환
    // setRows(prevRows => prevRows.filter(row => !selectedIds.includes(row.id)));
    // setSelectedRows([]); // 삭제 후 선택 상태를 초기화
  };

  const handleInviteTeacher = async() => {
    const result = await inviteTeacher(newTeacher.email);
    if(result.isValid === false)
      alert('존재하지 않는 이메일입니다.')
    else if(result.isValid === true){
      alert(`${result.response.data.message}`)
      setOpen(false)
    }
    
  }

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
        <Button variant="contained" onClick={handleOpen}>선생님 초대하기</Button>
        <Button variant="contained" color="error" onClick={handleDeleteSelected}>선택된 선생님 삭제</Button>
      </Stack>
      <DataGrid
         columns={[
            { field: 'teacher_id', headerName: 'ID'},
            { field: 'teacher_name', headerName: '이름', flex: 1 },
            { field: 'age', headerName: '나이', flex: 1 },
            { field: 'phoneNum', headerName: '전화번호', flex: 1 },
            { field: 'gender', headerName: '성별', flex: 1 },
            { field: 'bank_account', headerName: '계좌번호', flex: 1 },
            { field: 'birth', headerName: '생일', flex: 1 }
          ]}
        rows={teachers}
        pageSize={5}
        rowsPerPageOptions={5}
        checkboxSelection
        onRowSelectionModelChange={(newSelectionModel) => {
          console.log(newSelectionModel); // 디버그용 콘솔 로그
          setSelectedRows(newSelectionModel);
        }}
        getRowId={(row) => row.teacher_id} 
        columnVisibilityModel={{
          teacher_id: false, // ID 열을 숨김
        }}
        
      />
      <Modal
        open={open}
        onClose={handleClose}
      >
        <Box sx={modalStyle}>
          <Typography variant="h6" component="h2">선생님 초대하기</Typography>
          <Stack spacing={2} mt={2}>
            <TextField label="이메일" name="email" value={newTeacher.email} onChange={handleChange} />

            <Button variant="contained" onClick={handleInviteTeacher}>초대</Button>
          </Stack>
        </Box>
      </Modal>
     
    </Box>
  );
}
