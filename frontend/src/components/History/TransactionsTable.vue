<script setup lang="ts">
import { formatCurrency, formatDate } from '@/utils/formatters'

const props = defineProps<{
  transactions?: {
    id: number
    title: string
    description: string
    amount: number
    type: string
    category: string
    timestamp: string
  }[]
}>()
</script>

<template>
  <div class="flex flex-col gap-4">
    <h1 class="text-2xl font-semibold text-gray-800">Transactions History</h1>

    <div class="overflow-x-auto w-full border border-gray-200 rounded-lg max-h-[400px]">
      <table class="min-w-[600px] w-full divide-y divide-gray-200">
        <thead class="bg-gray-50">
          <tr>
            <th class="px-6 py-3 text-left text-xs font-semibold text-gray-500 uppercase">ID</th>
            <th class="px-6 py-3 text-left text-xs font-semibold text-gray-500 uppercase">Title</th>
            <th class="px-6 py-3 text-left text-xs font-semibold text-gray-500 uppercase">
              Amount
            </th>
            <th class="px-6 py-3 text-left text-xs font-semibold text-gray-500 uppercase">
              Description
            </th>
            <th class="px-6 py-3 text-left text-xs font-semibold text-gray-500 uppercase">Type</th>
            <th class="px-6 py-3 text-left text-xs font-semibold text-gray-500 uppercase">
              Category
            </th>
            <th class="px-6 py-3 text-left text-xs font-semibold text-gray-500 uppercase">
              Date and Time
            </th>
          </tr>
        </thead>
        <tbody class="divide-y divide-gray-200 bg-white">
          <tr
            v-for="(transaction, index) in props.transactions"
            :key="index"
            class="transition-colors hover:bg-gray-50"
          >
            <td class="px-6 py-4 text-sm whitespace-nowrap text-gray-500">
              {{ transaction.id }}
            </td>
            <td class="px-6 py-4 text-sm whitespace-nowrap text-gray-500">
              {{ transaction.title }}
            </td>

            <td class="px-6 py-4 text-sm whitespace-nowrap text-gray-500">
              {{ formatCurrency(transaction.amount) }}
            </td>

            <td class="px-6 py-4 text-sm whitespace-nowrap text-gray-500">
              {{ transaction.description }}
            </td>

            <td class="px-6 py-4 text-sm whitespace-nowrap text-gray-500">
              <div
                :class="
                  transaction.type === 'CREDIT'
                    ? 'bg-green-50 text-green-600'
                    : 'bg-red-50 text-red-600'
                "
                class="px-4 py-1 rounded-full text-xs font-medium w-fit"
              >
                {{ transaction.type }}
              </div>
            </td>

            <td class="px-6 py-4 text-sm whitespace-nowrap text-gray-500">
              {{ transaction.category }}
            </td>

            <td class="px-6 py-4 text-sm whitespace-nowrap text-gray-500">
              {{ formatDate(transaction.timestamp) }}
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>
