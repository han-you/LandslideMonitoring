import Vue from 'vue'
import App from './App.vue'
import Vuetify from '@/plugins/vuetify'
import 'vuetify/dist/vuetify.min.css'
import router from "@/router/index"
import particles from 'particles.js'
import store from "./store"
import request from "@/utils/request";
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import VueNotification from "vue-notification";
Vue.use(ElementUI,{size:'small'});
Vue.use(VueNotification)
Vue.prototype.$request = request
Vue.use(particles)
Vue.config.productionTip = false
new Vue({
  render: h => h(App),
  vuetify:Vuetify,
  router:router,
  store:store,
  el: '#app',
}).$mount('#app')