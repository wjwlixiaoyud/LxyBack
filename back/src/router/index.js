import Vue from 'vue'
import VueRouter from 'vue-router'
import store from "@/store";

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    component: () => import("../views/Manage.vue"),
    redirect:"/home",
    children:[
      {path: 'home', name: '首页', component: () => import("../views/Home.vue")},
      {path: 'user', name: '用户管理', component: () => import("../views/User.vue")},
      {path: 'role', name: '角色管理', component: () => import("../views/Role.vue")},
      {path: 'menu', name: '菜单管理', component: () => import("../views/Menu.vue")},
      {path: 'person', name: '个人信息', component: () => import("../views/Person.vue")},
      {path: 'file', name: '文件管理', component: () => import("../views/File.vue")}
    ]
  },
  {
    path: '/about',
    name: 'about',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "about" */ '../views/AboutView.vue')
  },
  {
    path: '/login',
    name: 'Login',
    component: () => import(/* webpackChunkName: "about" */ '../views/Login.vue')
  },
  {
    path: '/resgister',
    name: 'Resgister',
    component: () => import(/* webpackChunkName: "about" */ '../views/Resgister.vue')
  },
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})
router.beforeEach((to, from, next)=>{
  localStorage.setItem("currentPathName",to.name)
  store.commit("setPath")//设置当前的路由名称
  next()
})
export default router
