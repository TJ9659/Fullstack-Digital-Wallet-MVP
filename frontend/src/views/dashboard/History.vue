<script setup lang="ts">
import TransactionsTable from '@/components/History/TransactionsTable.vue'
import AuthLayout from '@/components/layouts/AuthLayout.vue'
import LoadingSpinnerPage from '@/components/ui/LoadingSpinnerPage.vue'
import Pagination from '@/components/ui/Pagination.vue'
import { useAccountStore } from '@/stores/accountStore'
import { useAuthStore } from '@/stores/authStore'
import { useNotificationStore } from '@/stores/notificationStore'
import { storeToRefs } from 'pinia'
import { onMounted, ref } from 'vue'

const authStore = useAuthStore()
const notificationStore = useNotificationStore()
const accountStore = useAccountStore()
const { transactions, loading, currentPage, totalPages } = storeToRefs(accountStore)
const { user } = storeToRefs(authStore)

const fetchHistory = async (currentPage = 0) => {
  loading.value = true
  try {
    await accountStore.fetchTransactions(currentPage)
  } catch (error) {
    console.error(error)
  } finally {
    loading.value = false
  }
}

onMounted(() => {
  fetchHistory()
})
</script>

<template>
  <AuthLayout>
    <div v-if="loading" class="flex flex-1 items-center justify-center min-h-[400px]">
      <LoadingSpinnerPage />
    </div>
    <template v-else>
      <div>
        <TransactionsTable :transactions="transactions" />
        <Pagination
          :current-page="currentPage"
          :total-pages="totalPages"
          @change-page="fetchHistory"
        />
      </div>
    </template>
  </AuthLayout>
</template>
