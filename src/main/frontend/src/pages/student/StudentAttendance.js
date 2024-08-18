import React from 'react'
import { useParams } from 'react-router-dom'
import Sidenavbar from '../../common/component/sidebar/Sidenavbar.js'
import Topbar from '../../common/component/topbar/Topbar.js'

import "./students.css"
import StudentAttendanceList from './StudentAttendanceList.js'

export default function StudentAttendance() {
    const {id} = useParams();
    return(
        <div className='studentpage-container'>
            <Topbar/>        
            <Sidenavbar/>
            <StudentAttendanceList/>
        </div>
    )
}