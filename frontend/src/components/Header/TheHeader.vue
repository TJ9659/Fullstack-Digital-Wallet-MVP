<script setup lang="ts">
import { ref, watch } from 'vue'
import { useAuthStore } from '@/stores/authStore'
import {
  Menu,
  X,
  Plus,
  User,
  UserCircle,
  House,
  LayoutDashboard,
  BriefcaseBusiness,
  LogOut,
} from 'lucide-vue-next'

const authStore = useAuthStore()
const isMenuOpen = ref(false)

watch(isMenuOpen, (val) => {
  document.body.style.overflow = val ? 'hidden' : ''
})

const publicLinks = [{ name: 'Home', path: '/', icon: House }]

const handleLogout = async () => {
  await authStore.logout()
}
</script>

<template>
  <nav class="relative bg-white border-b border-gray-200">
    <div class="flex items-center justify-between px-6 py-4 max-w-6xl mx-auto">
      <div class="flex-shrink-0 flex items-center gap-4 text-lg font-medium">
        <router-link to="/">
          <img src="/images/logo.svg" class="h-5 md:h-6" alt="JobPortal" />
        </router-link>

        <router-link
          v-for="item in publicLinks"
          :key="item.name"
          :to="item.path"
          class="hidden md:flex text-gray-600 hover:text-teal-500 transition px-4 py-2 rounded-lg"
          active-class="bg-teal-50 text-teal-600 font-semibold rounded-lg"
        >
          {{ item.name }}
        </router-link>
      </div>

      <div class="hidden md:flex gap-6 items-center">
        <template v-if="authStore.isAuthenticated">
          <div
            class="flex items-center gap-2 border border-gray-200 rounded-full px-4 py-2 bg-gray-100"
          >
            <UserCircle class="w-6 h-6 text-gray-400" />
            <span class="text-sm font-medium text-gray-600">{{ authStore.user?.name }}</span>
          </div>
          <button
            @click="handleLogout"
            class="bg-teal-400 hover:bg-teal-500 text-white px-4 py-2 rounded-lg text-sm transition"
          >
            Logout
          </button>
        </template>

        <template v-else>
          <router-link to="/login" class="text-gray-600 hover:text-teal-500">Login</router-link>
          <router-link
            to="/register"
            class="bg-teal-400 hover:bg-teal-500 text-white px-4 py-2 rounded-lg transition"
            >Register</router-link
          >
        </template>
      </div>

      <button
        @click="isMenuOpen = true"
        class="md:hidden p-2 text-gray-600 hover:bg-gray-100 rounded-lg transition"
      >
        <Menu class="w-6 h-6" />
      </button>
    </div>
  </nav>
</template>
