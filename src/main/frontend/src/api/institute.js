import axios from 'axios';

export const registerUser = async (userData) => {
    try {
        const response = await axios.post('http://localhost:8080/api/institute', userData);
        console.log(response);
        return response;
    } catch (error) {
        throw error;  // 필요에 따라 에러를 다시 던질 수 있습니다.
    }
};
