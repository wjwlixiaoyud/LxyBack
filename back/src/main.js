import Vue from 'vue'
import store from "@/store";
import App from './App.vue'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import router from './router'
import  './assets/gloable.css'
import request from "@/utils/request";

Vue.config.productionTip = false

Vue.use(ElementUI,{size:"mini"});

Vue.prototype.request = request

new Vue({
  router,
  store,
  el: '#app',
  render: h => h(App)
}).$mount('#app')
