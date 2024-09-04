import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import reportWebVitals from './reportWebVitals';
import { createBrowserRouter, RouterProvider } from 'react-router-dom';
import Students from './pages/student/Students.js';
import Teachers from './pages/teacher/Teachers.js';
import Calendars from './pages/calendar/Calendars.js';
import LoginPage from './pages/login/LoginPage';
import SignUp from './pages/login/SignUp';
import Example from './example';
import { AuthProvider } from './common/Auth/AuthProvider';
import ProtectedRoute from './common/Auth/ProtectedRoute';
import LecturePage from './pages/lecture/LecturePage';
import FeePage from './pages/fee/FeePage';
import { RecoilRoot } from 'recoil';
import StudentAttendance from './pages/student/StudentAttendance';
import OpenLecturePage from './pages/lecture/OpenLecturePage';
import StudentFeePage from './pages/fee/StudentFeePage';
import LectureAttendancePage from './pages/lecture/LectureAttendancePage';
import HomePage from './pages/home/HomePage';
import { LocalizationProvider } from '@mui/x-date-pickers';
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs';

const router = createBrowserRouter([
  {
    path: "/home",
    element: <ProtectedRoute><HomePage/></ProtectedRoute>
  },
  {
    path: "/students",
    element: <ProtectedRoute><Students /></ProtectedRoute>,
  },
  {
    path: "/students/attendance/:id",
    element: <ProtectedRoute><StudentAttendance/></ProtectedRoute>
  },
  {
    path: "/teachers",
    element: <ProtectedRoute><Teachers /></ProtectedRoute>
  },
  {
    path: "/calendar",
    element: <ProtectedRoute><Calendars /></ProtectedRoute>
  },
  {
    path: "/login",
    element: <LoginPage />
  },
  {
    path: "/signup",
    element: <SignUp />
  },
  {
    path: "/example",
    element: (
        <ProtectedRoute><Example /></ProtectedRoute>
    )
  },
  {
    path:"/lecture",
    element: <LecturePage/>
  },
  {
    path:'/openlecture',
    element: <OpenLecturePage/>
  },
  {
    path: "/openlecture/attendance/:id",
    element: <LectureAttendancePage/>
  },
  {
    path: "/fee",
    element : <FeePage/>
  },
  {
    path: "/studentfee",
    element : <StudentFeePage/>
  },
  {
    path: '/home',
    element: <HomePage/>
  }
]);

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  //<React.StrictMode>
  <RecoilRoot>
    <AuthProvider>
      <LocalizationProvider dateAdapter={AdapterDayjs}>
        <RouterProvider router={router} />
      </LocalizationProvider>
    </AuthProvider>
  </RecoilRoot>
  //</React.StrictMode>
);

reportWebVitals();
