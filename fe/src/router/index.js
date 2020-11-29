import Vue from 'vue'
import VueRouter from 'vue-router'
import Layout from '@/layout'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    component: () => import('../views/login.vue'),
    hidden: true
  },

  {
    path:'/layout',
    name:'layout',
    component: () => import(/* webpackChunkName: "about" */ '../layout')
  },
  {
    path:'/new_task',
    component: Layout,
    name:'new_task',
    redirect: '/new_task/index',
    children: [
      {
        path: 'index',
        name: 'Form',
        component: () => import('../views/task/new_task'),
        meta: { title: 'new_task', icon: 'new_task' }
      }
    ]
  },
  {
    path:'/task_details',
    component: Layout,
    name:'task_details',
    redirect: '/task_details/index',
    children: [
      {
        path: 'index',
        name: 'Form',
        component: () => import('../views/task/task_details'),
        meta: { title: 'task_details', icon: 'task_details' }
      }
    ]

  },
  {
    path:'/do_task_details',
    component: Layout,
    name:'do_task_details',
    redirect: '/do_task_details/index',
    children: [
      {
        path: 'index',
        name: 'Form',
        component: () => import('../views/task/do_task_details'),
        meta: { title: 'do_task_details', icon: 'do_task_details' }
      }
    ]

  },
  {
    path:'/graph',
    component: Layout,
    name:'graph',
    redirect: '/graph/index',
    children: [
      {
        path: 'index',
        name: 'Form',
        component: () => import('../views/graph/graph'),
        meta: { title: 'graph', icon: 'task_details' }
      }
    ]
  },
  {
    path:'/Home',
    component: Layout,
    name:'Home',
    redirect: '/Home/index',
    children: [
      {
        path: 'index',
        name: 'Form',
        component: () => import('../views/Home.vue'),
        meta: { title: 'Home', icon: 'Home' }
      }
    ]
  },
  {
    path:'/tree',
    component: Layout,
    name:'tree',
    redirect: '/tree/index',
    children: [
      {
        path: 'index',
        name: 'Form',
        component: () => import('../views/tree/tree'),
        meta: { title: 'tree', icon: 'tree' }
      }
    ]
  },
  {
    path:'/Sidebar2',
    component: Layout,
    name:'Sidebar2',
    children: [
      {
        path: 'index',
        name: 'Form',
        component: () => import(/* webpackChunkName: "about" */ '../views/Home.vue')
      }
    ]
  },
  {
    path:'/organization',
    component: Layout,
    name:'org_management',
    redirect: '/organization/index',
    children: [
      {
        path: 'index',
        name: 'Form',
        component: () => import('../views/organization/org_management.vue'),
        meta: { title: 'org_management', icon: 'org_management' }
      }
    ]
  },{
    path: '/layout',
    name: 'layout',
    component: () => import('../layout/index')
  }
]


const createRouter = () => new VueRouter({
  // mode: 'history', // require service support
  scrollBehavior: () => ({ y: 0 }),
  routes: routes
})

const router = createRouter()

export function resetRouter() {
  const newRouter = createRouter()
  router.matcher = newRouter.matcher // reset router
}

export default router
