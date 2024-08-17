import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import App from './App';
import reportWebVitals from './reportWebVitals';
import {createBrowserRouter, RouterProvider} from 'react-router-dom';
import Students from './pages/student/Students.js';
import Teachers from './pages/teacher/Teachers.js';
import { Calendar } from 'react-big-calendar';
import CalendarContainer from './pages/calendar/CalendarContainer.js';
import Calendars from './pages/calendar/Calendars.js';

const router = createBrowserRouter([
  {
    path: "/",
    element: <div>Hello world!!</div>
  },
  {
    path: "/students",
    element: <Students/>
  }
  ,
  {
    path: "/teachers",
    element: <Teachers/>
  }
  ,
  {
    path: "/calendar",
    element: <Calendars/>
  }
]);





const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  <React.StrictMode>
    <RouterProvider router={router} />
  </React.StrictMode>
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
