import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import App from './App';
import reportWebVitals from './reportWebVitals';
import axios from 'axios';

const instance = axios.create();

instance.interceptors.request.use((config) => {
  const token = window.accessToken || 'dummy';
  config.headers['Authorization'] = 'Bearer ' + token;
  return config;
}, (error) => Promise.reject(error));

instance.interceptors.response.use(
    (response) => response,
    (error) => Promise.reject(error));

ReactDOM.render(
    <React.StrictMode>
      <App />
    </React.StrictMode>,
    document.getElementById('root'),
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
