<template>
  <div style="height:100%;width:100%;padding: 0;background-image: linear-gradient(to right top, #a1d1ff, #c2daff, #dce5ff, #f0f1ff, #ffffff);">
    <el-container>
      <el-main>
        <el-table :data="tableData" border>
          <el-table-column prop="deviceName" label="设别名称" width="180">
          </el-table-column>
          <el-table-column prop="windSpeedSValue" label="风速" width="180">
          </el-table-column>
          <el-table-column prop="windDirSValue" label="风向" width="180">
          </el-table-column>
          <el-table-column prop="airPressureSValue" label="气压" width="180">
          </el-table-column>
          <el-table-column prop="temperatureSValue" label="温度" width="180">
          </el-table-column>
          <el-table-column prop="humiditySValue" label="湿度" width="180">
          </el-table-column>
          <el-table-column prop="time" label="时间" width="180">
          </el-table-column>
          <el-table-column prop="forecast" label="预警">
          </el-table-column>
        </el-table>
        <div style="padding:10px 0">
          <el-pagination
              @size-change="handleSizeChange"
              @current-change="handleCurrentChange"
              :page-sizes="[5,10]"
              :page-size="PageSize"
              layout="total, sizes, prev, pager, next, jumper"
              :total="DeviceDetailsNum">
          </el-pagination>
        </div>
      </el-main>
    </el-container>
  </div>
</template>
<script>

import request from "@/utils/request";

export default {
  data() {
    return {
      DeviceDetailsNum:0,
      PageSize:5,
      PageNum:1,
      tableData: [],
      DeviceDetails:[]
    }
  },
  mounted() {
    const deviceName=this.$route.query.deviceName
    console.log(deviceName)
    this.initialize(deviceName)
  },
  methods:{
    handleSizeChange(PageSize){
      this.tableData=[]
      this.PageSize=PageSize
      let i=0
      while(i<this.PageSize&&this.PageSize*(this.PageNum-1)+i<this.DeviceDetailsNum){
        this.tableData.push(this.DeviceDetails[this.PageSize*(this.PageNum-1)+i])
        i+=1
      }
    },
    handleCurrentChange(PageNum){
      this.tableData=[]
      this.PageNum=PageNum
      let i=0
      while(i<this.PageSize&&this.PageSize*(this.PageNum-1)+i<this.DeviceDetailsNum){
        this.tableData.push(this.DeviceDetails[this.PageSize*(this.PageNum-1)+i])
        i+=1
      }
    },
    initialize(deviceName){
      request.post("/Data_Query/Current_Msg_Query?"+"UserAccount="+JSON.parse(localStorage.getItem('User')).userAccount+"&DeviceName="+deviceName).then(res=>{
        console.log(res)
        this.DeviceDetails=res.data
        this.DeviceDetailsNum=this.DeviceDetails.length
        let i=0
        while(i<this.PageSize&&this.PageSize*(this.PageNum-1)+i<this.DeviceDetailsNum){
          this.tableData.push(this.DeviceDetails[this.PageSize*(this.PageNum-1)+i])
          i+=1
        }
      })
    }
  }
};
</script>
<style scoped>

</style>