<script setup lang="ts">
import AuthLayout from '@/components/layouts/AuthLayout.vue'
import PublicLayout from '@/components/layouts/PublicLayout.vue'
import { ref } from 'vue'

const featureCards = [
  {
    title: 'One-Tap Access',
    description:
      'Move between your wallet, payment screen, and transaction history without ever getting lost in deep menus or complicated settings.',
    image: '/images/feature-cards/one-tap-access.png',
  },
  {
    title: 'Visual Insights',
    description:
      'Complex financial data is transformed into easy-to-read lists and balances, giving you an immediate understanding of your financial health.',
    image: '/images/feature-cards/visual-insights.png',
  },
  {
    title: 'Guided Feedbacks',
    description:
      'Clear confirmation prompts and real-time feedback ensure you always know exactly where your money is going before you hit send.',
    image: '/images/feature-cards/guided-feedback.png',
  },
]
</script>

<template>
  <PublicLayout>
    <div>
      <nav class="relative h-16 px-6">
        <div class="flex items-center justify-between px-6 py-4 mx-auto">
          <div class="flex-shrink-0 flex items-center gap-4 text-lg font-medium">
            <router-link to="/">
              <img
                src="/images/logo.svg"
                class="h-6 md:h-8 object-contain w-auto"
                alt="JobPortal"
              />
            </router-link>
          </div>

          <div class="flex items-center gap-2 md:gap-4">
            <router-link
              to="/login"
              class="text-gray-600 hover:bg-indigo-700 hover:text-white px-4 py-2 rounded-lg transition"
              >Login</router-link
            >

            <router-link
              to="/register"
              class="hover:bg-indigo-700 hover:text-white px-4 py-2 rounded-lg px-4 py-2 border border-indigo-700 transition"
              >Register</router-link
            >
          </div>
        </div>
      </nav>
      <div
        className="flex flex-col md:flex-row items-center gap-10 lg:gap-20 mt-24 max-md:mt-16 max-sm:mt-12 px-10 py-4"
      >
        <div className="w-full md:w-1/2 md:order-2 flex justify-center">
          <img
            src="/images/landing-page-1.png"
            className="w-full h-auto object-contain max-w-[990px]"
            alt="Guy looking at revenue"
          />
        </div>
        <div className="w-full md:w-1/2 space-y-6 md:order-1">
          <h1
            className="text-6xl lg:text-8xl xl:text-[100px] font-bold text-gray-800 leading-[1.1]"
          >
            Welcome to SwiftSpend
          </h1>
          <p className="text-lg md:text-xl font-medium text-gray-600 max-w-lg">
            Revolutionizing the way you move, manage, and multiply your money in a digital-first
            world.
          </p>
          <router-link
            to="/login"
            class="inline-block text-center bg-indigo-600 font-semibold text-white border border-slate-200 rounded-full w-[200px] px-4 py-4 hover:bg-indigo-700 transition duration-300 ease-in-out"
          >
            Get Started
          </router-link>
        </div>
      </div>

      <div class="px-10 py-16 bg-gray-200 mt-20">
        <div class="flex flex-col text-center gap-2 font-bold text-gray-800 leading-[1.1]">
          <h1 class="text-5xl">What Is SwiftSpend?</h1>
          <h2 class="text-4xl">Why Should I Use It?</h2>
        </div>

        <div className="flex flex-col md:flex-row items-center gap-10 lg:gap-20 mt-10">
          <div className="w-full md:w-1/2 flex justify-center">
            <img
              src="/images/landing-page-2.png"
              className="w-full h-auto object-contain max-w-[990px]"
              alt="Guy looking at revenue"
            />
          </div>
          <div className="w-full md:w-1/2 space-y-6">
            <h1 className="text-lg md:text-2xl font-medium max-w-lg">
              SwiftSpend is a modern financial ecosystem designed to bridge the gap between complex
              banking and the need for instant, secure transactions.
            </h1>

            <h1 className="text-lg md:text-2xl font-medium max-w-lg">
              Every feature is placed exactly where you expect it, ensuring that managing your money
              feels natural rather than a chore.
            </h1>

            <h1 className="text-lg md:text-2xl font-medium max-w-lg">
              SwiftSpend is designed to be accessible to everyone, regardless of their financial
              literacy:
              <ul className="list-disc list-inside">
                <li>SwiftSpend is user-friendly and easy to navigate.</li>
                <li>
                  Move funds to anyone on the platform with a single click, ensuring your money
                  reaches its destination in real-time.
                </li>
                <li>
                  Access a chronological and transparent history of every transaction to stay
                  perfectly informed about your spending.
                </li>
              </ul>
            </h1>
          </div>
        </div>
      </div>
      <div class="px-10 py-10 bg-gray-100 mt-10">
        <h1 class="text-4xl font-bold text-gray-800 leading-[1.1] text-center">Why Choose Us?</h1>

        <div class="grid grid-cols-1 gap-10 mt-10 md:grid-cols-3">
          <div
            v-for="card in featureCards"
            :key="card.title"
            class="flex flex-col items-center gap-4"
          >
            <img
              :src="card.image"
              class="w-full max-w-[300px] h-auto object-contain"
              :alt="card.title"
            />
            <h1 class="text-2xl font-bold text-gray-800 leading-[1.1] text-center">
              {{ card.title }}
            </h1>
            <p class="text-lg font-medium text-gray-600 max-w-lg text-center">
              {{ card.description }}
            </p>
          </div>
        </div>
      </div>

      <footer class="bg-indigo-700 mt-20 px-6 py-12">
        <div class="max-w-7xl mx-auto">
          <div class="grid grid-cols-1 md:grid-cols-4 gap-8 text-white">
            <div class="space-y-4">
              <h2 class="text-2xl font-bold tracking-tight">SwiftSpend</h2>
              <p class="text-indigo-100 text-sm">
                Secure, fast, and transparent digital wallet for your daily transactions.
              </p>
            </div>

            <div>
              <h3 class="text-sm font-semibold uppercase tracking-wider mb-4 text-indigo-200">
                Socials
              </h3>
              <ul class="space-y-2 text-indigo-100">
                <li>
                  <a href="https://www.linkedin.com/" class="hover:text-white transition-colors"
                    >LinkedIn</a
                  >
                </li>
                <li>
                  <a href="https://github.com/" class="hover:text-white transition-colors"
                    >GitHub</a
                  >
                </li>
                <li>
                  <a href="https://twitter.com/" class="hover:text-white transition-colors"
                    >Twitter</a
                  >
                </li>
              </ul>
            </div>

            <div>
              <h3 class="text-sm font-semibold uppercase tracking-wider mb-4 text-indigo-200">
                Resources
              </h3>
              <ul class="space-y-2 text-indigo-100">
                <li>
                  <router-link to="/" class="hover:text-white transition-colors">Home</router-link>
                </li>
                <li>
                  <router-link to="/login" class="hover:text-white transition-colors"
                    >Login</router-link
                  >
                </li>
              </ul>
            </div>

            <div>
              <h3 class="text-sm font-semibold uppercase tracking-wider mb-4 text-indigo-200">
                Contact
              </h3>
              <ul class="space-y-2 text-indigo-100">
                <li class="flex items-center">
                  <span>+6012-345 6789</span>
                </li>
                <li class="flex items-center">
                  <span>support@swiftspend.com</span>
                </li>
                <li>Kuchai Lama, 58200,Kuala Lumpur, Malaysia</li>
              </ul>
            </div>
          </div>

          <div class="border-t border-indigo-500 mt-12 pt-8 text-center text-indigo-200 text-sm">
            <p>&copy; 2026 SwiftSpend. All rights reserved.</p>
          </div>
        </div>
      </footer>
    </div>
  </PublicLayout>
</template>
