import React, { useState, useEffect } from 'react';
import { Box, Stack, TextField, Button, Modal, Typography } from '@mui/material';
import { DataGrid } from '@mui/x-data-grid';
import { LocalizationProvider } from '@mui/x-date-pickers/LocalizationProvider';
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs';
import { DatePicker } from '@mui/x-date-pickers/DatePicker';
import dayjs from 'dayjs';

export default function FeeManagement() {
  const [rows, setRows] = useState([]);
  const [open, setOpen] = useState(false);
  const [newEntry, setNewEntry] = useState({
    id: '',
    student: '',
    lecture: '',
    fee: '',
    date: ''
  });
  const [selectedMonth, setSelectedMonth] = useState(dayjs());
  const [showAll, setShowAll] = useState(true);
  const [monthlyTotal, setMonthlyTotal] = useState(0);

  useEffect(() => {
    updateTotalFeesByMonth(rows, showAll ? null : selectedMonth, showAll);
  }, [rows, selectedMonth, showAll]);

  const handleOpen = () => setOpen(true);
  const handleClose = () => {
    setOpen(false);
    setNewEntry({
      id: '',
      student: '',
      lecture: '',
      fee: '',
      date: ''
    });
  };

  const handleChange = (e) => {
    const { name, value } = e.target;
    setNewEntry(prevState => ({
      ...prevState,
      [name]: value
    }));
  };

  const handleAddEntry = () => {
    const newId = rows.length > 0 ? Math.max(...rows.map(row => row.id)) + 1 : 1;
    setRows(prevRows => [...prevRows, { ...newEntry, id: newId }]);
    handleClose();
  };

  const handleMonthChange = (newMonth) => {
    setSelectedMonth(newMonth);
    setShowAll(false);
    updateTotalFeesByMonth(rows, newMonth, false);
  };

  const handleShowAll = () => {
    setShowAll(true);
    updateTotalFeesByMonth(rows, null, true);
  };

  const updateTotalFeesByMonth = (rows, month = selectedMonth, showAll = false) => {
    const filteredRows = showAll ? rows : rows.filter(row => dayjs(row.date).isSame(month, 'month'));
    const totalFee = filteredRows.reduce((acc, row) => acc + parseFloat(row.fee), 0);
    setMonthlyTotal(totalFee);
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

  return (
    <LocalizationProvider dateAdapter={AdapterDayjs}>
      <Box sx={{ width: '100%', mt: 6 }}>
        <Box sx={{ display: 'flex', justifyContent: 'center', alignItems: 'center', mb: 2 }}>
          <DatePicker
            views={['year', 'month']}
            label="년-월 선택"
            value={selectedMonth}
            onChange={handleMonthChange}
            renderInput={(params) => <TextField {...params} />}
          />
          <Button variant="contained" onClick={handleShowAll} sx={{ ml: 2 }}>
            전체 현황 보기
          </Button>
          <Typography variant="h6" sx={{ ml: 2 }}>
            {`선택된 월의 수강료 합계: ${monthlyTotal.toLocaleString()}원`}
          </Typography>
        </Box>
        <Stack direction="row" spacing={2} mb={2}>
          <Button variant="contained" onClick={handleOpen}>수강료 입력</Button>
        </Stack>
        <Box sx={{ height: 400, mb: 6 }}>
          <DataGrid
            columns={[
              { field: 'id', headerName: 'ID', width: 70 },
              { field: 'student', headerName: '학생', width: 150 },
              { field: 'lecture', headerName: '강좌', width: 150 },
              { field: 'fee', headerName: '수강료', width: 130 },
              { field: 'date', headerName: '날짜', width: 150 },
            ]}
            rows={showAll ? rows : rows.filter(row => dayjs(row.date).isSame(selectedMonth, 'month'))}
            pageSize={5}
            rowsPerPageOptions={[5]}
          />
        </Box>
        <Modal open={open} onClose={handleClose}>
          <Box sx={modalStyle}>
            <Typography variant="h6" component="h2">수강료 입력</Typography>
            <Stack spacing={2} mt={2}>
              <TextField label="학생" name="student" value={newEntry.student} onChange={handleChange} />
              <TextField label="강좌" name="lecture" value={newEntry.lecture} onChange={handleChange} />
              <TextField label="수강료" name="fee" value={newEntry.fee} onChange={handleChange} />
              <TextField label="날짜" name="date" value={newEntry.date} onChange={handleChange} type="date" InputLabelProps={{ shrink: true }} />
              <Button variant="contained" onClick={handleAddEntry}>추가</Button>
            </Stack>
          </Box>
        </Modal>
      </Box>
    </LocalizationProvider>
  );
}
