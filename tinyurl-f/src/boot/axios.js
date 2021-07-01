import { boot } from 'quasar/wrappers'
import axios from 'axios'
import { Notify } from 'quasar'

// Be careful when using SSR for cross-request state pollution
// due to creating a Singleton instance here;
// If any client changes this (global) instance, it might be a
// good idea to move this instance creation inside of the
// "export default () => {}" function below (which runs individually
// for each client)
const api = axios.create()
axios.defaults.baseURL = process.env.NODE_ENV !== 'production' ? '/dev' : ''
axios.interceptors.request.use(config => {
  config.headers['Content-Type'] = 'application/json'
  return config
})
axios.interceptors.response.use(response => response, error => {
  const originalReq = error.config
  // in dev mode, 401 returns /dev/endpoint and /dev is concatinated again making url /dev/dev/endpoint
  if (process.env.NODE_ENV !== 'production') {
    originalReq.url = originalReq.url.replace(/^\/dev/, '')
  }

  Notify.create({
    type: 'negative',
    message: error.response.data.message || msg,
    timeout: 2000
  })
  return Promise.reject(error.response)
})
export default boot(({ app }) => {
  // for use inside Vue files (Options API) through this.$axios and this.$api

  app.config.globalProperties.$axios = axios
  // ^ ^ ^ this will allow you to use this.$axios (for Vue Options API form)
  //       so you won't necessarily have to import axios in each vue file

  app.config.globalProperties.$api = api
  // ^ ^ ^ this will allow you to use this.$api (for Vue Options API form)
  //       so you can easily perform requests against your app's API
})

function get(url, data) {
  return axios.get(url, data)
}

function post(url, data) {
  return axios.post(url, data)
}

export { api, get, post }
