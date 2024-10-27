// MonthNavigator.js
import React from 'react';
import { useRecoilState } from 'recoil';
import { Box, IconButton, Typography } from '@mui/material';
import ArrowBackIcon from '@mui/icons-material/ArrowBack';
import ArrowForwardIcon from '@mui/icons-material/ArrowForward';
import { selectedDateState } from '../../common/Auth/recoilAtom';

const MonthNavigator = () => {
  // useRecoilState를 사용하여 Recoil 상태를 가져옴
  const [selectedDate, setSelectedDate] = useRecoilState(selectedDateState);

  const handlePrevMonth = () => {
    setSelectedDate(selectedDate.subtract(1, 'month'));
  };

  const handleNextMonth = () => {
    setSelectedDate(selectedDate.add(1, 'month'));
  };

  return (
    <Box display={'flex'} alignItems={'center'} justifyContent={'center'} mt={2} mb={2}>
      <IconButton onClick={handlePrevMonth}>
        <ArrowBackIcon />
      </IconButton>
      <Typography variant="h6">
        {selectedDate.format('YYYY년 M월')}
      </Typography>
      <IconButton onClick={handleNextMonth}>
        <ArrowForwardIcon />
      </IconButton>
    </Box>
  );
};

export default MonthNavigator;
