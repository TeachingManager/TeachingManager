import React, { useState } from 'react';
import "./sidenavbar.css";
import { FaHome } from "react-icons/fa";  // Importing necessary icons
import { IoMdPeople } from "react-icons/io";
import { PiChalkboardTeacher } from "react-icons/pi";
import { CiCalendarDate } from "react-icons/ci";
//import {ReactComponent as Logo } from '../../../assets/logo.svg';

import SchoolIcon from '@mui/icons-material/School';
import MenuBookIcon from '@mui/icons-material/MenuBook';
import LibraryBooksIcon from '@mui/icons-material/LibraryBooks';
import AttachMoneyIcon from '@mui/icons-material/AttachMoney';

export default function Sidenavbar() {
    const [isHovered, setIsHovered] = useState(false);

    return (
        <div 
            className="sidenav" 
            onMouseEnter={() => setIsHovered(true)} 
            onMouseLeave={() => setIsHovered(false)}
        >
            <div className="header">
                <span className='text'>
                    {isHovered ? <p className='logo'>Teaching Manager</p> : <p className='logo'>T</p>}
                </span>
            </div>
            <a href="/home">
                <FaHome className="icon" />
                <span className="text">홈</span>
            </a>
            <a href="/teachers">
                <PiChalkboardTeacher className="icon" />
                <span className="text">선생님</span>
            </a>
            <a href="/students">
                <IoMdPeople className="icon" />
                <span className="text">학생</span>
            </a>
            <a href="/calendar">
                <CiCalendarDate className="icon" />
                <span className="text">일정</span>
            </a>
            <a href ="/openlecture">
                <MenuBookIcon />
                <span className='text'>강의</span>
            </a>
            <a href ="/studentfee">
                <AttachMoneyIcon/>
                <span className='text'>수강료</span>
            </a>
        </div>
    );
}
