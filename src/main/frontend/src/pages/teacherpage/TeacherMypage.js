import React from 'react'
import Topbar from '../../common/component/topbar/Topbar'
import Sidenavbar from '../../common/component/sidebar/Sidenavbar'
import "./teachermypage.css"
import MypageContents from './MypageContents'
import "./mypagecontents.css"
export default function TeacherMyPage () {
    return(
        <div className='teacherpage-container'>
            <Topbar/> 
            <Sidenavbar/>
            <MypageContents/>
        </div>
    )
}