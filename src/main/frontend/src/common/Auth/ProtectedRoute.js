import React, { useEffect, useState } from 'react';
import { Navigate } from 'react-router-dom';
import { useRecoilValue } from 'recoil';
import { isAuthenticatedState } from './recoilAtom';

const ProtectedRoute = ({ children }) => {
  const isAuthenticated = useRecoilValue(isAuthenticatedState);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    if (isAuthenticated !== undefined) {
      setLoading(false);
    }
  }, [isAuthenticated]);

  if (loading) {
    // 로딩 중일 때 표시할 UI를 반환합니다
    return <div>Loading...</div>;
  }

  if (!isAuthenticated) {
    return <Navigate to="/login" replace />;
  }

  return children;
};

export default ProtectedRoute;
