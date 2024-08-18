import { atom } from 'recoil';

export const isAuthenticatedState = atom({
    key: 'isAuthenticatedState', // atom의 고유 ID (다른 atom과 중복되지 않도록 설정)
    default: false, // 기본값 (초기값)
  });