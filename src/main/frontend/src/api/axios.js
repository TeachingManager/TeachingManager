import axios from 'axios';

// Axios 기본 설정
const instance = axios.create({
    baseURL: 'http://localhost:8080/api', // 기본 API URL 설정
    headers: {
        'Content-Type': 'application/json'
    }
});

// 요청 인터셉터: 모든 요청에 자동으로 토큰을 추가
instance.interceptors.request.use(
    (config) => {
        const token = localStorage.getItem('token');
        if (token) {
            config.headers['Authorization'] = `Bearer ${token}`;
        }
        return config;
    },
    (error) => {
        return Promise.reject(error);
    }
);

// 응답 인터셉터: 응답 에러 처리
instance.interceptors.response.use(
    (response) => response,
    (error) => {
        if (error.response && error.response.status === 401) {
            // 토큰 만료 또는 권한 문제 처리 (예: 로그아웃 처리)
            console.error('인증 오류: 로그아웃이 필요합니다.');
            localStorage.removeItem('token');
            window.location.href = '/login'; // 로그아웃 후 로그인 페이지로 리다이렉트
        }
        return Promise.reject(error);
    }
);

export default instance;
