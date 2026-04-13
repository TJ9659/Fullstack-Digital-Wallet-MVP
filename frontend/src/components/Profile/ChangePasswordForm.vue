<script setup lang="ts">
import { ref, reactive, watch } from 'vue'
import { formatCardNumber, validEmail } from '@/utils/formatters'
import { useAccountStore } from '@/stores/accountStore'
import LoadingSpinner from '@/components/ui/LoadingSpinner.vue'
import { useNotificationStore } from '@/stores/notificationStore'
import { useAuthStore } from '@/stores/authStore'
import { mapBackendErrors } from '@/utils/error-mapper'

const accountStore = useAccountStore()
const authStore = useAuthStore()
const notificationStore = useNotificationStore()

const form = ref({
  currentPassword: '',
  newPassword: '',
  confirmPassword: '',
})

const errors = ref({
  currentPassword: '',
  newPassword: '',
  confirmPassword: '',
})

const isLoading = ref(false)

const checkForm = () => {
  errors.value.currentPassword = ''
  errors.value.newPassword = ''
  errors.value.confirmPassword = ''

  if (!form.value.currentPassword) {
    errors.value.currentPassword = 'Old password required.'
  }

  if (!form.value.newPassword) {
    errors.value.newPassword = 'New password required.'
  }

  if (!form.value.confirmPassword) {
    errors.value.confirmPassword = 'Confirm password required.'
  }

  if (form.value.newPassword !== form.value.confirmPassword) {
    errors.value.confirmPassword = 'Passwords must match.'
  }

  if (errors.value.currentPassword || errors.value.newPassword || errors.value.confirmPassword) {
    return
  }

  handlePasswordUpdate()
}

const clearForm = () => {
  form.value.currentPassword = ''
  form.value.newPassword = ''
  form.value.confirmPassword = ''
}

const handlePasswordUpdate = async () => {
  try {
    isLoading.value = true
    await authStore.updateUserPassword(form.value)
    notificationStore.notify('Successfully updated password', 'success')
    clearForm()
  } catch (error) {
    console.error(error)
    mapBackendErrors(error, errors.value, (msg) => (accountStore.error = msg))
    notificationStore.notify('Update failed', 'error')
  } finally {
    isLoading.value = false
  }
}
</script>

<template>
  <form class="flex flex-col gap-4 mt-4" @submit.prevent="checkForm">
    <div class="relative w-full">
      <label for="accNo" class="text-sm font-medium text-gray-600">Current Password</label>
      <input
        id="currentPassword"
        v-model="form.currentPassword"
        type="password"
        class="w-full border border-gray-300 rounded-lg px-4 py-2 bg-white transition-all duration-200 outline-none focus:ring-2 focus:ring-indigo-600 focus:border-transparent"
        placeholder="••••••••"
      />
      <span v-if="errors.currentPassword" class="text-red-500 font-semibold text-xs mt-1">{{
        errors.currentPassword
      }}</span>
    </div>

    <div class="relative w-full">
      <label for="email" class="text-sm font-medium text-gray-600">New Password</label>
      <input
        id="newPassword"
        v-model="form.newPassword"
        type="password"
        class="w-full border border-gray-300 rounded-lg px-4 py-2 bg-white transition-all duration-200 outline-none focus:ring-2 focus:ring-indigo-600 focus:border-transparent"
        placeholder="••••••••"
      />
      <span v-if="errors.newPassword" class="text-red-500 font-semibold text-xs mt-1">{{
        errors.newPassword
      }}</span>
    </div>

    <div class="relative w-full">
      <label for="name" class="text-sm font-medium text-gray-600">Confirm Password</label>
      <input
        id="confirmPassword"
        v-model="form.confirmPassword"
        type="password"
        placeholder="••••••••"
        class="w-full border border-gray-300 rounded-lg px-4 py-2 bg-white transition-all duration-200 outline-none focus:ring-2 focus:ring-indigo-600 focus:border-transparent"
      />
      <span v-if="errors.confirmPassword" class="text-red-500 font-semibold text-xs mt-1">{{
        errors.confirmPassword
      }}</span>
    </div>

    <div class="flex mt-4 items-center justify-center">
      <button
        :disabled="isLoading"
        class="min-w-[200px] bg-indigo-600 font-semibold text-white border border-transparent rounded-xl px-4 py-4 hover:bg-indigo-700 disabled:bg-indigo-400 transition duration-300 ease-in-out flex justify-center items-center hover:cursor-pointer"
        type="submit"
      >
        <template v-if="!isLoading">
          <span>Change Password</span>
        </template>
        <template v-else>
          <LoadingSpinner class="w-5 h-5 mr-2" />
          Updating...
        </template>
      </button>
    </div>
  </form>
</template>
