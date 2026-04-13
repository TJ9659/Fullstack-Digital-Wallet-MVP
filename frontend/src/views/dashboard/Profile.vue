<script setup lang="ts">
import AuthLayout from '@/components/layouts/AuthLayout.vue'
import ChangePasswordForm from '@/components/Profile/ChangePasswordForm.vue'
import ProfileInfoForm from '@/components/Profile/ProfileInfoForm.vue'
import LoadingSpinner from '@/components/ui/LoadingSpinner.vue'
import { useAuthStore } from '@/stores/authStore'
import { storeToRefs } from 'pinia'
import { onMounted } from 'vue'

const authStore = useAuthStore()

const { user } = storeToRefs(authStore)

const fetchProfileDetails = async () => {
  authStore.fetchCurrentUser()
}

onMounted(() => {
  fetchProfileDetails()
  console.log(user.value)
})
</script>

<template>
  <AuthLayout>
    <div class="space-y-8">
      <div class="bg-white p-6 rounded-2xl shadow-sm border border-gray-100">
        <h2 class="text-lg font-semibold text-gray-700 mb-4">Personal Information</h2>
        <p class="text-sm text-gray-500 mb-6">
          Update your display name and view your account details.
        </p>

        <div v-if="authStore.isInitialLoading">
          <LoadingSpinner />
        </div>
        <ProfileInfoForm v-else-if="user" :user="user" />
      </div>

      <div class="bg-white p-6 rounded-2xl shadow-sm border border-gray-100">
        <h2 class="text-lg font-semibold text-gray-700 mb-4">Security</h2>
        <p class="text-sm text-gray-500 mb-6">Change your password to keep your account secure.</p>
        <ChangePasswordForm />
      </div>
    </div>
  </AuthLayout>
</template>
