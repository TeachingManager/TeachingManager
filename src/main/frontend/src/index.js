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
import FeeManagement from './pages/fee/FeePage';

const router = createBrowserRouter([
  {
    path: "/",
    element: <div>Hello world!!</div>
  },
  {
    path: "/students",
    element: <Students />
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
  {path: "/fee",
    element : <FeeManagement/>}
]);

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  <React.StrictMode>
    <AuthProvider>
      <RouterProvider router={router} />
    </AuthProvider>
  </React.StrictMode>
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
