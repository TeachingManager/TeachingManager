import React, { Children } from 'react';

import "./studentcontents.css";
import StudentBar from './StudentBar';
import StudentToolbar from './StudentToolbar';
import StudentList from './StudentList';


export default function StudentContent(){
    return(
        <div className='student-container'>
            <StudentBar/>
        
            <StudentList/>
        </div>
    )
}