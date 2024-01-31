<template>
   <div style="height:100%;width:100%;padding: 0;background-image: linear-gradient(to right top, #a1d1ff, #c2daff, #dce5ff, #f0f1ff, #ffffff);">
     <el-container>
       <el-main>
         <div  style="padding: 10px">
             <el-button type="primary" @click="addDevice">添加设备</el-button>
         </div>
         <el-table :data="tableData" ref="table" border @row-click="handleRowClick">
           <el-table-column prop="deviceName" label="设别名称" width="800">
           </el-table-column>
           <el-table-column>
             <el-button type="warning" @click="deleteDialog">删除</el-button>
             <el-button type="primary" @click="WhetherChangeDeviceName=true">修改名称</el-button>
               <el-button type="primary" @click="seeDetails">查看详情</el-button>
           </el-table-column>
         </el-table>
         <div style="padding:10px 0">
           <el-pagination
               @size-change="handleSizeChange"
               @current-change="handleCurrentChange"
               :page-sizes="[2,5]"
               :page-size="PageSize"
               layout="total, sizes, prev, pager, next, jumper"
               :total="DeviceOfUserNum">
           </el-pagination>
         </div>
<!--添加设备的-->
           <v-dialog v-model="WhetherAddDevice" width="400px">
             <v-card>
               <v-tabs>
                 <v-tab>添加设备</v-tab>
                 <v-tab-item>
                   <v-form
                       ref="DetailForm"
                       class="pa-5"
                       lazy-validation>
                     <v-text-field
                         :rules="Rules"
                         label="设备名称"
                         v-model="NewDeviceInfo.deviceName"
                         required
                     ></v-text-field>
                     <v-select v-model="select" :items="DeviceClasses" :rules="[v => !!v || '设备类']" item-text="deviceClassName" item-value="deviceClassID"
                               label="设备类" prepend-icon="mdi-cog-outline" required return-object></v-select>
                     <v-btn
                         class="mr-4"
                         color="success"
                         @click.prevent="save"
                     >
                       保存
                     </v-btn>
                   </v-form>
                 </v-tab-item>
               </v-tabs>
             </v-card>
           </v-dialog>
         <v-dialog v-model="WhetherChangeDeviceName" width="400px">
           <v-card>
             <v-tabs>
               <v-tab>修改设备名称</v-tab>
               <v-tab-item>
                 <v-form
                     ref="ChangeDeviceNameForm"
                     class="pa-5"
                     lazy-validation>
                   <v-text-field
                       :rules="Rules"
                       label="新的设备名称"
                       v-model="NewDeviceName"
                       required
                       style="margin-bottom:10px"
                   ></v-text-field>
                   <v-btn
                       class="mr-4"
                       color="success"
                       @click="identifyNewDeviceName"
                   >
                     确定修改
                   </v-btn>
                 </v-form>
               </v-tab-item>
             </v-tabs>
           </v-card>
         </v-dialog>
         <el-dialog
             title="提示"
             :visible.sync="dialogVisible"
             width="30%"
         >
           <span>确定删除设备{{currentRow.deviceName}}吗?</span>
           <span slot="footer" class="dialog-footer">
              <el-button @click="dialogVisible = false">取 消</el-button>
              <el-button type="primary" @click="deleteDevice">确 定</el-button>
            </span>
         </el-dialog>
         <el-dialog
             title="提示"
             :visible.sync="dialogVisible2"
             width="30%"
         >
           <span>确定将{{currentRow.deviceName}}修改为{{NewDeviceName}}吗?</span>
           <span slot="footer" class="dialog-footer">
              <el-button @click="dialogVisible2 = false">取 消</el-button>
              <el-button type="primary" @click="changeDeviceName">确 定</el-button>
            </span>
         </el-dialog>
       </el-main>
     </el-container>
   </div>
</template>
<script>
import {mapGetters} from "vuex";
import request from "@/utils/request";
export default {
  data() {
    return {
      text:"",
      dialogVisible:false,
      dialogVisible2:false,
      logoutVisible:false,
      DeviceOfUserNum:0,
      PageSize:2,
      PageNum:1,
      select:{
        deviceClassID:null,
          deviceName:null,
          deviceArchitecture:null,
          deviceManufacture:null,
          airPressureSensorModel:null,
          windSpeedSensorModel:null,
          windDirectionSensorModel:null,
          temperatureSensorModel:null
      },
      WhetherChangeDeviceName:false,
      WhetherAddDevice:false,
      NewDeviceInfo:{
        deviceName:null
      },

      NewDeviceName:"",
      currentRow:{
        userAccount:null,
        deviceName:null
      },
      DeviceOfUser:[],
      DeviceClasses:[],
      tableData:[],
      Rules:[
        v => !!v || '设别名称必填',
      ]
    }
  },
  mounted() {
    this.initializeData()
  },
  computed:{
...mapGetters('User',['getUserAccount']),
  },
  methods:{
    handleSizeChange(PageSize){
      this.tableData=[]
      this.PageSize=PageSize
      let i=0
      while(i<this.PageSize&&this.PageSize*(this.PageNum-1)+i<this.DeviceOfUserNum){
        this.tableData.push(this.DeviceOfUser[this.PageSize*(this.PageNum-1)+i])
        i+=1
      }
      console.log(this.tableData)
    },
    handleCurrentChange(PageNum){
      this.tableData=[]
      this.PageNum=PageNum
      let i=0
      while(i<this.PageSize&&this.PageSize*(this.PageNum-1)+i<this.DeviceOfUserNum){
        this.tableData.push(this.DeviceOfUser[this.PageSize*(this.PageNum-1)+i])
        i+=1
      }
    },
    initializeData(){
      //请求所有设备类
      console.log(JSON.parse(localStorage.getItem('User')).userAccount)
        request.post('/Data_Query/Device_Msg_Query?'+'UserAccount='+JSON.parse(localStorage.getItem('User')).userAccount).then(response=>{
        console.log(response.data)
        this.DeviceOfUser=response.data
        this.DeviceOfUserNum=this.DeviceOfUser.length
        let i=0
        console.log(this.PageNum)
        while(i<this.PageSize&&this.PageSize*(this.PageNum-1)+i<this.DeviceOfUserNum){
          this.tableData.push(this.DeviceOfUser[this.PageSize*(this.PageNum-1)+i])
          i+=1
        }
      })
      request.post('/Dev_Msg/Dev_List').then(response=>{
        // console.log(response.data)
        this.DeviceClasses=response.data
      })
    },
    identifyNewDeviceName(){
      if(this.NewDeviceName==null||this.NewDeviceName==""){
        this.$notify({
          text:'设备名为空',
          type:'error'
        })
        return
      }
      this.dialogVisible2=true

    },
    addDevice(){
      this.WhetherAddDevice=true
    },
    deleteDialog(){
      this.dialogVisible=true
    },
    deleteDevice(){
      console.log(this.currentRow.deviceName)
      request.post("/Dev_Msg/Device_Del?"+'UserAccount='+this.getUserAccount+'&DeviceName='+this.currentRow.deviceName).then(res=>{
        if(res.code==1){
          this.$notify({
            title:'删除成功',
            type:"success"
          })
          this.dialogVisible=false
          location.reload()

        }
      })
    },
    handleRowClick(row){
      this.currentRow.deviceName=row.deviceName
    },
    seeDetails(){
      setTimeout(()=>{
        this.$router.push({
          path:'/index/devicedetails',
          query:{
            deviceName:this.currentRow.deviceName
          }
        })
      },1000);

    },
    save(){
      if(this.NewDeviceInfo.deviceName==null||this.NewDeviceInfo.deviceName==""){
        this.$notify({
          text:'设备名为空',
          type:'error'
        })
        return
      }
      request.post("/Dev_Msg/Device_Add?"+"UserAccount="+this.getUserAccount+"&"+"DeviceName="+this.NewDeviceInfo.deviceName+"&"+"DeviceClassID="+this.select.deviceClassID).then(response=>{
        if(response.code==1){
          this.WhetherAddDevice=false
           this.$notify({
            text:'添加成功',
             type:'success'
          })
          this.dialogVisible2=false
          location.reload()

        }else if(response.code==27){
          this.$notify({
            title:'添加失败',
            text:'设备名过长',
            type:'error'
          })
        }else if(response.code==29){
          this.$notify({
            title:'添加失败',
            text:'设备名重复',
            type:'error'
          })
        }
      })
      //还要发请求
    },
    changeDeviceName(){
      console.log(this.currentRow.deviceName)
      console.log(this.NewDeviceName)
      request.post("/Dev_Msg/Device_Name_Modify?"+"UserAccount="+this.getUserAccount+"&OldDeviceName="+this.currentRow.deviceName+"&NewDeviceName="+this.NewDeviceName).then(res=>{
        console.log(res)
        if(res.code==1){
          this.WhetherChangeDeviceName=false
          this.dialogVisible2=false
          this.$notify({
            title:"修改成功",
            type:"success"
          })
          location.reload()

        }else if(res.code==30) {
          this.$notify({
            title: "修改失败",
            text: "设备名已存在",
            type: "error"
          })
          this.dialogVisible2=false
        }else if(res.code==32){
            this.$notify({
              title:"修改失败",
              text:"设备名过长",
              type:"error"
            })
          this.dialogVisible2=false
        }
      }).catch(err=>{
        console.log(err)
      })
    }
  }
};
</script>
<style scoped>
.el-header {
  background-color: #B3C0D1;
  color: #333;
  line-height: 60px;
}
.container{
  height:100%
}
.el-aside {
  color: #333;
}
</style>