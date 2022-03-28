let baseUrl = ''
if (process.env.NODE_ENV === 'development') {
  baseUrl = 'http://localhost:8081'
} else {
  // 你的 API 地址
  baseUrl = 'http://localhost:8081'
}

export default {
  baseUrl
}