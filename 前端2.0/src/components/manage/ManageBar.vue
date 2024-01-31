<template>
    <component :is="selectRole"></component>
</template>
<script>
import {mapGetters} from "vuex";
import adminManageBar from "@/components/manage/AdminManageBar.vue";
import userManageBar from "@/components/manage/UserManagerBar.vue";
export default {
  components:{
    adminManageBar,
    userManageBar
  },
  data(){
    return {
      bars: ['adminManageBar', 'userManageBar']
    }
  },
  computed:{
    ...mapGetters("User",['getUserRole','getUserAccount']),
    selectRole(){
      let tmp=this.getUserRole
      if(tmp=='Admin')
      {
        return this.bars[0]
      }
      else
      {
        return this.bars[1]
      }
    }
  },
}
</script>
<style scoped>

</style>