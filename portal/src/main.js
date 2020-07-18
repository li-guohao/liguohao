import Vue from 'vue'
import App from './App.vue'
import router from './router'                     // 导入路由，router下的index.js
import './assets/Global.less'                     //引入全局样式
import ElementUI from 'element-ui'                 // 导入element-ui,这里选择全部导入
import 'element-ui/lib/theme-chalk/index.css'     // 导入element-ui样式文件
import './assets/lib/font-awesome/css/font-awesome.min.css' //引入fa字体图标库
import mavonEditor from 'mavon-editor'            // 导入markdown编辑器
import 'mavon-editor/dist/css/index.css'          // 导入markdown编辑器对应的样式文件


import axios from 'axios'
// 配置请求的跟路径
// 本地开发
axios.defaults.baseURL = 'http://localhost:8888/'
// 线上部署
// axios.defaults.baseURL = 'https://api.liguohao.cn/'


// http request 拦截器 作用是添加全局请求头
axios.interceptors.request.use(
  config => {  
    config.headers['Authorization'] =  window.sessionStorage.getItem('token');
    config.headers['UID'] = window.sessionStorage.getItem('UID');
    return config;
  })
// 
Vue.prototype.$http = axios


// Vue全局配置
Vue.config.productionTip = false
Vue.use(mavonEditor)
Vue.use(ElementUI)

// 主Vue实例
new Vue({
  router,
  render: h => h(App)
}).$mount('#app')

