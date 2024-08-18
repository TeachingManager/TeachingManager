import React from 'react';
import FeeChart from './FeeChart';

import { Typography } from '@mui/material';

import "./fee-overview.css"
import '@fontsource/roboto/300.css';
import '@fontsource/roboto/400.css';
import '@fontsource/roboto/500.css';
import '@fontsource/roboto/700.css';
import MonthNavigator from './MonthNavigator';
import TutionTable from './TutionTable';

const FeeOverview = () => {
    
    return(
        <div className='fee-overview-container'>
            <div className='chart-container'>
                <Typography variant="h6" component="h6" align='center'>
                    1년간 수강료 현황
                </Typography>
                <FeeChart/>
            </div>
            <div>
                <MonthNavigator/>
                <TutionTable/>
            </div>
        </div>
    )
}

export default FeeOverview;