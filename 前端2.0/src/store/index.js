import Vue from 'vue'
import Vuex from 'vuex'
import User from "@/store/User";
Vue.use(Vuex);
const store=new Vuex.Store({
    state:{
        count:0
    },
    modules:{
        User:User,
    }
})
export default store