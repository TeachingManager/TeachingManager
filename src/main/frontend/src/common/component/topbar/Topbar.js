import React, { useEffect } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import './topbar.css';
import { useAuth } from '../../Auth/AuthProvider';
import { useRecoilState, useRecoilValue } from 'recoil';
import { institueListState, nameState } from '../../Auth/recoilAtom';
import { getUserInfo } from '../../../api/institute';

export default function Topbar() {
    const location = useLocation();
    const path = location.pathname;
    const { logout, isAuthenticated } = useAuth();
    const navigate = useNavigate();

    const [name, setName] = useRecoilState(nameState); // useRecoilState로 name을 업데이트할 수 있게 함

    let pageTitle = '메인화면'; // default title
    let pagePath = '/home';
    if (path.includes('/students/attendance')) {
        pageTitle = '학생 출석현황';
        pagePath = '/students/attendance';
    } else if (path.includes('/calendar')) {
        pageTitle = '달력';
        pagePath = '/calendar';
    } else if (path.includes('/students')) {
        pageTitle = '학생';
        pagePath = '/students';
    } else if (path.includes('/teachers')) {
        pageTitle = '선생님';
        pagePath = '/teachers';
    } else if (path.includes('/lecture')) {
        pageTitle = '강의';
        pagePath = '/openlecture';
    } else if (path.includes('/openlecture')) {
        pageTitle = '강의';
        pagePath = '/openlecture';
    } else if (path.includes('/fee')) {
        pageTitle = '수강료';
        pagePath = '/fee';
    } else if (path.includes('/studentfee')) {
        pageTitle = '수강료';
        pagePath = '/studentfee';
    }

    const handleLogout = () => {
        logout();
        navigate('/login');
    };

    // 토큰 디코드 함수
    const decodeToken = (token) => {
        try {
            const base64Url = token.split('.')[1];
            const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
            const jsonPayload = decodeURIComponent(
                atob(base64)
                    .split('')
                    .map(function (c) {
                        return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
                    })
                    .join('')
            );

            return JSON.parse(jsonPayload);
        } catch (error) {
            console.error('Failed to decode token', error);
            return null;
        }
    };

    useEffect(() => {
        const accessToken = localStorage.getItem('token');
        if (accessToken) {
            const decodedToken = decodeToken(accessToken);
            if (decodedToken && decodedToken.name) {
                setName(decodedToken.name); // Recoil 상태에 name을 설정
            }
        }
    }, [setName]); // setName을 의존성으로 추가하여 한 번만 실행

    const handleTitleClick = () => {
        navigate(pagePath);
    };

    return (
        <div className="topbar-container">
            <div className="topbar-left">
                <span className="page-title" onClick={handleTitleClick} style={{ cursor: 'pointer' }}>
                    {pageTitle}
                </span>
            </div>
            <div className="topbar-right">
                <span className="user-name">{name}님</span>

                <button className="logout-button" onClick={handleLogout}>로그아웃</button>
            </div>
        </div>
    );
}
