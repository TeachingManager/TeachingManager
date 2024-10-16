import React, { useState, useEffect } from 'react';
import { DataGrid } from '@mui/x-data-grid';
import { Button, Checkbox, Box, Typography } from '@mui/material';
import axios from 'axios';
import { selectedDateState } from '../../common/Auth/recoilAtom';
import { useRecoilState } from 'recoil';

const CheckTutionTable = () => {
  const [feeList, setFeeList] = useState([]);
  const [rows, setRows] = useState([]);
  const [editing, setEditing] = useState(false);
  const [selectedDate, setSelectedDate] = useRecoilState(selectedDateState);

  useEffect(() => {
    const fetchFeebyMonth = async () => {
      try {
        const token = localStorage.getItem("token");
        const response = await axios.get(`http://localhost:8080/api/fee?year=${selectedDate.year()}&month=${selectedDate.month() + 1}`, {
          headers: {
            Authorization: `Bearer ${token}`, // Bearer 토큰 설정
          },
        });
        
        const dataWithId = response.data.map((item, index) => ({
          ...item,
          id: `${item.student_id}-${item.lecture_id}-${index}`, // Create a unique ID
          paymentstatus: item.fullPaid, // Set initial payment status
        }));

        setFeeList(dataWithId); // Store data with IDs
        setRows(dataWithId); // Set the rows for DataGrid

      } catch (error) {
        console.error("월별 수강료 조회 중 오류 발생", error);
      }
    };

    fetchFeebyMonth();
  }, [selectedDate]);

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
      field: 'student_name',
      headerName: '이름',
      flex: 1,
      headerAlign: 'center',
      align: 'center',
    },
    {
      field: 'lecture_name',
      headerName: '강좌제목',
      flex: 2,
      headerAlign: 'center',
      align: 'center',
    },
    {
      field: 'teacher_name',
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
      field: 'fullPaid',
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
        <Typography sx={{ fontSize: 20, align: 'center', flexGrow: 1, ml: 1 }}>
          {selectedDate.month() + 1}월 총 수강료 : {
            feeList.reduce((acc, fee) => acc + fee.fee, 0).toLocaleString()
          }
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
