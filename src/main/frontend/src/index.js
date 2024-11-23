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
import TeacherSignUp from './pages/login/TeacherSignUp'
import NotFound from './common/NotFound.js';
import ChangePassword from './pages/login/ChangePassword.js';
import TeacherMyPage from './pages/teacherpage/TeacherMypage.js';
const router = createBrowserRouter([
  {
    path: '/',
    element: <LoginPage /> 
  },
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
    path: "/signup/institute",
    element: <SignUp />
  },
  {
    path: "/signup/teacher",
    element: <TeacherSignUp />
  },
  {
    path:"/lecture",
    element: <ProtectedRoute><LecturePage/></ProtectedRoute>
  },
  {
    path:'/openlecture',
    element: <ProtectedRoute><OpenLecturePage/></ProtectedRoute>
  },
  {
    path: "/openlecture/attendance/:id",
    element: <ProtectedRoute><LectureAttendancePage/></ProtectedRoute>
  },
  {
    path: "/fee",
    element : <ProtectedRoute><FeePage/></ProtectedRoute>
  },
  {
    path: "/studentfee",
    element : <ProtectedRoute><StudentFeePage/></ProtectedRoute>
  },
  {
    path: '/home',
    element: <ProtectedRoute><HomePage/></ProtectedRoute>
  },
  {
    path: "*", // 정의되지 않은 모든 경로를 잡아내는 와일드카드 경로
    element: <NotFound />, // NotFound 컴포넌트로 처리
  },
  {
    path: '/change/password',
    element: <ChangePassword />
  },
  {
    path :'/mypage',
    element: <ProtectedRoute><TeacherMyPage/></ProtectedRoute>
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
