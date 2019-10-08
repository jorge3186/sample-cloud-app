import Vue from 'vue'
import SuiVue from 'semantic-ui-vue';
import App from './App.vue'

Vue.config.productionTip = false

Vue.use(SuiVue);

import 'semantic-ui-css/semantic.min.css';

new Vue({
  render: h => h(App),
}).$mount('#app')
