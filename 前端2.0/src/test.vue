<template>
  <v-btn @getMessage>点击获取</v-btn>
  <img :src="codeImg">
</template>
<script>
import request from "@/utils/request";

export default {

  data(){
    return{
      codeImg:""
    }
  },
  method:{
    getMessage(){
      request({
        url:
      }).then(response=>{
        console.log(response)
        this.codeImg=window.URL.createObjectURL(response.image);
      })
    }
  }
}
</script>