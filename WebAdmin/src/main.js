// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import 'core-js/es6/promise'
import 'core-js/es6/string'
import 'core-js/es7/array'
import 'flatpickr/dist/flatpickr.css'
// import cssVars from 'css-vars-ponyfill'
import Vue from 'vue'
import BootstrapVue from 'bootstrap-vue'
import VueFlatPickr from 'vue-flatpickr-component'
import App from './App'
import router from './router'
import { getToken } from './openapi';

router.beforeEach((to, from, next) => {
  if (!getToken()) {
    // Guest
    if (to.name === 'Login') {
      next();
    } else {
      next('/pages/login')
    }
  } else {
    // Admin
    if (to.name === 'Login') {
      next('/');
    } else {
      next()
    }
  }
})
// todo
// cssVars()

Vue.use(BootstrapVue)
Vue.use(VueFlatPickr)

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  template: '<App/>',
  components: {
    App
  }
})
