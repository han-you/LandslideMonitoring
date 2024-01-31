import VueRouter from "vue-router";
import indexView from "@/views/IndexView.vue";
import loginView from "@/views/LoginView.vue";
import Vue from "vue";
Vue.use(VueRouter)
const routes=[{
    path:"/",
    name:"login",
    component:loginView,
},
    {
        path:"/index",
        name:"index",
        component:indexView,
        redirect:'/index/userinfo',
        children:[
            {
                path:'userinfo',
                name:'userinfo',
                component:()=>import("@/components/manage/info/UserInfo.vue"),
            },
            {
                path:'deviceinfo',
                name:'deviceinfo',
                component:()=>import('@/components/manage/info/DeviceInfo.vue'),
            },
            {
                path:'adminsearchuser',
                name:'deviceinfo',
                component:()=>import('@/components/manage/info/AdminSearchUser.vue'),
            },
            {
                path:'devicedetails',
                name:'devicedetails',
                component:()=>import('@/components/manage/info/DeviceDetails.vue')
            }
        ]
    }];
const r=new VueRouter({
    mode:"hash",
    routes
    }
)
export default r;