<script setup lang="ts">
import CardLayout from '../ui/CardLayout.vue'
import { formatCurrency } from '@/utils/formatters'

const props = defineProps<{
  balance?: number
}>()
</script>

<template>
  <CardLayout>
    <div class="flex flex-col gap-4 text-center md:text-left">
      <div class="flex md:flex-row flex-col justify-between items-center gap-4">
        <h1 class="text-2xl font-semibold text-gray-800">Total Balance</h1>

        <router-link
          to="/top-up"
          class="bg-indigo-600 text-white px-4 py-2 rounded-lg hover:bg-indigo-700 transition"
          >Top Up</router-link
        >
      </div>

      <div>
        <h2 class="text-3xl font-semibold text-gray-800">${{ formatCurrency(props.balance) }}</h2>
      </div>
    </div>
  </CardLayout>
</template>
