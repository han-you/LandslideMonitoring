<template>
  <v-card-text class="my-4 ">
    <v-form ref="form">
      <v-text-field label="用户名" :rules="UserNameRules" v-model="UserName" prepend-icon="mdi-account-box" required></v-text-field>
      <v-text-field type="password" label="密码" :rules="PasswordRules" v-model="Password" prepend-icon="mdi-lock" required></v-text-field>
      <v-text-field type="password" label="确认密码" :rules="PasswordValiRules" v-model="PasswordVali" prepend-icon="mdi-lock" required></v-text-field>
      <v-fade-transition appear>
        <v-card-actions>
          <v-row justify="center">
            <v-btn color="primary" depressed large style="color: white;" tile @click="userRegister()">
              快 速 注 册
            </v-btn>
          </v-row>
        </v-card-actions>
      </v-fade-transition>
    </v-form>
  </v-card-text>
</template>
<script>
import {mapActions} from "vuex";
import vue from "vue";
export default {
  name:"RegisterCom",
  data(){
    return{
      UserName:"",
      Password:"",
      PasswordVali:"",
      UserNameRules:[
        v=>!!v||'用户名是必须项'
      ],
      PasswordRules:[
        v=>!!v||'密码是必须项'
      ],
      PasswordValiRules:[
        v=>!!v||'密码是必须项'
      ]
    }
  },
  methods:{
    async userRegister(){
      this.$refs.form.validate()
      if(this.UserName&&this.Password&&this.PasswordVali){
        if(this.Password!=this.PasswordVali){
          vue.notify({
            title:'注册失败',
            text:'两次密码需要保持一致',
            type:'error'
          })
          return;
        }
        let LoginData=new FormData()
        let RegisterData={
          userAccount:this.UserName,
          password:this.Password,
          isAdmin:null,
          mail:null,
          address:null,
          phone:null
        }
        for(const key in RegisterData){
          LoginData.append(key,RegisterData[key])
        }
        await this.register(LoginData)
      }
    },
    ...mapActions("User",["register"])
  }
}
</script>
<style>

</style>