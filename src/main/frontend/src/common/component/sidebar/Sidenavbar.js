import React, { useState } from 'react';
import "./sidenavbar.css";
import { FaHome } from "react-icons/fa";  // Importing necessary icons
import { IoMdPeople } from "react-icons/io";
import { PiChalkboardTeacher } from "react-icons/pi";
import { CiCalendarDate } from "react-icons/ci";
//import {ReactComponent as Logo } from '../../../assets/logo.svg';

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
            <a href="/">
                <FaHome className="icon" />
                <span className="text">Home</span>
            </a>
            <a href="teachers">
                <PiChalkboardTeacher className="icon" />
                <span className="text">Teachers</span>
            </a>
            <a href="students">
                <IoMdPeople className="icon" />
                <span className="text">Students</span>
            </a>
            <a href="calendar">
                <CiCalendarDate className="icon" />
                <span className="text">Calendar</span>
            </a>
        </div>
    );
}
