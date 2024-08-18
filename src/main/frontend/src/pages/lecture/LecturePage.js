import React from 'react';

import "./lecturepage.css"
import Topbar from '../../common/component/topbar/Topbar';
import Sidenavbar from '../../common/component/sidebar/Sidenavbar';

import LectureList from './LectureList';

export default function LecturePage() {
    return(
        <div className='lecturepage-container'>
            <Topbar/>
            <Sidenavbar/>
            <div className='lecture-content'>
                    <div className='lecture-title'>
                        <span className='lecture-title-name'>강의 리스트</span>
                    </div>
                    <LectureList/>
            </div>
          \
        </div>
    )
}
