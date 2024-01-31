<template>
  <v-navigation-drawer disable-resize-watcher v-model="drawer" :mini-variant.sync="mini" app color="secondary" permanent>
    <v-list-item class="px-2" style="color: white;">
      <v-list-item-avatar style="background-color: white;">
        <v-icon color="primary"> mdi-account</v-icon>
      </v-list-item-avatar>

      <v-list-item-title>
        <h2>{{getUserAccount}}</h2>
      </v-list-item-title>

      <v-btn icon @click.stop="mini = !mini">
        <v-icon style="color: white;">mdi-chevron-left</v-icon>
      </v-btn>
    </v-list-item>

    <v-divider></v-divider>
    <v-list shaped>
      <v-list-item v-for="item in items" @click="routeToPath(item.linker)" :key="item.title">
        <v-list-item-icon >
          <v-icon style="color: white;">{{ item.icon }}</v-icon>
        </v-list-item-icon>
        <v-list-item-content>
          <v-list-item-title style="color: white;font-size: 1.1rem;">{{ item.title }}</v-list-item-title>
        </v-list-item-content>
      </v-list-item>
    </v-list>
    <v-divider></v-divider>
    <v-list>
      <v-list-item style="margin-top:30px" @click="userLogout">
        <v-list-item-icon>
          <v-icon style="color:white">
            mdi-logout
          </v-icon>
        </v-list-item-icon>
        <v-list-item-content style="color:white;font-size: 1.1rem">
          退出登录
        </v-list-item-content>
      </v-list-item>
    </v-list>
  </v-navigation-drawer>

</template>
<script>
import {mapGetters,mapActions} from "vuex";

export default{
  data(){
    return{
      drawer:true,
      mini:true,
      items:[{title:"个人信息管理",icon:"mdi-information-variant-box",linker:"/index/userinfo"},
        {title:"管理设备",icon:"mdi-devices",linker:"/index/deviceinfo"},
        {title:"管理用户",icon:"mdi-account-search",linker:"/index/adminsearchuser"}
      ]
    }
  },
  computed:{
    ...mapGetters("User",["getUserAccount","getUserIsAdmin"]),
  },
  methods:{
    ...mapActions("User",["logout"]),
    routeToPath(e){
      this.$router.push(e).catch(()=>{})
    },
    userLogout(){
      this.logout()
    }
  }
}
</script>