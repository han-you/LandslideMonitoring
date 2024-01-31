<template>
<v-app>
  <manageBar></manageBar>
  <router-view></router-view>
</v-app>
</template>
<script>
import manageBar from "@/components/manage/ManageBar.vue";
import{mapActions} from "vuex";

export default {
  components:{
    manageBar
  },
  data(){
    return{
    }
  },
  methods:{
    ...mapActions("User",["isLogin"])
  },
  mounted() {
    // console.log("这是index界面")
    this.isLogin()
  }

}
</script>

<style scoped>
</style>