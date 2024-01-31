<template>
    <v-app>
      <v-main style="padding: 0;background-image: linear-gradient(to right top, #a1d1ff, #c2daff, #dce5ff, #f0f1ff, #ffffff);">
        <v-row style="height: 50%;border: 2px dashed grey">
          <v-col align-self="center" style="width: 50%">
        <v-card class="mx-auto flat" outlined width="400">
          <v-list-item three-line>
          <v-list-item-content>
            <div class="text-h3 mb-10">
              {{UserDetails.userAccount}}
            </div>
          <v-list-item-title class="orange--text mb-3">
            mail: {{ UserDetails.emailAddress}}
          </v-list-item-title>
          <v-list-item-title class="blue--text mb-3">
            address: {{ UserDetails.companyAddress}}
          </v-list-item-title>
          <v-list-item-title>
            phone: {{ UserDetails.phoneNumber}}
          </v-list-item-title>
          </v-list-item-content>
          </v-list-item>
          <v-card-actions >
            <div class="mx-auto">
              <v-btn :large="dynamicWidth>400" color="primary"
                     rounded
                     depressed
                     @click="changeDetail">
                修改信息
              </v-btn>
            </div>
          </v-card-actions>
        </v-card>
          </v-col>
        </v-row>
        <v-dialog v-model="WhetherChangeDetails" width="400px">
          <v-card>
            <v-tabs>
              <v-tab>用户信息</v-tab>
              <v-tab>密码修改</v-tab>
              <v-tab-item>
                <v-form
                  ref="DetailForm"
                  class="pa-5"
                  lazy-validation>
                  <v-text-field
                    v-model="UserMessage.userAccount"
                    required
                    disabled
                    ></v-text-field>
                  <v-text-field
                    v-model="UserMessage.emailAddress"
                    :rules="MailRules"
                    label="邮箱"
                    required></v-text-field>
                  <v-text-field
                      v-model="UserMessage.companyAddress"
                      :rules="AddressRules"
                      label="地址"
                      required></v-text-field>
                  <v-text-field
                      v-model="UserMessage.phoneNumber"
                      :rules="PhoneRules"
                      :counter="11"
                      label="电话号码"
                      required></v-text-field>
                  <v-btn
                      class="mr-4"
                      color="info"
                      @click="reset"
                  >
                    重置
                  </v-btn>
                  <v-btn
                      color="success"
                      @click.prevent="save"
                  >
                    保存
                  </v-btn>
                </v-form>
              </v-tab-item>
              <v-tab-item>
                <v-form
                    ref="PasswordFrom"
                    class="pa-5"
                    lazy-validation
                >
                  <v-text-field
                      type="password"
                      v-model="OldPassword"
                      :rules="passwordRules"
                      label="旧密码"
                      required
                  >
                  </v-text-field>
                  <v-text-field
                      type="password"
                      v-model="NewPassword"
                      :rules="passwordRules"
                      label="新密码"
                      required
                  >
                  </v-text-field>
                  <v-btn
                      color="success"
                      @click.prevent="savePass"
                  >
                    保存
                  </v-btn>
                </v-form>
              </v-tab-item>
            </v-tabs>
          </v-card>
        </v-dialog>
      </v-main>
    </v-app>
</template>
<script>
import { mapGetters ,mapState,mapActions} from "vuex";
import request from "@/utils/request";

export default {
  data() {
    return {
      ChangeSuccessfully:false,
      WhetherChangeDetails:false,
      UserMessage:{
        userAccount:null,
        password:null,
        isAdmin:null,
        emailAddress:null,
        companyAddress:null,
        phoneNumber:null
      },
      MailRules:[
        v => !!v || '邮箱必填',
      ],
      AddressRules:[
        v => !!v || '地址必填',
      ],
      PhoneRules:[
        v => !!v || '电话号码必填',
      ],
      OldPassword:'',
      NewPassword:'',
      passwordRules: [
        v => !!v || '密码必填',
      ],
    }
  },
  computed: {
    ...mapGetters("User", ["getUser"]),
    ...mapState('User',['User']),
    UserDetails(){
      return this.User
    },
    dynamicWidth() {
      switch (this.$vuetify.breakpoint.name) {
        case 'xs':
          return 220
        case 'sm':
          return 360
        case 'md':
          return 500
        case 'lg':
          return 600
        case 'xl':
          return 800
        default:
          return 400
      }
    },
  },
  methods:{
    ...mapActions('User',['logout']),

    changeDetail(){
      this.UserMessage= JSON.parse(JSON.stringify((this.UserDetails)))
      this.WhetherChangeDetails=true
    },
    reset(){
      this.UserMessage=this.UserDetails
    },
    save(){
      // console.log("保存开始啦")
      // console.log('新的User是')
      // console.log(this.UserMessage)
      // console.log('旧的User是'+JSON.stringify(this.User))
      if(this.$refs.DetailForm.validate()){
        // console.log("表单验证通过")
        if(JSON.stringify(this.UserMessage)==JSON.stringify(this.User)){
          this.$notify({
            title:'表单没变化',
            type:'info'
          })
          // console.log("表单没变化")
          this.WhetherChangeDetails=false
        }
        else{
          // console.log("开始更新啦")
          let form=new FormData()
          const data={
            userAccount:this.UserMessage.userAccount,
            password:'',
            isAdmin:null,
            emailAddress:this.UserMessage.emailAddress,
            companyAddress:this.UserMessage.companyAddress,
            phoneNumber:this.UserMessage.phoneNumber
          }
          for(const key in data) {
            form.append(key, data[key])
          }

          request.post('/Per_Msg/Company_Addr_Modify',form).then(res=>{
            console.log(res)
            if(res.code==1)
            {
              this.ChangeSuccessfully=true
              this.$notify({
                title:'地址修改成功',
                type:'success'
              })
            }else if(res.code==17)
            {
              this.$notify({
                title:'地址过长',
                type:'error'
              })
            }else{
              this.$notify({
                title:'地址修改失败',
                type:'error'
              })
            }
          })
          setTimeout(()=>{
            request.post('/Per_Msg/Phone_Num_Modify',form).then(res=>{
              console.log(res)
              if(res.code==1)
              {
                this.ChangeSuccessfully=true
                this.$notify({
                  title:'手机号修改成功',
                  type:'success'
                })
              }else if(res.code==19)
              {
                this.$notify({
                  title:'手机号长度错误',
                  type:'error'
                })
              }else if(res.code==15){
                this.$notify({
                  title:'手机号含有非法字符',
                  type:'error'
                })
              }else if(res.code==16){
                this.$notify({
                  title:'新旧手机号一样',
                  type:'info'
                })
              }
            })
          },1000)
          setTimeout(()=>{
            request.post('/Per_Msg/Email_Addr_Modify',form).then(res=>{
              console.log(res)
              if(res.code==1)
              {
                this.ChangeSuccessfully=true
                this.$notify({
                  title:'邮箱修改成功',
                  type:'success'
                })
              }else if(res.code==24)
              {
                this.$notify({
                  title:'邮箱过长',
                  type:'error'
                })
              }else if(res.code==26){
                this.$notify({
                  title:'邮箱格式错误',
                  type:'error'
                })
              }else if(res.code==25){
                this.$notify({
                  title:'新旧邮箱地址一样',
                  type:'info'
                })
              }
            })
          },2000)

        }
      }else{
        this.$notify({
          title:'表项错误',
          type:'error'
        })
      }
    },
    savePass(){
      if(this.$refs.PasswordFrom.validate()){
        let extend='UserAccount='+this.UserDetails.userAccount+'&'+'OldPassword='+this.OldPassword+'&'+'NewPassword='+this.NewPassword
         request.post('/Register/User_Passwd_Modify?'+extend).then(response=>{
        console.log(response)
            if(response.code==1)
            {
              this.WhetherChangeDetails=false
              this.$notify({
                title: '请重新登录！',
                text: "修改成功",
                type: 'info'
              })
              this.ChangeSuccessfully=true
            }
            else if(response.code==13) {
              this.$notify({
                title: '密码错误',
                type: 'error'
              })
            }
            else if(response.code==14) {
              this.$notify({
                title: '新旧密码一致',
                type: 'error'
              })
            }
            else if(response.code==20) {
              this.$notify({
                title: '新密码过长',
                type: 'error'
              })
            }
            else if(response.code==40) {
              this.$notify({
                title: '新密码或者旧密码含有非法字符',
                type: 'error'
              })
            }
            else if(response.code==41) {
              this.$notify({
                title: '新密码过短',
                type: 'error'
              })
            }
        })
      }else{
        this.$notify({
          title:'表格错误',
          type:'error'
        })
      }
    }
  },
  watch:{
    ChangeSuccessfully(newvalue,oldvalue){
      console.log(oldvalue)
      if(newvalue){
        this.logout()
      }
    }
  }
};
</script>
<style>

</style>