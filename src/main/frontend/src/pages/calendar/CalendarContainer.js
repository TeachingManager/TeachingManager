import React, { useEffect, useState } from 'react';
import FullCalendar from '@fullcalendar/react';
import dayGridPlugin from '@fullcalendar/daygrid';
import interactionPlugin from '@fullcalendar/interaction';
import axios from 'axios';
import { Button, Box, Stack, TextField, Modal, Typography, IconButton } from '@mui/material';
import { v4 as uuidv4 } from 'uuid';
import DeleteIcon from '@mui/icons-material/Delete';
import dayjs from 'dayjs';
import "./calendarcontainer.css"
import { setDate } from 'date-fns';

export default function CalendarContents() {
  
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
  
  const [rows, setRows] = useState([
    { id: 1, title: '미적분 수업', teacherName: '이승찬', studentNames: ['김승찬'] },
  ]);

  const [newInfo, setNewInfo] = useState({
    id: '',
    title: '',
    teacherName: '',
    studentNames: [''],
    date: '',
    startTime: '',
    endTime: ''
  });

  const [events, setEvents] = useState([]);
  const [open, setOpen] = useState(false);
  const [isEditing, setIsEditing] = useState(false);
  const [editingEvent, setEditingEvent] = useState(null);

  const [dateInfo, setDateInfo] = useState(dayjs().format('YYYY-MM-DD'))

  const handlePrev = () => {
    const newDate = dayjs(dateInfo).subtract(1,'month').format('YYYY-MM-DD');
    setDateInfo(newDate)
  }

  const handleNext = () => {
    const newDate = dayjs(dateInfo).add(1, 'month').format('YYYY-MM-DD');
    setDateInfo(newDate)
  }


  useEffect(() => {
    
    const fetchLecture = async(lecture_id) => {
      try {
        const token = localStorage.getItem("token")
        const response = await axios.get(`${process.env.REACT_APP_API_BASE_URL}/api/lectures/${lecture_id}`,{
          headers: {
            Authorization: `Bearer ${token}`, // Bearer 토큰 설정
          },
        })
        
        const teacher_id = response.data.teacherId;
        const teacher_Name = response.data.teacherName

        if(teacher_id) {
          return {teacher_id, teacher_Name}
        } else {
          console.log('선생님 id를 찾지 못하였습니다')
          return "선생님 찾기 실패"
        }
      } catch(error){
        console.error("강의 조회중 오류 발생", error)
      }
    }
    const fetchSchedule = async (dateInfo) => {
      try {
        const token = localStorage.getItem("token");
        const currentDate = new Date().toISOString().split('T')[0];

        const response = await axios.get(`${process.env.REACT_APP_API_BASE_URL}/api/Schedule`,{
          headers: {
            Authorization: `Bearer ${token}`, // Bearer 토큰 설정
          },
          params: {
            date_info: dateInfo
          }
        })

        const formatted_data = await Promise.all(response.data.scheduleList.map(async (data) => {
          const response = await fetchLecture(data.lecture_id);
          const teacher_name = response.teacher_Name
          console.log(response.teacher_id, response.teacher_Name)
          return {
            id: data.schedule_id,
            title: data.title,
            start: data.start_date,
            end: data.end_date,
            description: `선생님: ${teacher_name}`  // Adding teacher name to description
          };
        }));

        setEvents(formatted_data)
  
        console.log(response.data);
      } catch (error) {
        console.error("일정 조회 중 오류 발생", error);
      }
    }
  
    fetchSchedule(dateInfo);
  }, [dateInfo]);

  const handleOpen = () => setOpen(true);
  const handleClose = () => {
    setOpen(false);
    setIsEditing(false);
    setEditingEvent(null);
  };

  const handleDateClick = (arg) => {
    setNewInfo(prevState => ({
      ...prevState,
      date: arg.dateStr
    }));
    handleOpen();
  };

  const handleEventDrop = (arg) => {
    const updatedEvents = events.map(event => {
      if (event.id === arg.event.id) {
        return {
          ...event,
          start: arg.event.startStr,
          end: arg.event.endStr
        };
      }
      return event;
    });
    setEvents(updatedEvents);
  };

  const renderEventContent = (eventInfo) => (
    <div className="fc-event-custom-content">
      <b>{eventInfo.timeText}</b>
      <br/>
      <br/>
      <i>{eventInfo.event.title}</i>
      <p dangerouslySetInnerHTML={{ __html: eventInfo.event.extendedProps.description }}></p>
    </div>
  );

  const handleChange = (e) => {
    const { name, value } = e.target;
    if (name.startsWith("studentNames")) {
      const index = parseInt(name.split("-")[1], 10);
      setNewInfo(prevState => {
        const newStudentNames = [...prevState.studentNames];
        newStudentNames[index] = value;
        return {
          ...prevState,
          studentNames: newStudentNames
        };
      });
    } else {
      setNewInfo(prevState => ({
        ...prevState,
        [name]: value
      }));
    }
  };

  const handleAddTeacher = () => {
    const newId = rows.length > 0 ? Math.max(...rows.map(row => row.id)) + 1 : 1;
    const newRow = {
      id: newId,
      title: newInfo.title,
      teacherName: newInfo.teacherName,
      studentNames: newInfo.studentNames,
      startTime: newInfo.startTime,
      endTime: newInfo.endTime
    };
    const newEvent = {
      id: uuidv4(),
      title: newInfo.title,
      start: newInfo.date,
      end: newInfo.date,
      description: `선생님: ${newInfo.teacherName}<br> <br>수강 학생: ${newInfo.studentNames.map(name => `<br>${name}`).join('')}<br> <br> 수업 시간: ${newInfo.startTime} - ${newInfo.endTime}`
    };

    setRows((prevRows) => [...prevRows, newRow]);
    setEvents((prevEvents) => [...prevEvents, newEvent]);

    handleClose();
    setNewInfo({
      id: '',
      title: '',
      teacherName: '',
      studentNames: [''],
      date: '',
      startTime: '',
      endTime: ''
    });
  };

  const handleEditTeacher = () => {
    const updatedEvents = events.map(event => {
      if (event.id === editingEvent.id) {
        return {
          ...event,
          title: newInfo.title,
          description: `선생님: ${newInfo.teacherName}<br> <br>수강 학생: ${newInfo.studentNames.map(name => `<br>${name}`).join('')}<br> <br> 수업 시간: ${newInfo.startTime} - ${newInfo.endTime}`
        };
      }
      return event;
    });
    setEvents(updatedEvents);

    handleClose();
    setNewInfo({
      id: '',
      title: '',
      teacherName: '',
      studentNames: [''],
      date: '',
      startTime: '',
      endTime: ''
    });
  };

  const handleDeleteTeacher = () => {
    const updatedEvents = events.filter(event => event.id !== editingEvent.id);
    setEvents(updatedEvents);
    handleClose();
  };

  const handleEventClick = (arg) => {
    const event = events.find(event => event.id === arg.event.id);
    if (event) {
      setNewInfo({
        id: event.id,
        title: event.title,
        teacherName: event.description.match(/선생님: (.*?)<br>/)[1],
        studentNames: event.description.match(/수강 학생: (.*?)<br>/)[1].split('<br>').map(name => name.trim()),
        date: event.start,
        startTime: event.startTime,
        endTime: event.endTime
      });
      setIsEditing(true);
      setEditingEvent(event);
      handleOpen();
    }
  };

  const addStudentField = () => {
    setNewInfo(prevState => ({
      ...prevState,
      studentNames: [...prevState.studentNames, '']
    }));
  };

  const removeStudentField = (index) => {
    setNewInfo(prevState => {
      const newStudentNames = prevState.studentNames.filter((_, i) => i !== index);
      return {
        ...prevState,
        studentNames: newStudentNames
      };
    });
  };


  return (
    <div className='calendar-container'>
      <FullCalendar
        key = {dateInfo}
        plugins={[dayGridPlugin, interactionPlugin]}
        initialView="dayGridMonth"
        initialDate={dateInfo}
        events={events}
        locale='ko'
        editable={false}
        eventContent={renderEventContent}
        eventTimeFormat={{
          hour: 'numeric',
          minute: '2-digit',
          meridiem: true
        }}
        aspectRatio={1.5}
        displayEventEnd= {true}
        headerToolbar={{
          right: 'customPrev,customNext today', // customPrev와 customNext 버튼을 추가
        }}
        customButtons={{
          customPrev: {
            text: '이전달',
            click: handlePrev
          },
          customNext: {
            text: '다음달',
            click: handleNext
          },
          today : {
            text :'오늘'
          }
        }}
      />

      <Modal
        open={open}
        onClose={handleClose}
      >
        <Box sx={modalStyle}>
          <Typography variant="h6" component="h2">{isEditing ? '일정 수정' : '일정 입력'}</Typography>
          <Stack spacing={2} mt={2}>
            <TextField label="강의 제목" name="title" value={newInfo.title} onChange={handleChange} />
            <TextField label="선생님 이름" name="teacherName" value={newInfo.teacherName} onChange={handleChange} />
            {newInfo.studentNames.map((studentName, index) => (
              <Box key={index} display="flex" alignItems="center">
                <TextField
                  label={`학생 이름 ${index + 1}`}
                  name={`studentNames-${index}`}
                  value={studentName}
                  onChange={handleChange}
                  fullWidth
                />
                <IconButton onClick={() => removeStudentField(index)}>
                  <DeleteIcon />
                </IconButton>
              </Box>
            ))}
            <TextField
              label="시작 시간"
              type="time"
              name="startTime"
              value={newInfo.startTime}
              onChange={handleChange}
              InputLabelProps={{ shrink: true }}
              inputProps={{ step: 300 }}
            />
            <TextField
              label="종료 시간"
              type="time"
              name="endTime"
              value={newInfo.endTime}
              onChange={handleChange}
              InputLabelProps={{ shrink: true }}
              inputProps={{ step: 300 }}
            />
            <Button variant="outlined" onClick={addStudentField}>학생 추가</Button>
            {isEditing ? (
              <>
                <Button variant="contained" onClick={handleEditTeacher}>수정</Button>
                <Button variant="contained" color="error" onClick={handleDeleteTeacher}>삭제</Button>
              </>
            ) : (
              <Button variant="contained" onClick={handleAddTeacher}>추가</Button>
            )}
          </Stack>
        </Box>
      </Modal>
    </div>
  );
}
