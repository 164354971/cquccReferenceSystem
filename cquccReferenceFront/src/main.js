import Vue from 'vue'
import Vuex from 'vuex';
import App from './App.vue'
import axios from 'axios';
import router from "@/router/index.js"
import store from "@/store/index.js"
import qs from 'qs'
import * as echarts from 'echarts';
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import SlideVerify from 'vue-monoplasty-slide-verify';

import '@/css/html.css'
/*import '@/css/common.css';
import '@/less/student.less';
import '@/iconfont/iconfont.css';*/

Vue.use(ElementUI)
Vue.use(SlideVerify);
Vue.prototype.$echarts = echarts
axios.defaults.baseURL = 'http://localhost:8088/cqucc'
Vue.prototype.axios = axios
Vue.config.productionTip = false

new Vue({
  render: h => h(App),
  //在Vue项目中，要想把路由用起来，必须把路由实例对象，通过下面的方式进行挂载
  //router: 路由的实例对象,属性名和属性值同名可直接写属性名
  //router: router,
  router, store
}).$mount('#app')
