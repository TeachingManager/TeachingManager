import React, { useEffect, useState } from 'react';
import { Box, Grid, Card, CardContent, Typography, Avatar, List, ListItem, ListItemText } from '@mui/material';
import EventIcon from '@mui/icons-material/Event';
import AttachMoneyIcon from '@mui/icons-material/AttachMoney';
import PeopleIcon from '@mui/icons-material/People';
import CalendarTodayIcon from '@mui/icons-material/CalendarToday';
import dayjs from 'dayjs';
import { useRecoilState } from 'recoil';
import { institueListState, selectedDateState } from '../../common/Auth/recoilAtom';
import axios from 'axios';

const DashBoard = () => {
  const selectedDate = dayjs(); // 오늘 날짜로 고정
  const [userInfo, setUserInfo] = useRecoilState(institueListState);

  const [selectedDates, setSelectedDates] = useRecoilState(selectedDateState);
  const [feeList, setFeeList] = useState([]);
  const [totalFee, setTotalFee] = useState(0); // 수강료 합계를 위한 상태
  const [rows, setRows] = useState([]);
  const [studentNumber, setStudentNumber] = useState(0);
  const [events, setEvents] = useState([]); // Store fetched schedule events
  const [monthFee, setMonthFee] = useState(0)
  const [countSchedule, setCountSchedule] = useState(0)
  useEffect(() => {
    const fetchTeacher = async (teacher_id) => {
      try {
        const token = localStorage.getItem("token");
        const response = await axios.get(`${process.env.REACT_APP_API_BASE_URL}/api/teacher`, {
          headers: {
            Authorization: `Bearer ${token}`, // Bearer 토큰 설정
          },
        });

        const teacher = response.data.teacherList.find(teacher => teacher.teacher_id === teacher_id);
        return teacher ? teacher.teacher_name : "Unknown Teacher";  // Return fallback value if teacher not found

      } catch (error) {
        console.error("선생님 조회 중 오류 발생");
        return "Unknown Teacher";
      }
    };

    const fetchLecture = async (lecture_id) => {
      try {
        const token = localStorage.getItem("token");
        const response = await axios.get(`${process.env.REACT_APP_API_BASE_URL}/api/lectures/${lecture_id}`, {
          headers: {
            Authorization: `Bearer ${token}`, // Bearer 토큰 설정
          },
        });

        return response.data.teacherId || "Unknown Teacher";  // Return teacher_id or fallback

      } catch (error) {
        console.error("강의 조회중 오류 발생", error);
        return "Unknown Teacher";
      }
    };

    const fetchSchedule = async () => {
      try {
        const token = localStorage.getItem("token");
        const formattedDate = selectedDate.format('YYYY-MM-DD');

        const response = await axios.get(`${process.env.REACT_APP_API_BASE_URL}/api/Schedule/day`, {
          headers: {
            Authorization: `Bearer ${token}`, // Bearer 토큰 설정
          },
          params: {
            date_info: formattedDate // Pass the formatted date as a query parameter
          }
        });

        const formatted_data = await Promise.all(response.data.scheduleList.map(async (data) => {
          const teacher_id = await fetchLecture(data.lecture_id);
          const teacher_name = teacher_id !== "Unknown Teacher" ? await fetchTeacher(teacher_id) : "Unknown Teacher";
          
          return {
            id: data.schedule_id,
            title: data.title,
            start: dayjs(data.start_date).format('h:mm A'),
            end: dayjs(data.end_date).format('h:mm A'),
            description: `선생님: ${teacher_name}`  // Adding teacher name to description
          };
        }));

        setEvents(formatted_data);

      } catch (error) {
        console.error("일정 조회 중 오류 발생", error);
      }
    };

    const fetchScheduleCount = async () => {
      try {
        const token = localStorage.getItem("token");
        const currentDate = new Date().toISOString().split('T')[0];

        const response = await axios.get(`${process.env.REACT_APP_API_BASE_URL}/api/Schedule`,{
          headers: {
            Authorization: `Bearer ${token}`, // Bearer 토큰 설정

          },
          params: {
            date_info: currentDate
          }
        })

        const count = response.data.scheduleList.length;

        setCountSchedule(count)

  
      } catch (error) {
        console.error("일정 조회 중 오류 발생", error);
      }
    }
    

    

    fetchSchedule();
    fetchScheduleCount();
  }, []); // Dependencies: Will run once on mount

  useEffect(() => {
    const fetchFee = async () => {
      try{
        const token = localStorage.getItem("token")
        const response = await axios.get(`${process.env.REACT_APP_API_BASE_URL}/api/fee/year?year=${selectedDates.year()}&month=${selectedDate.month() + 1}`, {
          headers: {
            Authorization: `Bearer ${token}`, // Bearer 토큰 설정
          },
        })
        console.log("fetchfee", response.data)
        const matchingMonthFee = response.data.find(item => item.year === selectedDates.year() && item.month === selectedDate.month() + 1);
        setMonthFee(matchingMonthFee.payedMonthFee);
      } catch (error) {
        console.error("수강료 조회중 오류 발생", error)
      }
    }

    const fetchFeebyMonth = async () => {
      try {
        const token = localStorage.getItem("token");
        const response = await axios.get(`${process.env.REACT_APP_API_BASE_URL}/api/fee?year=${selectedDates.year()}&month=${selectedDate.month() + 1}`, {
          headers: {
            Authorization: `Bearer ${token}`, // Bearer 토큰 설정
          },
        });

        const dataWithId = response.data.map((item, index) => ({
          ...item,
          id: `${item.student_id}-${item.lecture_id}-${index}`, // Create a unique ID
          paymentstatus: item.fullPaid, // Set initial payment status
        }));

        setFeeList(dataWithId); // Store data with IDs
        setRows(dataWithId); // Set the rows for DataGrid


        // 수강료 합계 계산
        const total = dataWithId.reduce((acc, fee) => acc + fee.fee, 0);
        setTotalFee(total); // 수강료 합계를 상태에 저장

      } catch (error) {
        console.error("월별 수강료 조회 중 오류 발생", error);
      }
    };

    const fetchStudentNumber = async () => {
      try {
        const token = localStorage.getItem("token");
        const response = await axios.get(`${process.env.REACT_APP_API_BASE_URL}/api/students`, {
          headers: {
            Authorization: `Bearer ${token}`, // Bearer 토큰 설정
          },
        });
        const count = response.data.length;
        setStudentNumber(count);
        
      } catch (error) {
        console.error("학생 수 조회 중 오류 발생", error);
      }
    };

    fetchFeebyMonth();
    fetchStudentNumber();
    fetchFee();

  }, [selectedDates]);

  // 카드 데이터 배열
  const data = [
    { icon: <EventIcon />, label: "이번달 일정 개수", value: countSchedule },
    { icon: <AttachMoneyIcon />, label: "이번달 수강료 합계", value: `${totalFee.toLocaleString()} 원` }, // 수강료 합계 표시
    { icon: <AttachMoneyIcon />, label: "이번달 납입된 수강료 합계", value: `${monthFee.toLocaleString()} 원` }, // 수강료 합계 표시

    { icon: <PeopleIcon />, label: "총 학생수", value: studentNumber },
  ];

  return (
    <Box sx={{ padding: 4, backgroundColor: '#e3f2fd', minHeight: '100vh' }}>
      <Typography variant="h4" sx={{ fontWeight: 'bold', marginBottom: 2 }}>
        {userInfo.institute_name}님 환영합니다!
      </Typography>
      
      <Grid container spacing={2} gap={12}>
        {data.map((item, index) => (
          <Grid item xs={12} sm={6} md={4} lg={2} key={index}>
            <Card
              sx={{
                textAlign: 'center',
                padding: 2,
                borderRadius: 2,
                boxShadow: '0px 3px 10px rgba(0, 0, 0, 0.1)',
                backgroundColor: '#fff',
                '&:hover': {
                  boxShadow: '0px 5px 15px rgba(0, 0, 0, 0.2)',
                },
              }}
            >
              <CardContent>
                <Avatar
                  sx={{
                    margin: 'auto',
                    marginBottom: 1,
                    backgroundColor: '#e3f2fd',
                    color: '#1976d2',
                    width: 56,
                    height: 56,
                  }}
                >
                  {item.icon}
                </Avatar>
                <Typography variant="h5" sx={{ fontWeight: 'bold' }}>
                  {item.value}
                </Typography>
                <Typography variant="body2" sx={{ color: '#6c757d' }}>
                  {item.label}
                </Typography>
              </CardContent>
            </Card>
          </Grid>
        ))}
      </Grid>

      {/* Agenda Section */}
      <Box sx={{ marginTop: 4, padding: 3, backgroundColor: '#fff', borderRadius: 2, boxShadow: '0px 3px 10px rgba(0, 0, 0, 0.1)' }}>
        <Typography variant="h6" sx={{ marginBottom: 2, display: 'flex', alignItems: 'center' }}>
          <CalendarTodayIcon sx={{ marginRight: 1 }} /> {selectedDate.format('YYYY-MM-DD')}  • 오늘의 일정
        </Typography>
        <List>
          {events.map((event) => (
            <ListItem key={event.id} sx={{ borderBottom: '1px solid #e0e0e0', paddingBottom: 2 }}>
              <ListItemText
                primary={
                  <Typography variant="h6" sx={{ fontWeight: 'bold' }}>
                    {event.title}
                  </Typography>
                }
                secondary={
                  <Box>
                    {/* Time with AM/PM format */}
                    <Typography variant="body2" sx={{ color: '#757575', marginBottom: 1, marginTop: 1 }}>
                      {`${event.start} ~ ${event.end}`}
                    </Typography>
                    {/* Teacher name */}
                    <Typography variant="body2" sx={{ color: '#757575', marginBottom: 1 }}>
                      {event.description}
                    </Typography>
                  </Box>
                }
              />
            </ListItem>
          ))}
        </List>
      </Box>
    </Box>
  );
};

export default DashBoard;
