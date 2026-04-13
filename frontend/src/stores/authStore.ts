import { defineStore } from 'pinia'
import axios from 'axios'
import router from '@/router'
import api from '@/services/api'

interface User {
  name: string
  email: string
  accountNumber: string
}

interface AuthState {
  user: User | null
  token: string | null
  error: string | null
  isInitialLoading: boolean | true
}
interface RegisterCredentials {
  name: string
  email: string
  password: string
  password_confirmation: string
}
interface LoginCredentials {
  email: string
  password: string
}

interface ChangePasswordRequest {
  currentPassword: string
  newPassword: string
  confirmPassword: string
}

export const useAuthStore = defineStore('auth', {
  state: (): AuthState => ({
    user: null,
    token: localStorage.getItem('token') || null,
    error: null,
    isInitialLoading: false,
  }),
  getters: {
    isAuthenticated: (state) => !!state.token,
  },
  actions: {
    async register(credentials: RegisterCredentials) {
      try {
        const data = new FormData()
        Object.keys(credentials).forEach((key) => {
          const value = (credentials as any)[key]

          if (value !== null && value !== undefined) {
            data.append(key, value)
          }
        })
        const response = await api.post('auth/signup', data)

        this.setToken(response.data.token)
        this.user = response.data.user
        router.push('/dashboard')
      } catch (err: any) {
        throw err
      }
    },
    async login(credentials: LoginCredentials) {
      this.error = null
      try {
        const response = await api.post('auth/login', credentials)
        this.user = response.data.user
        this.setToken(response.data.token)
        router.push('/dashboard')
      } catch (error: any) {
        this.error = error.response?.data?.message || 'Login failed. Please try again.'
      }
    },
    async logout() {
      //   try {
      //     await api.post('auth/logout')
      //   } catch (error: any) {
      //     console.error(error)
      //   } finally {
      this.setToken(null)
      this.user = null
      router.replace({ name: 'landing-page' })
      //   }
    },
    async fetchCurrentUser() {
      if (this.user || this.isInitialLoading) return
      this.isInitialLoading = true
      if (!this.token) {
        this.isInitialLoading = false
        return
      }

      try {
        const response = await api.get('auth/me')
        this.user = response.data
      } catch (error: any) {
        this.setToken(null)
        this.user = null
      } finally {
        this.isInitialLoading = false
      }
    },
    async fetchUserDetails() {
      try {
        const response = await axios.get('http://localhost:8000/api/user', {
          headers: {
            Authorization: `Bearer ${this.token}`,
          },
        })
        return response.data
      } catch (error: any) {
        this.setToken(null)
        this.user = null
      }
    },
    async updateUserDetails(user: Partial<User>) {
      try {
        const response = await api.put('api/me', user)
        this.user = response.data
        this.fetchCurrentUser()
      } catch (error: any) {
        throw error
      }
    },
    async updateUserPassword(changePasswordRequest: ChangePasswordRequest) {
      try {
        await api.put('api/me/change-password', changePasswordRequest)
        return { success: true }
      } catch (error: any) {
        throw error
      }
    },
    setToken(token: string | null) {
      this.token = token
      if (token) {
        localStorage.setItem('token', token)
      } else {
        localStorage.removeItem('token')
      }
    },
  },
})
