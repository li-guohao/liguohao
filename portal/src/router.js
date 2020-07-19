import Vue from 'vue'
import VueRouter from 'vue-router' //路由组件
// 后台管理
import Manager from '@/components/manager/Index'    // @ 代表 src路径下   Index.vue 可省略.vue后缀
import Welcome from '@/components/manager/Welcome'
import Login from '@/components/manager/user/Login' // Login.vue组件 等价于 from '../components/user/Login.vue' 或 src 下 components/manager/user/Login.vue
import UserInfo from '@/components/manager/user/UserInfo'
import ArticleEdit from '@/components/manager/article/Edit'
import ArticleList from '@/components/manager/article/List'
import TagManager from '@/components/manager/tag/Manager'
import CommentList from '@/components/manager/comment/List'
import SystemOption from '@/components/manager/system/Option'
import SystemLinks from '@/components/manager/system/Links'
import SystemSkin from '@/components/manager/system/Skin'
import Img from '@/components/manager/file/Img'
import Music from '@/components/manager/file/Music'
import Video from '@/components/manager/file/Video'


// PC前台
import Home from '@/components/pc/Home' //首页组件
import Notfound from '@/components/pc/Notfound' //404Notfound组件
import Error from '@/components/pc/Error' //500组件
import Article from '@/components/pc/article/Article' //文章组件
import ArticleInfo from '@/components/pc/article/ArticleInfo' //文章详情组件
import Tags from '@/components/pc/Tags' //标签墙组件
import SearchResult from '@/components/pc/SearchResult' //查询结果页面组件
// 测试组件
import Test from '@/components/Test' //查询结果页面组件



Vue.use(VueRouter)

const routes = [
  // {
  //   path: '*',
  //   redirect: '/notfound'
  // },
  {
    path: '/test',
    component: Test
  },
  {
    path: '/notfound',
    component: Notfound
  },
  {
    path: '/search/:term',
    component: SearchResult
  },
  {
    path: '/error',
    component: Error
  },
  {
    path: '/',
    name: 'Home',
    component: Home
  },
  {
    path: '/article',
    component: Article
  },
  { path: '/article/info/:aid', component: ArticleInfo },
  {
    path: '/tags',
    component: Tags
  },
  { //后台管理
    path: '/manager',
    component: Manager,
    redirect: '/manager/welcome',
    children: [
      { path: 'article/edit', component: ArticleEdit },
      { path: 'article/list', component: ArticleList },
      { path: 'tag/manager', component: TagManager },
      { path: 'comment/list', component: CommentList },
      { path: 'system/option', component: SystemOption },
      { path: 'system/skin', component: SystemSkin },
      { path: 'system/links', component: SystemLinks },
      { path: 'user/userInfo', component: UserInfo },
      { path: 'file/img', component: Img },
      { path: 'file/music', component: Music },
      { path: 'file/video', component: Video },
      {path:'welcome', component:Welcome}
    ]
    
  },
  {
    path: '/login',
    component: Login
  }
]

const router = new VueRouter({
  mode:'history', //这里使用浏览器历史模式路由
  routes
})

// 挂载路由导航守卫
router.beforeEach((to, from, next) => {
  // to 将要访问的路径
  // from 代表从哪个路径跳转而来
  // next 是一个函数，表示放行
  //     next()  放行    next('/login')  强制跳转

  // 访问的是manager开头的地址
  // if (to.path === '/manager/**') {
  //   const token = sessionStorage.getItem('token')
  //   const uid = sessionStorage.getItem('UID')

  //   if(!token) return next('/login')
  //   if(!uid) return next('/login')
  // }
  next()
})

export default router
