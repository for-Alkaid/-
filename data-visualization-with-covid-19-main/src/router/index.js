import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

const routes = [
  {
    path: '/',
    name: 'Main',
    component: () => import('@/views/main-layout')
  },
  {
    path: '/zero-patient',
    name: 'findZeroPatient',
    component: () => import('@/views/zero-patient')
  },
  {
    path: '/covid19-graph-mock',
    name: 'mockGraph',
    component: () => import('@/views/covid19-graph-mock')
  },
]

const router = new Router({
  routes: routes
})

export default router