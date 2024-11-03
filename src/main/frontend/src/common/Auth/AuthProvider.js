import React, { createContext, useContext, useState, useEffect } from 'react';
import { useRecoilState } from 'recoil';
import { isAuthenticatedState, nameState, rolesState, instIdState } from './recoilAtom'; // recoilAtoms.js에서 가져옴
import axios from 'axios';

const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
    const [isAuthenticated, setIsAuthenticated] = useRecoilState(isAuthenticatedState);
    const [token, setToken] = useState(localStorage.getItem('token') || '');
    const [name, setName] = useRecoilState(nameState);
    const [roles, setRoles] = useRecoilState(rolesState);
    const [instId, setInstId] = useRecoilState(instIdState);

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
                `${process.env.REACT_APP_API_BASE_URL}/api/login`,
                JSON.stringify(requestData),
                {
                    headers: {
                        'Content-Type': 'application/json',
                    },
                }
            );
            console.log(response);
            if (response.status === 201) {
                const accessToken = response.data.accessToken;
                if (checkTokenValidity(accessToken)) {
                    setToken(accessToken);
                    localStorage.setItem('token', accessToken);
                    setIsAuthenticated(true);

                    // 토큰 디코드 후 name, roles, instId 설정
                    const decodedToken = decodeToken(accessToken);
                    if (decodedToken) {
                        setName(decodedToken.name);
                        setRoles(decodedToken.roles);
                        setInstId(decodedToken.inst_id || null);
                    }
                    console.log('토큰 설정 및 사용자 정보 저장 완료');
                    return { isVaild: true };
                } else {
                    return { isVaild: false, response };
                }
            }
        } catch (error) {
            console.error('Login error', error);
            setIsAuthenticated(false);
            const response = error.response ? error.response : null;
            return { isVaild: false, response };
        }
    };

    const logout = () => {
        setToken('');
        localStorage.removeItem('token');
        setIsAuthenticated(false);
    };

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

    const checkTokenValidity = (token) => {
        const decodedToken = decodeToken(token);
        if (decodedToken && decodedToken.exp) {
            const currentTime = Math.floor(Date.now() / 1000);
            return decodedToken.exp > currentTime;
        }
        return false;
    };

    const value = {
        isAuthenticated,
        setIsAuthenticated,
        token,
        login,
        logout,
        checkTokenValidity,
    };

    return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>;
};

export const useAuth = () => useContext(AuthContext);
