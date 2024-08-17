import React from 'react'

import Topbar from '../../common/component/topbar/Topbar.js'
import Sidenavbar from '../../common/component/sidebar/Sidenavbar'
import TeacherContents from './TeacherContents.js'

import "./teachers.css"

export default function Teachers(){
    return(
        <div className='teacherpage-container'>
            <Topbar/> 
            <Sidenavbar/>
            <TeacherContents/>

        </div>
    )
}