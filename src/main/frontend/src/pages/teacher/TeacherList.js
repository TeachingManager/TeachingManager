import React, { useState, useEffect } from 'react';
import { Button, Box, Stack, TextField, Modal, Typography } from '@mui/material';
import { DataGrid } from '@mui/x-data-grid';
import { inviteTeacher, getTeachersInfo } from '../../api/institute';
import { teacherListState } from '../../common/Auth/recoilAtom';
import { useRecoilState } from 'recoil';
import axios from 'axios';

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

  const handleDeleteSelected = async () => {
    if (selectedRows.length === 0) {
      alert("등록 취소할 선생님을 선택해주세요.");
      return;
    }
  
    const token = localStorage.getItem('token');
    const errors = [];
  
    for (const teacherId of selectedRows) {
      const requestDTO = { teacherPK: teacherId };
  
      try {
        const response = await axios.put(
          `${process.env.REACT_APP_API_BASE_URL}/api/teacher/out`,
          requestDTO,
          {
            headers: {
              Authorization: `Bearer ${token}`,
            },
          }
        );
        console.log(`선생님 ID ${teacherId} 등록 취소 성공`);
      } catch (error) {
        console.error(`선생님 ID ${teacherId} 등록 취소 실패`, error);
        errors.push(teacherId); // 실패한 ID를 저장
      }
    }
  
    if (errors.length > 0) {
      alert(
        `등록 취소에 실패한 선생님 ID: ${errors.join(', ')}. 다시 시도해주세요.`
      );
    } else {
      alert("선택된 선생님 등록이 모두 취소되었습니다.");
    }
  
    // 성공적으로 처리된 선생님을 UI에서 제거
    setTeachers((prevTeachers) =>
      prevTeachers.filter((teacher) => !selectedRows.includes(teacher.teacher_id))
    );
    setSelectedRows([]); // 선택된 행 초기화
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
        <Button variant="contained" color="error" onClick={handleDeleteSelected}>선택된 선생님 등록취소</Button>
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
        disableRowSelectionOnClick={true}
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
