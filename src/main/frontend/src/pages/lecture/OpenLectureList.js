import React, { useState, useEffect } from 'react';
import { Box, IconButton, Button, Stack, TextField, Modal, Typography, Select, MenuItem, FormControl, InputLabel, Checkbox, ListItemText } from '@mui/material';
import { DataGrid } from '@mui/x-data-grid';
import { TimePicker } from '@mui/x-date-pickers/TimePicker';
import { LocalizationProvider } from '@mui/x-date-pickers/LocalizationProvider';
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs';
import dayjs from 'dayjs';
import { useRecoilState } from 'recoil';
import { useNavigate } from 'react-router-dom';
import BookIcon from '@mui/icons-material/Book';
import { fetchLectureByMonth, getOneLecture } from '../../api/lecture';
import { teacherListState, lectureListState } from '../../common/Auth/recoilAtom';
import { getTeachersInfo } from '../../api/institute';
import { selectedDateState } from '../../common/Auth/recoilAtom';
import HowToRegIcon from '@mui/icons-material/HowToReg';
import { CourseModal } from './CourseModal';

export default function OpenLectureList() {
    const navigate = useNavigate();
    const [teachers, setTeachers] = useRecoilState(teacherListState);
    const [selectedDate, setSelectedDate] = useRecoilState(selectedDateState);
  // const [rows, setRows] = useState([
  //   { id: 1, category: '수학', name: '기초 수학', teacher: '홍길동', day: '월', time: '10:00-11:30', fee: '100000' },

  // ]); 지워도됨




  const [lectureIdList, setLectureIdList] = useState([])
  const [lectureList, setLectureList] = useState([])


  useEffect(() => {
    const fetchLectureListByMonth = async () => {
      try {
        const fetchResult = await fetchLectureByMonth({
          "month" : selectedDate.month() + 1,
           "year" : selectedDate.year()
        });
        // fetchResult는 강의 리스트의 배열을 반환함.
        console.log(fetchResult)

        // 해당 월에 해당하는 강의 id 리스트
        const ids = fetchResult.map((r) => r.lecture_id)
        setLectureIdList(ids)



        const lectures = await Promise.all(
          ids.map((id) => getOneLecture(id))
        )
        setLectureList(lectures);
        console.log(lectures)
      } catch (error) {
        console.error(error);
      }
    };

    const fetchTeachers = async () => {
      try {
        const fetchResult = await getTeachersInfo();
        const teacherdata = fetchResult.data.teacherList;
        setTeachers(teacherdata);
        console.log(teacherdata)
      } catch (error) {
        console.error(error);
      }
    };




    fetchLectureListByMonth();
    fetchTeachers();

    console.log('wait');
    console.log(selectedDate)


  }, [selectedDate]);











  const [open, setOpen] = useState(false);
  const [isEditing, setIsEditing] = useState(false);
  const [newLecture, setNewLecture] = useState({
    id: '',
    subject: '',
    name: '',
    teacher: '',
    day: [],
    time: [null, null],
    fee: ''
  });

  const [selectedRows, setSelectedRows] = useState([]);

  const handleClose = () => {
    setOpen(false);
  }
  // const handleClose = () => {
  //   setOpen(false);
  //   setIsEditing(false);
  //   setNewLecture({
  //     id: '',
  //     subject: '',
  //     name: '',
  //     teacher: '',
  //     day: [],
  //     time: [null, null],
  //     fee: ''
  //   });
  // };

  const handleChange = (e) => {
    const { name, value } = e.target;
    setNewLecture(prevState => ({
      ...prevState,
      [name]: value
    }));
  };

  const handleDayChange = (event) => {
    const { target: { value } } = event;
    setNewLecture(prevState => ({
      ...prevState,
      day: typeof value === 'string' ? value.split(',') : value
    }));
  };

  const handleTimeChange = (index, newValue) => {
    setNewLecture(prevState => {
      const updatedTime = [...prevState.time];
      updatedTime[index] = newValue;
      return {
        ...prevState,
        time: updatedTime
      };
    });
  };

  const formatTimeRange = (timeRange) => {
    return timeRange.map(time => time ? time.format('HH:mm') : '').join('-');
  };

  const handleRowDoubleClick = (params) => {
    const lecture = { 
      ...params.row, 
      time: params.row.time.split('-').map(time => dayjs(`1970-01-01T${time}:00`)),
      day: params.row.day.split(', ')
    };
    setNewLecture(lecture);
    setIsEditing(true);
    setOpen(true);
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

  const daysOfWeek = ['월', '화', '수', '목', '금', '토', '일'];

  const formatTimeForDisplayMultiLine = (convertedTime) => {
    if (!Array.isArray(convertedTime)) {
      return '';  // convertedTime이 배열이 아니면 빈 문자열 반환
    }
    return convertedTime.map(item => `${item.day}: ${item.time}`).join('\n');
  };

  return (
    <LocalizationProvider dateAdapter={AdapterDayjs}>
      <Box sx={{ height: 600}}>
        <Button onClick={()=> {
            navigate("/lecture")
        }}>강의 개설</Button>
        <Button>선택 강의 폐강</Button>
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
            },
            { field: 'fee', headerName: '수강료', width: 130, headerAlign: 'center',
              renderCell: (params) => (
                <div style={{ display: 'flex', alignItems: 'center', justifyContent: 'center', width: '100%', height: '100%' }}>
                  {params.value}
                </div>
              )
             },
            {
                field: 'attendance',
                headerName: '출석현황',
                headerAlign: 'center',
                width: 150,
                renderCell: (params) => {
                  const handleClick = () => {
                    const lectureId = params.row.id;
                    const year = selectedDate.year();
                    const month = selectedDate.month() + 1; // month는 0부터 시작하므로 +1
                  
                    // URL에 lectureId와 year, month를 파라미터로 추가
                    navigate(`/openlecture/attendance/${lectureId}?year=${year}&month=${month}`);
                  };
                  
    
                  return (
                    <div
                    style={{
                      display: 'flex',
                      alignItems: 'center',
                      justifyContent: 'center',
                      width: '100%',
                      height: '100%',
                    }}
                  >
                    <IconButton onClick={handleClick}>
                      <BookIcon />
                    </IconButton>
                    </div>
                  );
                },
              },{
                field : 'courseAdjustment',
                headerName: '수강 정정',
                headerAlign: 'center',
                width: 150,
                renderCell: (params) => {
                  return(
                    <div
                    style={{
                      display: 'flex',
                      alignItems: 'center',
                      justifyContent: 'center',
                      width: '100%',
                      height: '100%',
                    }}>
                      <IconButton onClick={()=>setOpen(true)}>
                        <HowToRegIcon/>
                      </IconButton>
                      <CourseModal open={open} handleClose={handleClose} lectureId={params.row.id} month = {selectedDate.month() + 1} year = {selectedDate.year()} />
                    </div>
                  )
                }

              }
          ]}
          rows={lectureList}
          pageSize={5}
          rowsPerPageOptions={[5]}
          checkboxSelection
          onRowSelectionModelChange={(newSelectionModel) => {
            setSelectedRows(newSelectionModel);
          }}
          onRowDoubleClick={handleRowDoubleClick}
          getRowHeight={() => 'auto'}
        />




















        {/* <Modal
          open={open}
          onClose={handleClose}
        >
          <Box sx={modalStyle}>
            <Typography variant="h6" component="h2">{isEditing ? '강의 정보 수정' : '강의 정보 입력'}</Typography>
            <Stack spacing={2} mt={2}>
              <TextField label="과목" name="subject" value={newLecture.subject} onChange={handleChange} />
              <TextField label="강의명" name="name" value={newLecture.name} onChange={handleChange} />
              <TextField label="담당선생님" name="teacher" value={newLecture.teacher} onChange={handleChange} />
              <FormControl>
                <InputLabel>요일</InputLabel>
                <Select
                  multiple
                  value={newLecture.day}
                  onChange={handleDayChange}
                  renderValue={(selected) => selected.join(', ')}
                >
                  {daysOfWeek.map((day) => (
                    <MenuItem key={day} value={day}>
                      <Checkbox checked={newLecture.day.indexOf(day) > -1} />
                      <ListItemText primary={day} />
                    </MenuItem>
                  ))}
                </Select>
              </FormControl>
              <TimePicker
                label="시작 시간"
                value={newLecture.time[0]}
                onChange={(newValue) => handleTimeChange(0, newValue)}
                renderInput={(params) => <TextField {...params} />}
              />
              <TimePicker
                label="종료 시간"
                value={newLecture.time[1]}
                onChange={(newValue) => handleTimeChange(1, newValue)}
                renderInput={(params) => <TextField {...params} />}
              />
              <TextField label="수강료" name="fee" value={newLecture.fee} onChange={handleChange} />
              {isEditing ? (
                <Button variant="contained" onClick={()=>{}}>수정</Button>
              ) : (
                <Button variant="contained" onClick={()=>{}}>추가</Button>
              )}
            </Stack>
          </Box>
        </Modal> */}
      </Box> 
    </LocalizationProvider>
  );
}
