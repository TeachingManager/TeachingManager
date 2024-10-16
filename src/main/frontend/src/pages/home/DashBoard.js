import React, { useEffect, useState } from 'react';
import { Box, Grid, Card, CardContent, Typography, Avatar } from '@mui/material';
import EventIcon from '@mui/icons-material/Event';
import AttachMoneyIcon from '@mui/icons-material/AttachMoney';
import PeopleIcon from '@mui/icons-material/People';
import dayjs from 'dayjs';
import { useRecoilState } from 'recoil';
import { institueListState } from '../../common/Auth/recoilAtom';
import { selectedDateState } from '../../common/Auth/recoilAtom';
import axios from 'axios';

const DashBoard = () => {
  const selectedDate = dayjs(); // 오늘 날짜로 고정
  const [userInfo, setUserInfo] = useRecoilState(institueListState);

  const [selectedDates, setSelectedDates] = useRecoilState(selectedDateState);
  const [feeList, setFeeList] = useState([]);
  const [totalFee, setTotalFee] = useState(0); // 수강료 합계를 위한 상태
  const [rows, setRows] = useState([]);
  const [studentNumber, setStudentNumber] = useState(0)

  useEffect(() => {
    const fetchFeebyMonth = async () => {
      try {
        const token = localStorage.getItem("token");
        const response = await axios.get(`http://localhost:8080/api/fee?year=${selectedDates.year()}&month=${selectedDate.month() + 1}`, {
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
        const response = await axios.get('http://localhost:8080/api/students',
          {
            headers : {
              Authorization: `Bearer ${token}`, // Bearer 토큰 설정
            }
          }
        )
        const count = response.data.length;
        setStudentNumber(count);
        console.log(count)
        
      } catch(error){
        console.error("학생 수 조회 중 오류 발생")
      }
    }


    fetchFeebyMonth();
    fetchStudentNumber();
  }, [selectedDates]);

  // 카드 데이터 배열
  const data = [
    { icon: <EventIcon />, label: "이번주 일정", value: 0 },
    { icon: <AttachMoneyIcon />, label: "이번달 수강료 합계", value: `${totalFee.toLocaleString()} 원` }, // 수강료 합계 표시
    { icon: <PeopleIcon />, label: "총 학생수", value: studentNumber },
  ];

  return (
    <Box sx={{ padding: 4, backgroundColor: '#e3f2fd', minHeight: '100vh' }}>
      <Typography variant="h4" sx={{ fontWeight: 'bold', marginBottom: 2 }}>
        {userInfo.institute_name}님 환영합니다!
      </Typography>
      
      <Grid container spacing={2} gap={12}   >
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
        <Typography variant="h6" sx={{ marginBottom: 1 }}>
          {selectedDate.format('YYYY-MM-DD')}  • 오늘의 일정
        </Typography>
        {/* 일정 표시 로직 */}
      </Box>
    </Box>
  );
};

export default DashBoard;
