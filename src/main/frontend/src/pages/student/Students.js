import React from 'react'
import Sidenavbar from '../../common/component/sidebar/Sidenavbar.js'
import Topbar from '../../common/component/topbar/Topbar.js'
import StudentContent from './StudentContents.js'

import "./students.css"

export default function Students(){
    return(
        <div className='studentpage-container'>
            <Topbar/>        
            <Sidenavbar/>
            <StudentContent/>    
        </div>
    )
}