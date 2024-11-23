import { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { useAuth } from '../../common/Auth/AuthProvider';

const OAuthResponseHandler = () => {
    const { googleLogin } = useAuth();
    const navigate = useNavigate();

    useEffect(() => {
        const handleOAuthResponse = async () => {
            // 현재 URL에서 토큰 추출
            const params = new URLSearchParams(window.location.search);
            const accessToken = params.get('accessToken');
            const refreshToken = params.get('refreshToken');

            if (accessToken && refreshToken) {
                try {
                    // AuthProvider의 googleLogin 호출
                    const result = await googleLogin(accessToken, refreshToken);
                    if (result.isValid) {
                        // 로그인 성공 시 /home으로 이동
                        navigate('/home');
                    } else {
                        console.error('Google OAuth 로그인 실패');
                        navigate('/login');
                    }
                } catch (error) {
                    console.error('Google OAuth 처리 중 오류 발생:', error);
                    navigate('/login'); // 에러 발생 시 로그인 페이지로 리다이렉트
                }
            } else {
                console.error('Google OAuth: 토큰이 없습니다.');
                navigate('/login');
            }
        };

        handleOAuthResponse();
    }, [googleLogin, navigate]);

    return <div>처리 중입니다...</div>;
};

export default OAuthResponseHandler;
