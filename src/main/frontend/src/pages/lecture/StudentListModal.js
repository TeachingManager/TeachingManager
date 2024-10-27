import React, {useState, useEffect} from 'react';
import { Box, TextField, Avatar, Checkbox, Button, Modal, Typography } from '@mui/material';
import { DataGrid } from '@mui/x-data-grid';
import InputLabel from '@mui/material/InputLabel';
import MenuItem from '@mui/material/MenuItem';
import FormControl from '@mui/material/FormControl';
import Select from '@mui/material/Select';
import { getStudent } from '../../api/student';
import { enrollLecture } from '../../api/lecture';

export default function StudentListModal({ open, handleClose, selectedLectureId}) {
  // 연도와 월을 저장하는 변수
  const months = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12];
  const years = [2024, 2025, 2026];

  const colors = ['#f44336', '#3f51b5', '#4caf50', '#ff9800', '#9c27b0'];
  // 연도와 월을 설정하는 변수
  const [year, setYear] = useState('');
  const [month, setMonth] = useState('');
  // 학생 관련 변수
  const [studentList, setStudentList] = useState([])
  const [selectedStudentIds, setSelectedStudentIds] = useState([])
  const handleYearChange = (event)=> {
    setYear(event.target.value)
  }
  const handleMonthChange = (event)=> {
    setMonth(event.target.value)
  }
  // 정보 초기화
  const clearInfo = ()=> {
    setYear(null)
    setMonth(null)
    setSelectedStudentIds([])
  }
  // 모든 학생 조회
  useEffect(() => {
    const fetchStudents = async () => {
      try {
        // getStudent는 학생들의 리스트를 반환한다.
        const result = await getStudent();
        setStudentList(result)
      } catch (error) {
        console.error(error);
      }
    };

    fetchStudents();
  }, []);

  // 체크 박스 클릭
  const handleToggle = (id) => {
    setSelectedStudentIds((prev) =>
      prev.includes(id) ? prev.filter((studentId) => studentId !== id) : [...prev, id]
    );
  };
  
  const columns = [
    {
      field: 'checkbox',
      headerName: '',
      width: 70,
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

  const handleToggleStudent = (studentId) => {
    setSelectedStudentIds((prevSelectedIds) => {
      if (prevSelectedIds.includes(studentId)) {
        return prevSelectedIds.filter((id) => id !== studentId);
      } else {
        return [...prevSelectedIds, studentId];
      }
    });
  };


  const rows = studentList.map((student) => ({
    id: student.id,
    name: student.name,
    age: student.age,
  }));

  // 강의 개설 관련

  const requestOpenLecture = async ()=> {
    
    if (!isFormValid()) {
      alert("연도와 월을 선택하고 최소 한 명의 학생을 선택해야 합니다.");
      return;
    }

    const selectedIds = selectedStudentIds.map((studentId) => studentId);

    const openLectureDTO = {
      lecture_id : selectedLectureId,
      year : year,
      month: month,
      studentIdList : selectedIds
    }
    
    const requestEnroll = await enrollLecture(openLectureDTO);

    if(requestEnroll.isValid == true){
      alert('강의가 개설되었습니다!')

      handleClose()
      clearInfo()
    }
    else{
      alert('이미 개설된 강의입니다.')

      handleClose()
      clearInfo()
    }

    




  }

  const isFormValid = () => {
    return year !== null && month !== null && selectedStudentIds.length > 0;
  };






  return (
    <Modal
      open={open}
      onClose={handleClose}
      aria-labelledby="modal-modal-title"
      aria-describedby="modal-modal-description"
      handleToggle = {handleToggleStudent}
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
            개설 연도와 월을 선택해주세요.
        </Typography>
      
        <Box display="flex" gap={1}>
          <FormControl sx={{ width: '50%' }}>
            <InputLabel id="year-label">연도</InputLabel>
            <Select
              labelId="year-label"
              id="year-select"
              value={year}
              label="연도"
              onChange={handleYearChange}
            > 
              {years.map(year => (
                <MenuItem key={year} value={year}>{year}년</MenuItem>
              ))}
            </Select>
          </FormControl>

          <FormControl sx={{ width: '50%' }}>
            <InputLabel id="month-label">월</InputLabel>
            <Select
              labelId="month-label"
              id="month-select"
              value={month}
              label="월"
              onChange={handleMonthChange}
            >
              {months.map(month => (
                <MenuItem key = {month} value = {month}>{month}월</MenuItem>
              ))}
            </Select>
          </FormControl>
        </Box>



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
                {rows
                  .filter((s) => selectedStudentIds.includes(s.id)) // Only show selected students
                  .map((s, index) => (
                    <Avatar
                      key={s.id}
                      sx={{
                        fontSize: 14,
                        width: 50,
                        height: 50,
                        backgroundColor: colors[index % colors.length],
                        color: '#fff',
                      }}
                    >
                      {s.name}
                    </Avatar>
                  ))}
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
          <Button onClick = {requestOpenLecture}
            sx={{
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
