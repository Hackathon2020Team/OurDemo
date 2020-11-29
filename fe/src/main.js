import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import cookies from 'vue-cookies'
import toast from '@/components/toast';

//echarts
import * as echarts from 'echarts';
export * from 'echarts/src/echarts';
Vue.prototype.$echarts = echarts;
Vue.use(toast);
Vue.config.productionTip = false;
Vue.prototype.$cookies = cookies;
// this.$cookies.config('1d');
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css'
import 'normalize.css/normalize.css'

import '@/styles/index.scss' // global css

// import '@/icons' // icon
// import '@/permission' // permission control

Vue.use(ElementUI);

new Vue({
  router,
  store,
  cookies,
  render: h => h(App)
}).$mount('#app')
