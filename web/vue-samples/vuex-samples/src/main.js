import Vue from 'vue'
import App from './App.vue'

import Vuex from './store'

// 关闭Vue的生产提示
Vue.config.productionTip = false

import router from './router'

new Vue({
    render: h => h(App),
    router,
    store: Vuex
}).$mount('#app')
