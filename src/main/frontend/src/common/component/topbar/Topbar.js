// src/common/component/topbar/Topbar.js
import React from 'react';
import { useLocation } from 'react-router-dom';
import './topbar.css';

export default function Topbar() {
    const location = useLocation();
    const path = location.pathname;

    let pageTitle = 'Home'; // default title
    if (path.includes('/calendar')) {
        pageTitle = 'Calendar';
    } else if (path.includes('/students')) {
        pageTitle = 'Student';
    } else if (path.includes('/teachers')) {
        pageTitle = 'Teacher';
    }

    return (
        <div className="topbar-container">
            <div className="topbar-left">
                <span>{pageTitle}</span>
            </div>
            <div className="topbar-right">
                <i className="fas fa-bell"></i>
                <i className="fas fa-question-circle"></i>
                <span className="user-name">seungchan kim</span>
                <div className="user-icon">KS</div>
            </div>
        </div>
    );
}
