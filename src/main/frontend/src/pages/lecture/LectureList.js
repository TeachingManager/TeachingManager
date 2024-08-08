import React, { useState } from 'react';
import { Button, Box, Stack, TextField, Modal, Typography, Select, MenuItem, FormControl, InputLabel, Checkbox, ListItemText } from '@mui/material';
import { DataGrid } from '@mui/x-data-grid';
import { TimePicker } from '@mui/x-date-pickers/TimePicker';
import { LocalizationProvider } from '@mui/x-date-pickers/LocalizationProvider';
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs';
import dayjs from 'dayjs';

export default function LectureList() {
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

  const handleOpen = () => setOpen(true);
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

  const handleAddLecture = () => {
    const newId = rows.length > 0 ? Math.max(...rows.map(row => row.id)) + 1 : 1;
    setRows(prevRows => [...prevRows, { ...newLecture, id: newId, time: formatTimeRange(newLecture.time), day: newLecture.day.join(', ') }]);
    handleClose();
  };

  const handleEditLecture = () => {
    setRows(prevRows => prevRows.map(row => (row.id === newLecture.id ? { ...newLecture, time: formatTimeRange(newLecture.time), day: newLecture.day.join(', ') } : row)));
    handleClose();
  };

  const handleDeleteSelected = () => {
    const selectedIds = selectedRows.map(Number);
    setRows(prevRows => prevRows.filter(row => !selectedIds.includes(row.id)));
    setSelectedRows([]);
  };

  const handleRowDoubleClick = (params) => {
    const lecture = { 
      ...params.row, 
      time: params.row.time.split('-').map(time => dayjs(`1970-01-01T${time}:00`)),
      day: params.row.day.split(', ')
    };
    setNewLecture(lecture);
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

  const daysOfWeek = ['월', '화', '수', '목', '금', '토', '일'];

  return (
    <LocalizationProvider dateAdapter={AdapterDayjs}>
      <Box sx={{ height: 600, mt: 6 }}>
        <Stack direction="row" spacing={2} mb={2}>
          <Button variant="contained" onClick={handleOpen}>강의 추가하기</Button>
          <Button variant="contained" color="error" onClick={handleDeleteSelected}>선택된 강의 삭제</Button>
        </Stack>
        <DataGrid
          columns={[
            { field: 'id', headerName: 'ID', width: 70 },
            { field: 'subject', headerName: '과목', width: 90 },
            { field: 'name', headerName: '강의명', width: 130 },
            { field: 'teacher', headerName: '담당선생님', width: 90 },
            { field: 'day', headerName: '요일', width: 150 },
            { field: 'time', headerName: '강의 시각', width: 130 },
            { field: 'fee', headerName: '수강료', width: 130 },
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
                <Button variant="contained" onClick={handleEditLecture}>수정</Button>
              ) : (
                <Button variant="contained" onClick={handleAddLecture}>추가</Button>
              )}
            </Stack>
          </Box>
        </Modal>
      </Box>
    </LocalizationProvider>
  );
}
