<script setup lang="ts">
import { onMounted } from 'vue'
import { useAuthStore } from '@/stores/authStore'
import { RouterView } from 'vue-router'
import LoadingSpinnerPage from './components/ui/LoadingSpinnerPage.vue'
import NotificationToast from './components/ui/NotificationToast.vue'
const auth = useAuthStore()

onMounted(async () => {
  await auth.fetchCurrentUser()
})
</script>

<template>
  <div v-if="auth.isInitialLoading">
    <LoadingSpinnerPage />
  </div>

  <template v-else>
    <NotificationToast />
    <RouterView />
  </template>
</template>
