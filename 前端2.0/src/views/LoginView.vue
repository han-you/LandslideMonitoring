<template>
  <section class="fullpage-section dark-gradient">
    <antimated-background></antimated-background>
    <v-container id="loginForm">
      <v-card class="mx-auto outer-card" max-width="450px">
        <v-card-title>
          <v-scroll-y-transition appear>
            <v-card class="v-header py-2 mx-auto my-n12" min-width="300px" raised>
              <v-card-title>
                <v-row justify="center">
                  <h3 style="color: white;">{{ formIndex == '0' ? 'L O G I N' : 'R e g i s t e r' }}</h3>
                </v-row>
              </v-card-title>
              <v-card-actions>
                <v-row justify="center">
                  <v-tooltip bottom>
                    <template v-slot:activator="{ on, attrs }">
                      <v-btn @click="changeFormIndex()" icon>
                        <v-icon v-bind="attrs" v-on="on" style="color: white;">mdi-cached</v-icon>
                      </v-btn>
                    </template>
                    <span>切换</span>
                  </v-tooltip>
                </v-row>
              </v-card-actions>
            </v-card>
          </v-scroll-y-transition>
        </v-card-title>
        <br />
        <keep-alive>
          <component :is="selectPage" style="max-height: 60vh;overflow:auto;"></component>
        </keep-alive>
      </v-card>
    </v-container>

  </section>

</template>
<script>
import LoginCom from "../components/login/login.vue"
import RegisterCom from "../components/login/register.vue"
import AntimatedBackground from "@/components/custom/antimated-background.vue";
export default {
  data(){
    return{
      formIndex:0,
      formType:["LoginCom","RegisterCom"]
    }
  },
  components:{
    LoginCom,
    RegisterCom,
    AntimatedBackground
  },
  computed:{
    selectPage(){
      return this.formType[this.formIndex]
    }
  },
  methods:{
    changeFormIndex(){
      if(this.formIndex==1)
      {
        this.formIndex=0;
      }
      else
      {
        this.formIndex=1;
      }
    }
  }
}
</script>
<style scoped>
#loginForm{
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
}
.v-header {
  background-image: linear-gradient(60deg, #758bfd, #3e5fe2);
  text-align: center;
  width: 100%;
  margin-top: -40px;
}
.outer-card{
  border-radius: 8px !important;
  min-height: 300px;
  min-width: 200px;
}
</style>