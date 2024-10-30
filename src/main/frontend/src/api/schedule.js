import axios from 'axios';

export const addStudent = async (studentData) => {
    const token = localStorage.getItem('token');
    if (isTokenExpired(token)) {
        return true;
    }
    console.log(studentData);
    if (!token) {
      console.error('토큰이 없습니다. 로그인 상태를 확인해주세요.');
      return;
    }
  
    try {
      const response = await axios.post(`${process.env.REACT_APP_API_BASE_URL}/api/schedule`, studentData, {
        headers: {
          'Authorization': `Bearer ${token}`,
          'Content-Type': 'application/json'  // JSON 형식으로 명시적으로 지정
        }
      });
  
      console.log('일정 추가 성공:', response.data);
      return response;
    } catch (error) {
      if (error.response) {
        console.error('서버 응답 오류:', error.response.data);
      } else if (error.request) {
        console.error('요청 오류:', error.request);
      } else {
        console.error('설정 오류:', error.message);
      }
      throw error;
    }
};
