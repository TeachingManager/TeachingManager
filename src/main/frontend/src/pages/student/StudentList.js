import React, { useEffect, useState } from 'react';
import { Button, Box, Stack, TextField, Modal, Typography, IconButton } from '@mui/material';
import { DataGrid } from '@mui/x-data-grid';
import { useNavigate } from 'react-router-dom'; // useNavigate 임포트
import BookIcon from '@mui/icons-material/Book';
import { addStudent, getStudent, deleteStudent, changeStudent } from '../../api/student';
import { useRecoilState } from 'recoil';
import { studentListState } from '../../common/Auth/recoilAtom';
import { useAuth } from '../../common/Auth/AuthProvider';
export default function StudentList() {
  const [students, setStudents] = useRecoilState(studentListState);
  const {logout} = useAuth();
  useEffect(() => {
    const fetchStudents = async () => {
      try {
        const student = await getStudent();
        console.log(student);
        setStudents(student);
      } catch (error) {
        console.error(error);
      }
    };

    fetchStudents();
  }, []);

  useEffect(() => {
    console.log('Updated students:', students);
  }, [students]);

  const [open, setOpen] = useState(false);
  const [isEditing, setIsEditing] = useState(false);
  const [newStudent, setNewStudent] = useState({
    id: '',
    name: '',
    age: '',
    grade: '',
    phoneNumber: '',
    parentName: '',
    parentNumber: '',
    gender: '',
    level: ''
  });

  const [selectedRows, setSelectedRows] = useState([]);
  const navigate = useNavigate(); // useNavigate 훅 사용

  const handleOpen = () => setOpen(true);
  const handleClose = () => {
    setOpen(false);
    setIsEditing(false);
    setNewStudent({
      id: '',
      name: '',
      age: '',
      grade: '',
      phoneNumber: '',
      parentName: '',
      parentNumber: '',
      gender: '',
      level: ''
    });
  };

  const handleChange = (e) => {
    const { name, value } = e.target;
    setNewStudent(prevState => ({
      ...prevState,
      [name]: value
    }));
  };

  const handleAddStudent = async () => {
    const studentData = {
      name: newStudent.name,
      age: parseInt(newStudent.age),
      grade: parseInt(newStudent.grade),
      phoneNumber: newStudent.phoneNumber,
      parentName: newStudent.parentName,
      parentNumber: newStudent.parentNumber,
      gender: newStudent.gender,
      level: newStudent.level
    };
  
    console.log('추가할 학생 데이터:', studentData); // studentData가 올바른지 확인
  
    try {
      const result = await addStudent(studentData);

      if (result && result.status === 201) {
        const createdStudent = result.data;
        alert("학생이 추가되었습니다");
  
        // Recoil 상태 업데이트
        setStudents(prevStudents => [...prevStudents, createdStudent]);
  
        handleClose();
      } else {
        console.error("학생 추가에 실패하였습니다");
        alert("학생 추가에 실패하였습니다.");
      }
    } catch (error) {
      console.error("학생 추가에 실패하였습니다", error);
    }
  };

  const handleEditStudent = async () => {
    const updatedStudentData = {
      name: newStudent.name,
      age: parseInt(newStudent.age),
      grade: parseInt(newStudent.grade),
      phoneNumber: newStudent.phoneNumber,
      parentName: newStudent.parentName,
      parentNumber: newStudent.parentNumber,
      gender: newStudent.gender,
      level: newStudent.level
    };

    try {
      await changeStudent(newStudent.id, updatedStudentData);

      setStudents(prevStudents =>
        prevStudents.map(student =>
          student.id === newStudent.id ? { ...student, ...updatedStudentData } : student
        )
      );

      alert("학생 정보가 수정되었습니다.");
      handleClose();
    } catch (error) {
      console.error('학생 정보 수정에 실패했습니다:', error);
      alert('학생 정보 수정에 실패했습니다.');
    }
  };

  const handleDeleteSelected = async () => {
    if (selectedRows.length === 0) {
      alert('삭제할 학생을 선택해주세요.');
      return;
    }

    const confirmed = window.confirm('정말로 선택된 학생을 삭제하시겠습니까?');
    if (!confirmed) return;

    try {
      for (let id of selectedRows) {
        await deleteStudent(id);
      }

      setStudents(prevStudents => prevStudents.filter(student => !selectedRows.includes(student.id)));
      setSelectedRows([]);
      alert('선택된 학생이 삭제되었습니다.');
    } catch (error) {
      console.error('학생 삭제에 실패했습니다:', error);
      alert('학생 삭제에 실패했습니다.');
    }
  };

  const handleRowDoubleClick = (params) => {
    setNewStudent(params.row);
    setIsEditing(true);
    handleOpen();
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

  const isFormValid = () => {
    const { id, ...fields } = newStudent;
    return Object.values(fields).every(field => field.trim() !== '');
  };

  return (
    <Box sx={{ height: 600, mt: 6 }}>
      <Stack direction="row" spacing={2} mb={2}>
        <Button variant="contained" onClick={handleOpen}>학생 추가하기</Button>
        <Button variant="contained" color="error" onClick={handleDeleteSelected}>선택된 학생 삭제</Button>
      </Stack>
      <DataGrid
  columns={[
    { field: 'id', headerName: 'ID', width: 70, align: 'center', headerAlign: 'center'  },
    { field: 'name', headerName: '이름', width: 130, align: 'center', headerAlign: 'center' },
    { field: 'age', headerName: '나이', width: 90, align: 'center', headerAlign: 'center' },
    { field: 'grade', headerName: '학년', width: 90, align: 'center', headerAlign: 'center' },
    { field: 'phoneNumber', headerName: '전화번호', width: 130, align: 'center', headerAlign: 'center' },
    { field: 'parentName', headerName: '보호자명', width: 130, align: 'center', headerAlign: 'center' },
    { field: 'parentNumber', headerName: '보호자 연락처', width: 130, align: 'center', headerAlign: 'center' },
    { field: 'gender', headerName: '성별', width: 90, align: 'center', headerAlign: 'center' },
    { field: 'level', headerName: '수준', width: 90, align: 'center', headerAlign: 'center' },
    {
      field: 'attendance',
      headerName: '출석현황',
      width: 150,
      align: 'center',
      headerAlign: 'center',
      renderCell: (params) => {
        const handleClick = () => {
          const studentId = params.row.id;
          navigate(`/students/attendance/${studentId}`); // useNavigate로 URL 이동
        };

        return (
          <IconButton onClick={handleClick}>
            <BookIcon />
          </IconButton>
        );
      },
    },
  ]}
  rows={students}
  pageSize={5}
  rowsPerPageOptions={[5]}
  checkboxSelection
  onRowSelectionModelChange={(newSelectionModel) => {
    setSelectedRows(newSelectionModel);
  }}
  onRowDoubleClick={handleRowDoubleClick}
  columnVisibilityModel={{
    id: false, // ID 열을 숨김
  }}
/>

      <Modal
        open={open}
        onClose={handleClose}
      >
        <Box sx={modalStyle}>
          <Typography variant="h6" component="h2">{isEditing ? '학생 정보 수정' : '학생 정보 입력'}</Typography>
          <Stack spacing={2} mt={2}>
            <TextField label="이름" name="name" value={newStudent.name} onChange={handleChange} />
            <TextField label="나이" name="age" value={newStudent.age} onChange={handleChange} />
            <TextField label="학년" name="grade" value={newStudent.grade} onChange={handleChange} />
            <TextField label="전화번호" name="phoneNumber" value={newStudent.phoneNumber} onChange={handleChange} />
            <TextField label="보호자명" name="parentName" value={newStudent.parentName} onChange={handleChange} />
            <TextField label="보호자 연락처" name="parentNumber" value={newStudent.parentNumber} onChange={handleChange} />
            <TextField label="성별" name="gender" value={newStudent.gender} onChange={handleChange} />
            <TextField label="수준" name="level" value={newStudent.level} onChange={handleChange} />
            {isEditing ? (
              <Button variant="contained" onClick={handleEditStudent}>수정</Button>
            ) : (
              <Button variant="contained" onClick={handleAddStudent} disabled={!isFormValid()}>추가</Button>
            )}
          </Stack>
        </Box>
      </Modal>
    </Box>
  );
}
