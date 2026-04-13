import { defineStore } from 'pinia'
import api from '@/services/api'
import router from '@/router'

interface DashboardData {
  accountNumber: string
  totalBalance: number

  totalSavings: number
  savingsToday: number
  savingsThisWeek: number
  savingsThisMonth: number

  totalExpenses: number
  expensesToday: number
  expensesThisWeek: number
  expensesThisMonth: number

  recentActivities: TransactionDto[]
}

interface TransactionDto {
  id: number
  title: string
  description: string
  amount: number
  type: string
  category: string
  timestamp: string
}

interface TopUpRequest {
  cardNumber: string
  amount: number
  cvv: string
  expiry: string
}

interface TransferRequest {
  recipientAccountNumber: string
  amount: number
  description: string
}

export const useAccountStore = defineStore('account', {
  state: () => ({
    dashboardData: null as DashboardData | null,
    transactions: [] as TransactionDto[],
    loading: false,
    error: null as string | null,
    currentPage: 0,
    totalPages: 0,
    pageSize: 10,
  }),
  actions: {
    async fetchDashboardData() {
      this.loading = true
      try {
        const response = await api.get('api/dashboard')
        this.dashboardData = response.data
        console.log('Dashboard Data Loaded:', this.dashboardData)
      } catch (error: any) {
        console.error('Fetch failed:', error.response?.status, error.message)
      } finally {
        this.loading = false
      }
    },
    async topUp(topUpRequest: TopUpRequest) {
      this.loading = true
      try {
        const response = await api.post('api/top-up', topUpRequest)
        this.fetchDashboardData()
        router.replace('/dashboard')
      } catch (error: any) {
        console.error('Top-up failed:', error.response?.status, error.message)
      } finally {
        this.loading = false
      }
    },
    async transfer(transferRequest: TransferRequest) {
      this.loading = true
      try {
        const response = await api.post('/api/transfer', transferRequest)
        this.fetchDashboardData()
        router.replace('/dashboard')
      } catch (err: any) {
        throw err
      } finally {
        this.loading = false
      }
    },
    async fetchTransactions(page: number) {
      this.loading = true
      try {
        const response = await api.get('api/history', {
          params: {
            page: page,
            pageSize: 10,
          },
        })
        this.transactions = response.data.content
        this.totalPages = response.data.totalPages
        this.currentPage = response.data.number
        console.log('Transactions Loaded:', this.transactions)
      } catch (error: any) {
        console.error('Fetch failed:', error.response?.status, error.message)
      } finally {
        this.loading = false
      }
    },
  },
})
