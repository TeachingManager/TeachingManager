import React, { useState } from 'react';
import Avatar from '@mui/material/Avatar';
import Button from '@mui/material/Button';
import CssBaseline from '@mui/material/CssBaseline';
import TextField from '@mui/material/TextField';
import Link from '@mui/material/Link';
import Grid from '@mui/material/Grid';
import Box from '@mui/material/Box';
import LockOutlinedIcon from '@mui/icons-material/LockOutlined';
import Typography from '@mui/material/Typography';
import Container from '@mui/material/Container';
import { createTheme, ThemeProvider } from '@mui/material/styles';
import { registerUser } from '../../api/institute';
import { useNavigate } from 'react-router-dom';
const defaultTheme = createTheme();

export default function SignUp() {
  const navigate = useNavigate();
  const [formValues, setFormValues] = useState({
    insName: '',
    email: '',
    password: '',
    address: '',
    phoneNum: ''
  });

  const [formErrors, setFormErrors] = useState({
    insName: false,
    email: false,
    password: false,
    address: false,
    phoneNum: false
  });

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setFormValues({
      ...formValues,
      [name]: value
    });

    // 유효성 검사
    setFormErrors({
      ...formErrors,
      [name]: value === ''
    });
  };

  const handleSubmit = async (event) => {
    event.preventDefault();
    // formValues를 사용하여 제출 작업 수행
    //registerUser(formValues)
        try {
          const result = await registerUser(formValues);
          if(result.status === 200)
            alert('계정이 생성 되었습니다.');
          navigate('/login')
      } catch (error) {
          if(error.status === 409)
            alert('이미 생성된 계정입니다.')
      }
  };

  // 모든 필드가 유효한지 확인
  const isFormValid = Object.values(formValues).every(value => value !== '');

  return (
    <ThemeProvider theme={defaultTheme}>
      <Container component="main" maxWidth="xs">
        <CssBaseline />
        <Box
          sx={{
            marginTop: 8,
            display: 'flex',
            flexDirection: 'column',
            alignItems: 'center',
          }}
        >
          <Avatar sx={{ m: 1, bgcolor: 'secondary.main' }}>
            <LockOutlinedIcon />
          </Avatar>
          <Typography component="h1" variant="h5">
            학원 회원가입
          </Typography>
          <Box component="form" noValidate onSubmit={handleSubmit} sx={{ mt: 3 }}>
            <Grid container spacing={2}>
              <Grid item xs={12}>
                <TextField
                  autoComplete="given-name"
                  name="insName"
                  required
                  fullWidth
                  id="insName"
                  label="기관명"
                  autoFocus
                  value={formValues.insName}
                  onChange={handleInputChange}
                  error={formErrors.insName}
                  helperText={formErrors.insName ? '이 필드는 필수입니다.' : ''}
                />
              </Grid>
              <Grid item xs={12}>
                <TextField
                  required
                  fullWidth
                  id="email"
                  label="이메일 주소"
                  name="email"
                  autoComplete="email"
                  value={formValues.email}
                  onChange={handleInputChange}
                  error={formErrors.email}
                  helperText={formErrors.email ? '이 필드는 필수입니다.' : ''}
                />
              </Grid>
              <Grid item xs={12}>
                <TextField
                  required
                  fullWidth
                  name="password"
                  label="비밀번호"
                  type="password"
                  id="password"
                  autoComplete="new-password"
                  value={formValues.password}
                  onChange={handleInputChange}
                  error={formErrors.password}
                  helperText={formErrors.password ? '이 필드는 필수입니다.' : ''}
                />
              </Grid>
              <Grid item xs={12}>
                <TextField
                  required
                  fullWidth
                  name="address"
                  label="주소명"
                  type="text"
                  id="address"
                  autoComplete="address-line1"
                  value={formValues.address}
                  onChange={handleInputChange}
                  error={formErrors.address}
                  helperText={formErrors.address ? '이 필드는 필수입니다.' : ''}
                />
              </Grid>
              <Grid item xs={12}>
                <TextField
                  required
                  fullWidth
                  name="phoneNum"
                  label="전화번호"
                  type="tel"
                  id="phoneNum"
                  autoComplete="tel"
                  value={formValues.phoneNum}
                  onChange={handleInputChange}
                  error={formErrors.phoneNum}
                  helperText={formErrors.phoneNum ? '이 필드는 필수입니다.' : ''}
                />
              </Grid>
            </Grid>
            <Button
              type="submit"
              fullWidth
              variant="contained"
              sx={{ mt: 3, mb: 2 }}
              disabled={!isFormValid} // 폼이 유효하지 않으면 버튼을 비활성화
            >
               회원가입
            </Button>
            <Grid container justifyContent="flex-end">
              <Grid item>
                <Link href="/login" variant="body2">
                  이미 계정이 있으신가요? 로그인하러가기
                </Link>
              </Grid>
            </Grid>
          </Box>
        </Box>
      </Container>
    </ThemeProvider>
  );
}
