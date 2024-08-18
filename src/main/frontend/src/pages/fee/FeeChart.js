import React from 'react';
import { BarChart } from '@mui/x-charts/BarChart';

import "./fee-chart.css";

const FeeChart = () => {
    
    const getLast12Months = ()=> {
        const months = [];
        const now = new Date();
        
        for(let i = 0; i < 12; i++){
            const year = now.getFullYear();
            const month = now.getMonth() - i;

            const calculatedDate = new Date(year, month, 1);
            const formattedYearMonth = `${calculatedDate.getFullYear()}-${(calculatedDate.getMonth() + 1).toString().padStart(2, '0')}`;

            months.unshift(formattedYearMonth)
        }

        return(months)
    }
    // 12개월 간 수강료 배열 반환.
    const getFee12Months = () => {
        // 
        const Fees = [1000000,1500000,2000000,0,1522222,333333,222222,100000,222222,1000000,1313113,222222];

        return Fees;
    }
    
    
    
    
    
    
    return(
        <div className='fee-chart'>
            <BarChart
                xAxis={[{ scaleType: 'band', 
                          data: getLast12Months() }]}
                series={[{ data: [1000000,1500000,2000000,0,1522222,333333,222222,100000,222222,1000000,1313113,222222], color: '#0895D6' }]}
                margin={ {
                    top: 32,
                    right: 32,
                    left: 72, // left margin을 넓혀서 Y축 레이블이 잘리지 않도록 함
                    bottom: 32,
                  }}
                height={350}

            />
            
        </div>
    )
}

export default FeeChart;