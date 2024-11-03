import React, { useState, useEffect } from 'react';
import { Button, Box, Stack, IconButton, TextField, Modal, Typography, Checkbox, FormGroup, FormControlLabel } from '@mui/material';
import { DataGrid } from '@mui/x-data-grid';
import { TimePicker } from '@mui/x-date-pickers/TimePicker';
import { LocalizationProvider } from '@mui/x-date-pickers/LocalizationProvider';
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs';
import dayjs from 'dayjs';
import MenuBookIcon from '@mui/icons-material/MenuBook';
import StudentListModal from './StudentListModal';
import MenuItem from '@mui/material/MenuItem';
import InputLabel from '@mui/material/InputLabel';
import FormControl from '@mui/material/FormControl';
import Select from '@mui/material/Select';
import { teacherListState, lectureListState } from '../../common/Auth/recoilAtom';
import { useRecoilState } from 'recoil';
import { getTeachersInfo } from '../../api/institute';
import { addLecture, getLecture, deleteLecture, changeLecture } from '../../api/lecture';

export default function LectureList() {
  const [rows, setRows] = useState([
    { id: 1, subject: '수학', name: '기초 수학', teacher: '홍길동', time: 'MONDAY-11:00~12:00,TUESDAY-13:00~14:00', fee: '100000' },
    { id: 2, subject: '영어', name: '기초 영어', teacher: '김철수', time: 'TUESDAY-13:00~14:30,THURSDAY-15:00~17:00', fee: '120000' }
  ]);
  const [teachers, setTeachers] = useRecoilState(teacherListState);
  const [lectures, setLectures] = useRecoilState(lectureListState);
  const [selectedLectureId, setSelectedLectureId] = useState(null);

  const [open, setOpen] = useState(false);
  const [isEditing, setIsEditing] = useState(false);
  const [newLecture, setNewLecture] = useState({
    id: '',
    subject: '',
    name: '',
    teacher: '',
    time: {}, // 요일별 시간을 저장하는 객체
    fee: '',
    grade: ''
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

    const fetchLectures = async () => {
      try {
        const fetchResult = await getLecture();

        setLectures(fetchResult)
        
      } catch(error){
        console.error(error);
      }
    }



    fetchTeachers();
    fetchLectures();


  }, []);


  const handleTeacherChange = (event) => {
    setNewLecture(prevState => ({
      ...prevState,
      teacher: event.target.value
    }));
  };

  const handleOpen = () => setOpen(true);
  const handleClose = () => {
    setOpen(false);
    setIsEditing(false);
    setNewLecture({
      id: '',
      subject: '',
      name: '',
      teacher: '',
      time: {},
      fee: '',
      grade: ''
    });
  };

  const handleChange = (e) => {
    const { name, value } = e.target;
    setNewLecture(prevState => ({
      ...prevState,
      [name]: value
    }));
  };

  // 요일 선택 시 해당 요일을 추가/제거
  const handleDayChange = (day) => {
    setNewLecture(prevState => {
      const newTime = { ...prevState.time };
      if (newTime[day]) {
        delete newTime[day]; // 선택 해제 시 요일 제거
      } else {
        newTime[day] = { startTime: null, endTime: null }; // 선택 시 요일 추가
      }
      return { ...prevState, time: newTime };
    });
  };

  // 시간을 'MONDAY-12:30~13:20' 형식으로 변환하여 저장
  const handleTimeChange = (day, startTime, endTime) => {
    setNewLecture(prevState => ({
      ...prevState,
      time: {
        ...prevState.time,
        [day]: { startTime, endTime }
      }
    }));
  };

 // 시간을 문자열로 변환하여 저장
const formatTimeForDisplay = (time) => {
  // time 객체의 키를 daysOfWeek 배열에 따라 정렬
  const sortedTime = Object.entries(time).sort(
    ([dayA], [dayB]) => daysOfWeek.findIndex(day => day.english === dayA) - daysOfWeek.findIndex(day => day.english === dayB)
  );

  return sortedTime
    .map(([day, times]) => {
      const startTime = times.startTime ? times.startTime.format('HH:mm') : '';
      const endTime = times.endTime ? times.endTime.format('HH:mm') : '';
      return `${day.toUpperCase()}-${startTime}~${endTime}`;
    })
    .join(',');
};


  const handleAddLecture = async () => {
    
    const daysOfWeekMap = {
      MONDAY: '월요일',
      TUESDAY: '화요일',
      WEDNESDAY: '수요일',
      THURSDAY: '목요일',
      FRIDAY: '금요일',
      SATURDAY: '토요일',
      SUNDAY: '일요일'
    };
    
    const convertTimeStringToArray = (timeString) => {
      if (!timeString) return [];
    
      return timeString.split(',').map((item) => {
        const [day, timeRange] = item.split('-'); // 요일과 시간 구분
        const koreanDay = daysOfWeekMap[day]; // 영어 요일을 한글로 변환
        return {
          day: koreanDay || day, // 변환되지 않으면 원래 요일 사용
          time: timeRange // 시간 범위를 그대로 사용
        };
      });
    };
    
    
    
    
    const lectureWithFormattedTime = {
      ...newLecture,
      time: formatTimeForDisplay(newLecture.time)
    };
  
    const result = await addLecture(lectureWithFormattedTime);

    if (result.isValid === true) {
      alert("강의가 추가 되었습니다!");
  
      // 새 강의에 고유한 ID 추가
      setLectures(prevLectures => [
        ...prevLectures,
        { ...lectureWithFormattedTime, category: lectureWithFormattedTime.subject, teacherId: lectureWithFormattedTime.teacher, convetedTime : convertTimeStringToArray(result.response.data.time), id: result.response.data.lecture_id  }  // 고유한 ID 부여
      ]);
      
      setOpen(false);  // 모달 닫기
    } else {
      alert("강의 추가에 실패하였습니다.");
      setOpen(false);
    }
  };
  

  const handleEditLecture = async () => {
    // 영어 요일을 한글로 변환하는 맵
    const daysOfWeekMap = {
      MONDAY: '월요일',
      TUESDAY: '화요일',
      WEDNESDAY: '수요일',
      THURSDAY: '목요일',
      FRIDAY: '금요일',
      SATURDAY: '토요일',
      SUNDAY: '일요일'
    };
  
    // 시간 문자열을 배열로 변환하는 함수
    const convertTimeStringToArray = (timeString) => {
      if (!timeString) return [];
  
      return timeString.split(',').map((item) => {
        const [day, timeRange] = item.split('-'); // 요일과 시간 구분
        const koreanDay = daysOfWeekMap[day]; // 영어 요일을 한글로 변환
        return {
          day: koreanDay || day, // 변환되지 않으면 원래 요일 사용
          time: timeRange // 시간 범위를 그대로 사용
        };
      });
    };
  
    // 새로운 강의 정보를 포맷팅
    const lectureWithFormattedTime = {
      ...newLecture,
      time: formatTimeForDisplay(newLecture.time) // 시간을 포맷팅
    };
  
    const pk = newLecture.id; // primary key (강의의 고유 ID)
  
    try {
      // ChangeLecture 함수에 pk와 lectureWithFormattedTime 전달
      const result = await changeLecture(lectureWithFormattedTime, pk); // pk 추가
  
      if (result.status === 200) {
        alert("강의가 수정되었습니다!");
  
        // 강의 목록을 업데이트
        setLectures(prevRows =>
          prevRows.map(row =>
            row.id === newLecture.id
              ? {
                  ...lectureWithFormattedTime,
                  category: lectureWithFormattedTime.subject,
                  teacherId: lectureWithFormattedTime.teacher,
                  convetedTime: convertTimeStringToArray(result.data.time) // 시간 변환
                }
              : row
          )
        );
  
        setOpen(false);  // 모달 닫기
      } else {
        alert("강의 수정에 실패하였습니다.");
        setOpen(false);
      }
    } catch (error) {
      console.error("Error editing lecture:", error);
      alert("강의 수정 중 오류가 발생했습니다.");
      setOpen(false);
    }
  };
  


  const handleDeleteSelected = async () => {
    if (selectedRows.length === 0) {
      alert('삭제할 강의를 선택해주세요.');
      return;
    }
  
    const confirmed = window.confirm('정말로 선택된 강의를 삭제하시겠습니까?');
    if (!confirmed) return;
  
    try {
      for (let id of selectedRows) {
        await deleteLecture(id); // Call the API to delete the lecture
      }
  
      // Update the lectures state after deletion
      setLectures(prevLectures => prevLectures.filter(lecture => !selectedRows.includes(lecture.id)));
      setSelectedRows([]); // Clear the selected rows after deletion
      alert('선택된 강의가 삭제되었습니다.');
    } catch (error) {
      console.error('강의 삭제에 실패했습니다:', error);
      alert('강의 삭제에 실패했습니다.');
    }
  };
  


  const handleRowDoubleClick = (params) => {
    const data = params.row;
    setNewLecture({
      id: data.id,
      subject: data.category,
      name: data.name,
      teacher: '',
      time: {},
      fee: data.fee,
      grade: data.grade
    });
    // Set the modal to editing mode and open it
    setIsEditing(true);
    handleOpen();
  };
  
  
  
  
  const modalStyle = {
    position: 'absolute',
    top: '50%',
    left: '50%',
    transform: 'translate(-50%, -50%)',
    width: 500,
    bgcolor: 'background.paper',
    border: '2px solid #000',
    boxShadow: 24,
    p: 4,
  };

  const daysOfWeek = [
    { english: 'Monday', korean: '월' },
    { english: 'Tuesday', korean: '화' },
    { english: 'Wednesday', korean: '수' },
    { english: 'Thursday', korean: '목' },
    { english: 'Friday', korean: '금' },
    { english: 'Saturday', korean: '토' },
    { english: 'Sunday', korean: '일' }
  ];
  

  const [isStudentModalOpen, setIsStudentModalOpen] = useState(false);

  const handleOpenStudentModal = (lectureId) => {
    setSelectedLectureId(lectureId)
    setIsStudentModalOpen(true);
  };

  const handleCloseStudentModal = () => {
    setIsStudentModalOpen(false);
  };

  const students = [
    { id: 1, name: '홍길동', age: 20 },
    { id: 2, name: '김철수', age: 15 },
    { id: 3, name: '아이유', age: 28 }
  ];

  const [selectedStudentIds, setSelectedStudentIds] = useState([]);

  const areFieldsFilled = () => {
    const { subject, name, teacher, fee, time } = newLecture;

    // 시간 정보가 있는지 확인
    const areTimesValid = Object.values(time).every(
      ({ startTime, endTime }) => startTime && endTime
    );

    return subject && name && teacher && fee && areTimesValid;
  };

  const formatTimeForDisplayMultiLine = (convertedTime) => {
    if (!Array.isArray(convertedTime)) {
      return '';  // convertedTime이 배열이 아니면 빈 문자열 반환
    }
    return convertedTime.map(item => `${item.day}: ${item.time}`).join('\n');
  };
  
  

  return (
    <LocalizationProvider dateAdapter={AdapterDayjs}>
      <Box sx={{ height: 600, mt: 6 }}>
        <Stack direction="row" spacing={2} mb={2}>
          <Button variant="contained" onClick={handleOpen}>강의 추가하기</Button>
          <Button variant="contained" color="error" onClick={handleDeleteSelected}>선택된 강의 삭제</Button>
        </Stack>
        <DataGrid
          columns={[
            { field: 'id', headerName: 'ID', width: 70, align: 'center', headerAlign: 'center', 
              renderCell: (params) => (
                <div style={{ display: 'flex', alignItems: 'center', justifyContent: 'center', width: '100%', height: '100%' }}>
                  {params.value}
                </div>
              )
             },
            { field: 'category', headerName: '과목', width: 90, align: 'center', headerAlign: 'center',
              renderCell: (params) => (
                <div style={{ display: 'flex', alignItems: 'center', justifyContent: 'center', width: '100%', height: '100%' }}>
                  {params.value}
                </div>
              )
             },
            { field: 'name', headerName: '강의명', width: 130, align: 'center', headerAlign: 'center',
              renderCell: (params) => (
                <div style={{ display: 'flex', alignItems: 'center', justifyContent: 'center', width: '100%', height: '100%' }}>
                  {params.value}
                </div>
              )
             },
            { field: 'grade', headerName: '학년', width: 90 , align: 'center', headerAlign: 'center',
              renderCell: (params) => (
                <div style={{ display: 'flex', alignItems: 'center', justifyContent: 'center', width: '100%', height: '100%' }}>
                  {params.value}
                </div>
              )
            },
            { field: 'teacherId', headerName: '담당선생님', width: 90, headerAlign: 'center',
              renderCell: (params) => {
                const teacher = teachers.find((t) => t.teacher_id === params.value);
                return (
                  <div style={{ display: 'flex', alignItems: 'center', justifyContent: 'center', width: '100%', height: '100%' }}>
                    {teacher ? teacher.teacher_name : 'Unknown'}
                  </div>
                );
              },
            },
            {
              field: 'convetedTime',
              headerName: '시간',
              width: 300,
              headerAlign: 'center',
              renderCell: (params) => (
                <div style={{ display: 'flex', alignItems: 'center', justifyContent: 'center', width: '100%', height: '100%' }}>
                  <Typography style={{ whiteSpace: 'pre-line', lineHeight: 2, textAlign: 'center' }}>
                    {params.value ? formatTimeForDisplayMultiLine(params.value) : '시간 정보 없음'}
                  </Typography>
                </div>
              )
            }
            ,
            { field: 'fee', headerName: '수강료', width: 130, headerAlign: 'center',
              renderCell: (params) => (
                <div style={{ display: 'flex', alignItems: 'center', justifyContent: 'center', width: '100%', height: '100%' }}>
                  {params.value}
                </div>
              )
             },
            {
              field: 'openlecture',
              headerName: '강의 개설',
              width: 150,
              headerAlign: 'center',
              renderCell: (params) => (
                <div style={{ display: 'flex', alignItems: 'center', justifyContent: 'center', width: '100%', height: '100%' }}>
                  <IconButton onClick={
                    ()=> handleOpenStudentModal(params.row.id)
                  }>
                    <MenuBookIcon />
                  </IconButton>
                </div>
              )
            }
          ]}
          rows={lectures}
          pageSize={5}
          rowsPerPageOptions={[5]}
          checkboxSelection
          disableRowSelectionOnClick={true}  
          onRowSelectionModelChange={(newSelectionModel) => setSelectedRows(newSelectionModel)}
          onRowDoubleClick={handleRowDoubleClick}
          getRowHeight={() => 'auto'}
          columnVisibilityModel={{
            id: false, // ID 열을 숨김
          }}
        />
        <Modal
          open={open}
          onClose={handleClose}
        >
          <Box sx={modalStyle}>
            <Typography variant="h6" component="h2">{isEditing ? '강의 정보 수정' : '강의 정보 입력'}</Typography>
            <Stack spacing={2} mt={2}>
              <TextField label="과목" name="subject" value={newLecture.subject} onChange={handleChange} />
              <TextField label="강의명" name="name" value={newLecture.name} onChange={handleChange} />
              <TextField label="학년" name="grade" value={newLecture.grade} onChange={handleChange} />
              <FormControl fullWidth>
                <InputLabel id="demo-simple-select-label">Teacher</InputLabel>
                <Select
                  labelId="demo-simple-select-label"
                  id="demo-simple-select"
                  value={newLecture.teacher}
                  label="Teacher"
                  onChange={handleTeacherChange}
                >
                  {teachers.map((teacher) => (
                    <MenuItem key={teacher.teacher_id} value={teacher.teacher_id}>
                      {teacher.teacher_name}
                    </MenuItem>
                  ))}
                </Select>
              </FormControl>

              {/* 요일 선택을 위한 체크박스 */}
              <FormGroup row>
                {daysOfWeek.map(({ english, korean }) => (
                  <FormControlLabel
                    key={english}
                    control={
                      <Checkbox
                        checked={!!newLecture.time[english]}
                        onChange={() => handleDayChange(english)}
                      />
                    }
                    label={korean}
                  />
                ))}
              </FormGroup>

              {/* 선택된 요일의 시간 선택 */}
                {Object.keys(newLecture.time)
                  .sort((a, b) => daysOfWeek.findIndex(day => day.english === a) - daysOfWeek.findIndex(day => day.english === b)) // 요일 순서에 맞게 정렬
                  .map((englishDay) => {
                    const { korean } = daysOfWeek.find(({ english }) => english === englishDay); // 영어 요일을 한글로 변환

                    return (
                      <Box key={englishDay}>
                        <Typography variant="body2">{korean}</Typography> {/* 한글 요일 출력 */}
                        <Stack direction="row" spacing={3}>
                          <TimePicker
                            label={`${korean} 시작 시간`}  // 한글 요일로 시작 시간 표시
                            value={newLecture.time[englishDay]?.startTime || null}
                            onChange={(newValue) => handleTimeChange(englishDay, newValue, newLecture.time[englishDay]?.endTime || null)}
                            slotProps={{ textField: { fullWidth: true } }}
                          />
                          <TimePicker
                            label={`${korean} 종료 시간`}  // 한글 요일로 종료 시간 표시
                            value={newLecture.time[englishDay]?.endTime || null}
                            onChange={(newValue) => handleTimeChange(englishDay, newLecture.time[englishDay]?.startTime || null, newValue)}
                            slotProps={{ textField: { fullWidth: true } }}
                          />
                        </Stack>
                      </Box>
                    );
                  })}

              <TextField label="수강료" name="fee" value={newLecture.fee} onChange={handleChange} />
              {isEditing ? (
                <Button variant="contained" onClick={handleEditLecture} disabled={!areFieldsFilled()} >수정</Button>
              ) : (
                <Button variant="contained" onClick={handleAddLecture} disabled={!areFieldsFilled()}>추가</Button>
              )}
            </Stack>
          </Box>
        </Modal>

        <StudentListModal
          open={isStudentModalOpen}
          handleClose={handleCloseStudentModal}
          students={students}
          selectedStudentIds={selectedStudentIds}
          selectedLectureId={selectedLectureId}
        />
      </Box>
    </LocalizationProvider>
  );
}
