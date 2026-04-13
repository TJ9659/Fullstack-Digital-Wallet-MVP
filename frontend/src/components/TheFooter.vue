<template>
  <footer class="bg-white border-t border-gray-200 mt-auto">
    <div class="max-w-6xl mx-auto py-4 px-4 sm:px-6 lg:px-8">
      <div class="flex flex-col md:flex-row justify-between items-center">
        <div class="text-gray-500 text-sm">
          &copy; 2026 SwiftSpend. All rights reserved.
          <a
            class="text-gray-400 hover:text-gray-500"
            href="https://github.com/TJ9659"
            target="_blank"
            rel="noopener noreferrer"
            >Developed by @TJ9659 on Github.</a
          >
        </div>

        <div class="flex space-x-6 mt-4 md:mt-0">
          <router-link to="/privacy" class="text-gray-400 hover:text-gray-500 text-sm"
            >Privacy Policy</router-link
          >
          <router-link to="/terms" class="text-gray-400 hover:text-gray-500 text-sm"
            >Terms of Service</router-link
          >
          <router-link to="/contact" class="text-gray-400 hover:text-gray-500 text-sm"
            >Contact</router-link
          >
        </div>
      </div>
    </div>
  </footer>
</template>
