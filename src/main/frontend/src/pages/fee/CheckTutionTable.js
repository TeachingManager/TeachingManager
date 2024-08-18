import React, { useState } from 'react';
import { DataGrid } from '@mui/x-data-grid';
import { Button, Checkbox, Box, Typography } from '@mui/material';


const CheckTutionTable = () => {
  const initialRows = [
    { id: 1, name: '홍길동', course: 'React 기초', teacher: '김선생', fee: 50000, paymentstatus: false },
    { id: 2, name: '이순신', course: '자바스크립트 고급', teacher: '박선생', fee: 75000, paymentstatus: false },
    { id: 3, name: '강감찬', course: 'CSS 디자인', teacher: '이선생', fee: 60000, paymentstatus: false },
    { id: 4, name: '김유신', course: 'HTML 기초', teacher: '최선생', fee: 45000, paymentstatus: false },
    { id: 5, name: '유관순', course: 'Python 기초', teacher: '장선생', fee: 55000, paymentstatus: false },
    { id: 6, name: '안중근', course: 'JavaScript 고급', teacher: '박선생', fee: 80000, paymentstatus: false },
    { id: 7, name: '윤봉길', course: 'React 고급', teacher: '이선생', fee: 70000, paymentstatus: false },
    { id: 8, name: '신사임당', course: 'HTML/CSS 디자인', teacher: '김선생', fee: 48000, paymentstatus: false },
    { id: 9, name: '세종대왕', course: 'Kotlin 기초', teacher: '최선생', fee: 60000, paymentstatus: false },
    { id: 10, name: '장보고', course: 'Swift 기초', teacher: '박선생', fee: 65000, paymentstatus: false },
  ];

  const [rows, setRows] = useState(initialRows);
  const [editing, setEditing] = useState(false);

  const handleEditToggle = () => {
    setEditing(!editing);
  };

  const handleCheckboxChange = (id) => {
    setRows((prevRows) =>
      prevRows.map((row) =>
        row.id === id ? { ...row, paymentstatus: !row.paymentstatus } : row
      )
    );
  };

  const columns = [
    {
      field: 'id',
      headerName: 'ID',
      flex: 0.5,
      headerAlign: 'center',
      align: 'center',
    },
    {
      field: 'name',
      headerName: '이름',
      flex: 1,
      headerAlign: 'center',
      align: 'center',
    },
    {
      field: 'course',
      headerName: '강좌제목',
      flex: 2,
      headerAlign: 'center',
      align: 'center',
    },
    {
      field: 'teacher',
      headerName: '담당 선생님',
      flex: 1,
      headerAlign: 'center',
      align: 'center',
    },
    {
      field: 'fee',
      headerName: '수강료',
      type: 'number',
      flex: 1,
      headerAlign: 'center',
      align: 'center',
    },
    {
      field: 'paymentstatus',
      headerName: '납부 현황',
      flex: 1,
      headerAlign: 'center',
      align: 'center',
      renderCell: (params) =>
        editing ? (
          <Checkbox
            checked={params.row.paymentstatus}
            onChange={() => handleCheckboxChange(params.row.id)}
          />
        ) : params.value ? (
          '납부완료'
        ) : (
          '미납'
        ),
    },
  ];

  return (
    <div className='check-tution-table'>
      <Box sx={{ display: 'flex', justifyContent: 'flex-end', textAlign: 'flex-end', alignItems: 'center', mb: 1, mr: 7, height: '100%' }}>
        <Typography sx = {{fontSize: 20, align: 'center', flexGrow: 1, ml: 1}}>
          8월 총 수강료 : 100,0000
        </Typography>
        <Button onClick={handleEditToggle} sx={{ fontSize: 16 }}>
          {editing ? '납부 상태 수정 완료' : '납부 상태 수정'}
        </Button>
      </Box>
      <DataGrid
        columns={columns}
        rows={rows}
        pageSize={10}
        rowsPerPageOptions={[10]}
        rowHeight={50}
        autoHeight
        style={{ width: '100%' }}
      />
    </div>
  );
};

export default CheckTutionTable;
