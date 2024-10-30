import React, { useState } from 'react';
import { useLocation } from 'react-router-dom';
import { Container, Box, Typography, TextField, Button } from '@mui/material';
import { changepassword } from '../../api/institute';

const ChangePassword = () => {
  const location = useLocation();
  const queryParams = new URLSearchParams(location.search);
  const token = queryParams.get('token');

  const [newPassword, setNewPassword] = useState('');
  const [confirmPassword, setConfirmPassword] = useState('');

  const handlePasswordChange = () => {
    if (newPassword !== confirmPassword) {
      alert('같은 비밀번호를 입력해주세요.');
      return;
    }
    // 비밀번호 변경
    changepassword({newPassword}, token)
    .then(response => {
        alert(response.data.message)
    })
    .catch(error => {
        console.error("비밀번호 변경 실패", error)
    })
    console.log('Password changed:', newPassword);
  };

  return (
    <Container component="main" maxWidth="xs">
      <Box
        sx={{
          marginTop: 8,
          display: 'flex',
          flexDirection: 'column',
          alignItems: 'center',
        }}
      >
        <Typography component="h1" variant="h5">
          TeachingManager 비밀번호 변경
        </Typography>
        <Typography variant="body1" sx={{ mt: 2, mb: 4, textAlign: 'center' }}>
          새로운 비밀번호를 입력 해주세요.
        </Typography>
        <Box component="form" noValidate sx={{ mt: 1 }}>
          <TextField
            margin="normal"
            required
            fullWidth
            label="새 비밀번호"
            type="password"
            value={newPassword}
            onChange={(e) => setNewPassword(e.target.value)}
          />
          <TextField
            margin="normal"
            required
            fullWidth
            label="비밀번호 확인"
            type="password"
            value={confirmPassword}
            onChange={(e) => setConfirmPassword(e.target.value)}
          />
          <Button
            fullWidth
            variant="contained"
            color="primary"
            sx={{ mt: 3, mb: 2 }}
            onClick={handlePasswordChange}
          >
            비밀번호 변경
          </Button>
        </Box>
      </Box>
    </Container>
  );
};

export default ChangePassword;
