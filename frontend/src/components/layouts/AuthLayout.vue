<script setup lang="ts">
import TheHeader from '../Header/TheHeader.vue'
import TheMobileMenu from '../Sidebar/TheMobileMenu.vue'
import TheSidebar from '../Sidebar/TheSidebar.vue'
import TheFooter from '../TheFooter.vue'
</script>

<template>
  <div class="h-screen bg-gray-100 flex flex-col md:flex-row overflow-hidden">
    <TheSidebar class="hidden md:flex" />

    <TheMobileMenu class="md:hidden" />

    <main class="flex-1 overflow-y-auto px-8 py-6 md:px-10">
      <div class="max-w-6xl mx-auto flex flex-col gap-6">
        <slot />
      </div>
    </main>
  </div>
</template>
