<script setup lang="ts">
import { ref, reactive, watch } from 'vue'
import { formatCardNumber } from '@/utils/formatters'
import { useAccountStore } from '@/stores/accountStore'
import LoadingSpinner from '@/components/ui/LoadingSpinner.vue'
import { useNotificationStore } from '@/stores/notificationStore'

const accountStore = useAccountStore()
const notificationStore = useNotificationStore()

const form = ref({
  amount: undefined as number | undefined,
  cardNumber: '',
  expiry: '',
  cvv: '',
  bankName: 'maybank',
})

const errors = ref({
  amount: '',
  cardNumber: '',
  expiry: '',
  cvv: '',
  bankName: '',
})

const banks = [
  { id: 'maybank', name: 'Maybank2u' },
  { id: 'cimb', name: 'CIMB Clicks' },
  { id: 'pbe', name: 'Public Bank' },
  { id: 'rhb', name: 'RHB Now' },
  { id: 'hlb', name: 'Hong Leong Connect' },
]
const isLoading = ref(false)

const validAmount = (amount: number) => {
  return amount > 0
}

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

const checkForm = () => {
  errors.value.amount = ''
  errors.value.cardNumber = ''
  errors.value.cvv = ''
  errors.value.expiry = ''
  errors.value.bankName = ''

  if (!form.value.amount) {
    errors.value.amount = 'Please enter an amount'
  } else if (!validAmount(form.value.amount)) {
    errors.value.amount = 'Please enter an amount greater than 0.'
  }

  if (!form.value.cardNumber) {
    errors.value.cardNumber = 'Please enter a card number'
  }

  if (!form.value.expiry) {
    errors.value.expiry = 'Please enter an expiry date'
  }

  if (!form.value.cvv) {
    errors.value.cvv = 'Please enter a CVV'
  }

  if (!form.value.bankName) {
    errors.value.bankName = 'Please select a bank'
  }

  if (
    errors.value.amount ||
    errors.value.cardNumber ||
    errors.value.expiry ||
    errors.value.cvv ||
    errors.value.bankName
  ) {
    return
  }

  handleTopUp()
}

const handleTopUp = async () => {
  try {
    const payload = {
      ...form.value,
      cardNumber: form.value.cardNumber.replace(/\s/g, ''),
    }
    await accountStore.topUp(payload)
    notificationStore.notify(`Successfully toped up ${payload.amount}`, 'success')
  } catch (error) {
    console.error(error)
    notificationStore.notify('Top up failed', 'error')
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
      <label id="bank" class="text-sm font-medium text-gray-600">Select Bank</label>
      <select
        id="bank"
        v-model="form.bankName"
        class="w-full border border-gray-300 rounded-lg px-4 py-2 bg-white transition-all duration-200 outline-none focus:ring-2 focus:ring-indigo-600 focus:border-transparent"
      >
        <option v-for="bank in banks" :key="bank.id" :value="bank.id">
          {{ bank.name }}
        </option>
      </select>
      <span v-if="errors.bankName" class="text-red-500 font-semibold">{{ errors.bankName }}</span>
    </div>
    <div class="relative w-full">
      <label id="cardNumber" class="text-sm font-medium text-gray-600">Card Number</label>
      <input
        id="cardNumber"
        v-model="form.cardNumber"
        type="text"
        placeholder="0000 0000 0000 0000"
        maxlength="19"
        @input="formatCardNumber($event, form)"
        class="w-full border border-gray-300 rounded-lg px-4 py-2 bg-white transition-all duration-200 outline-none focus:ring-2 focus:ring-indigo-600 focus:border-transparent"
      />
      <span v-if="errors.cardNumber" class="text-red-500 font-semibold">{{
        errors.cardNumber
      }}</span>
    </div>

    <div class="flex gap-4">
      <div class="flex-1">
        <label id="expiry" class="text-sm font-medium text-gray-600">Expiry (MM/YY)</label>
        <input
          id="expiry"
          v-model="form.expiry"
          type="text"
          placeholder="12/26"
          oninput="this.value = this.value.replace(/[^0-9/]/g, '')"
          maxlength="5"
          class="w-full border border-gray-300 rounded-lg px-4 py-2 bg-white"
        />
        <span v-if="errors.expiry" class="text-red-500 font-semibold">{{ errors.expiry }}</span>
      </div>
      <div class="w-24">
        <label id="cvv" class="text-sm font-medium text-gray-600">CVV</label>
        <input
          id="cvv"
          v-model="form.cvv"
          type="password"
          placeholder="123"
          autocomplete="off"
          inputmode="numeric"
          oninput="this.value = this.value.replace(/\D+/g, '')"
          maxlength="3"
          class="w-full border border-gray-300 rounded-lg px-4 py-2 bg-white"
        />
        <span v-if="errors.cvv" class="text-red-500 font-semibold">{{ errors.cvv }}</span>
      </div>
    </div>

    <div class="flex mt-4 items-center justify-center">
      <button
        class="min-w-[200px] bg-indigo-600 font-semibold text-white border border-slate-200 rounded-xl px-4 py-4 hover:bg-indigo-700 hover:cursor-pointer transition duration-300 ease-in-out"
        type="submit"
      >
        <div v-if="!isLoading">
          <span>Top Up</span>
        </div>

        <div v-else class="flex justify-center items-center gap-2">
          <LoadingSpinner />
          Processing...
        </div>
      </button>
    </div>
  </form>
</template>
