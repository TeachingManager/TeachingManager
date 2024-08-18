import React from 'react';

import "./lecturepage.css"
import Topbar from '../../common/component/topbar/Topbar';
import Sidenavbar from '../../common/component/sidebar/Sidenavbar';

import OpenLectureList from './OpenLectureList';
import MonthNavigator from '../fee/MonthNavigator';
import { Button } from '@mui/material';

export default function OpenLecturePage() {
    return(
        <div className='lecturepage-container'>
            <Topbar/>
            <Sidenavbar/>
            <div className='lecture-content'>
                    <div className='lecture-title'>
                        <span className='lecture-title-name'>개설된 강의 리스트</span>
                    </div>
                    <MonthNavigator/>
                  
                    <OpenLectureList/>
            </div>
          \
        </div>
    )
}
