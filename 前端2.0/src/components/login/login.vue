<template>
  <v-card-text class="my-4">
    <v-form ref="form">
      <v-text-field label="用户名" :rules="UserNameRules" v-model="UserName" prepend-icon="mdi-account-box" required></v-text-field>
      <v-text-field type="password" label="密码" :rules="PasswordRules" v-model="Password" prepend-icon="mdi-lock" required></v-text-field>
      <v-card-actions>
        <v-row justify="center">
          <v-btn color="primary" large @click="userLogin">
            登录
          </v-btn>
        </v-row>
      </v-card-actions>
    </v-form>
  </v-card-text>
</template>
<script>
import {mapActions} from "vuex";

export default {
  name:'LoginCom',
  data(){
    return{
      UserName:"",
      UserNameRules:[
          v=>!!v||'用户名是必须项'
      ],
      Password:"",
      PasswordRules:[
        v=>!!v||'密码是必须项'
      ]
    }

  },
  methods:{
    async userLogin(){
      this.$refs.form.validate()
      if(this.UserName && this.Password){
        let LoginData=new FormData()
        const data={
          userAccount:this.UserName,
          password:this.Password,
          isAdmin:0,
          emailAddress:null,
          companyAddress:null,
          phoneNumber:null
        }
        for(const key in data){
          LoginData.append(key,data[key]);
        }
        await this.login(LoginData)
      }
    },
    ...mapActions("User", ["login"]),
  }

}
</script>
<style scoped>
v-text-field{
  width: 80%;
}
v-btn{
  width:200px;
}
</style>
