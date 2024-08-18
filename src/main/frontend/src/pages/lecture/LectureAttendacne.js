import React, { useState } from 'react';
import {
  Box,
  Typography,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  Paper,
  Button,
  Checkbox,
  IconButton
} from '@mui/material';

import ArrowBackIcon from '@mui/icons-material/ArrowBack';
import { useNavigate } from 'react-router-dom';

const LectureAttendance = () => {
    const navigate = useNavigate();
  const [attendance, setAttendance] = useState({
    '미적분': {
      dates: ['8/12', '8/15', '8/22', '8/29', '9/05', '9/12'],
      students: {
        '홍길동': {
          '8/12': true,
          '8/15': false,
          '8/22': true,
          '8/29': false,
          '9/05': true,
          '9/12': null,  // null로 설정하여 확인되지 않은 상태를 나타냄
        },
        '이순신': {
          '8/12': false,
          '8/15': true,
          '8/22': true,
          '8/29': true,
          '9/05': false,
          '9/12': null,  // null로 설정하여 확인되지 않은 상태를 나타냄
        },
        '강감찬': {
          '8/12': true,
          '8/15': true,
          '8/22': false,
          '8/29': true,
          '9/05': true,
          '9/12': null,  // null로 설정하여 확인되지 않은 상태를 나타냄
        }
      }
    }
  });

  const [isEditing, setIsEditing] = useState(false);
  const courseName = '미적분';

  const handleAttendanceChange = (student, date) => {
    if (isEditing) {
      setAttendance(prevAttendance => ({
        ...prevAttendance,
        [courseName]: {
          ...prevAttendance[courseName],
          students: {
            ...prevAttendance[courseName].students,
            [student]: {
              ...prevAttendance[courseName].students[student],
              [date]: prevAttendance[courseName].students[student][date] === null
                ? true
                : prevAttendance[courseName].students[student][date] === true
                ? false
                : null,
            }
          }
        }
      }));
    }
  };

  return (
    <Box sx={{ width: '1200', p: 4}}>
      <Box sx={{ display: 'flex', alignItems: 'center', position: 'relative' }}>
      {/* 왼쪽 화살표 아이콘 */}
      <IconButton onClick= {()=> {
        navigate(-1)
      }}sx={{ position: 'absolute', left: 0,  }}>
        <ArrowBackIcon sx={{ fontSize: 30 }}/>
      </IconButton>

      {/* 가운데 출석 체크 현황 텍스트 */}
      <Typography
        variant="h5"
        gutterBottom
        sx={{ flexGrow: 1, textAlign: 'center', lineHeight: 'normal' }}
      >
        {courseName} 출석 체크 현황
      </Typography>
    </Box>
      
      
        <Box sx={{ display: 'flex', justifyContent: 'flex-end', mb: 2, mr: 4 }}>
        <Button
            variant="contained"
            onClick={() => setIsEditing(!isEditing)}
            sx={{ fontSize: 16 }}
        >
            {isEditing ? '수정 완료' : '수정'}
        </Button>
        </Box>

      <TableContainer component={Paper}>
        <Table>
          <TableHead>
            <TableRow>
              <TableCell>이름</TableCell>
              {attendance[courseName].dates.map(date => (
                <TableCell key={date} align="center">
                  {date}
                </TableCell>
              ))}
            </TableRow>
          </TableHead>
          <TableBody>
            {Object.keys(attendance[courseName].students).map(student => (
              <TableRow key={student}>
                <TableCell component="th" scope="row">
                  {student}
                </TableCell>
                {attendance[courseName].dates.map(date => (
                  <TableCell key={date} align="center">
                    {isEditing ? (
                      <Checkbox
                        checked={attendance[courseName].students[student][date] === true}
                        indeterminate={attendance[courseName].students[student][date] === null}
                        onChange={() => handleAttendanceChange(student, date)}
                      />
                    ) : (
                      attendance[courseName].students[student][date] === null
                        ? ''
                        : attendance[courseName].students[student][date] ? 'O' : 'X'
                    )}
                  </TableCell>
                ))}
              </TableRow>
            ))}
          </TableBody>
        </Table>
      </TableContainer>
    </Box>
  );
};

export default LectureAttendance;