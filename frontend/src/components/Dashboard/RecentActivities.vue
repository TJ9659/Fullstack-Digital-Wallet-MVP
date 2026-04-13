<script setup lang="ts">
import { formatCurrency, formatDate } from '@/utils/formatters'
import CardLayout from '../ui/CardLayout.vue'

const props = defineProps<{
  recentActivities?: {
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
  <CardLayout>
    <div class="flex flex-col gap-4">
      <h1 class="text-2xl font-semibold text-gray-800">Recent Activities</h1>

      <div class="overflow-x-auto w-full border border-gray-200 rounded-lg max-h-[400px]">
        <table class="min-w-[600px] w-full divide-y divide-gray-200">
          <thead class="bg-gray-50">
            <tr>
              <th class="px-6 py-3 text-left text-xs font-semibold text-gray-500 uppercase">ID</th>
              <th class="px-6 py-3 text-left text-xs font-semibold text-gray-500 uppercase">
                Title
              </th>
              <th class="px-6 py-3 text-left text-xs font-semibold text-gray-500 uppercase">
                Amount
              </th>
              <th class="px-6 py-3 text-left text-xs font-semibold text-gray-500 uppercase">
                Type
              </th>
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
              v-for="(activity, index) in props.recentActivities"
              :key="index"
              class="transition-colors hover:bg-gray-50"
            >
              <td class="px-6 py-4 text-sm whitespace-nowrap text-gray-500">
                {{ activity.id }}
              </td>
              <td class="px-6 py-4 text-sm whitespace-nowrap text-gray-500">
                {{ activity.title }}
              </td>

              <td class="px-6 py-4 text-sm whitespace-nowrap text-gray-500">
                {{ formatCurrency(activity.amount) }}
              </td>

              <td class="px-6 py-4 text-sm whitespace-nowrap text-gray-500">
                <div
                  :class="
                    activity.type === 'CREDIT'
                      ? 'bg-green-50 text-green-600'
                      : 'bg-red-50 text-red-600'
                  "
                  class="px-4 py-1 rounded-full text-xs font-medium w-fit"
                >
                  {{ activity.type }}
                </div>
              </td>

              <td class="px-6 py-4 text-sm whitespace-nowrap text-gray-500">
                {{ activity.category }}
              </td>

              <td class="px-6 py-4 text-sm whitespace-nowrap text-gray-500">
                {{ formatDate(activity.timestamp) }}
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
  </CardLayout>
</template>
