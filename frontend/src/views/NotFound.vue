<script setup lang="ts">
import { useAuthStore } from '@/stores/authStore'
const auth = useAuthStore()
</script>

<template>
  <div class="min-h-[80vh] flex flex-col items-center justify-center px-4 text-center">
    <div class="mb-6">
      <h1 class="text-9xl font-bold text-blue-600 opacity-20">404</h1>
      <div class="text-3xl font-bold text-gray-900 -mt-12">Lost in space?</div>
    </div>

    <p class="text-gray-600 mb-8 max-w-md">
      The page you're looking for doesn't exist or has been moved. Don't worry, your money is safe!
    </p>

    <router-link
      :to="auth.isAuthenticated ? { name: 'dashboard' } : { name: 'landing-page' }"
      class="px-8 py-3 bg-blue-600 text-white font-semibold rounded-xl hover:bg-blue-700 transition-colors shadow-lg shadow-blue-200"
    >
      Go back home
    </router-link>
  </div>
</template>
