import { atom } from 'recoil';
import dayjs from 'dayjs';

export const isAuthenticatedState = atom({
    key: 'isAuthenticatedState', // atom의 고유 ID (다른 atom과 중복되지 않도록 설정)
    default: false, // 기본값 (초기값)
  });


export const studentListState = atom({
  key : 'studentListState',
  default: [],
})

export const institueListState = atom({
  key: 'institueListState',
  default: {}
})

export const authTokenState = atom({
  key: 'authTokenState',
  default: localStorage.getItem('token') || null, // 로컬 스토리지에서 JWT 토큰을 가져옴
});

export const teacherListState = atom({
  key: 'teacherListState',
  default: [],
})

export const lectureListState = atom({
  key : 'lectureListState',
  default: [],
})

export const selectedDateState = atom({
  key: 'selectedDateState',
  default: dayjs(), // 초기값으로 현재 날짜 설정
});