import React from 'react'
import Topbar from '../../common/component/topbar/Topbar'
import Sidenavbar from '../../common/component/sidebar/Sidenavbar'

import './student-fee-page.css'
import MonthNavigator from './MonthNavigator'
import CheckTutionTable from './CheckTutionTable'

const StudentFeePage = ()=> {
    return(
        <div className='feepage-container'>
            <Topbar/>        
            <Sidenavbar/>
            <div className='check-tution-table'>
                <div className='studentbar-container'>
                    <div className='studentbar-content'>
                    <span className='studentbar-title'>수강료 현황</span>
                    </div>
                </div>
                <MonthNavigator/>
                <CheckTutionTable/>

            </div>
            
            
    </div>
    )
}


export default StudentFeePage