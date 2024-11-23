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
import { registerTeacher } from '../../api/institute';
import { useNavigate } from 'react-router-dom';

const defaultTheme = createTheme();

export default function TeacherSignUp() {
  const navigate = useNavigate();
  const [formValues, setFormValues] = useState({
    email: '',
    password: '',
    teacherName: '',
    age: null,
    birth: '',
    phoneNum: '',
    gender: '',
    bank_account: '',
    nickname: ''
  });

  const [formErrors, setFormErrors] = useState({
    email: false,
    password: false,
    teacherName: false,
    age: false,
    birth: false,
    phoneNum: false,
    gender: false,
    bank_account: false,
    nickname: false
  });

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setFormValues({
      ...formValues,
      [name]: name === 'age' ? (value === '' ? null : Number(value)) : value // age를 숫자로 변환
    });

    // 유효성 검사
    setFormErrors({
      ...formErrors,
      [name]: value === ''
    });
  };

  const handleSubmit = async (event) => {
    event.preventDefault();
    try {
      const result = await registerTeacher(formValues);
      if (result.status === 200)
        alert('계정이 생성되었습니다.');
      navigate('/login');
    } catch (error) {
      if (error.status === 406)
        alert('이미 생성된 계정입니다.');
    }
  };

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
            강사 회원가입
          </Typography>
          <Box component="form" noValidate onSubmit={handleSubmit} sx={{ mt: 3 }}>
            <Grid container spacing={2}>
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
                  name="teacherName"
                  label="강사 이름"
                  type="text"
                  id="teacherName"
                  value={formValues.teacherName}
                  onChange={handleInputChange}
                  error={formErrors.teacherName}
                  helperText={formErrors.teacherName ? '이 필드는 필수입니다.' : ''}
                />
              </Grid>
              <Grid item xs={12}>
                <TextField
                  required
                  fullWidth
                  name="age"
                  label="나이"
                  type="number"
                  id="age"
                  value={formValues.age}
                  onChange={handleInputChange}
                  error={formErrors.age}
                  helperText={formErrors.age ? '이 필드는 필수입니다.' : ''}
                />
              </Grid>
              <Grid item xs={12}>
                <TextField
                  required
                  fullWidth
                  name="birth"
                  label="생년월일"
                  type="date"
                  id="birth"
                  InputLabelProps={{ shrink: true }}
                  value={formValues.birth}
                  onChange={handleInputChange}
                  error={formErrors.birth}
                  helperText={formErrors.birth ? '이 필드는 필수입니다.' : ''}
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
                  value={formValues.phoneNum}
                  onChange={handleInputChange}
                  error={formErrors.phoneNum}
                  helperText={formErrors.phoneNum ? '이 필드는 필수입니다.' : ''}
                />
              </Grid>
              <Grid item xs={12}>
                <TextField
                  required
                  fullWidth
                  name="gender"
                  label="성별"
                  type="text"
                  id="gender"
                  value={formValues.gender}
                  onChange={handleInputChange}
                  error={formErrors.gender}
                  helperText={formErrors.gender ? '이 필드는 필수입니다.' : ''}
                />
              </Grid>
              <Grid item xs={12}>
                <TextField
                  required
                  fullWidth
                  name="bank_account"
                  label="은행 계좌번호"
                  type="text"
                  id="bank_account"
                  value={formValues.bank_account}
                  onChange={handleInputChange}
                  error={formErrors.bank_account}
                  helperText={formErrors.bank_account ? '이 필드는 필수입니다.' : ''}
                />
              </Grid>
              <Grid item xs={12}>
                <TextField
                  required
                  fullWidth
                  name="nickname"
                  label="닉네임"
                  type="text"
                  id="nickname"
                  value={formValues.nickname}
                  onChange={handleInputChange}
                  error={formErrors.nickname}
                  helperText={formErrors.nickname ? '이 필드는 필수입니다.' : ''}
                />
              </Grid>
            </Grid>
            <Button
              type="submit"
              fullWidth
              variant="contained"
              sx={{ mt: 3, mb: 2 }}
              disabled={!isFormValid}
            >
              회원가입
            </Button>
            <Grid container justifyContent="flex-end">
              <Grid item>
                <Link href="#" variant="body2">
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
