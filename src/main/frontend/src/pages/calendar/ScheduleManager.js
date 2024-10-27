import React, { useState, useEffect } from "react";
import {
  Container,
  Breadcrumbs,
  Link,
  Typography,
  Grid,
  Paper,
  List,
  ListItem,
  ListItemText,
  Divider,
  Button,
  ButtonGroup,
  IconButton,
  Dialog,
  DialogActions,
  DialogContent,
  DialogTitle,
  TextField,
  Fab,
  Box,
} from "@mui/material";
import { ArrowBackIos, ArrowForwardIos, Delete, Add } from "@mui/icons-material";
import { startOfMonth, endOfMonth, startOfWeek, addDays } from 'date-fns';

const dummyData = {
  "2024-08": {
    "1주차": {
      Monday: [
        {
          title: "Math Class",
          teacher: "Mr. Kim",
          students: ["John Doe", "Jane Smith", "David Lee"],
          time: "10:00 AM - 11:00 AM",
        },
      ],
    },
    "2주차": {
      Tuesday: [
        {
          title: "Science Class",
          teacher: "Mr. Choi",
          students: ["Ethan Hunt", "Mia Wallace"],
          time: "9:00 AM - 10:00 AM",
        },
      ],
    },
    "3주차": {},
    "4주차": {},
  },
  "2024-09": {
    "1주차": {
      Friday: [
        {
          title: "Physics Class",
          teacher: "Mr. Park",
          students: ["Chris Pratt", "Zoe Saldana"],
          time: "2:00 PM - 3:00 PM",
        },
      ],
    },
    "2주차": {},
    "3주차": {},
    "4주차": {},
    "5주차": {},
  },
};

// 윈도우 달력 스타일로 주차를 나누는 함수
const getWeeksOfMonth = (year, month) => {
  const startDate = startOfMonth(new Date(year, month - 1));
  const endDate = endOfMonth(new Date(year, month - 1));
  let currentWeekStart = startOfWeek(startDate, { weekStartsOn: 0 }); // 일요일 시작
  const weeks = [];

  let weekNumber = 1;

  while (currentWeekStart <= endDate) {
    weeks.push(`${weekNumber}주차`);
    currentWeekStart = addDays(currentWeekStart, 7);
    weekNumber++;
  }

  return weeks;
};

const allDays = ["월요일", "화요일", "수요일", "목요일", "금요일", "토요일", "일요일"];

const ScheduleManager = () => {
  const [currentYear, setCurrentYear] = useState(2024);
  const [currentMonth, setCurrentMonth] = useState(8);
  const [weeks, setWeeks] = useState([]);
  const [selectedWeek, setSelectedWeek] = useState(null);
  const [selectedDay, setSelectedDay] = useState(null);
  const [open, setOpen] = useState(false);
  const [newClass, setNewClass] = useState({
    title: "",
    teacher: "",
    students: "",
    time: "",
  });

  useEffect(() => {
    const fetchData = () => {
      const data = dummyData[`${currentYear}-${String(currentMonth).padStart(2, "0")}`] || {};
      const weeksInMonth = getWeeksOfMonth(currentYear, currentMonth);

      setWeeks(weeksInMonth);
      const initialWeek = weeksInMonth[0];
      setSelectedWeek(data[initialWeek] || {});
      const initialDay = allDays.find((day) => selectedWeek && selectedWeek[day]) || allDays[0];
      setSelectedDay((data[initialWeek] && data[initialWeek][initialDay]) ? initialDay : allDays[0]);
    };

    fetchData();
  }, [currentYear, currentMonth]);

  const handlePrevMonth = () => {
    if (currentMonth === 1) {
      setCurrentYear(currentYear - 1);
      setCurrentMonth(12);
    } else {
      setCurrentMonth(currentMonth - 1);
    }
  };

  const handleNextMonth = () => {
    if (currentMonth === 12) {
      setCurrentYear(currentYear + 1);
      setCurrentMonth(1);
    } else {
      setCurrentMonth(currentMonth + 1);
    }
  };

  const handleWeekChange = (week) => {
    const data = dummyData[`${currentYear}-${String(currentMonth).padStart(2, "0")}`] || {};
    setSelectedWeek(data[week] || {});
    const initialDay = allDays.find((day) => data[week] && data[week][day]) || allDays[0];
    setSelectedDay((data[week] && data[week][initialDay]) ? initialDay : allDays[0]);
  };

  const handleClassDelete = (day, index) => {
    const updatedWeek = { ...selectedWeek };
    updatedWeek[day].splice(index, 1);
    if (updatedWeek[day].length === 0) delete updatedWeek[day];
    setSelectedWeek(updatedWeek);
  };

  const handleClassAdd = () => {
    const updatedWeek = { ...selectedWeek };
    if (!updatedWeek[selectedDay]) {
      updatedWeek[selectedDay] = [];
    }
    updatedWeek[selectedDay].push({
      title: newClass.title,
      teacher: newClass.teacher,
      students: newClass.students.split(",").map((s) => s.trim()),
      time: newClass.time,
    });
    setSelectedWeek(updatedWeek);
    setOpen(false);
    setNewClass({
      title: "",
      teacher: "",
      students: "",
      time: "",
    });
  };

  const handleClickOpen = () => {
    setOpen(true);
  };

  const handleClose = () => {
    setOpen(false);
  };

  return (
    <Container>
      <Grid container justifyContent="center" alignItems="center" spacing={3}>
        <Grid item>
          <ArrowBackIos style={{ cursor: "pointer" }} onClick={handlePrevMonth} />
        </Grid>
        <Grid item>
          <Typography variant="h6">{`${currentYear}년 ${currentMonth}월`}</Typography>
        </Grid>
        <Grid item>
          <ArrowForwardIos style={{ cursor: "pointer" }} onClick={handleNextMonth} />
        </Grid>
      </Grid>

      <Breadcrumbs aria-label="breadcrumb" style={{ marginTop: "20px", marginBottom: "20px" }}>
        {weeks.map((week, index) => (
          <Link
            key={index}
            underline="hover"
            color={selectedWeek === week ? "textPrimary" : "inherit"}
            onClick={() => handleWeekChange(week)}
            style={{ cursor: "pointer" }}
          >
            {week}
          </Link>
        ))}
      </Breadcrumbs>

      <Box display="flex" justifyContent="space-between" alignItems="center" marginBottom="20px">
        <ButtonGroup variant="outlined" aria-label="outlined button group">
          {allDays.map((day, index) => (
            <Button
              key={index}
              variant={selectedDay === day ? "contained" : "outlined"}
              onClick={() => setSelectedDay(day)}
            >
              {day}
            </Button>
          ))}
        </ButtonGroup>

        <Fab color="primary" aria-label="add" onClick={handleClickOpen}>
          <Add />
        </Fab>
      </Box>

      <Divider style={{ marginBottom: "24px" }} />

      <Typography variant="h5" gutterBottom>
        {selectedDay}
      </Typography>

      {selectedWeek && selectedWeek[selectedDay]?.length > 0 ? (
        selectedWeek[selectedDay].map((classItem, index) => (
          <Paper elevation={2} key={index} style={{ marginBottom: "16px", padding: "16px", position: "relative" }}>
            <Typography variant="h6" gutterBottom>
              {classItem.title}
            </Typography>
            <List>
              <ListItem>
                <ListItemText
                  primary={`${classItem.time}`}
                  secondary={`Teacher: ${classItem.teacher}, Students: ${classItem.students.join(", ")}`}
                />
                <IconButton
                  onClick={() => handleClassDelete(selectedDay, index)}
                  style={{ position: "absolute", right: "8px", top: "8px" }}
                >
                  <Delete />
                </IconButton>
              </ListItem>
              {index < selectedWeek[selectedDay].length - 1 && <Divider />}
            </List>
          </Paper>
        ))
      ) : (
        <Typography variant="body1">No classes scheduled for this day.</Typography>
      )}

      <Dialog open={open} onClose={handleClose}>
        <DialogTitle>Add New Class</DialogTitle>
        <DialogContent>
          <TextField
            autoFocus
            margin="dense"
            label="Class Title"
            type="text"
            fullWidth
            value={newClass.title}
            onChange={(e) => setNewClass({ ...newClass, title: e.target.value })}
          />
          <TextField
            margin="dense"
            label="Teacher Name"
            type="text"
            fullWidth
            value={newClass.teacher}
            onChange={(e) => setNewClass({ ...newClass, teacher: e.target.value })}
          />
          <TextField
            margin="dense"
            label="Students (comma separated)"
            type="text"
            fullWidth
            value={newClass.students}
            onChange={(e) => setNewClass({ ...newClass, students: e.target.value })}
          />
          <TextField
            margin="dense"
            label="Time"
            type="text"
            fullWidth
            value={newClass.time}
            onChange={(e) => setNewClass({ ...newClass, time: e.target.value })}
          />
        </DialogContent>
        <DialogActions>
          <Button onClick={handleClose} color="primary">
            Cancel
          </Button>
          <Button onClick={handleClassAdd} color="primary">
            Add
          </Button>
        </DialogActions>
      </Dialog>
    </Container>
  );
};

export default ScheduleManager;
