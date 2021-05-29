/* eslint-disable */
const createProxyMiddleware = require('http-proxy-middleware');
const morgan = require('morgan');

module.exports = (app) => {
  app.use(createProxyMiddleware('/auth', {
    target: process.env.API_BASEURL || 'http://localhost:9999/auth',
    changeOrigin: true,
    pathRewrite: {
      '^/auth': '/',
    },
  }));
  app.use(morgan('combined'));
};
/* eslint-enable */
