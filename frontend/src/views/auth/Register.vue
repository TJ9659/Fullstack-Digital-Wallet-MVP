<script setup lang="ts">
import { ref, watch } from 'vue'
import { RouterLink } from 'vue-router'
import { useAuthStore } from '@/stores/authStore'
import { CircleAlert } from 'lucide-vue-next'
import PublicLayout from '@/components/layouts/PublicLayout.vue'
import LoadingSpinner from '@/components/ui/LoadingSpinner.vue'

const form = ref({
  name: '',
  email: '',
  role: 'applicant',
  password: '',
  password_confirmation: '',
})

const auth = useAuthStore()
const fileInput = ref(null)
const previewUrl = ref(null)
const isLoading = ref(false)

const errors = ref({
  name: '',
  email: '',
  password: '',
  password_confirmation: '',
})

const checkForm = () => {
  errors.value.name = ''
  errors.value.email = ''
  errors.value.password = ''
  errors.value.password_confirmation = ''

  if (!form.value.name) {
    errors.value.name = 'Name required.'
  }

  if (!form.value.email) {
    errors.value.email = 'Email required.'
  } else if (!validEmail(form.value.email)) {
    errors.value.email = 'Valid email required.'
  }

  if (!form.value.password) {
    errors.value.password = 'Password required.'
  } else if (form.value.password !== form.value.password_confirmation) {
    errors.value.password = 'Passwords must match.'
  }

  if (errors.value.name || errors.value.email || errors.value.password) {
    return
  }

  submitForm()
}

const validEmail = (email: any) => {
  const re =
    /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/
  return re.test(email)
}

const submitForm = async () => {
  isLoading.value = true
  try {
    await auth.register(form.value)
  } catch (err: any) {
    const backendError = err.response?.data

    if (backendError?.field && backendError.field in errors.value) {
      errors.value[backendError.field] = backendError.detail
    } else {
      auth.error = backendError?.detail || 'Login failed'
    }
  } finally {
    isLoading.value = false
  }
}
</script>

<template>
  <PublicLayout>
    <nav class="relative h-16 px-6">
      <div class="flex items-center justify-between px-6 py-4 mx-auto">
        <div class="flex-shrink-0 flex items-center gap-4 text-lg font-medium">
          <router-link to="/">
            <img src="/images/logo.svg" class="h-6 md:h-8 object-contain w-auto" alt="JobPortal" />
          </router-link>
        </div>
        <router-link
          to="/"
          class="hover:bg-indigo-700 hover:text-white px-4 py-2 rounded-lg px-4 py-2 border border-indigo-700 transition"
          >Home</router-link
        >
      </div>
    </nav>
    <div class="flex justify-center items-center py-12 px-4 sm:px-6 lg:px-8">
      <div class="w-full max-w-md">
        <div class="flex flex-col pb-10 text-center">
          <h1 class="text-gray-800 font-semibold text-[28px]">Create your SwiftSpend account</h1>
          <h1 class="text-gray-600 font-normal text-[16px] mt-2">
            Already have an account?
            <RouterLink to="/login" class="underline underline-offset-4 text-gray-800"
              >Log in</RouterLink
            >
          </h1>
        </div>
        <div
          v-if="auth.error"
          class="p-4 mb-6 text-sm text-red-700 bg-red-50 border border-red-100 rounded-xl flex items-center gap-2"
        >
          <CircleAlert :size="20" />
          {{ auth.error }}
        </div>

        <form class="pb-10 space-y-6" @submit.prevent="checkForm">
          <div class="relative w-full">
            <label class="block mb-1.5 text-gray-700 font-semibold">Full Name</label>
            <input
              v-model="form.name"
              type="text"
              placeholder="John Doe"
              class="w-full pl-10 pr-4 py-2.5 bg-slate-50 border border-slate-200 text-slate-900 rounded-xl focus:outline-none focus:ring-2 focus:ring-indigo-600 focus:border-indigo-500 transition-all duration-200 placeholder-slate-400"
            />
            <span v-if="errors.name" class="text-red-500 font-semibold">{{ errors.name }}</span>
          </div>

          <div class="relative w-full">
            <label class="block mb-1.5 text-gray-700 font-semibold">Email</label>
            <input
              v-model="form.email"
              type="email"
              placeholder="john@example.com"
              class="w-full pl-10 pr-4 py-2.5 bg-slate-50 border border-slate-200 text-slate-900 rounded-xl focus:outline-none focus:ring-2 focus:ring-indigo-600 focus:border-indigo-600 transition-all duration-200 placeholder-slate-400"
            />
            <span v-if="errors.email" class="text-red-500 font-semibold">{{ errors.email }}</span>
          </div>

          <div class="relative w-full grid grid-cols-1 md:grid-cols-2 gap-2">
            <div class="relative">
              <label class="block mb-1.5 text-gray-700 font-semibold">Password</label>
              <input
                v-model="form.password"
                type="password"
                placeholder="••••••••"
                class="w-full pl-10 pr-4 py-2.5 bg-slate-50 border border-slate-200 text-slate-900 rounded-xl focus:outline-none focus:ring-2 focus:ring-indigo-600 focus:border-indigo-600 transition-all duration-200 placeholder-slate-400"
              />
            </div>
            <div class="relative">
              <label class="block mb-1.5 text-gray-700 font-semibold">Confirm Password</label>
              <input
                v-model="form.password_confirmation"
                type="password"
                placeholder="••••••••"
                class="w-full pl-10 pr-4 py-2.5 bg-slate-50 border border-slate-200 text-slate-900 rounded-xl focus:outline-none focus:ring-2 focus:ring-indigo-600 focus:border-indigo-600 transition-all duration-200 placeholder-slate-400"
              />
            </div>
            <span v-if="errors.password" class="text-red-500 font-semibold col-span-2">{{
              errors.password
            }}</span>
          </div>

          <button
            type="submit"
            :disabled="isLoading"
            class="bg-indigo-600 font-semibold text-white border border-slate-200 rounded-full w-full px-4 py-4 hover:bg-indigo-700 hover:cursor-pointer transition duration-300 ease-in-out disabled:bg-slate-300 disabled:cursor-not-allowed flex items-center justify-center"
          >
            <span v-if="!isLoading">Register</span>

            <div v-else class="flex justify-center items-center gap-2">
              <LoadingSpinner />
              Processing...
            </div>
          </button>
        </form>
      </div>
    </div>
  </PublicLayout>
</template>
