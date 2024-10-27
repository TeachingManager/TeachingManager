import React from 'react';

const NotFound = () => {
  return (
    <div>
      <style>
        {`
          body { margin: 0; color: #000; background: #fff; }
          .notfound-container {
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh; /* 화면 전체 높이 */
            text-align: center;
            flex-direction: column;
          }
          .error-h1 {
            border-right: 1px solid rgba(0, 0, 0, .3);
            margin: 0 20px 0 0;
            padding-right: 23px;
            font-size: 24px;
            font-weight: 500;
            line-height: 49px;
            display: inline-block;
            vertical-align: top;
          }


          @media (prefers-color-scheme: dark) {
            body { color: #fff; background: #000; }
            .error-h1 { border-right: 1px solid rgba(255, 255, 255, .3); }
          }
        `}
      </style>
      <div className="notfound-container">
        <h1 className="error-h1">404 페이지를 찾을 수 없습니다</h1>
      </div>
    </div>
  );
}

export default NotFound;
