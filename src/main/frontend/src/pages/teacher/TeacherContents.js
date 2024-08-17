import React from 'react';

import TeacherBar from './TeacherBar';
import TeacherList from './TeacherList';

import "./teachercontents.css";

export default function TeacherContents() {
    return(
        <div className='teacher-container'>
            <TeacherBar/>
            <TeacherList/>
        </div>
    )
}