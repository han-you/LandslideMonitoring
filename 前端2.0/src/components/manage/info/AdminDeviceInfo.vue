<template>
<!--    <v-main style="width:100%;padding: 0;background-image: linear-gradient(to right top, #a1d1ff, #c2daff, #dce5ff, #f0f1ff, #ffffff);">-->
  <div style="height:100%;width:100%;padding: 0;background-image: linear-gradient(to right top, #a1d1ff, #c2daff, #dce5ff, #f0f1ff, #ffffff);">
      <el-container class="container" >
          <el-main>
            <div  style="padding: 10px">
              <el-button type="primary" @click="addDevice">添加设备</el-button>
            </div>
            <el-table :data="tableData" ref="table" border @row-click="handleRowClick">
              <el-table-column prop="deviceClassName" label="设别类名称" width="130">
              </el-table-column>
              <el-table-column prop="deviceClassArch" label="设别架构" width="180">
              </el-table-column>
              <el-table-column prop="manufacture" label="生产厂家" width="130">
              </el-table-column>
              <el-table-column prop="airPressureSType" label="气压传感器型号" width="130">
              </el-table-column>
              <el-table-column prop="windSpeedSType" label="风速传感器型号" width="130">
              </el-table-column>
              <el-table-column prop="windDirSType" label="风向传感器型号" width="130">
              </el-table-column>
              <el-table-column prop="temperatureSType" label="温度传感器" width="130">
              </el-table-column>
              <el-table-column>
                <el-button type="warning" @click="dialogVisible = true">删除</el-button>
              </el-table-column>
            </el-table>
            <div style="padding:10px 0">
              <el-pagination
                  @size-change="handleSizeChange"
                  @current-change="handleCurrentChange"
                  :page-sizes="[5, 10, 15, 20]"
                  :page-size="PageSize"
                  layout="total, sizes, prev, pager, next, jumper"
                  :total="DeviceClassesNum">
              </el-pagination>
            </div>
            <v-dialog v-model="WhetherAddDeviceClasses" width="400px">
              <v-card>
                <v-tabs>
                  <v-tab>添加设备</v-tab>
                  <v-tab-item>
                    <v-form
                        ref="DetailForm"
                        class="pa-5"
                        lazy-validation>
                      <v-text-field
                          :rules="deviceNameRules"
                          label="设备类名称"
                          v-model="NewDeviceClass.deviceClassName"
                          required
                      ></v-text-field>
                      <v-text-field
                          :rules="deviceArchitectureRules"
                          label="设备类架构"
                          v-model="NewDeviceClass.deviceClassArch"
                          required
                      ></v-text-field>
                      <v-text-field
                          :rules="deviceManufactureRules"
                          label="生产厂家"
                          v-model="NewDeviceClass.manufacture"
                          required
                      ></v-text-field>
                      <v-text-field
                          :rules="airPressureSensorModelRules"
                          label="气压传感器型号"
                          v-model="NewDeviceClass.airPressureSType"
                          required
                      ></v-text-field>
                      <v-text-field
                          :rules="windDirectionSensorModelRules"
                          label="风向传感器型号"
                          v-model="NewDeviceClass.windDirSType"
                          required
                      ></v-text-field>
                      <v-text-field
                          :rules="windSpeedSensorModelRules"
                          label="风速传感器型号"
                          v-model="NewDeviceClass.windSpeedSType"
                          required
                      ></v-text-field>
                      <v-text-field
                          :rules="temperatureSensorModelRules"
                          label="温度传感器型号"
                          v-model="NewDeviceClass.temperatureSType"
                          required
                      ></v-text-field>
                      <v-text-field
                          :rules="HumiditySensorModelRules"
                          label="湿度传感器型号"
                          v-model="NewDeviceClass.humiditySType"
                          required
                      ></v-text-field>
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

            <el-dialog
                title="提示"
                :visible.sync="dialogVisible"
                width="30%"
            >
              <span>确定删除{{currentDeviceClassName}}?</span>
              <span slot="footer" class="dialog-footer">
              <el-button @click="dialogVisible = false">取 消</el-button>
              <el-button type="primary" @click="deleteDevice">确 定</el-button>
            </span>
            </el-dialog>
          </el-main>
        </el-container>
  </div>
</template>
<script>
import request from "@/utils/request";

export default{
  data(){
    return{
      dialogVisible:false,
      DeviceClassesNum:0,
      PageSize:10,
      PageNum:1,
      currentRow:null,
      currentDeviceClassName:"",
      WhetherAddDeviceClasses:false,
      DeviceClasses:null,
      tableData:[],
      currentDeviceClassId:null,
      NewDeviceClass:{
        deviceClassID:0,
        deviceClassName:null,
        deviceClassArch:null,
        manufacture:null,
        humiditySType:null,
        airPressureSType:null,
        windSpeedSType:null,
        windDirSType:null,
        temperatureSType:null
      },
      deviceNameRules:[
        v => !!v || '设别类名称必填',
      ],
      deviceArchitectureRules:[
        v => !!v || '设别架构必填',
      ],
      deviceManufactureRules:[
        v => !!v || '生产厂家必填',
      ],
      airPressureSensorModelRules:[
        v => !!v || '气压传感器型号必填',
      ],
      windSpeedSensorModelRules:[
        v => !!v || '风速传感器必填',
      ],
      windDirectionSensorModelRules:[
        v => !!v || '风向传感器必填',
      ],
      temperatureSensorModelRules:[
        v => !!v || '温度传感器必填',
      ],
      HumiditySensorModelRules:[
        v => !!v || '湿度传感器必填',
      ]
    }
  },
  mounted() {
    this.getDeviceClass()
  },
  computed:{
  },
  methods:{
    handleSizeChange(PageSize){
      this.tableData=[]
      this.PageSize=PageSize
      let i=0
      while(i<this.PageSize&&(this.PageSize)*(this.PageNum-1)+i<this.DeviceClassesNum){
        this.tableData.push(this.DeviceClasses[(this.PageSize)*(this.PageNum-1)+i])
        i+=1
      }
    },
    handleCurrentChange(PageNum){
      this.tableData=[]
      this.PageNum=PageNum
      let i=0
      while(i<this.PageSize&&(this.PageSize)*(this.PageNum-1)+i<this.DeviceClassesNum){
        this.tableData.push(this.DeviceClasses[(this.PageSize)*(this.PageNum-1)+i])
        i+=1
      }
    },
    save(){
      let formdata=new FormData()
      this.NewDeviceClass.deviceClassID=1
      for(const key in this.NewDeviceClass){
        console.log(key+"  "+this.NewDeviceClass[key])
        if(this.NewDeviceClass[key]==null||this.NewDeviceClass[key]=="")
        {
          this.$notify({
            title:"有选项没填",
            type:"error"
          })
          return
        }
        formdata.append(key,this.NewDeviceClass[key])
      }
      request.post("/Dev_Msg/Dev_Class_Add",formdata).then(response=>{
        if(response.code==1){
          this.WhetherAddDeviceClasses=false
          this.$notify({
            title:"添加成功",
            type:"success"
          })
          location.reload()
        }
        else if(response.code==43){
          this.$notify({
            title:"设备类已存在",
            type:"error"
          })
        }
      })
    },
    handleRowClick(row){
      this.currentRow=row.deviceClassID
      this.currentDeviceClassName=row.deviceClassName
    },
    getDeviceClass(){
      request.post("/Dev_Msg/Dev_List").then(response=>{
        // console.log(response)
        this.DeviceClasses=response.data
        this.DeviceClassesNum=this.DeviceClasses.length
        console.log(this.PageNum)
        let i=0
        while(i<this.PageSize&&(this.PageSize)*(this.PageNum-1)+i<this.DeviceClassesNum){
          this.tableData.push(this.DeviceClasses[(this.PageSize)*(this.PageNum-1)+i])
          i+=1
        }
        // this.tableData=this.DeviceClasses
        // console.log(this.tableData)
      })
    },
    addDevice(){
      this.WhetherAddDeviceClasses=true
    },
    deleteDevice(){
      console.log(this.currentRow)
      request.post("/Dev_Msg/Device_Class_Del?"+"DeviceClassID="+this.currentRow).then(response=>{
        if(response.code==1)
        {
          this.$notify({
            title:"删除成功",
            type:"success"
          })
          this.dialogVisible=false
          location.reload()
        }
      })
    }
  }

}
</script>