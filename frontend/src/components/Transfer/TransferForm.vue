<script setup lang="ts">
import { ref, reactive, watch } from 'vue'
import { formatCardNumber } from '@/utils/formatters'
import { useAccountStore } from '@/stores/accountStore'
import LoadingSpinner from '@/components/ui/LoadingSpinner.vue'
import { useNotificationStore } from '@/stores/notificationStore'
import { mapBackendErrors } from '@/utils/error-mapper'

const accountStore = useAccountStore()
const notificationStore = useNotificationStore()

const form = ref({
  amount: undefined as number | undefined,
  recipientAccountNumber: '',
  description: '',
})

const errors = ref({
  amount: '',
  recipientAccountNumber: '',
  description: '',
})

watch(
  () => form.value.amount,
  (newVal) => {
    if (!newVal) return

    const stringVal = newVal.toString()
    if (stringVal.includes('.')) {
      const [integer, fraction] = stringVal.split('.')
      if (fraction && fraction.length > 2) {
        form.value.amount = parseFloat(`${integer}.${fraction.substring(0, 2)}`)
      }
    }
  },
)

const isLoading = ref(false)

const validAmount = (amount: number) => {
  return amount > 0
}

const checkForm = () => {
  errors.value.amount = ''
  errors.value.recipientAccountNumber = ''
  errors.value.description = ''

  if (!form.value.amount) {
    errors.value.amount = 'Please enter an amount'
  } else if (!validAmount(form.value.amount)) {
    errors.value.amount = 'Please enter an amount greater than 0.'
  }

  if (!form.value.recipientAccountNumber) {
    errors.value.recipientAccountNumber = 'Please enter a valid account number'
  }

  if (!form.value.description) {
    errors.value.description = 'Please enter a description'
  }

  if (errors.value.amount || errors.value.recipientAccountNumber || errors.value.description) {
    return
  }

  handleTransfer()
}

const handleTransfer = async () => {
  try {
    await accountStore.transfer(form.value)
  } catch (err: any) {
    mapBackendErrors(err, errors.value, (msg) => (accountStore.error = msg))
  }
}
</script>

<template>
  <form class="flex flex-col gap-4 mt-4" @submit.prevent="checkForm">
    <div class="relative w-full">
      <label id="amount" class="text-sm font-medium text-gray-600">Amount</label>
      <input
        id="amount"
        v-model="form.amount"
        type="number"
        step="0.01"
        placeholder="0.00"
        class="w-full border border-gray-300 rounded-lg px-4 py-2 bg-white transition-all duration-200 outline-none focus:ring-2 focus:ring-indigo-600 focus:border-transparent"
      />
      <span v-if="errors.amount" class="text-red-500 font-semibold">{{ errors.amount }}</span>
    </div>

    <div class="relative w-full">
      <label id="accountNumber" class="text-sm font-medium text-gray-600">Account Number</label>
      <input
        id="accountNumber"
        v-model="form.recipientAccountNumber"
        type="text"
        autocomplete="off"
        inputmode="numeric"
        oninput="this.value = this.value.replace(/\D+/g, '')"
        maxlength="10"
        class="w-full border border-gray-300 rounded-lg px-4 py-2 bg-white transition-all duration-200 outline-none focus:ring-2 focus:ring-indigo-600 focus:border-transparent"
      />
      <span v-if="errors.recipientAccountNumber" class="text-red-500 font-semibold">{{
        errors.recipientAccountNumber
      }}</span>
    </div>

    <div class="relative w-full">
      <label id="description" class="text-sm font-medium text-gray-600">Description</label>
      <input
        id="accountNumber"
        v-model="form.description"
        type="text"
        autocomplete="off"
        class="w-full border border-gray-300 rounded-lg px-4 py-2 bg-white transition-all duration-200 outline-none focus:ring-2 focus:ring-indigo-600 focus:border-transparent"
      />
      <span v-if="errors.description" class="text-red-500 font-semibold">{{
        errors.description
      }}</span>
    </div>

    <div class="flex mt-4 items-center justify-center">
      <button
        class="min-w-[200px] bg-indigo-600 font-semibold text-white border border-slate-200 rounded-xl px-4 py-4 hover:bg-indigo-700 hover:cursor-pointer transition duration-300 ease-in-out"
        type="submit"
      >
        <div v-if="!accountStore.loading">
          <span>Transfer</span>
        </div>

        <div v-else class="flex justify-center items-center gap-2">
          <LoadingSpinner />
          Processing...
        </div>
      </button>
    </div>
  </form>
</template>
