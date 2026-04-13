<script setup lang="ts">
import RecentActivities from '@/components/Dashboard/RecentActivities.vue'
import TotalBalance from '@/components/Dashboard/TotalBalance.vue'
import TotalExpenses from '@/components/Dashboard/TotalExpenses.vue'
import TotalSavings from '@/components/Dashboard/TotalSavings.vue'
import AuthLayout from '@/components/layouts/AuthLayout.vue'
import { useAuthStore } from '@/stores/authStore'
import { useAccountStore } from '@/stores/accountStore'
import { storeToRefs } from 'pinia'
import { onMounted } from 'vue'
import { useNotificationStore } from '@/stores/notificationStore'
import LoadingSpinnerPage from '@/components/ui/LoadingSpinnerPage.vue'

const authStore = useAuthStore()
const notificationStore = useNotificationStore()
const accountStore = useAccountStore()
const { dashboardData, loading } = storeToRefs(accountStore)
const { user } = storeToRefs(authStore)

const fetchDashboardData = async () => {
  try {
    await accountStore.fetchDashboardData()
  } catch (error) {
    console.error(error)
  } finally {
    loading.value = false
  }
}

onMounted(() => {
  fetchDashboardData()
})
</script>

<template>
  <AuthLayout>
    <div v-if="loading" class="flex flex-1 items-center justify-center min-h-[400px]">
      <LoadingSpinnerPage />
    </div>
    <template v-else>
      <div class="flex flex-col gap-4">
        <h1 v-if="user" class="text-2xl font-semibold text-gray-800">Welcome, {{ user?.name }}</h1>
      </div>
      <TotalBalance :balance="dashboardData?.totalBalance" />
      <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
        <TotalSavings
          :total-savings="dashboardData?.totalSavings"
          :savings-today="dashboardData?.savingsToday"
          :savings-this-week="dashboardData?.savingsThisWeek"
          :savings-this-month="dashboardData?.savingsThisMonth"
        />
        <TotalExpenses
          :total-expenses="dashboardData?.totalExpenses"
          :expenses-today="dashboardData?.expensesToday"
          :expenses-this-week="dashboardData?.expensesThisWeek"
          :expenses-this-month="dashboardData?.expensesThisMonth"
        />
      </div>
      <RecentActivities :recent-activities="dashboardData?.recentActivities" />

      <!-- <div class="border border-gray-200 shadow-md bg-white p-5 rounded-xl">
        <h1>Your balance history</h1>
      </div> -->
    </template>
  </AuthLayout>
</template>
