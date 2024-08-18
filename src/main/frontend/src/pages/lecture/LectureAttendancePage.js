import React from 'react';
import Topbar from '../../common/component/topbar/Topbar';
import Sidenavbar from '../../common/component/sidebar/Sidenavbar';
import LectureAttendance from './LectureAttendacne';

import "./lecture-attendance-page.css"

const LectureAttendancePage = ()=> {
    return(
        <div className='lecturepage-container'>
            <Topbar/>
            <Sidenavbar/>
            <div className='lecture-attendacne-contents'>
                 <LectureAttendance/>
            </div>
            
        </div>
    )
}

export default LectureAttendancePage;