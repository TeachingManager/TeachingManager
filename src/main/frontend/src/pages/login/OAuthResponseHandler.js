import { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { useAuth } from '../../common/Auth/AuthProvider';

const OAuthResponseHandler = () => {
    const { googleLogin } = useAuth();
    const navigate = useNavigate();

    useEffect(() => {
        // 현재 URL에서 토큰 추출
        const params = new URLSearchParams(window.location.search);
        const accessToken = params.get('accessToken');
        const refreshToken = params.get('refreshToken');

        if (accessToken && refreshToken) {
            // AuthProvider의 googleLogin 호출
            googleLogin(accessToken, refreshToken).then((result) => {
                if (result.isValid) {
                    // 로그인 성공 시 /home으로 이동
                    navigate('/home');
                } else {
                    console.error('Google OAuth 로그인 실패');
                    navigate('/login');
                }
            });
        } else {
            console.error('Google OAuth: 토큰이 없습니다.');
            navigate('/login');
        }
    }, [googleLogin, navigate]);

    return <div>처리 중입니다...</div>;
};

export default OAuthResponseHandler;
