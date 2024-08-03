import React from 'react'

import Topbar from '../../common/component/topbar/Topbar.js'
import Sidenavbar from '../../common/component/sidebar/Sidenavbar'
import CalendarContents from './CalendarContainer.js'

import "./calendars.css"

export default function Calendars(){
    return(
        <div className='calendarpage-container'>
            <Topbar/> 
            <Sidenavbar/>
            <CalendarContents/>
            
        </div>
    )
}