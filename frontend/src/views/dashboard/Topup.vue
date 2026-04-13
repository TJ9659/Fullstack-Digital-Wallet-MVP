<script setup lang="ts">
import AuthLayout from '@/components/layouts/AuthLayout.vue'
import TopUpForm from '@/components/TopUp/TopUpForm.vue'
import { reactive, ref } from 'vue'
</script>

<template>
  <AuthLayout>
    <div class="py-10">
      <h1 class="text-2xl font-semibold text-gray-800">Top Up your Account</h1>
      <TopUpForm />
    </div>
  </AuthLayout>
</template>
