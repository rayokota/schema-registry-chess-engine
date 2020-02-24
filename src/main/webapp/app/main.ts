// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.common with an alias.
import Vue from 'vue';
import setupAxiosInterceptors from './config/axios-interceptor';
import App from './App.vue';
import router from './router';
import {FontAwesomeIcon} from '@fortawesome/vue-fontawesome';
import * as bootstrapVueConfig from './config/config-bootstrap-vue';
import * as config from './shared/config';
import JhiItemCountComponent from './shared/ItemCount.vue';

import 'bootstrap/dist/css/bootstrap.css';
import 'bootstrap-vue/dist/bootstrap-vue.css';

Vue.config.productionTip = false;

setupAxiosInterceptors(() => console.log('Unauthorized!'));

const store = config.initVueXStore(Vue);
config.initVueApp(Vue);
bootstrapVueConfig.initBootstrapVue(Vue);
Vue.component('font-awesome-icon', FontAwesomeIcon);
Vue.component('jhi-item-count', JhiItemCountComponent);

/* eslint-disable no-new */
new Vue({
    el: '#app',
    components: {App},
    template: '<App/>',
    router,
    store
});
