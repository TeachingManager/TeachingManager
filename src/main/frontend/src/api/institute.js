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


export const loginUser = async (requestData) => {
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
        return response.data;
    } catch(error) {
        console.error(error)
    }
}

export const getUserInfo = async() => {
    const token = localStorage.getItem('token')
    console.log(`기관조회 토큰${token} `)
    try {
        const response = await axios.get('http://localhost:8080/api/institute', {
            headers: {
                'Authorization': `Bearer ${token}`
            }
        })
        
        console.log(response.data)
        return(response.data)
    } catch(error) {
        console.error(error);
        throw error;
    }}