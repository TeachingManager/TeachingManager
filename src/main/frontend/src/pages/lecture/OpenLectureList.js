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
    const [selectedLecture, setSelectedLecture] = useState(null); // 선택된 강의 상태 추가
    const [isModalOpen, setIsModalOpen] = useState(false); // 모달 열림 상태 추가

    const handleOpenModal = (lecture) => {
      setSelectedLecture(lecture); // 선택된 강의 정보 저장
      setIsModalOpen(true); // 모달 열림 상태로 설정
  };
  
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


        console.log(ids)
        const lectures = await Promise.all(
          ids.map((id) => getOneLecture(id))
        )
        if(lectures === false)
          setLectureList([])
        else
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


  }, [selectedDate, isModalOpen]);


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

  const handleCloseModal = () => {
    setIsModalOpen(false);
    setSelectedLecture(null); // 선택된 강의 정보 초기화
};


  return (
    <LocalizationProvider dateAdapter={AdapterDayjs}>
      <Box sx={{ height: 600}}>
        <Button onClick={()=> {
            navigate("/lecture")
        }}>강의 개설</Button>
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
                      <IconButton onClick={() => handleOpenModal(params.row)}>
                       <HowToRegIcon/>
                      </IconButton>
                      {selectedLecture && (
                      <CourseModal
                          open={isModalOpen}
                          handleClose={handleCloseModal}
                          lectureId={selectedLecture.id}
                          month={selectedDate.month() + 1}
                          year={selectedDate.year()}
                      />
                  )}                    </div>
                  )
                }

              }
          ]}
          rows={lectureList}
          pageSize={5}
          rowsPerPageOptions={[5]}
          //checkboxSelection
          onRowSelectionModelChange={(newSelectionModel) => {
            setSelectedRows(newSelectionModel);
          }}
          getRowHeight={() => 'auto'}
          columnVisibilityModel={{
            id: false, // ID 열을 숨김
          }}
        />
      </Box> 
    </LocalizationProvider>
  );
}
