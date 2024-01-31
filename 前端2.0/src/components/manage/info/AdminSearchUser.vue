<template>
  <div    style="height:100%;width:100%;padding: 0;background-image: linear-gradient(to right top, #a1d1ff, #c2daff, #dce5ff, #f0f1ff, #ffffff);">
    <el-container style="height: 800px; border: 1px solid rgb(209,209,220)">
      <el-container>
        <el-header style="text-align: right; font-size: 12px">
          <el-form :inline="true" :model="formInline" class="demo-form-inline">
            <el-form-item label="账号">
              <el-input
                  v-model="formInline.name"
                  placeholder="输入账号"
              >
              </el-input>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="searchByAccount">查询</el-button>
            </el-form-item>
            <el-form-item label="手机号">
              <el-input
                  v-model="formInline.phone"
                  placeholder="输入手机号"
                  maxlength="20"
                  show-word-limit>
              </el-input>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="searchByPhone">查询</el-button>
            </el-form-item>
          </el-form>
        </el-header>

        <el-main>
          <el-table
              :data="tableData"
              height="530"
              border
              style="width: 100%">
            <el-table-column
                prop="userAccount"
                label="账号"
                width="180">
            </el-table-column>
            <el-table-column
                prop="password"
                label="密码"
                width="180">
            </el-table-column>
            <el-table-column
                prop="emailAddress"
                label="邮箱">
            </el-table-column>
            <el-table-column
                prop="companyAddress"
                label="地址">
            </el-table-column>
            <el-table-column
                prop="phoneNumber"
                label="电话">
            </el-table-column>
            <el-table-column
                fixed="right"
                label="操作"
                width="100">
              <template slot-scope="scope">
                <el-button @click="requset_logout(scope.row.userAccount)" type="text" size="small">注销</el-button>
              </template>
            </el-table-column>

          </el-table>
          <div class="pagination-container">
            <el-pagination
                :page-size="pageSize"
                layout="prev, pager, next"
                :total="totalNum"
                :current-page="currentPage"
                @current-change="handleChangePage">
            </el-pagination>
          </div>
          <el-dialog
              title="提示"
              :visible.sync="dialogVisible"
              width="30%"
          >
            <span>是否确定注销{{text}}?</span>
            <span slot="footer" class="dialog-footer">
              <el-button @click="dialogVisible = false">取 消</el-button>
              <el-button type="primary" @click="handle_logout(text)">确 定</el-button>
            </span>
          </el-dialog>
          <el-dialog
              title="提示"
              :visible.sync="logoutVisible"
              width="30%"
          >
            <span>注销成功</span>
            <span slot="footer" class="dialog-footer">
              <el-button @click="logoutVisible = false">确定</el-button>
            </span>
          </el-dialog>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>
<style>
.pagination-container {
  text-align: center;
  display: flex;
  justify-content: center;
}
</style>

<script>
import request from "@/utils/request";
export default {
  data(){
    return {
      formInline: {
        name: '' ,
        phone:'',

      },
      dialogVisible: false,
      logoutVisible: false,
      text: '',
      currentPage: 1,
      totalNum: 0,
      userInfonList: [],
      tableData: [],
      pageSize: 10,
      search_state:false
    }
  },
  methods:{
    //获取用户列表
    getList(){
      request.get('/Register/List_User').then(res=> {
        this.userInfoList=res.data;
        this.totalNum=res.data.length
        this.tableData = this.getPageData()[this.currentPage - 1];
      });
      this.search_state=true;
    },
    //获取每一页数据
    getPageData() {
      const length = this.userInfoList.length;
      if (!length) {
        return [];
      }
      let index = 0;
      let resIndex = 0;
      let result = new Array(Math.ceil(length / this.pageSize));
      while (index < length) {
        result[resIndex++] = this.userInfoList.slice(index, (index += this.pageSize));
      }
      return result;
    },
    //处理页号变化
    handleChangePage(page){
      this.currentPage=page;
      if(this.search_state){
        this.tableData = this.getPageData()[this.currentPage - 1];
      }
      else{
        this.getList();
      }
    },
    //通过账号查询
    searchByAccount(){
      let form=new FormData()
      const data={
        userAccount:this.formInline.name,
        password:null,
        isAdmin:null,
        mail:null,
        address:null,
        phone:null
      }
      for(const key in data) {
        form.append(key, data[key]);
      }
      request.post('/Register/Search_User_By_Name',form).then(res=>{
        if(res.code==1){
          if(res.data.length==0){
            this.$message({
              type: 'warning',
              message: '没有查询到相关用户'
            });
          }
          this.userInfoList=res.data;
          this.currentPage=1;
          this.formInline.name='';
          this.tableData=this.getPageData()[this.currentPage-1];
          this.totalNum=this.userInfoList.length;
          this.search_state=true;
        }
      })
    },
    //通过手机号查询
    searchByPhone(){
      let form=new FormData()
      const data={
        userAccount:null,
        password:null,
        isAdmin:null,
        emailAddress:null,
        companyAddress:null,
        phoneNumber:this.formInline.phone
      }
      for(const key in data) {
        form.append(key, data[key])
      }
      request.post('/Register/Search_User_By_Phone',form).then(res=>{
        if(res.code==1){
          if(res.data.length==0){
            this.$message({
              type: 'warning',
              message: '没有查询到相关用户'
            });
          }
          this.userInfoList=res.data;
          this.currentPage=1;
          this.tableData=this.getPageData()[this.currentPage-1];
          this.totalNum=this.userInfoList.length;
          this.formInline.phone='';
          this.search_state=true;
        }

      })
    },
    //请求注销
    requset_logout(account){
      this.dialogVisible = true;
      this.text=account;
    },
    //处理注销
    handle_logout(account){
      this.dialogVisible = false;
      let form=new FormData();
      const data={
        userAccount:account,
        password:null,
        isAdmin:null,
        mail:null,
        address:null,
        phone:null
      }
      for(const key in data) {
        form.append(key, data[key]);
      }
      request.post('/Register/User_Unregister',form).then(res=>{
        if(res.code==1){
          this.logoutVisible = true;
          this.getList();
        }
      })
    },
  },
  created(){
    this.getList();
  }
};
</script>
