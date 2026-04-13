<script setup lang="ts">
import { ref, watch } from 'vue' // Added watch for body scroll lock
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
  Send,
  FileUser,
  PlusCircle,
  History,
  Home,
} from 'lucide-vue-next'

const authStore = useAuthStore()
const isMenuOpen = ref(false)

watch(isMenuOpen, (val) => {
  document.body.style.overflow = val ? 'hidden' : ''
})

const menuLinks = [
  { name: 'Home', icon: Home, path: '/dashboard' },
  { name: 'Transfer', icon: Send, path: '/transfer' },
  { name: 'Top Up', icon: PlusCircle, path: '/top-up' },
  { name: 'History', icon: History, path: '/transactions' },
  { name: 'Profile', icon: User, path: '/profile' },
]

const handleLogout = async () => {
  await authStore.logout()
}
</script>

<template>
  <nav class="relative bg-white border-b border-gray-200">
    <div class="md:hidden flex items-center justify-between px-6 py-4">
      <router-link to="/">
        <img src="/images/logo.svg" class="h-6" alt="Logo" />
      </router-link>

      <button
        @click="isMenuOpen = true"
        class="p-2 text-gray-600 hover:bg-gray-100 rounded-lg transition"
      >
        <Menu class="w-6 h-6" />
      </button>
    </div>
    <Teleport to="body">
      <Transition
        enter-active-class="transition duration-300 ease-out"
        enter-from-class="opacity-0"
        enter-to-class="opacity-100"
        leave-active-class="transition duration-200 ease-in"
        leave-from-class="opacity-100"
        leave-to-class="opacity-0"
      >
        <div
          v-if="isMenuOpen"
          @click="isMenuOpen = false"
          class="fixed inset-0 bg-black/50 z-40 md:hidden"
        ></div>
      </Transition>

      <aside
        :class="[
          'fixed md:hidden inset-y-0 left-0 z-50 w-64 bg-white border-r border-gray-200 transform transition-transform duration-300 ease-in-out lg:translate-x-0 lg:static lg:inset-0 h-full overflow-y-auto',
          isMenuOpen ? 'translate-x-0' : '-translate-x-full',
        ]"
      >
        <div class="flex flex-col justify-between h-full">
          <div class="flex items-center justify-between p-6 border-b border-gray-100">
            <RouterLink :to="'/'">
              <img src="/images/logo.svg" class="w-24 h-auto" />
            </RouterLink>
            <button @click="isMenuOpen = false" class="p-1 hover:bg-gray-100 rounded-full">
              <X class="w-6 h-6 text-gray-400" />
            </button>
          </div>

          <div class="p-6 flex flex-col gap-2">
            <router-link
              v-for="item in menuLinks"
              :key="item.name"
              :to="item.path"
              @click="isMenuOpen = false"
              class="flex items-center gap-4 text-gray-700 p-3 hover:bg-indigo-50 rounded-xl transition"
              active-class="bg-indigo-50 text-indigo-600 font-semibold"
            >
              <component :is="item.icon" class="w-5 h-5 text-indigo-500" />
              <span class="font-medium">{{ item.name }}</span>
            </router-link>

            <!-- <template v-if="authStore.isAuthenticated">
              <router-link
                v-if="authStore.user?.role === 'employer'"
                to="/post-job"
                @click="isMenuOpen = false"
                class="flex items-center gap-4 text-gray-700 p-3 hover:bg-teal-50 rounded-xl"
              >
                <BriefcaseBusiness class="w-5 h-5 text-teal-500" />
                <span class="font-medium">Post a Job</span>
              </router-link>
            </template> -->
          </div>

          <div class="flex-grow"></div>

          <div class="p-6 border-t border-gray-100 bg-gray-50 space-y-4">
            <template>
              <div class="flex items-center gap-3 px-2">
                <div
                  class="w-10 h-10 rounded-full bg-indigo-100 flex items-center justify-center text-indigo-600 font-bold"
                >
                  {{ authStore.user?.name?.charAt(0) }}
                </div>
                <div class="flex flex-col overflow-hidden">
                  <span class="text-sm font-bold text-gray-800 truncate">{{
                    authStore.user?.name
                  }}</span>
                </div>
              </div>
              <button
                @click="handleLogout"
                class="w-full flex items-center justify-center gap-2 bg-red-50 text-red-600 p-3 rounded-xl font-bold hover:bg-red-100 transition"
              >
                <LogOut class="w-5 h-5" />
                Logout
              </button>
            </template>
          </div>
        </div>
      </aside>
    </Teleport>
  </nav>
</template>
