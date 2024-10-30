import axios from 'axios';

export const addLecture = async(LectureData) => {
    const token = localStorage.getItem('token');
    // 토큰 검사하고 이상하면 false 반환
    const requestData = {
        "category": LectureData.subject,
        "name": LectureData.name,
        "grade": LectureData.grade,
        "teacherId": LectureData.teacher,
        "time": LectureData.time,
        "fee": LectureData.fee
    }
    try {
        const response = await axios.post('http://localhost:8080/api/lectures', requestData, {
            headers: {
              'Authorization': `Bearer ${token}`,
              'Content-Type': 'application/json'  // JSON 형식으로 명시적으로 지정
            }
          });
          return {isValid: true, response};
    } catch (error) {
        console.error(error)
        return {isValid:false};
    }
}


export const getLecture = async(LectureData) => {
    const token = localStorage.getItem('token');

    try {
        const response = await axios.get('http://localhost:8080/api/lectures', {
            headers : {
                'Authorization': `Bearer ${token}`,

            }
        })
        console.log(response.data)
        return response.data
    } catch (error) {
        console.error(error)
        return false;
    }
}

export const getOneLecture = async(pk) => {
    const token = localStorage.getItem('token');

    try {
        const response = await axios.get(`http://localhost:8080/api/lectures/${pk}`, {
            headers : {
                'Authorization': `Bearer ${token}`,

            }
        })
        console.log(response.data)
        return response.data
    } catch (error) {
        console.error(error)
        return false;
    }
}








export const deleteLecture = async (pk)=> {
    const token = localStorage.getItem('token')
    // if(isTokenExpired(token)){
    //     return true;
    // }
    console.log(pk)
    console.log(token)
    try {
        const response = await axios.put(`http://localhost:8080/api/delete/lectures/${pk}`, {}, {
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

export const changeLecture = async (LectureData, pk) => {
    const token = localStorage.getItem('token')
    // if(isTokenExpired(token)){
    //     return true;
    // }
    console.log(LectureData)

    const formmatedLectureData = {
        name : LectureData.name,
        category : LectureData.subject,
        grade : LectureData.grade,
        fee : LectureData.fee,
        time : LectureData.time,
        teacherId : LectureData.teacher
    }
    try {
        const response = await axios.put(`http://localhost:8080/api/lectures/${pk}`, formmatedLectureData, {
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

// 수강 파트

export const enrollLecture = async (requestDTO) => {
    const token = localStorage.getItem('token')

    const {lecture_id, year, month, studentIdList} = requestDTO;
    const request = {
        "studentIdList" : studentIdList
    }
    console.log(`requestDTO : ${lecture_id, year}`)
    console.log(request)
    try {
        const response = await axios.post(`http://localhost:8080/api/enroll?lecture_id=${lecture_id}&year=${year}&month=${month}`,request, 
              {
                headers: {
              'Authorization': `Bearer ${token}`,
              'Content-Type': 'application/json'  
            }
          });

          console.log(response)
          return {isValid: true, response};
    } catch (error) {
        console.error(error)
        return {isValid:false, error};
    }
}


// 특정달 개설된 강의 조희

export const fetchLectureByMonth = async (data) => {
    const token = localStorage.getItem('token');

    const {year, month} = data;
    try {
        const response = await axios.get(`http://localhost:8080/api/enroll/lectures?year=${year}&month=${month}`, {
            headers : {
                'Authorization': `Bearer ${token}`,
            }
        })
        console.log(response.data)
        return response.data
    } catch (error) {
        console.error(error)
        return error
    }
}

