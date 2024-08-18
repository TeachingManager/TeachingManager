import React from 'react';
import { useLocation } from 'react-router-dom';
import './topbar.css';

export default function Topbar() {
    const location = useLocation();
    const path = location.pathname;

    let pageTitle = 'Home'; // default title
    if (path.includes('/students/attendance')) {
        pageTitle  = '학생 출석현황';
    } else if (path.includes('/calendar')) {
        pageTitle = '달력';
    } else if (path.includes('/students')) {
        pageTitle = '학생';
    } else if (path.includes('/teachers')) {
        pageTitle = '선생님';
    } else if (path.includes('/lecture')) {
        pageTitle  = '강의';
    } else if (path.includes('/openlecture')) {
        pageTitle  = '강의';
    }
    else if (path.includes('/fee')) {
        pageTitle  = '수강료';
    }else if (path.includes('/studentfee')) {
        pageTitle  = '수강료';
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
