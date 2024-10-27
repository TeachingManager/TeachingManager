import React, { useState, useEffect } from 'react';
import { useNavigate, useLocation } from 'react-router-dom';
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
import axios from 'axios';
import MonthNavigator from '../fee/MonthNavigator';
import { selectedDateState } from '../../common/Auth/recoilAtom';
import { useRecoilState } from 'recoil';

const StudentAttendanceList = () => {
  const navigate = useNavigate();
  const location = useLocation();
  const [selectedDate, setSelectedDate] = useRecoilState(selectedDateState);

  const studentId = location.pathname.split('/').pop();

  const [attendance, setAttendance] = useState({});
  const [isEditing, setIsEditing] = useState(false);

  useEffect(() => {
    // 학생 출석 정보 API 호출
    const fetchAttendanceData = async () => {
      const token = localStorage.getItem('token');
      try {
        console.log(selectedDate.month());
        const response = await axios.get(
          `http://localhost:8080/api/attend/student?student_id=${studentId}&date_info=${selectedDate.year()}-${String(
            selectedDate.month() + 1
          ).padStart(2, '0')}-15`,
          {
            headers: {
              Authorization: `Bearer ${token}`,
            },
          }
        );
        const data = response.data;

        // 출석 데이터를 필요한 형태로 변환, attend_id 포함
        const formattedAttendance = data.reduce((acc, course) => {
          acc[course.lecture_name] = {
            dates: course.attendInfoList.map((info) => info.start_date),
            records: course.attendInfoList.reduce((records, info) => {
              records[info.start_date] = {
                attendId: info.attend_id, // attend_id 포함
                status:
                  info.attendance === 3
                    ? true
                    : info.attendance === 2
                    ? null
                    : false,
              };
              return records;
            }, {}),
          };
          return acc;
        }, {});

        setAttendance(formattedAttendance);
      } catch (error) {
        console.error('출석 데이터를 가져오는 중 오류 발생:', error);
      }
    };

    fetchAttendanceData();
  }, [studentId, selectedDate]);

  const handleAttendanceChange = (course, date) => {
    if (isEditing) {
      setAttendance((prevAttendance) => ({
        ...prevAttendance,
        [course]: {
          ...prevAttendance[course],
          records: {
            ...prevAttendance[course].records,
            [date]: {
              ...prevAttendance[course].records[date],
              status:
                prevAttendance[course].records[date].status === null
                  ? true
                  : prevAttendance[course].records[date].status === true
                  ? false
                  : null,
            },
          },
        },
      }));
    }
  };

  const handleSave = async () => {
    const updatedAttendList = {};

    // attend_id와 수정된 상태를 저장
    Object.keys(attendance).forEach((course) => {
      Object.keys(attendance[course].records).forEach((date) => {
        const record = attendance[course].records[date];
        updatedAttendList[record.attendId] =
          record.status === true
            ? 3 // 출석
            : record.status === null
            ? 2 // 지각
            : 1; // 결석
      });
    });

    const token = localStorage.getItem('token');
    try {
      await axios.put(
        'http://localhost:8080/api/attend',
        {
          attendList: updatedAttendList,
        },
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );
      alert('출석 정보가 성공적으로 저장되었습니다.');
      setIsEditing(false);
    } catch (error) {
      console.error('출석 상태 저장 중 오류 발생:', error);
      alert('출석 상태 저장 중 오류가 발생했습니다.');
    }
  };

  const handleBack = () => {
    navigate(-1);
  };

  return (
    <Box sx={{ width: '100%', p: 3, mx: 'auto', maxWidth: 1400 }}>
      <Box
        sx={{
          display: 'flex',
          alignItems: 'center',
          justifyContent: 'space-between',
          mb: 2,
        }}
      >
        <IconButton onClick={handleBack}>
          <ArrowBackIcon />
        </IconButton>
        <Typography variant="h5" sx={{ flexGrow: 1, textAlign: 'center' }}>
          학생 출석현황
        </Typography>
        <Button
          variant="contained"
          onClick={isEditing ? handleSave : () => setIsEditing(true)}
        >
          {isEditing ? '출석 수정완료' : '출석 수정하기'}
        </Button>
      </Box>

      <Box sx={{ mb: 3 }}>
        <MonthNavigator />
      </Box>

      {Object.keys(attendance).length === 0 ? (
        <Typography
          variant="body1"
          align="center"
          sx={{ py: 3, color: 'gray' }}
        >
          수강 중인 강의가 없습니다.
        </Typography>
      ) : (
        Object.keys(attendance).map((course) => (
          <React.Fragment key={course}>
            <Typography variant="h6" sx={{ mt: 2 }}>
              {course}
            </Typography>
            <TableContainer component={Paper} sx={{ mb: 2 }}>
              <Table>
                <TableHead>
                  <TableRow>
                    {attendance[course].dates.map((date) => (
                      <TableCell key={date} align="center">
                        {date}
                      </TableCell>
                    ))}
                  </TableRow>
                </TableHead>
                <TableBody>
                  <TableRow>
                    {attendance[course].dates.map((date) => (
                      <TableCell key={date} align="center">
                        {isEditing ? (
                          <Checkbox
                            checked={attendance[course].records[date].status === true}
                            indeterminate={attendance[course].records[date].status === null}
                            onChange={() => handleAttendanceChange(course, date)}
                          />
                        ) : (
                          attendance[course].records[date].status === null
                            ? '△' // 지각
                            : attendance[course].records[date].status === true
                            ? 'O'
                            : 'X'
                        )}
                      </TableCell>
                    ))}
                  </TableRow>
                </TableBody>
              </Table>
            </TableContainer>
            <Divider />
          </React.Fragment>
        ))
      )}
    </Box>
  );
};

export default StudentAttendanceList;
