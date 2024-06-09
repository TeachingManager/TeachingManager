// src/common/component/topbar/Topbar.js
import React from 'react';
import './topbar.css';

export default function Topbar() {
    return (
        <div className="topbar-container">
            <div className="topbar-left">
                <span>Home</span>
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
