<script setup>
import PublicLayout from '@/components/layouts/PublicLayout.vue'
import { ref } from 'vue'
import { useAuthStore } from '@/stores/authStore'
import { CircleAlert } from 'lucide-vue-next'
import LoadingSpinner from '@/components/ui/LoadingSpinner.vue'
const auth = useAuthStore()
const isLoading = ref(false)
const form = ref({
  email: '',
  password: '',
})
const errors = ref({
  email: null,
  password: null,
})

const checkForm = () => {
  errors.value.email = null
  errors.value.password = null

  if (!form.value.email) {
    errors.value.email = 'Email required.'
  } else if (!validEmail(form.value.email)) {
    errors.value.email = 'Valid email required.'
  }

  if (!form.value.password) {
    errors.value.password = 'Password required.'
  }

  if (errors.value.email || errors.value.password) {
    return
  }

  submit()
}

const validEmail = (email) => {
  const re =
    /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/
  return re.test(email)
}

const submit = async () => {
  isLoading.value = true
  try {
    await auth.login(form.value)
  } catch (error) {
    console.error(error)
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
          <h1 class="text-gray-800 font-semibold text-[28px]">Welcome back</h1>
          <h1 class="text-gray-600 font-normal text-[16px] mt-2">
            New to SwiftSpend?
            <RouterLink to="/register" class="underline underline-offset-4 text-gray-800"
              >Sign Up</RouterLink
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
        <form class="pb-10" @submit.prevent="checkForm">
          <div class="relative w-full pb-10 space-y-6">
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

            <div class="relative w-full">
              <label class="block mb-1.5 text-gray-700 font-semibold">Password</label>
              <input
                v-model="form.password"
                type="password"
                placeholder="••••••••"
                class="w-full pl-10 pr-4 py-2.5 bg-slate-50 border border-slate-200 text-slate-900 rounded-xl focus:outline-none focus:ring-2 focus:ring-indigo-600 focus:border-indigo-600 transition-all duration-200 placeholder-slate-400"
              />
              <span v-if="errors.password" class="text-red-500 font-semibold">{{
                errors.password
              }}</span>
            </div>
          </div>

          <button
            type="submit"
            :disabled="isLoading"
            class="bg-indigo-600 font-semibold text-white border border-slate-200 rounded-xl w-full px-4 py-4 hover:bg-indigo-700 hover:cursor-pointer transition duration-300 ease-in-out disabled:bg-slate-300 disabled:cursor-not-allowed flex items-center justify-center"
          >
            <span v-if="!isLoading">Login</span>

            <div v-else class="flex justify-center items-center gap-2">
              <LoadingSpinner />
              Logging in...
            </div>
          </button>
        </form>
      </div>
    </div>
  </PublicLayout>
</template>
