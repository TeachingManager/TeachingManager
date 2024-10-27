import React, { useState, useEffect } from 'react';
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
import { useNavigate, useLocation } from 'react-router-dom';
import axios from 'axios';

const LectureAttendance = () => {
  const navigate = useNavigate();
  const location = useLocation();

  const [attendanceData, setAttendanceData] = useState([]);
  const [isEditing, setIsEditing] = useState(false);
  const [dates, setDates] = useState([]);

  // URL에서 lecture_id, year, month 가져오기
  const searchParams = new URLSearchParams(location.search);
  const lectureId = location.pathname.split('/').pop();  // URL의 마지막 부분에서 lecture_id 추출
  const year = searchParams.get('year');
  const month = searchParams.get('month');

  useEffect(() => {
    // API 요청을 보내 출석 데이터를 가져옴
    const fetchAttendanceData = async () => {
      const token = localStorage.getItem("token")
      try {
        const response = await axios.get(`http://localhost:8080/api/attend/lecture?lecture_id=${lectureId}&date_info=${year}-${month}-01`,{
            headers: {
                Authorization: `Bearer ${token}`  // Authorization 헤더에 Bearer 토큰 설정
            }
        });
        const data = response.data;

        // 날짜 목록 설정
        const uniqueDates = Array.from(new Set(data.flatMap(student => student.attendInfoList.map(info => info.start_date))));

        // 출석 데이터 설정
        const formattedData = data.map(student => ({
          studentName: student.student_name,
          studentId: student.student_id,
          attendance: student.attendInfoList.reduce((acc, info) => ({
            ...acc,
            [info.start_date]: {
              attendId: info.attend_id,  // attend_id 추가
              status: info.attendance    // attendance 상태 (0: 미정, 1: 결석, 2: 지각, 3: 출석)
            }
          }), {})
        }));

        setDates(uniqueDates);
        setAttendanceData(formattedData);
      } catch (error) {
        console.error("출석 데이터를 가져오는 중 오류 발생:", error);
      }
    };

    fetchAttendanceData();
  }, [lectureId, year, month]);

  const handleAttendanceChange = (studentId, date) => {
    if (isEditing) {
      setAttendanceData(prevData =>
        prevData.map(student =>
          student.studentId === studentId
            ? {
                ...student,
                attendance: {
                  ...student.attendance,
                  [date]: {
                    ...student.attendance[date],
                    status:
                      student.attendance[date].status === null
                        ? 1
                        : student.attendance[date].status === 3
                        ? 0
                        : student.attendance[date].status + 1 // 상태 변경 (0 → 1 → 2 → 3 → 0)
                  }
                }
              }
            : student
        )
      );
    }
  };

  const handleSave = async () => {
    // 출석 변경사항을 저장하는 로직
    const updatedAttendList = {};

    attendanceData.forEach(student => {
      Object.keys(student.attendance).forEach(date => {
        const attendInfo = student.attendance[date];
        updatedAttendList[attendInfo.attendId] = attendInfo.status;
      });
    });

    const token = localStorage.getItem("token");
    try {
      await axios.put('http://localhost:8080/api/attend', {
        attendList: updatedAttendList
      }, {
        headers: {
          Authorization: `Bearer ${token}`
        }
      });
      alert('출석 상태가 성공적으로 저장되었습니다.');
      setIsEditing(false);
    } catch (error) {
      console.error('출석 상태 저장 중 오류 발생:', error);
      alert('출석 상태 저장 중 오류가 발생했습니다.');
    }
  };

  return (
    <Box sx={{ width: '100%', p: 4, }}>
      <Box sx={{ display: 'flex', alignItems: 'center', position: 'relative' }}>
        {/* 왼쪽 화살표 아이콘 */}
        <IconButton onClick={() => navigate(-1)} sx={{ position: 'absolute', left: 0 }}>
          <ArrowBackIcon sx={{ fontSize: 30 }} />
        </IconButton>

        {/* 가운데 출석 체크 현황 텍스트 */}
        <Typography
          variant="h5"
          gutterBottom
          sx={{ flexGrow: 1, textAlign: 'center', lineHeight: 'normal' }}
        >
          출석 체크 현황
        </Typography>
      </Box>

      <Box sx={{ display: 'flex', justifyContent: 'flex-end', mb: 2, mr: 4 }}>
        <Button
          variant="contained"
          onClick={isEditing ? handleSave : () => setIsEditing(true)}
          sx={{ fontSize: 16 }}
        >
          {isEditing ? '저장' : '수정'}
        </Button>
      </Box>

      <TableContainer component={Paper}>
        <Table>
          <TableHead>
            <TableRow>
              <TableCell>이름</TableCell>
              {dates.map(date => (
                <TableCell key={date} align="center">
                  {date}
                </TableCell>
              ))}
            </TableRow>
          </TableHead>
          <TableBody>
            {attendanceData.map(student => (
              <TableRow key={student.studentId}>
                <TableCell component="th" scope="row">
                  {student.studentName}
                </TableCell>
                {dates.map(date => (
                  <TableCell key={date} align="center">
                    {isEditing ? (
                      <Checkbox
                        checked={student.attendance[date]?.status === 3}
                        indeterminate={student.attendance[date]?.status === 2}
                        onChange={() => handleAttendanceChange(student.studentId, date)}
                      />
                    ) : (
                      student.attendance[date]?.status === null
                        ? ''
                        : student.attendance[date].status === 3
                        ? 'O'
                        : student.attendance[date].status === 2
                        ? '△'
                        : 'X'
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
