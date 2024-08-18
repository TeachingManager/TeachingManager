import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import App from './App';
import reportWebVitals from './reportWebVitals';
import { createBrowserRouter, RouterProvider } from 'react-router-dom';
import Students from './pages/student/Students.js';
import Teachers from './pages/teacher/Teachers.js';
import CalendarContainer from './pages/calendar/CalendarContainer.js';
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

const router = createBrowserRouter([
  {
    path: "/home",
    element: <HomePage/>
  },
  {
    path: "/students",
    element: <Students />,

  },
  {
    path: "/students/attendance/:id",
    element: <StudentAttendance/>
  },
  {
    path: "/teachers",
    element: <Teachers />
  },
  {
    path: "/calendar",
    element: <Calendars />
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
  }
  ,
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
  {path: "/fee",
    element : <FeePage/>},

  {path: "/studentfee",
    element : <StudentFeePage/>},

  {path: '/home',
    element: <HomePage/>}
]);

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  //<React.StrictMode>
  <RecoilRoot>
    <AuthProvider>
      <RouterProvider router={router} />
    </AuthProvider>
  </RecoilRoot>
    
  //</React.StrictMode>
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
