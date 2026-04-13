import axios from 'axios'
import { useAuthStore } from '@/stores/authStore'
import router from '@/router'

const api = axios.create({
  baseURL: 'http://localhost:8080/',
})

api.interceptors.request.use((config) => {
  //   const authStore = useAuthStore()
  const token = localStorage.getItem('token')
  config.headers['Content-Type'] = 'application/json'
  config.headers['Accept'] = 'application/json'
  if (token) {
    config.headers.Authorization = `Bearer ${token}`
  }
  return config
})

api.interceptors.response.use(
  (response) => response,
  (error) => {
    const authStore = useAuthStore()

    if (error.response && error.response.status === 401) {
      const authStore = useAuthStore()
      authStore.$reset() // Clear the store
      localStorage.removeItem('token')
      router.push('/login')
    }
    return Promise.reject(error)
  },
)

export default api
