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





export const registerTeacher = async (userData, isJson = true) => {
    try {
        const response = await axios.post(
            'http://localhost:8080/api/teacher', 
            isJson ? JSON.stringify(userData) : userData, // JSON 타입일 때 stringify
            {
                headers: {
                    'Content-Type': isJson ? 'application/json' : 'text/plain' // JSON 타입이면 application/json 사용
                }
            }
        );
        console.log(response);
        return response;
    } catch (error) {
        throw error;
    }
};



export const proveUser = async(userData)=> {
    try {
        const response = await axios.post(
           'http://localhost:8080/api/email/initial/prove',
           userData,
        )

        return response;
    }
    catch(error){
        console.error(error);
    }
}

export const inviteTeacher = async(userData) => {
    const token = localStorage.getItem('token')

    const fetchUserInfo = await getUserInfo();
    const institute_email = fetchUserInfo.institute_email;

    const requestdata = {
        "institute_email" : institute_email,
        "teacher_email" : userData,
    }
    try{
        const response = await axios.post('http://localhost:8080/api/invite/teacher', requestdata, {
            headers: {
                'Authorization': `Bearer ${token}`
            }
        })
        return {isValid : true, response};

    }
    catch(error){
        console.error(error);
        if(error.status === 404)
            return {isValid: false};
    }
}

export const getTeachersInfo = async ()=> {
    const token = localStorage.getItem('token')

    try {
        const response = await axios.get('http://localhost:8080/api/teacher', {
            headers: {
                'Authorization': `Bearer ${token}`
            }
        })

        return response;
    } catch (error) {
        console.error(error);
    }
}