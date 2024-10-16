import { React, useState } from 'react';
import Avatar from '@mui/material/Avatar';
import Button from '@mui/material/Button';
import CssBaseline from '@mui/material/CssBaseline';
import TextField from '@mui/material/TextField';
import FormControlLabel from '@mui/material/FormControlLabel';
import Checkbox from '@mui/material/Checkbox';
import Link from '@mui/material/Link';
import Grid from '@mui/material/Grid';
import Box from '@mui/material/Box';
import LockOutlinedIcon from '@mui/icons-material/LockOutlined';
import Typography from '@mui/material/Typography';
import Container from '@mui/material/Container';
import { createTheme, ThemeProvider } from '@mui/material/styles';
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogContentText from '@mui/material/DialogContentText';
import DialogTitle from '@mui/material/DialogTitle';
import { useAuth } from '../../common/Auth/AuthProvider';
import { useNavigate } from 'react-router-dom';
import { useGoogleReCaptcha } from 'react-google-recaptcha-v3';
import { getUserInfo, loginUser, proveUser } from '../../api/institute';
import { useRecoilState } from 'recoil';
import { institueListState } from '../../common/Auth/recoilAtom';

const defaultTheme = createTheme();

export default function SignIn() {
  const { executeRecaptcha } = useGoogleReCaptcha();
  const { login, isAuthenticated } = useAuth();
  const [error, setError] = useState('');
  const navigate = useNavigate();
  const [institute, setInstitute] = useRecoilState(institueListState);
  const [open, setOpen] = useState(false);  // 모달 열림 상태
  const [email, setEmail] = useState('');   // 입력한 이메일 상태

  console.log("login page is");
  console.log(isAuthenticated);

  const handleLogin = async (event) => {
    event.preventDefault();

    if (!executeRecaptcha) {
      console.log("리캡차가 활성화 되지 않았습니다.");
      return;
    }

    const token = await executeRecaptcha('login');

    const { email, password } = event.target;
    console.log(email.value, password.value);

    const requestData = {
      recaptchaResponse: token,
      email: email.value,
      password: password.value,
    };

    console.log(requestData);

    const result = await login(requestData);
    console.log(result);
    if (result.isVaild === true) {
      navigate('/home');
    } else {
      if (result.response && result.response.data) {
        alert(`${result.response.data}`);
      } else {
        alert('존재하지 않는 아이디입니다.');
      }
    }
  };

  // 계정 활성화 모달 열기
  const handleActivateAccount = () => {
    setOpen(true);
  };

  // 모달 닫기
  const handleClose = () => {
    setOpen(false);
  };

  // 이메일 전송 핸들러
  const handleSendEmail = () => {
    const emailData = { email }; // { "email": "example@gmail.com" } 형식으로 객체 생성
    proveUser(emailData)  // proveUser 함수로 이메일 객체 전송
      .then(response => {
        console.log("이메일 활성화 요청 성공:", response);
        alert(`${response.data.message}`)
      })
      .catch(error => {
        console.error("이메일 활성화 요청 실패:", error);
      });
    setOpen(false);
  };

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
            로그인
          </Typography>
          {error && (
            <Typography variant="body2" color="error">
              {error}
            </Typography>
          )}
          <Box component="form" onSubmit={handleLogin} noValidate sx={{ mt: 1 }}>
            <TextField
              margin="normal"
              required
              fullWidth
              id="email"
              label="이메일 주소"
              name="email"
              autoComplete="email"
              autoFocus
            />
            <TextField
              margin="normal"
              required
              fullWidth
              name="password"
              label="비밀번호"
              type="password"
              id="password"
              autoComplete="current-password"
            />
            <FormControlLabel
              control={<Checkbox value="remember" color="primary" />}
              label="아이디 기억하기"
            />
            <Button
              type="submit"
              fullWidth
              variant="contained"
              sx={{ mt: 3, mb: 2 }}
            >
              로그인
            </Button>
            <Grid container>
              <Grid item xs>
                <Link href="#" variant="body2">
                  비밀번호 찾기
                </Link>
              </Grid>

              <Grid item>
                <Grid container spacing={1}>
                  <Grid item>
                    <Link href="/signup/institute" variant="body2">
                      {"학원 회원가입"}
                    </Link>
                  </Grid>
                  <Grid item>
                    <Link href="/signup/teacher" variant="body2">
                      {"선생님 회원가입"}
                    </Link>
                  </Grid>
                </Grid>
              </Grid>
            </Grid>

            <Grid container>
              <Grid item>
                <Grid item>
                  <Link variant="body2" onClick={handleActivateAccount} style={{ cursor: 'pointer' }}>
                    {"계정 활성화"}
                  </Link>
                </Grid>
              </Grid>
            </Grid>

          </Box>
        </Box>
      </Container>

      {/* 계정 활성화 모달 */}
      <Dialog open={open} onClose={handleClose}>
        <DialogTitle>계정 활성화</DialogTitle>
        <DialogContent>
          <DialogContentText>
            활성화할 계정의 이메일을 입력해주세요.
          </DialogContentText>
          <TextField
            autoFocus
            margin="dense"
            label="이메일 주소"
            type="email"
            fullWidth
            variant="standard"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
          />
        </DialogContent>
        <DialogActions>
          <Button onClick={handleClose}>취소</Button>
          <Button onClick={handleSendEmail}>전송</Button>
        </DialogActions>
      </Dialog>
    </ThemeProvider>
  );
}