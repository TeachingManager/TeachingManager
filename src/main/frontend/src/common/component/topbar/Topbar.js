import React, { useEffect } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import './topbar.css';
import { useAuth } from '../../Auth/AuthProvider';
import { useRecoilState } from 'recoil';
import { institueListState } from '../../Auth/recoilAtom';
import { getUserInfo } from '../../../api/institute';
export default function Topbar() {
    const location = useLocation();
    const path = location.pathname;
    const { logout, isAuthenticated } = useAuth();
    const navigate = useNavigate();
    const [userInfo, setUserInfo] = useRecoilState(institueListState)

    let pageTitle = '메인화면'; // default title
    if (path.includes('/students/attendance')) {
        pageTitle  = '학생 출석현황';
    } else if (path.includes('/calendar')) {
        pageTitle = '달력';
    } else if (path.includes('/students')) {
        pageTitle = '학생';
    } else if (path.includes('/teachers')) {
        pageTitle = '선생님';
    } else if (path.includes('/lecture')) {
        pageTitle  = '강의';
    } else if (path.includes('/openlecture')) {
        pageTitle  = '강의';
    }
    else if (path.includes('/fee')) {
        pageTitle  = '수강료';
    }else if (path.includes('/studentfee')) {
        pageTitle  = '수강료';
    }

    const handleLogout = ()=> {
        logout();
        navigate('/login')

    }
    useEffect(() => {
        // 비동기 함수를 선언하고 호출
        const fetchUserInfo = async () => {
            try {
                const userInfo = await getUserInfo(); // getUserInfo 호출
                setUserInfo(userInfo); // Recoil 상태에 학원 정보 저장
                console.log("User info:", userInfo);
            } catch (error) {
                console.error("Error fetching user info:", error);
            }
        };

        fetchUserInfo(); // 비동기 함수 호출
    }, []);
    return (
        <div className="topbar-container">
            <div className="topbar-left">
                <span>{pageTitle}</span>
            </div>
            <div className="topbar-right">
                <span className="user-name">{userInfo.institute_name}님</span>

                <button className="logout-button" onClick={handleLogout}>로그아웃</button>
            </div>
        </div>
    );
}
