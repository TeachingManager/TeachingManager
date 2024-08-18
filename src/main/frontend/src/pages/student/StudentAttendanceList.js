import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import {
  Button,
  Box,
  Typography,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  Checkbox,
  Paper,
  Divider,
  IconButton,
} from '@mui/material';
import ArrowBackIcon from '@mui/icons-material/ArrowBack';

const StudentAttendanceList = () => {
  const navigate = useNavigate();
  const [attendance, setAttendance] = useState({
    '미적분': {
      dates: ['8/13', '8/16', '8/20', '8/22', '8/25', '8/27', '8/29', '9/02', '9/05', '9/08', '9/10', '9/12'],
      records: {
        '8/13': true,
        '8/16': false,
        '8/20': true,
        '8/22': null,
        '8/25': true,
        '8/27': true,
        '8/29': null,
        '9/02': true,
        '9/05': false,
        '9/08': null,
        '9/10': false,
        '9/12': true,
      }
    },
    '물리학': {
      dates: ['8/11', '8/22', '8/29'],
      records: {
        '8/11': true,
        '8/22': null,
        '8/29': false,
      }
    },
    '화학': {
      dates: ['8/10', '8/13', '8/17', '8/20', '8/24', '8/27', '8/31', '9/03', '9/07', '9/10', '9/14', '9/17', '9/21', '9/24'],
      records: {
        '8/10': null,
        '8/13': true,
        '8/17': false,
        '8/20': true,
        '8/24': true,
        '8/27': null,
        '8/31': true,
        '9/03': null,
        '9/07': false,
        '9/10': true,
        '9/14': null,
        '9/17': true,
        '9/21': false,
        '9/24': true,
      }
    },
    '생물학': {
      dates: ['8/09', '8/16', '8/23', '8/30', '9/06'],
      records: {
        '8/09': true,
        '8/16': null,
        '8/23': false,
        '8/30': true,
        '9/06': null,
      }
    },
    '역사': {
      dates: ['8/12', '8/19', '8/26'],
      records: {
        '8/12': true,
        '8/19': null,
        '8/26': true,
      }
    }
  });

  const [isEditing, setIsEditing] = useState(false);

  const handleAttendanceChange = (course, date) => {
    if (isEditing) {
      setAttendance(prevAttendance => ({
        ...prevAttendance,
        [course]: {
          ...prevAttendance[course],
          records: {
            ...prevAttendance[course].records,
            [date]: prevAttendance[course].records[date] === null
              ? true
              : prevAttendance[course].records[date] === true
              ? false
              : null,
          },
        },
      }));
    }
  };

  const handleBack = ()=> {
    navigate(-1);
  }

  return (
    <Box sx={{ width: '100%', p: 4, ml: 8}}>
      
      <Box sx={
        {
            display : 'flex',
            alignItems : 'center',
            justifyContent: 'space-between',
            width: '100%'
        }
      }>
        <IconButton onClick={handleBack}>
            <ArrowBackIcon/>
        </IconButton>
        <Typography variant="h5" gutterBottom>
            홍길동 학생 출석현황
        </Typography>

        <Button
            variant="contained"
            onClick={() => setIsEditing(!isEditing)}
            sx={{ mr: 14 }}
        >
            {isEditing ? '출석 수정완료' : '출석 수정하기'}
        </Button>
      </Box>
        
      {Object.keys(attendance).map(course => (
        <React.Fragment key={course}>
          <Typography variant="h6" component="h3" sx={{ mt: 2 }}>
            {course}
          </Typography>
          <TableContainer component={Paper} sx={{ mb: 2 }}>
            <Table>
              <TableHead>
                <TableRow>
                  {attendance[course].dates.map(date => (
                    <TableCell key={date} align="center">
                      {date}
                    </TableCell>
                  ))}
                </TableRow>
              </TableHead>
              <TableBody>
                <TableRow>
                  {attendance[course].dates.map(date => (
                    <TableCell key={date} align="center">
                      {isEditing ? (
                        <Checkbox
                          checked={attendance[course].records[date] === true}
                          indeterminate={attendance[course].records[date] === null}
                          onChange={() => handleAttendanceChange(course, date)}
                        />
                      ) : (
                        attendance[course].records[date] === null
                          ? ''
                          : attendance[course].records[date] ? 'O' : 'X'
                      )}
                    </TableCell>
                  ))}
                </TableRow>
              </TableBody>
            </Table>
          </TableContainer>
          <Divider />
        </React.Fragment>
      ))}
    </Box>
  );
};

export default StudentAttendanceList;