<script setup lang="ts">
import { ref, reactive, watch } from 'vue'
import { formatCardNumber, validEmail } from '@/utils/formatters'
import { useAccountStore } from '@/stores/accountStore'
import LoadingSpinner from '@/components/ui/LoadingSpinner.vue'
import { useNotificationStore } from '@/stores/notificationStore'
import { useAuthStore } from '@/stores/authStore'

const props = defineProps<{
  user: any
}>()

const accountStore = useAccountStore()
const authStore = useAuthStore()
const notificationStore = useNotificationStore()

const form = ref({
  name: props.user?.name || '',
  email: props.user?.email || '',
  accountNumber: props.user?.accountNumber || '',
})

const errors = ref({
  email: '',
  name: '',
  accountNumber: '',
})

const isLoading = ref(false)

const checkForm = () => {
  errors.value.email = ''
  errors.value.name = ''
  errors.value.accountNumber = ''

  if (!form.value.email) {
    errors.value.email = 'Email required.'
  } else if (!validEmail(form.value.email)) {
    errors.value.email = 'Valid email required.'
  }

  if (!form.value.name) {
    errors.value.name = 'Name required.'
  }

  if (!form.value.accountNumber) {
    errors.value.accountNumber = 'Account number required.'
  }

  if (errors.value.email || errors.value.name || errors.value.accountNumber) {
    return
  }

  handleTopUp()
}

const handleTopUp = async () => {
  try {
    isLoading.value = true
    await authStore.updateUserDetails(form.value)

    notificationStore.notify('Successfully updated details', 'success')
  } catch (error) {
    console.error(error)
    notificationStore.notify('Update failed', 'error')
  } finally {
    isLoading.value = false
  }
}
</script>

<template>
  <form class="flex flex-col gap-4 mt-4" @submit.prevent="checkForm">
    <div class="relative w-full">
      <label for="accNo" class="text-sm font-medium text-gray-600">Account Number</label>
      <input
        id="accNo"
        v-model="form.accountNumber"
        type="text"
        :disabled="true"
        class="w-full border border-gray-300 bg-gray-200 rounded-lg px-4 py-2 text-gray-600 cursor-not-allowed outline-none"
      />
    </div>

    <div class="relative w-full">
      <label for="email" class="text-sm font-medium text-gray-600">Email</label>
      <input
        id="email"
        v-model="form.email"
        type="email"
        :disabled="true"
        class="w-full border border-gray-300 bg-gray-200 rounded-lg px-4 py-2 text-gray-600 cursor-not-allowed outline-none"
      />
    </div>

    <div class="relative w-full">
      <label for="name" class="text-sm font-medium text-gray-600">Name</label>
      <input
        id="name"
        v-model="form.name"
        type="text"
        placeholder="Full Name"
        class="w-full border border-gray-300 rounded-lg px-4 py-2 bg-white transition-all duration-200 outline-none focus:ring-2 focus:ring-indigo-600 focus:border-transparent"
      />
      <span v-if="errors.name" class="text-red-500 font-semibold text-xs mt-1">{{
        errors.name
      }}</span>
    </div>

    <div class="flex mt-4 items-center justify-center">
      <button
        :disabled="isLoading"
        class="min-w-[200px] bg-indigo-600 font-semibold text-white border border-transparent rounded-xl px-4 py-4 hover:bg-indigo-700 disabled:bg-indigo-400 transition duration-300 ease-in-out flex justify-center items-center hover:cursor-pointer"
        type="submit"
      >
        <template v-if="!isLoading">
          <span>Update Profile</span>
        </template>
        <template v-else>
          <LoadingSpinner class="w-5 h-5 mr-2" />
          Updating...
        </template>
      </button>
    </div>
  </form>
</template>
