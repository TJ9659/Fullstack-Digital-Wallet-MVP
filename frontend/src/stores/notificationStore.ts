import { defineStore } from 'pinia'

export const useNotificationStore = defineStore('notification', {
  state: () => ({
    message: '',
    type: 'success' as 'success' | 'error' | 'warning',
    show: false,
    timeout: null as ReturnType<typeof setTimeout> | null,
  }),

  actions: {
    notify(message: string, type: 'success' | 'error' | 'warning' = 'success') {
      if (this.timeout) {
        clearTimeout(this.timeout)
      }
      this.message = message
      this.type = type
      this.show = true

      this.timeout = setTimeout(() => {
        this.show = false
        this.timeout = null
      }, 3000)
    },

    close() {
      this.show = false
      if (this.timeout) clearTimeout(this.timeout)
    },
  },
})
