<script setup lang="ts">
import { formatCurrency } from '@/utils/formatters'
import CardLayout from '../ui/CardLayout.vue'

const props = defineProps<{
  totalSavings?: number
  savingsToday?: number
  savingsThisWeek?: number
  savingsThisMonth?: number
}>()
</script>

<template>
  <CardLayout>
    <div class="flex flex-col gap-4 text-center md:text-left">
      <div class="flex md:flex-row flex-col items-center">
        <h1 class="text-2xl font-semibold text-gray-800">Total Savings</h1>
      </div>

      <div>
        <h2 class="text-3xl font-semibold text-gray-800">
          ${{ formatCurrency(props.totalSavings) }}
        </h2>
      </div>

      <div class="grid grid-cols-1 md:grid-cols-3 gap-4">
        <div>
          <p class="text-lg font-medium text-gray-600">Today</p>
          <p class="text-2xl font-semibold text-gray-800">
            ${{ formatCurrency(props.savingsToday) }}
          </p>
        </div>
        <div>
          <p class="text-lg font-medium text-gray-600">This Week</p>
          <p class="text-2xl font-semibold text-gray-800">
            ${{ formatCurrency(props.savingsThisWeek) }}
          </p>
        </div>
        <div>
          <p class="text-lg font-medium text-gray-600">This Month</p>
          <p class="text-2xl font-semibold text-gray-800">
            ${{ formatCurrency(props.savingsThisMonth) }}
          </p>
        </div>
      </div>
    </div>
  </CardLayout>
</template>
