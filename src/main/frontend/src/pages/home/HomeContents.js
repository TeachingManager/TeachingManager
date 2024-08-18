import React from "react";
import { Box } from "@mui/material";
import FeeChart from "../fee/FeeChart";

const HomeContents = ()=> {
    return(
        <div className="home-contents-container">
             <Box sx={{ width: '91%', height: '90vh', ml: 8, p: 4,}}>
      {/* 상단 큰 박스 */}
      <Box
        sx={{
          border: '1px solid black',
          height: '50%',
          mb: 2,
    
        }}
      />

      {/* 하단 두 개의 작은 박스 */}
      <Box sx={{ display: 'flex', justifyContent: 'space-between', gap: 2 }}>
        <Box
          sx={{
            border: '1px solid black',
            flex: 1, // 박스가 남은 공간을 고르게 차지하게 함
            height: '350px',
          }}
        />
        <Box
          sx={{
            border: '1px solid black',
          flex: 1, // 남은 공간을 차지하게 함
          height: '350px', // 동일한 높이로 설정
          display: 'flex', // Flexbox로 내부 요소를 정렬
          alignItems: 'center', // 내부 요소를 수직 가운데 정렬
          justifyContent: 'center', // 내부 요소를 가로 가운데 정렬
          padding: 1,
          width: '500px'
    
          }}
        />
            <FeeChart/>
      </Box>
    </Box>
        </div>
    )
}

export default HomeContents;