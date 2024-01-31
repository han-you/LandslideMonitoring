<template>
  <v-app>
    <component :is="selectDeviceInfo"></component>
  </v-app>
</template>
<script>
import {mapGetters} from "vuex";
import adminDeviceInfo from "@/components/manage/info/AdminDeviceInfo.vue"
import userDeviceInfo from "@/components/manage/info/UserDeviceInfo.vue"
export default{
  components:{
    adminDeviceInfo,
    userDeviceInfo
  },
  data(){
    return{
      bar:['adminDeviceInfo','userDeviceInfo']
    }
  },
  methods:{

  },
  computed:{
    ...mapGetters('User',['getUserRole']),
    selectDeviceInfo(){
      let role=this.getUserRole
      if(role=='Admin'){
        return this.bar[0]
      }else
      {
        return this.bar[1]
      }
    }
  }
}
</script>
<style>

</style>