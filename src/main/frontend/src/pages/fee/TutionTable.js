import React, { useEffect, useState } from 'react'
import { DataGrid } from '@mui/x-data-grid';
import axios from 'axios';

const TutionTable = () => {
  const [feeList, setFeeList] = useState([])

  useEffect(() => {
    const fetchFeebyMonth = async () => {
      try {
        const token = localStorage.getItem("token");
        const response = await axios.get(`${process.env.REACT_APP_API_BASE_URL}/api/fee?year=2024&month=8`, {
          headers: {
            Authorization: `Bearer ${token}`, // Bearer 토큰 설정
          },
        });
        
        console.log(response.data);
        setFeeList(response.data); // API로부터 받은 데이터를 상태로 저장

      } catch (error) {
        console.error("월별 수강료 조회 중 오류 발생", error);
      }
    };

    fetchFeebyMonth();
  }, []);
  
  const columns = [
        { field: 'id', headerName: 'ID', flex: 0.5, headerAlign: 'center', align: 'center' },
        { field: 'name', headerName: '이름', flex: 1, headerAlign: 'center', align: 'center' },
        { field: 'course', headerName: '강좌제목', flex: 2, headerAlign: 'center', align: 'center' },
        { field: 'teacher', headerName: '담당 선생님', flex: 1, headerAlign: 'center', align: 'center' },
        { field: 'fee', headerName: '수강료', type: 'number', flex: 1, headerAlign: 'center', align: 'center' },
      ];
    
    const rows = [
        { id: 2, name: '이순신', course: '자바스크립트 고급', teacher: '박선생', fee: 75000 },
        { id: 3, name: '강감찬', course: 'CSS 디자인', teacher: '이선생', fee: 60000 },
        { id: 4, name: '김유신', course: 'HTML 기초', teacher: '최선생', fee: 45000 },
        { id: 5, name: '유길동', course: 'Python 기초', teacher: '장선생', fee: 55000 },
        { id: 6, name: '안길동', course: 'JavaScript 고급', teacher: '박선생', fee: 80000 },
        { id: 7, name: '윤철수', course: 'React 고급', teacher: '이선생', fee: 70000 },
        { id: 8, name: '강철수', course: 'HTML/CSS 디자인', teacher: '김선생', fee: 48000 },
        { id: 9, name: '오철수', course: 'Kotlin 기초', teacher: '최선생', fee: 60000 },
        { id: 10, name: '장철수', course: 'Swift 기초', teacher: '박선생', fee: 65000 },
    ];

    return (
    <div>
      <DataGrid
        columns={columns}
        rows={rows}
        pageSize={10}
        rowsPerPageOptions={[10]}
        rowHeight={50}
        autoHeight={true}
        style={{ width: '100%' }}
      />
    </div>
  )
}

export default TutionTable
