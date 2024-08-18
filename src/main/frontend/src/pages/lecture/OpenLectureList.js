import React, { useState } from 'react';
import { Box, IconButton, Button, Stack, TextField, Modal, Typography, Select, MenuItem, FormControl, InputLabel, Checkbox, ListItemText } from '@mui/material';
import { DataGrid } from '@mui/x-data-grid';
import { TimePicker } from '@mui/x-date-pickers/TimePicker';
import { LocalizationProvider } from '@mui/x-date-pickers/LocalizationProvider';
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs';
import dayjs from 'dayjs';
import { useNavigate } from 'react-router-dom';
import BookIcon from '@mui/icons-material/Book';

export default function OpenLectureList() {
    const navigate = useNavigate();
  const [rows, setRows] = useState([
    { id: 1, subject: '수학', name: '기초 수학', teacher: '홍길동', day: '월', time: '10:00-11:30', fee: '100000' },
    { id: 2, subject: '영어', name: '기초 영어', teacher: '김철수', day: '화', time: '13:00-14:30', fee: '120000' }
  ]);

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
    setIsEditing(false);
    setNewLecture({
      id: '',
      subject: '',
      name: '',
      teacher: '',
      day: [],
      time: [null, null],
      fee: ''
    });
  };

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

  return (
    <LocalizationProvider dateAdapter={AdapterDayjs}>
      <Box sx={{ height: 600}}>
        <Button onClick={()=> {
            navigate("/lecture")
        }}>강의 개설</Button>
        <Button>선택 강의 폐강</Button>
        <DataGrid
          columns={[
            { field: 'id', headerName: 'ID', width: 70 },
            { field: 'subject', headerName: '과목', width: 90 },
            { field: 'name', headerName: '강의명', width: 130 },
            { field: 'teacher', headerName: '담당선생님', width: 90 },
            { field: 'day', headerName: '요일', width: 150 },
            { field: 'time', headerName: '강의 시각', width: 130 },
            { field: 'fee', headerName: '수강료', width: 130 },
            {
                field: 'attendance',
                headerName: '출석현황',
                width: 150,
                renderCell: (params) => {
                  const handleClick = () => {
                    const studentId = params.row.id;
                    navigate(`/openlecture/attendance/${studentId}`); // useNavigate로 URL 이동
                  };
    
                  return (
                    <IconButton onClick={handleClick}>
                      <BookIcon />
                    </IconButton>
                  );
                },
              },
          ]}
          rows={rows}
          pageSize={5}
          rowsPerPageOptions={[5]}
          checkboxSelection
          onRowSelectionModelChange={(newSelectionModel) => {
            setSelectedRows(newSelectionModel);
          }}
          onRowDoubleClick={handleRowDoubleClick}
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
        </Modal>
      </Box>
    </LocalizationProvider>
  );
}
