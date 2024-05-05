import logo from './logo.svg';
import './App.css';
import { useEffect, useState } from "react";
import axios from 'axios';

function App() {
    const [students, setStudents] = useState([]);
    const [formData, setFormData] = useState({
        name: '',
        age: '',
        grade: '',
        phoneNumber: '',
        parentName: '',
        parentNumber: '',
        gender: '',
        level: ''
    });

    const [editformData, setEditFormData] = useState({
        name: '',
        age: '',
        grade: '',
        phoneNumber: '',
        parentName: '',
        parentNumber: '',
        gender: '',
        level: ''
    });

    const [editIndex, setEditIndex] = useState(null); // 수정 중인 학생의 인덱스를 저장

    useEffect(() => {
        axios.get('/api/students')
            .then(response => {
                setStudents(response.data);
            })
            .catch(error => {
                console.error('Error fetching student data:', error);
            })
    }, []);

    const handleChange = (event) => {
        const { name, value } = event.target;
        setFormData({
            ...formData,
            [name]: value
        });
    };

    const editHandleChange = (event) => {
        const { name, value } = event.target;
        setEditFormData({
            ...editformData,
            [name]: value
        });
    };

    const handleSubmit = async () => {
        try {
            const response = await axios.post('/api/students', formData);
            console.log('Response:', response.data);
        } catch (error) {
            console.error('Error:', error);
        }
    };

    const handleDelete = async (id) => {
        try {
            await axios.post(`/api/delete/students/${id}`);
            const updatedStudents = students.filter(student => student.id !== id);
            setStudents(updatedStudents); // 삭제된 학생을 제외한 목록으로 업데이트
        } catch (error) {
            console.error('Error:', error);
        }
    };

    const handleEditToggle = (index) => {
        setEditIndex(index); // 수정 중인 학생의 인덱스 설정
    };

    const handleEditCancel = () => {
        setEditIndex(null); // 수정 취소 시 수정 중인 학생 인덱스 초기화
    };

    const handleEditSubmit = async (id) => {
        try {
            await axios.put(`/api/students/${id}`, editformData); // 해당 학생의 정보를 수정하는 PUT 요청
            // 수정이 완료되면 수정 중인 상태를 초기화
            setEditIndex(null);
        } catch (error) {
            console.error('Error:', error);
        }
    };

    return (
        <div>
            <h1>학생 추가하기</h1>
            {/* 입력 폼 */}
            <input
                type="text"
                name="name"
                value={formData.name}
                onChange={handleChange}
                placeholder="이름"
            />
            <input
                type="number"
                name="age"
                value={formData.age}
                onChange={handleChange}
                placeholder="나이"
            />
            <input
                type="number"
                name="grade"
                value={formData.grade}
                onChange={handleChange}
                placeholder="학년"
            />
            <input
                type="text"
                name="phoneNumber"
                value={formData.phoneNumber}
                onChange={handleChange}
                placeholder="전화번호"
            />
            <input
                type="text"
                name="parentName"
                value={formData.parentName}
                onChange={handleChange}
                placeholder="부모님 성함"
            />
            <input
                type="text"
                name="parentNumber"
                value={formData.parentNumber}
                onChange={handleChange}
                placeholder="부모님 전화번호"
            />
            <input
                type="text"
                name="gender"
                value={formData.gender}
                onChange={handleChange}
                placeholder="성별"
            />
            <input
                type="text"
                name="level"
                value={formData.level}
                onChange={handleChange}
                placeholder="레벨"
            />
            <button onClick={handleSubmit}>추가</button>

            <h1>학생리스트</h1>
            {/* 학생 리스트 */}
            {students.map((student, index) => (
                <div key={index}>
                    {/* 학생 정보 */}
                    id: {student.id} &nbsp;  이름: {student.name}, &nbsp;  나이: {student.age}, &nbsp;  학년: {student.grade},&nbsp;   전화번호: {student.phoneNumber},   &nbsp;부모님 성함: {student.parentName}, &nbsp;  부모님 전화번호: {student.parentNumber},  &nbsp; 성별: {student.gender},  &nbsp; 수준: {student.level}

                    {/* 삭제 버튼 */}
                    <button onClick={() => handleDelete(student.id)}>삭제</button>
                    {/* 수정 버튼 */}
                    <button onClick={() => editIndex === index ? handleEditCancel() : handleEditToggle(index)}>
                        {editIndex === index ? '취소' : '수정'}
                    </button>
                    {/* 수정 폼 */}
                    {editIndex === index && (
                        <div>
                            <h1>수정하기</h1>
                            {/* 수정 폼 입력 */}
                            <input
                                type="text"
                                name="name"
                                value={editformData.name}
                                onChange={editHandleChange}
                                placeholder="이름"
                            />
                            <input
                                type="number"
                                name="age"
                                value={editformData.age}
                                onChange={editHandleChange}
                                placeholder="나이"
                            />
                            <input
                                type="number"
                                name="grade"
                                value={editformData.grade}
                                onChange={editHandleChange}
                                placeholder="학년"
                            />
                            <input
                                type="text"
                                name="phoneNumber"
                                value={editformData.phoneNumber}
                                onChange={editHandleChange}
                                placeholder="전화번호"
                            />
                            <input
                                type="text"
                                name="parentName"
                                value={editformData.parentName}
                                onChange={editHandleChange}
                                placeholder="부모님 성함"
                            />
                            <input
                                type="text"
                                name="parentNumber"
                                value={editformData.parentNumber}
                                onChange={editHandleChange}
                                placeholder="부모님 전화번호"
                            />
                            <input
                                type="text"
                                name="gender"
                                value={editformData.gender}
                                onChange={editHandleChange}
                                placeholder="성별"
                            />
                            <input
                                type="text"
                                name="level"
                                value={editformData.level}
                                onChange={editHandleChange}
                                placeholder="레벨"
                            />
                            <button onClick={() => handleEditSubmit(student.id)}>수정완료</button>
                        </div>
                    )}
                </div>
            ))}
        </div>
    )
}

export default App;
