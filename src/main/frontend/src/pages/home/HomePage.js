import React from 'react';
import Topbar from '../../common/component/topbar/Topbar';
import Sidenavbar from '../../common/component/sidebar/Sidenavbar';
import HomeContents from './HomeContents';
import './homepage.css'

const HomePage = () => {
    return(
        <div className='homepage-container'>
            <Topbar/>        
            <Sidenavbar/>
            <HomeContents/>
        </div>
    )
}

export default HomePage