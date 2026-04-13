<script setup lang="ts">
import { ref, computed } from 'vue'
import {
  LayoutDashboard,
  Briefcase,
  User,
  Settings,
  LogOut,
  Menu,
  X,
  FileUser,
  DollarSign,
  Send,
  PlusCircle,
  History,
} from 'lucide-vue-next'
import { useAuthStore } from '@/stores/authStore'
import { ArrowLeft, ArrowRight } from 'lucide-vue-next'
const isOpen = ref(true)
const auth = useAuthStore()
const menuItems = [
  // { name: 'Overview', icon: LayoutDashboard, path: '/dashboard', role: '' },
  { name: 'Home', icon: FileUser, path: '/dashboard' },
  { name: 'Transfer', icon: Send, path: '/transfer' },
  { name: 'Top Up', icon: PlusCircle, path: '/top-up' },
  { name: 'History', icon: History, path: '/transactions' },
  { name: 'Profile', icon: User, path: '/profile' },

  // { name: 'Settings', icon: Settings, path: '/dashboard/settings', role: '' },
]

const toggleSidebar = () => {
  isOpen.value = !isOpen.value
}

// const filteredMenuItems = computed(() => {
//   return menuItems.filter((item) => {
//     if (item.role === '') return true
//     return auth.user?.role === item.role
//   })
// })

const handleLogout = async () => {
  await auth.logout()
}
</script>

<template>
  <aside
    :class="[
      isOpen ? 'w-64' : 'w-20',
      'h-screen flex flex-col bg-white border-r border-gray-100 py-4 px-2 transition-all duration-300 ease-in-out',
    ]"
  >
    <div class="flex flex-col gap-2">
      <div
        :class="[isOpen ? 'flex-row justify-between' : 'flex-col gap-4', 'flex items-center mb-6']"
      >
        <RouterLink to="/dashboard">
          <img src="/images/logo.svg" v-if="isOpen" class="ml-3 w-24 h-auto" />
          <img v-else src="/images/logo-crop.svg" class="ml-3 w-8 h-auto" />
        </RouterLink>

        <button @click="toggleSidebar" class="p-1 hover:bg-gray-100 rounded-lg">
          <ArrowLeft
            :size="20"
            :class="[!isOpen ? 'rotate-180' : '', 'transition-transform duration-300']"
          />
        </button>
      </div>

      <nav class="flex flex-col gap-1">
        <RouterLink
          v-for="item in menuItems"
          :key="item.name"
          :to="item.path"
          :class="[
            isOpen ? '' : 'justify-center',
            'flex items-center gap-4 text-gray-700 p-3 hover:bg-indigo-50 rounded-xl transition group',
          ]"
          active-class="bg-indigo-50 text-indigo-600 font-semibold"
        >
          <Component :is="item.icon" :size="22" class="shrink-0" />
          <span v-if="isOpen" class="font-medium whitespace-nowrap">{{ item.name }}</span>
        </RouterLink>
      </nav>
    </div>

    <div class="mt-auto pt-6 border-t border-gray-100 space-y-4" v-if="auth.isAuthenticated">
      <div v-if="isOpen && auth.user" class="flex items-center gap-3 px-4">
        <div
          class="w-10 h-10 rounded-full bg-indigo-100 flex items-center justify-center text-indigo-600 font-bold"
        >
          {{ auth.user?.name?.charAt(0) }}
        </div>
        <span class="text-sm font-bold text-gray-800 truncate">{{ auth.user?.name }}</span>
      </div>

      <button
        @click="handleLogout"
        :class="[
          isOpen ? 'px-4 justify-start' : 'justify-center',
          'w-full flex items-center gap-3 text-red-600 p-3 rounded-xl font-bold hover:bg-red-50 transition',
        ]"
      >
        <LogOut :size="22" />
        <span v-if="isOpen">Logout</span>
      </button>
    </div>
  </aside>
</template>
