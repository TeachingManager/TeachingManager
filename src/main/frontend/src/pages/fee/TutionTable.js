import React from 'react'
import { DataGrid } from '@mui/x-data-grid';

const TutionTable = () => {
    const columns = [
        { field: 'id', headerName: 'ID', flex: 0.5, headerAlign: 'center', align: 'center' },
        { field: 'name', headerName: '이름', flex: 1, headerAlign: 'center', align: 'center' },
        { field: 'course', headerName: '강좌제목', flex: 2, headerAlign: 'center', align: 'center' },
        { field: 'teacher', headerName: '담당 선생님', flex: 1, headerAlign: 'center', align: 'center' },
        { field: 'fee', headerName: '수강료', type: 'number', flex: 1, headerAlign: 'center', align: 'center' },
      ];
    
      // API로 요청해야하는 부분.
    const rows = [
        { id: 1, name: '홍길동', course: 'React 기초', teacher: '김선생', fee: 50000 },
        { id: 2, name: '이순신', course: '자바스크립트 고급', teacher: '박선생', fee: 75000 },
        { id: 3, name: '강감찬', course: 'CSS 디자인', teacher: '이선생', fee: 60000 },
        { id: 4, name: '김유신', course: 'HTML 기초', teacher: '최선생', fee: 45000 },
        { id: 5, name: '유관순', course: 'Python 기초', teacher: '장선생', fee: 55000 },
        { id: 6, name: '안중근', course: 'JavaScript 고급', teacher: '박선생', fee: 80000 },
        { id: 7, name: '윤봉길', course: 'React 고급', teacher: '이선생', fee: 70000 },
        { id: 8, name: '신사임당', course: 'HTML/CSS 디자인', teacher: '김선생', fee: 48000 },
        { id: 9, name: '세종대왕', course: 'Kotlin 기초', teacher: '최선생', fee: 60000 },
        { id: 10, name: '장보고', course: 'Swift 기초', teacher: '박선생', fee: 65000 },
       
    ]
    ;

    return (
    <div>
      <DataGrid
        columns={columns}
        rows={rows}
        pageSize={10}
        rowsPerPageOptions={[10]}
        rowHeight={50}
        autoHeight={true}  // 자동 높이 설정

        style={{ width: '100%' }}  // 100% 너비로 설정
      />
    </div>
  )
}

export default TutionTable
