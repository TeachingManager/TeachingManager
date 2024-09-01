// src/common/Auth/AuthProvider.js
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
            setIsAuthenticated(true);
        }
    }, [token, setIsAuthenticated]);
    
    const login = async (requestData) => {
        // try {
        //     const response = await fetch('http://localhost:5000/api/login', {
        //         method: 'POST',
        //         headers: {
        //             'Content-Type': 'application/json',
        //         },
        //         body: JSON.stringify({ email, password }),
        //     });
        //     const data = await response.json();
        //     if (response.ok) {
        //         setToken(data.token);
        //         localStorage.setItem('token', data.token);
        //         setIsAuthenticated(true);
        //     } else {
        //         throw new Error(data.message || 'Login failed');
        //     }
        // } catch (error) {
        //     console.error('Login error', error);
        //     setIsAuthenticated(false);
        //     throw error;
        // }

        try{
            const response = await axios.post(
                'http://localhost:8080/api/login', 
                JSON.stringify(requestData), 
                {
                    headers: {
                        'Content-Type': 'application/json'
                    }
                }
            );
            console.log(response)
            if(response.status === 201) {
                setToken(response.data.accessToken)
                localStorage.setItem('token', response.data.accessToken);
                setIsAuthenticated(true);
                console.log("token 설정완료")
                return true;
                
            }

        } catch(error) {
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

    const value = {
        isAuthenticated,
        setIsAuthenticated,
        token,
        login,
        logout
    };

    return (
        <AuthContext.Provider value={value}>
            {children}
        </AuthContext.Provider>
    );
};

export const useAuth = () => useContext(AuthContext);
