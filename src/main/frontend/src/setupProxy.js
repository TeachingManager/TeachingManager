const { createProxyMiddleware } = require('http-proxy-middleware');

module.exports = function(app) {
  app.use(
    '/api', // '/api'로 시작하는 모든 경로를 프록시
    createProxyMiddleware({
      target: process.env.REACT_APP_API_BASE_URL, // 실제 서버 주소
      changeOrigin: true,
    })
  );
};
