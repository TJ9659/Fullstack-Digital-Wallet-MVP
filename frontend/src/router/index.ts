import { createRouter, createWebHistory } from 'vue-router'
import Register from '@/views/auth/Register.vue'
import Login from '@/views/auth/Login.vue'
// import Dashboard from '@/views/Dashboard.vue'
import LandingPage from '@/views/LandingPage.vue'
import { useAuthStore } from '@/stores/authStore'
import NotFound from '@/views/NotFound.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/:pathMatch(.*)*',
      name: 'NotFound',
      component: NotFound,
    },
    {
      path: '/',
      name: 'landing-page',
      component: LandingPage,
      meta: { requiresGuest: true },
    },
    {
      path: '/dashboard',
      name: 'dashboard',
      component: () => import('@/views/dashboard/Home.vue'),
      meta: { requiresAuth: true },
    },
    {
      path: '/transfer',
      name: 'transfer',
      component: () => import('@/views/dashboard/Transfer.vue'),
      meta: { requiresAuth: true },
    },
    {
      path: '/top-up',
      name: 'topup',
      component: () => import('@/views/dashboard/Topup.vue'),
      meta: { requiresAuth: true },
    },
    {
      path: '/transactions',
      name: 'transactions',
      component: () => import('@/views/dashboard/History.vue'),
      meta: { requiresAuth: true },
    },
    // {
    //   path: '/privacy',
    //   name: 'privacy',
    //   component: () => import('@/views/Privacy.vue'),
    //   meta: { guest: true },
    // },
    // {
    //   path: '/terms',
    //   name: 'terms',
    //   component: () => import('@/views/Terms.vue'),
    //   meta: { guest: true },
    // },
    // {
    //   path: '/contact',
    //   name: 'contact',
    //   component: () => import('@/views/Contact.vue'),
    //   meta: { guest: true },
    // },
    {
      path: '/login',
      name: 'login',
      component: Login,
      meta: { requiresGuest: true },
    },
    {
      path: '/register',
      name: 'register',
      component: Register,
      meta: { requiresGuest: true },
    },
    {
      path: '/profile',
      name: 'profile',
      component: () => import('@/views/dashboard/Profile.vue'),
      meta: { requiresAuth: true },
    },
    // {
    //   path: '/dashboard',
    //   name: 'dashboard',
    //   component: Dashboard,
    //   meta: { requiresAuth: true },
    // },
  ],
})

router.beforeEach((to, from, next) => {
  const isAuthenticated = !!localStorage.getItem('token')
  if (to.meta.requiresAuth && !isAuthenticated) {
    next({ name: 'login' })
  } else if (to.meta.requiresGuest && isAuthenticated) {
    next({ name: 'dashboard' })
  } else {
    next()
  }
})

export default router
