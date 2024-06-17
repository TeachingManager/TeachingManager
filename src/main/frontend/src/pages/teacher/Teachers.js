import React from 'react'

import Topbar from '../../common/component/topbar/Topbar.js'
import Sidenavbar from '../../common/component/sidebar/Sidenavbar'
import TeacherContents from './TeacherContents.js'

export default function Teachers(){
    return(
        <div>
            <Topbar/> 
            <Sidenavbar/>
            <TeacherContents/>
            
        </div>
    )
}