import React, { useState } from 'react';
import FullCalendar from '@fullcalendar/react';
import dayGridPlugin from '@fullcalendar/daygrid';
import interactionPlugin from '@fullcalendar/interaction';

import { Button, Box, Stack, TextField, Modal, Typography, IconButton } from '@mui/material';
import { v4 as uuidv4 } from 'uuid';
import DeleteIcon from '@mui/icons-material/Delete';

import "./calendarcontainer.css"

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
        plugins={[dayGridPlugin, interactionPlugin]}
        initialView="dayGridMonth"
        events={events}
        dateClick={handleDateClick}
        editable={true}
        eventDrop={handleEventDrop}
        eventClick={handleEventClick}
        eventContent={renderEventContent}
        aspectRatio={1.5}
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
