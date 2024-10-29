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
  const [monthFee, setMonthFee] = useState(0)
  
  useEffect(() => {
    const fetchFeebyMonth = async () => {
      try {
        const token = localStorage.getItem("token");
        const response = await axios.get(`${process.env.REACT_APP_API_BASE_URL}/api/fee?year=${selectedDate.year()}&month=${selectedDate.month() + 1}`, {
          headers: {
            Authorization: `Bearer ${token}`, // Bearer 토큰 설정
          },
        });
        
        const dataWithId = response.data.map((item) => ({
          ...item,
          id: item.enroll_id,
          paymentstatus: item.fullPaid, // Set initial payment status
        }));

        setFeeList(dataWithId); // Store data with IDs
        setRows(dataWithId); // Set the rows for DataGrid

      } catch (error) {
        console.error("월별 수강료 조회 중 오류 발생", error);
      }
    };

    const fetchFee = async () => {
      try{
        const token = localStorage.getItem("token")
        const response = await axios.get(`${process.env.REACT_APP_API_BASE_URL}/api/fee/year?year=${selectedDate.year()}&month=${selectedDate.month() + 1}`, {
          headers: {
            Authorization: `Bearer ${token}`, // Bearer 토큰 설정
          },
        })
        console.log("fetchfee", response.data)
        const matchingMonthFee = response.data.find(item => item.year === selectedDate.year() && item.month === selectedDate.month() + 1);
        setMonthFee(matchingMonthFee.payedMonthFee);
      } catch (error) {
        console.error("수강료 조회중 오류 발생", error)
      }
    }

    fetchFeebyMonth();
    fetchFee();
  }, [selectedDate, editing]);
  
  const sleep = (ms) => new Promise((resolve) => setTimeout(resolve, ms));

  const handleEditToggle = async () => {
    if (editing) {
      try {
        const token = localStorage.getItem("token");
        
        for (const row of rows) {
          const paidAmount = row.paymentstatus ? row.fee : 0;
  
          await axios.put(`${process.env.REACT_APP_API_BASE_URL}/api/fee`, {}, {
            params: {
              year: selectedDate.year(),
              month: selectedDate.month() + 1,
              enroll_id: row.id,
              paid_amount: paidAmount,
            },
            headers: {
              Authorization: `Bearer ${token}`,
            },
          });
  
          await sleep(300); // Delay in milliseconds (e.g., 300ms)
        }
  
        alert("납부 상태가 성공적으로 업데이트되었습니다.");
      } catch (error) {
        console.error("납부 상태 업데이트 중 오류 발생:", error);
      }
    }
  
    setEditing(!editing); // Toggle edit mode
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
      field: 'paymentstatus',
      headerName: '납부 현황',
      flex: 1,
      headerAlign: 'center',
      align: 'center',
      renderCell: (params) => {
        return editing ? (
          <Checkbox
            checked={params.row.paymentstatus}
            onChange={() => handleCheckboxChange(params.row.id)}
          />
        ) : (
          <span>{params.row.paymentstatus ? '납부완료' : '미납'}</span>
        );
      },
    },
  ];

  return (
    <div className='check-tution-table'>
      <Box sx={{ display: 'flex', justifyContent: 'flex-end', textAlign: 'flex-end', alignItems: 'center', mb: 1, mr: 7, height: '100%' }}>
        <Typography sx={{ fontSize: 20, align: 'center', flexGrow: 1, ml: 1 }}>
          {selectedDate.month() + 1}월 총 수강료 : {
            feeList.reduce((acc, fee) => acc + fee.fee, 0).toLocaleString()
          } &nbsp;&nbsp;  납입된 수강료 : {monthFee}
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
        columnVisibilityModel={{
          id: false, // ID 열을 숨김
        }}
      />
    </div>
  );
};

export default CheckTutionTable;
