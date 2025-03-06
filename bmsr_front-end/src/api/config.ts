import axios from 'axios'
import { ElMessage } from 'element-plus'

// 创建axios实例
export const api = axios.create({
  baseURL: 'http://localhost:5000',
  timeout: 5000,
  headers: {
    'Content-Type': 'application/json',
  },
})

// 响应拦截器
api.interceptors.response.use(
  response => response.data,
  error => {
    ElMessage.error(error.response?.data?.message || '操作失败')
    return Promise.reject(error)
  }
)