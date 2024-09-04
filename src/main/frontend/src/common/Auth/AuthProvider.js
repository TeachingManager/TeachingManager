import React, { createContext, useContext, useState, useEffect } from 'react';
import { useRecoilState } from 'recoil';
import { isAuthenticatedState } from './recoilAtom'; // recoilAtoms.js에서 가져옴
import axios from 'axios';

const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
    const [isAuthenticated, setIsAuthenticated] = useRecoilState(isAuthenticatedState);
    const [token, setToken] = useState(localStorage.getItem('token') || '');

    useEffect(() => {
        if (token) {
            const isTokenValid = checkTokenValidity(token); // 토큰 유효성 검사
            if (isTokenValid) {
                setIsAuthenticated(true);
            } else {
                logout(); // 토큰이 만료되었을 경우 로그아웃 처리
            }
        }
    }, [token, setIsAuthenticated]);

    const login = async (requestData) => {
        try {
            const response = await axios.post(
                'http://localhost:8080/api/login',
                JSON.stringify(requestData),
                {
                    headers: {
                        'Content-Type': 'application/json',
                    },
                }
            );
            console.log(response);
            if (response.status === 201) {
                setToken(response.data.accessToken);
                localStorage.setItem('token', response.data.accessToken);
                setIsAuthenticated(true);
                console.log('token 설정완료');
                return true;
            }
        } catch (error) {
            console.error('Login error', error);
            setIsAuthenticated(false);
            throw error;
        }
    };

    const logout = () => {
        setToken('');
        localStorage.removeItem('token');
        setIsAuthenticated(false);
    };

    const decodeToken = (token) => {
        try {
            // JWT는 세 개의 '.'으로 나누어진 부분으로 구성되며, 두 번째 부분이 페이로드임
            const base64Url = token.split('.')[1];
            const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
            const jsonPayload = decodeURIComponent(atob(base64).split('').map(function(c) {
                return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
            }).join(''));

            return JSON.parse(jsonPayload); // 페이로드 JSON 변환
        } catch (error) {
            console.error('Failed to decode token', error);
            return null;
        }
    };

    const checkTokenValidity = (token) => {
        const decodedToken = decodeToken(token);
        if (decodedToken && decodedToken.exp) {
            const currentTime = Math.floor(Date.now() / 1000); // 현재 시간을 초 단위로 변환
            if (decodedToken.exp > currentTime) {
                return true; // 토큰이 아직 유효함
            } else {
                return false; // 토큰이 만료됨
            }
        }
        return false; // 토큰 디코딩 실패 또는 exp 필드가 없음
    };

    const value = {
        isAuthenticated,
        setIsAuthenticated,
        token,
        login,
        logout,
        checkTokenValidity, // 필요할 경우 외부에서 사용할 수 있게 추가
    };

    return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>;
};

export const useAuth = () => useContext(AuthContext);
