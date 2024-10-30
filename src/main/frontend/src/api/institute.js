import axios from 'axios';

export const registerUser = async (userData) => {
    try {
        const response = await axios.post(`${process.env.REACT_APP_API_BASE_URL}/api/institute`, userData);
        console.log(response);
        return response;
    } catch (error) {
        throw error;
    }
};

export const loginUser = async (requestData) => {
    try {
        const response = await axios.post(
            `${process.env.REACT_APP_API_BASE_URL}/api/login`, 
            JSON.stringify(requestData), 
            {
                headers: {
                    'Content-Type': 'application/json'
                }
            }
        );
        console.log(response);
        return response.data;
    } catch (error) {
        console.error(error);
    }
};

export const getUserInfo = async () => {
    const token = localStorage.getItem('token');
    console.log(`기관조회 토큰${token}`);
    try {
        const response = await axios.get(`${process.env.REACT_APP_API_BASE_URL}/api/institute`, {
            headers: {
                'Authorization': `Bearer ${token}`
            }
        });
        
        console.log(response.data);
        return response.data;
    } catch (error) {
        console.error(error);
        throw error;
    }
};

export const registerTeacher = async (userData, isJson = true) => {
    try {
        const response = await axios.post(
            `${process.env.REACT_APP_API_BASE_URL}/api/teacher`, 
            isJson ? JSON.stringify(userData) : userData,
            {
                headers: {
                    'Content-Type': isJson ? 'application/json' : 'text/plain'
                }
            }
        );
        console.log(response);
        return response;
    } catch (error) {
        throw error;
    }
};

export const proveUser = async (userData) => {
    try {
        const response = await axios.post(
           `${process.env.REACT_APP_API_BASE_URL}/api/email/initial/prove`,
           userData
        );
        return response;
    } catch (error) {
        console.error(error);
    }
};

export const inviteTeacher = async (userData) => {
    const token = localStorage.getItem('token');
    const fetchUserInfo = await getUserInfo();
    const institute_email = fetchUserInfo.institute_email;

    const requestdata = {
        "institute_email": institute_email,
        "teacher_email": userData
    };
    try {
        const response = await axios.post(`${process.env.REACT_APP_API_BASE_URL}/api/invite/teacher`, requestdata, {
            headers: {
                'Authorization': `Bearer ${token}`
            }
        });
        return { isValid: true, response };
    } catch (error) {
        console.error(error);
        if (error.status === 404)
            return { isValid: false };
    }
};

export const getTeachersInfo = async () => {
    const token = localStorage.getItem('token');

    try {
        const response = await axios.get(`${process.env.REACT_APP_API_BASE_URL}/api/teacher`, {
            headers: {
                'Authorization': `Bearer ${token}`
            }
        });
        return response;
    } catch (error) {
        console.error(error);
    }
};

export const sendRecoverPasswordEmail = async (requestDTO) => {
    const token = localStorage.getItem('token');

    try {
        const response = await axios.post(`${process.env.REACT_APP_API_BASE_URL}/api/password/change`, requestDTO, {
            headers : {
                'Authorization': `Bearer ${token}`
            }
       })
       return response
    } catch(error){
        console.error("이메일 전송 실패", error)
    }
}

export const changepassword = async (requestDTO, token) => {
    try {
        const response = await axios.post(
            `${process.env.REACT_APP_API_BASE_URL}/password/change?token=${token}`, 
            requestDTO
        );
        return response;
    } catch (error) {
        console.error("비밀번호 변경 실패", error)
    }
}