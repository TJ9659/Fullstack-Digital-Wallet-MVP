// utils/error-mapper.ts
export const mapBackendErrors = (
  err: any,
  localErrors: any,
  setGeneralError: (msg: string) => void,
) => {
  const backendError = err.response?.data
  const field = backendError?.field
  const detail = backendError?.detail || 'An unexpected error occurred'

  if (field && Object.prototype.hasOwnProperty.call(localErrors, field)) {
    localErrors[field] = detail
  } else {
    setGeneralError(detail)
  }
}
