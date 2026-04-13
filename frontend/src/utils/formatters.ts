export const formatCurrency = (value: number | string | undefined) => {
  if (value === undefined || value === null) return '$0.00'

  const amount = typeof value === 'string' ? parseFloat(value) : value

  return new Intl.NumberFormat('en-US', {
    minimumFractionDigits: 2,
  }).format(amount)
}

export const formatDate = (dateString: string) => {
  if (!dateString) return ''
  return new Date(dateString).toLocaleDateString('en-US', {
    month: 'short',
    day: 'numeric',
    year: 'numeric',
    hour: '2-digit',
    minute: '2-digit',
  })
}

export const formatCardNumber = (e: Event, cardForm: { cardNumber: string }) => {
  const input = e.target as HTMLInputElement
  // remove all non-digits to get the raw 16 numbers
  const rawValue = input.value.replace(/\D/g, '')

  // group by 4s and join with a space
  const formatted = rawValue.match(/.{1,4}/g)?.join(' ') || ''

  cardForm.cardNumber = formatted
}

export const validEmail = (email: string) => {
  const re = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/
  return re.test(email)
}
