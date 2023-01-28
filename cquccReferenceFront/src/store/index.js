import Vue from 'vue';
import Vuex from 'vuex';

Vue.use(Vuex);

const store = new Vuex.Store({
    state: {
        // 存储token
        Authorization: localStorage.getItem('Authorization') ? localStorage.getItem('Authorization') : '',
        user: localStorage.getItem('user') ? localStorage.getItem('user') : ''
    },
    mutations: {
        // 修改token，并将token存入localStorage
        changeLogin (state, user) {
            state.user = user.user;
            localStorage.setItem('user', user.user);
        }
    }
});
export default store;