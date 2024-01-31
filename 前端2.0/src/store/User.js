
import request from "@/utils/request";
import vue from "vue";
import router from "@/router";

export default{
    namespaced:true,
    state: {
        User:{
            UserAccount:null,
            Password:null,
            IsAdmin:null,
            EmailAddress:null,
            CompanyAddress:null,
            PhoneNumber:null
        }
    },
    getters:{
        getUser(state){
            return state.User
        },
        getUserRole(state){
            // console.log("Store 中的User是")
            // console.log(state.User)
            return state.User.isAdmin==1?'Admin':'User'
        },
        getUserAccount(state) {
            return state.User.userAccount
        },
        getUserPassword(state){
            return  state.User.password
        },
        getUserIsAdmin(state){
            return state.User.isAdmin
        },
        getUserMail(state){
            return state.User.mail
        },
        getUserAddress(state){
            return state.User.address
        },
        getUserPhone(state){
            return state.User.phone
        }
    },
    mutations:{
        setUser(state,msg){
            state.User=msg
        },
        setUserAccount(state,msg){
            state.User.userAccount=msg
        },
        setUserPassword(state,msg){
            state.User.password=msg
        },
        setUserIsAdmin(state,msg){
            state.User.isAdmin=msg
        },
        setUserMail(state,msg){
            state.User.mail=msg
        },
        setUserAddress(state,msg){
            state.User.address=msg
        },
        setUserPhone(state,msg){
            state.User.phone=msg
        }
    },
    actions:{
        login(context,msg){
            // let tmp={
            //     userAccount:'han-you',
            //     password:'12345678',
            //     isAdmin:0,
            //     mail:"599696746@qq.com",
            //     address:"四川省成都市双流区四川大学",
            //     phone:'12345678915'
            // }
            // context.commit('setUser',tmp)
            // await router.push({
            //     path:"/"+"index",
            // })
            request.post('/Register/User_Login',msg).then(response=>{
                console.log(response)
                context.commit('setUser',response.data)
                if(response.code==1){
                    localStorage.setItem("User",JSON.stringify(response.data))
                    //登陆成
                    // request.post('/User_Msg',msg).then(response=>{
                    //     console.log(response)
                    //     context.commit('setUser',response.data)
                    // })
                    vue.notify({
                        title:"验证成功",
                        text:"您即将跳转页面",
                        type:"success"
                    })
                    router.push({
                        path:"/"+"index",
                    })
                }else if(response.code==5){

                    vue.notify(({
                        title:'验证错误',
                        text:'密码过长',
                        type:'error'
                    }))
                }
                else if(response.code==23)
                {
                    vue.notify(({
                        title:'验证错误',
                        text:'账号过长',
                        type:'error'
                    }))
                }
                else if(response.code==6)
                {

                    vue.notify(({
                        title:'验证错误',
                        text:'密码过短',
                        type:'error'
                    }))
                }
                else if(response.code==9) {

                    vue.notify(({
                        title: '验证错误',
                        text: '账号或密码错误',
                        type: 'error'
                    }))
                }else if(response.code==4){
                    vue.notify(({
                        title: '验证错误',
                        text: '密码含有非法字符',
                        type: 'error'
                    }))
                }
            })
        },
        async register(context,msg){
            await request.post("/Register/User_Register",msg).then(response=>{
                // console.log(response)
                if(response.code==1){
                    vue.notify({
                        title:'注册成功',
                        text:'您可以返回登录',
                        type:'success'
                    })
                }else if(response.code==2){
                    vue.notify({
                        title:'信息有误',
                        text:'该用户已被注册',
                        type:'error'
                    })
                }
                else if(response.code==3){
                    vue.notify({
                        title:'信息有误',
                        text:'账号长度过长',
                        type:'error'
                    })
                }
                else if(response.code==4){
                    vue.notify({
                        title:'信息有误',
                        text:'密码字符非法',
                        type:'error'
                    })
                }
                else if(response.code==5){
                    vue.notify({
                        title:'信息有误',
                        text:'密码长度过长',
                        type:'error'
                    })
                }
                else if(response.code==6){
                    vue.notify({
                        title:'信息有误',
                        text:'密码长度过短',
                        type:'error'
                    })
                }
            })
        },
        isLogin(context){
            // console.log("IsLogin开始啦")
            // console.log(JSON.parse(localStorage.getItem('User')))
            const user = JSON.parse(localStorage.getItem('User'))
            // console.log(user)
            if(user)
            {
                context.commit('setUser',user)
            }else
            {
                setTimeout(()=>{
                    console.log('未登录')
                    router.push({
                        path:"/"
                    })
                })

            }
        },
        logout(context){
            vue.notify({
                title:'退出登录',
                text:'即将跳转登陆界面',
                type:'success'
            })
            setTimeout(()=>{
                localStorage.removeItem("currentRow")
                localStorage.removeItem("User")
                context.commit('setUser',null)
                router.push({
                    path:'/'
                })
            })
        }
    }
}