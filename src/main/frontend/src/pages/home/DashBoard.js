import React from 'react';
import { Box, Grid, Card, CardContent, Typography, Avatar, List, ListItem, ListItemText } from '@mui/material';
import EventIcon from '@mui/icons-material/Event';
import CalendarTodayIcon from '@mui/icons-material/CalendarToday';
import AttachMoneyIcon from '@mui/icons-material/AttachMoney';
import TrendingUpIcon from '@mui/icons-material/TrendingUp';
import PeopleIcon from '@mui/icons-material/People';
import dayjs from 'dayjs';

const data = [
  { icon: <EventIcon />, label: "Events left this week", value: 0 },
  { icon: <CalendarTodayIcon />, label: "Studio events left this week", value: 0 },
  { icon: <AttachMoneyIcon />, label: "Payments received this month", value: "$0" },
  { icon: <TrendingUpIcon />, label: "Projected revenue this month", value: "$0.00" },
  { icon: <PeopleIcon />, label: "Active Students", value: 11 },
];

// Example events for different dates
const eventsData = {
  '2023-06-14': [
    {
      title: "Math Class",
      teacher: "Mr. Smith",
      participants: 25,
      startTime: "10:00 AM",
      endTime: "11:30 AM"
    },
    {
      title: "Science Workshop",
      teacher: "Ms. Johnson",
      participants: 18,
      startTime: "1:00 PM",
      endTime: "2:30 PM"
    },
  ],
  '2024-08-29': [
    {
      title: "Art Class",
      teacher: "Ms. Lee",
      participants: 20,
      startTime: "9:00 AM",
      endTime: "10:30 AM"
    },
    {
      title: "History Lecture",
      teacher: "Mr. Brown",
      participants: 15,
      startTime: "2:00 PM",
      endTime: "3:30 PM"
    },
  ],
  // Add more dates and events as needed
};

const DashBoard = () => {
  const selectedDate = dayjs(); // 오늘 날짜로 고정
  const events = eventsData[selectedDate.format('YYYY-MM-DD')] || [];

  return (
    <Box sx={{ padding: 4, backgroundColor: '#e3f2fd', minHeight: '100vh' }}>
      <Typography variant="h4" sx={{ fontWeight: 'bold', marginBottom: 2 }}>
        Welcome back, Amanda!
      </Typography>
      
      <Grid container spacing={3} justifyContent="space-between">
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
          {selectedDate.format('YYYY-MM-DD')} • Agenda for Amanda McKinnon
        </Typography>
        <Box>
          {events.length > 0 ? (
            <List>
              {events.map((event, index) => (
                <ListItem key={index} sx={{ marginBottom: 2, padding: 2, backgroundColor: '#f5f5f5', borderRadius: 1 }}>
                  <ListItemText
                    primary={
                      <Typography variant="h6" sx={{ fontWeight: 'bold' }}>
                        {event.title}
                      </Typography>
                    }
                    secondary={
                      <>
                        <Typography variant="body2">Teacher: {event.teacher}</Typography>
                        <Typography variant="body2">Participants: {event.participants}</Typography>
                        <Typography variant="body2">Time: {event.startTime} - {event.endTime}</Typography>
                      </>
                    }
                  />
                </ListItem>
              ))}
            </List>
          ) : (
            <Box sx={{ textAlign: 'center' }}>
              <img src="/path/to/your/image.png" alt="No events illustration" style={{ width: '150px', marginBottom: 16 }} />
              <Typography variant="h6" sx={{ color: '#6c757d' }}>
                There's nothing on your schedule for {selectedDate.format('YYYY-MM-DD')}
              </Typography>
            </Box>
          )}
        </Box>
      </Box>
    </Box>
  );
};

export default DashBoard;
