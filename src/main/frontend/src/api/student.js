import axios from 'axios'
import {jwtDecode} from 'jwt-decode';
import { useNavigate } from 'react-router-dom';

function isTokenExpired(token) {
    if (!token) return true;
  
    try {
      const decodedToken = jwtDecode(token);
  
      if (!decodedToken.exp) {
        console.error("토큰에 만료 시간(exp)이 없습니다.");
        return true;
      }
  
      const currentTime = Math.floor(Date.now() / 1000); // 초 단위로 현재 시간 계산
      return decodedToken.exp < currentTime; // 만료 시간이 현재 시간보다 작으면 true 반환
    } catch (error) {
      console.error("토큰 디코딩 중 오류 발생:", error);
      return true; // 디코딩 오류 발생 시 만료된 것으로 간주
    }
  }
  

export const addStudent = async (studentData) => {
    const token = localStorage.getItem('token');
    if(isTokenExpired(token)){
        return true;
    }
    console.log(studentData)
    if (!token) {
      console.error('토큰이 없습니다. 로그인 상태를 확인해주세요.');
      return;
    }
  
    try {
      const response = await axios.post('http://localhost:8080/api/students', studentData, {
        headers: {
          'Authorization': `Bearer ${token}`,
          'Content-Type': 'application/json'  // JSON 형식으로 명시적으로 지정
        }
      });
  
      console.log('학생 추가 성공:', response.data);
      return response;
    } catch (error) {
      if (error.response) {
        // 서버 응답이 있는 경우
        console.error('서버 응답 오류:', error.response.data);
      } else if (error.request) {
        // 요청은 전송되었으나 응답이 없는 경우
        console.error('요청 오류:', error.request);
      } else {
        // 요청 설정에서 발생한 오류
        console.error('설정 오류:', error.message);
      }
      throw error; // 에러를 다시 던져서 상위에서 처리할 수 있게 함
    }
  };
  


export const getStudent = async ()=> {
    const token = localStorage.getItem('token')
    if(isTokenExpired(token)){
        return true;
    }
    try {
        const response = await axios.get('http://localhost:8080/api/students', {
            headers: {
                'Authorization': `Bearer ${token}`
            }
        })
        
        console.log(response.data)
        return(response.data)
    } catch(error) {
        console.error(error);
        throw error;
    }

}

export const deleteStudent = async (pk)=> {
    const token = localStorage.getItem('token')
    if(isTokenExpired(token)){
        return true;
    }
    console.log(pk)
    console.log(token)
    try {
        const response = await axios.put(`http://localhost:8080/api/delete/students/${pk}`, {}, {
            headers: {
                'Authorization': `Bearer ${token}`
            }
        })
        return response;
    } catch (error) {
        console.error(error)
        throw error;
    }
}

export const changeStudent = async (pk, studentData) => {
    const token = localStorage.getItem('token')
    if(isTokenExpired(token)){
        return true;
    }
    try {
        const response = await axios.put(`http://localhost:8080/api/students/${pk}`, studentData, {
            headers: {
                'Authorization': `Bearer ${token}`
            }
        })
        return response;
    } catch (error) {
        console.error(error)
    }
}