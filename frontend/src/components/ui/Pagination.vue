<script setup lang="ts">
defineProps<{
  currentPage: number
  totalPages: number
  loading?: boolean
}>()

const emit = defineEmits(['changePage'])

const goToPage = (page: number) => {
  if (page >= 0) {
    emit('changePage', page)
  }
}
</script>

<template>
  <div
    class="flex items-center justify-between px-4 py-3 bg-white border-t border-gray-200 sm:px-6"
  >
    <div class="flex justify-between flex-1 sm:hidden">
      <button
        @click="goToPage(currentPage - 1)"
        :disabled="currentPage === 0 || loading"
        class="relative inline-flex items-center px-4 py-2 text-sm font-medium text-gray-700 bg-white border border-gray-300 rounded-md hover:bg-gray-50 disabled:opacity-50"
      >
        Previous
      </button>
      <button
        @click="goToPage(currentPage + 1)"
        :disabled="currentPage >= totalPages - 1 || loading"
        class="relative ml-3 inline-flex items-center px-4 py-2 text-sm font-medium text-gray-700 bg-white border border-gray-300 rounded-md hover:bg-gray-50 disabled:opacity-50"
      >
        Next
      </button>
    </div>
    <div class="hidden sm:flex sm:flex-1 sm:items-center sm:justify-between">
      <div>
        <p class="text-sm text-gray-700">
          Showing Page <span class="font-medium">{{ currentPage + 1 }}</span> of
          <span class="font-medium">{{ totalPages }}</span>
        </p>
      </div>
      <div>
        <nav class="inline-flex -space-x-px rounded-md shadow-sm" aria-label="Pagination">
          <button
            @click="goToPage(currentPage - 1)"
            :disabled="currentPage === 0 || loading"
            class="relative inline-flex items-center px-2 py-2 text-gray-400 ring-1 ring-inset ring-gray-300 hover:bg-gray-50 focus:z-20 focus:outline-offset-0 disabled:opacity-30"
          >
            <span>Previous</span>
          </button>

          <button
            @click="goToPage(currentPage + 1)"
            :disabled="currentPage >= totalPages - 1 || loading"
            class="relative inline-flex items-center px-2 py-2 text-gray-400 ring-1 ring-inset ring-gray-300 hover:bg-gray-50 focus:z-20 focus:outline-offset-0 disabled:opacity-30"
          >
            <span>Next</span>
          </button>
        </nav>
      </div>
    </div>
  </div>
</template>
