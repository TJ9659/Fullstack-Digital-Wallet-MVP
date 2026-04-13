<script setup lang="ts">
import AuthLayout from '@/components/layouts/AuthLayout.vue'
import TransferForm from '@/components/Transfer/TransferForm.vue'
</script>

<template>
  <AuthLayout>
    <div class="py-10">
      <h1 class="text-2xl font-semibold text-gray-800">Transfer your funds</h1>
      <TransferForm />
    </div>
  </AuthLayout>
</template>
