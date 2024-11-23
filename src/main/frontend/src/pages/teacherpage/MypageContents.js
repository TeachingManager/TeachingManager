import React from 'react';
import { useEffect, useState } from 'react';
import axios from 'axios';
import { TextField, Button, Grid, Typography, Box, Paper, Divider} from '@mui/material';
export default function MypageContents (){
    const [teacherData, setTeacherData] = useState({
        teacher_name: '',
        age: '',
        birth: '',
        phoneNum: '',
        gender: '',
        bank_account: '',
        nickname: ''
    });

    const [isEditing, setIsEditing] = useState(false);

    useEffect(() => {
        const token = localStorage.getItem('token')
        const decodedToken = decodeToken(token);
        const teacher_id = decodedToken.id
        console.log(teacher_id)
        const fetchTeacherData = async () =>{
            try {
                const response = await axios.post(`${process.env.REACT_APP_API_BASE_URL}/api/teacher/one`, {
                    "teacher_id" : teacher_id
                },{
                    headers: {
                        'Authorization': `Bearer ${token}`
                    }
                });
                const { teacher_name, age, birth, phoneNum, gender, bank_account, nickname } = response.data;
                setTeacherData((prev) => ({
                    ...prev,
                    teacher_name,
                    age,
                    birth,
                    phoneNum,
                    gender,
                    bank_account,
                    nickname,
                }));
                console.log(response.data)
            } catch (error) {
                console.error("선생님 정보 조회 에러 발생", error)
            }
        }

        fetchTeacherData();

    }, [])


    const handleChange = (e) => {
        const { name, value } = e.target;
        setTeacherData((prevData) => ({
            ...prevData,
            [name]: value
        }));
    };
    const handleSave = async () => {
        const token = localStorage.getItem('token');
        const requestDTO = {
            ...teacherData,
            salay : '0'
        }
        console.log(requestDTO)
        try {
            await axios.put(`${process.env.REACT_APP_API_BASE_URL}/api/teacher`, requestDTO, {
                headers: {
                    'Authorization': `Bearer ${token}`
                }
            });
            setIsEditing(false);
            alert('정보가 성공적으로 수정되었습니다.');
        } catch (error) {
            console.error("정보 수정 에러 발생", error);
            alert('정보 수정에 실패했습니다. 다시 시도해주세요.');
        }
    };

    const handleDeleteAccount = async () => {
        const token = localStorage.getItem('token');
        const confirmed = window.confirm("정말로 회원 탈퇴를 하시겠습니까? 탈퇴 후에는 복구할 수 없습니다.");
        if (confirmed) {
            try {
                await axios.put(`${process.env.REACT_APP_API_BASE_URL}/api/delete/teacher`, {
                    headers: {
                        'Authorization': `Bearer ${token}`
                    }
                });
                alert('회원 탈퇴가 완료되었습니다. 이용해 주셔서 감사합니다.');
                // 로그아웃 처리 및 메인 페이지로 이동
                localStorage.removeItem('token');
                window.location.href = '/login';
            } catch (error) {
                console.error("회원 탈퇴 에러 발생", error);
                alert('회원 탈퇴에 실패했습니다. 다시 시도해주세요.');
            }
        }
    };


    const labelMapping = {
        teacher_name: '이름',
        age: '나이',
        birth: '생일',
        phoneNum: '전화번호',
        gender: '성별',
        bank_account: '계좌번호',
        nickname: '닉네임'
    };
    // jwt 토큰 관련 코드
    const decodeToken = (token) => {
        try {
            const base64Url = token.split('.')[1];
            const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
            const jsonPayload = decodeURIComponent(atob(base64).split('').map(function(c) {
                return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
            }).join(''));

            return JSON.parse(jsonPayload);
        } catch (error) {
            console.error('Failed to decode token', error);
            return null;
        }
    };

    return(
        <div className='mypage-container'>
            <Box  sx={{ padding: 3, maxWidth: 800, margin: 'auto' }}>

                <Typography variant='h5' sx={{ fontWeight: 'bold',  marginBottom: 2 }}>
                    기본 정보
                </Typography>

                <Divider sx={{ marginBottom: 3 }} />

                <Grid container spacing={2}>
                    {Object.keys(teacherData).map((key) => (
                        <Grid item xs={12} key={key}>
                            <Box display='flex' justifyContent='space-between' alignItems='center'>
                                <Typography variant='subtitle1' sx={{ fontWeight: 'bold' }}>
                                    {labelMapping[key] || key}
                                </Typography>
                                {isEditing ? (
                                    <input
                                        type={key === 'birthday' ? 'date' : 'text'}
                                        name={key}
                                        value={teacherData[key]}
                                        onChange={handleChange}
                                        style={{
                                            border: '1px solid #ccc',
                                            borderRadius: '4px',
                                            padding: '4px 8px',
                                            width: '60%',
                                            fontSize: '1rem'
                                        }}
                                    />
                                ) : (
                                    <Typography variant='body1' sx={{ color: '#333' }}>
                                        {teacherData[key] || '입력되지 않음'}
                                    </Typography>
                                )}
                            </Box>
                        </Grid>
                    ))}

                    <Grid item xs={12}>
                        <Divider sx={{ marginY: 3 }} />
                        {isEditing ? (
                            <Button
                                variant='contained'
                                color='primary'
                                fullWidth
                                onClick={handleSave}
                                sx={{ padding: 1, fontSize: '1rem', borderRadius: 2 }}
                            >
                                저장
                            </Button>
                        ) : (
                            <Button
                                variant='outlined'
                                color='primary'
                                fullWidth
                                onClick={() => setIsEditing(true)}
                                sx={{ padding: 1, fontSize: '1rem', borderRadius: 2 }}
                            >
                                수정
                            </Button>
                        )}
                    </Grid>
                    <Grid item xs={12}>
                        <Button
                            variant='outlined'
                            color='error'
                            fullWidth
                            onClick={handleDeleteAccount}
                            sx={{ padding: 1, fontSize: '1rem', borderRadius: 2, marginTop: 2 }}
                        >
                            회원 탈퇴하기
                        </Button>
                    </Grid>
                </Grid>

        </Box>
        </div>
    )

}