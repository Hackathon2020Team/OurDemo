<template>
  <div class="detail-container">
    <div class="block">
      <div class="title-container">
        <h1 class="title">Welcome to Task Details Vue!</h1>
      </div>
      <div class="part1">
        <div class="title-container">
          <h2>任务明细</h2>
        </div>
        <el-carousel height="150px">
          <el-carousel-item>
            <h2 class="title1">任务名称: {{task_data.name}}</h2>
          </el-carousel-item>
          <el-carousel-item>
            <h2 class="title1">任务细节: {{task_data.details}}</h2>
          </el-carousel-item>
          <el-carousel-item>
            <h2 class="title1">父任务ID: {{task_data.superTaskId}}</h2>
          </el-carousel-item>
          <el-carousel-item>
            <h2 class="title1">DDL: {{task_data.deadLine}}</h2>
          </el-carousel-item>
        </el-carousel>
      </div>
      <div class="title-container">
        <h2>个人任务状态</h2>
        <h2>{{my_admin_task.personTaskStatus}}</h2>
      </div>
      <div v-if="my_admin_task.personTaskStatus=='NOT_RECEIVE'">
        <input v-model="receive_data.receiveMsg" type="textarea" placeholder="请输入信息"></input>
        <button v-on:click="receive">接受</button>
        <button v-on:click="refuse">拒绝</button>
      </div>







      <div class="left_part">
        <div class="part2" v-if="this.$cookies.get('user_data').id==main_admin_task.userId">
          <div class="title-container">
            <h2>已有任务管理者</h2>
          </div>
          <tbody v-for="item in colleagues.concat(subordinate)">
          <td v-if="admins_id.indexOf(item.userId)!=-1"> {{item.realName}}({{item.departmentName}}) </td>
          </tbody>
          <div class="title-container">
            <h2>添加任务管理者</h2>
          </div>
          <el-form ref="form" label-width="120px" class="manage-form">
            <el-form-item label="负责人职位" >
              <el-select  v-model="add_admin_colleagues_name" placeholder="请选择负责人职位">
                <el-option v-for="item in colleagues" :key="item.realName" :value="item.realName">{{item.realName}}({{item.departmentName}})</el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="任务管理描述">
              <el-input v-model="add_admin_details" placeholder="请输入任务管理描述" type="textarea"></el-input>
            </el-form-item>
            <el-form-item label="任务结束时间">
              <el-date-picker
                  v-model="add_admin_finishTime"
                  type="datetime"
                  placeholder="选择任务结束时间"
                  value-format="timestamp"
              ></el-date-picker>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="add_admin_colleagues">添加任务管理者</el-button>
            </el-form-item>
          </el-form>
        </div>


        <div class="part3">
          <div class="title-container">
            <h2>已有任务执行者</h2>
          </div>
          <tbody v-for="item in subordinate">
          <td v-if="doers_id.indexOf(item.userId)!=-1"> {{item.realName}}({{item.departmentName}}) </td>
          </tbody>
          <div class="title-container">
            <h2>添加任务执行者</h2>
          </div>
          <el-form ref="form" label-width="120px" class="manage-form">
            <el-form-item label="执行人职位" >
              <el-select  v-model="add_do_name" placeholder="请选择执行人职位">
                <el-option v-for="item in subordinate" :key="item.realName" :value="item.realName">{{item.realName}}({{item.departmentName}})</el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="任务执行描述">
              <el-input v-model="add_do_details" placeholder="请输入任务执行描述" type="textarea"></el-input>
            </el-form-item>
            <el-form-item label="任务结束时间">
              <el-date-picker
                  v-model="add_do_finishTime"
                  type="datetime"
                  placeholder="选择任务结束时间"
                  value-format="timestamp"
              ></el-date-picker>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="add_do">添加任务执行者</el-button>
            </el-form-item>
          </el-form>
        </div>
      </div>


      <div class="part4">
        <div class="table2">
          <div class="table-title">
            <h2 class="title">执行任务</h2>
          </div>
          <el-table
              :data="task_data.doTasks"
              style="width: 100%"
          >
            <el-table-column
                prop="doTaskId"
                label="任务ID"
                width="180">
            </el-table-column>
            <el-table-column
                prop="details"
                label="细节"
                width="180">
            </el-table-column>
            <el-table-column
                prop="personTaskStatus"
                label="状态"
                width="180">
            </el-table-column>
            <el-table-column
                prop="upload"
                label="Checking上传"
                width="180">
            </el-table-column>
            <el-table-column
                label="操作"
                width="100">
              <template slot-scope="scope">
                <div v-if="scope.row.personTaskStatus=='CHECKING'">
                  <input v-model="check_data.checkMsg" type="text" placeholder="请输入check信息"></input>
                  <el-button @click="pass(scope.row.doTaskId)" type="text" size="small">通过</el-button>
                  <el-button @click="no_pass(scope.row.doTaskId)" type="text" size="small">不通过</el-button>
                </div>

              </template>
            </el-table-column>
          </el-table>
        </div>
      </div>

    </div>

  </div>

</template>

<script>
const axios = require('axios');
export default {
name: "task_details",
  data(){
    return{
      main_admin_task:{},
      my_admin_task:{},
      task_data:{},
      colleagues:[],
      superior:[],
      subordinate:[],
      add_admin_colleagues_name:'',
      add_admin_colleagues_id:'',
      add_admin_details:'',
      add_admin_finishTime:'',
      add_do_name:'',
      add_do_id:'',
      add_do_details:'',
      add_do_finishTime:'',
      admins_id:[],
      doers_id:[],
      receive_data:{
        token:'',
        administerTaskId:'',
        isReceive:'',
        receiveMsg:''
      },
      check_data:{
        token:'',
        doTaskId:'',
        isOk:'',
        checkMsg:''
      },

    }
  },
  methods:{
    add_admin_colleagues:function(){
      for(let i=0;i<this.colleagues.length;i++){
        if(this.colleagues[i].realName==this.add_admin_colleagues_name){
          this.add_admin_colleagues_id = this.colleagues[i].userId;
        }
      }
      axios.post('http://njuzhy.com:7800/task/administer/add',
          {
            taskId:this.task_data.taskId,
            userId:this.add_admin_colleagues_id,
            details:this.add_admin_details,
            finishTime:this.add_admin_finishTime,
            token:this.$cookies.get('user_data').token,
          }).then((res)=>this.add_admin_colleagues2(res));
    },
    add_admin_colleagues2:function(res){
      console.log(res);
      if(res.data.code==-1){
        this.$toast.show(res.data.data,2);
      }
      else{
        this.$toast.show("添加任务管理者成功",2);
        this.add_admin_colleagues_id=null;
        this.add_admin_details=null;
        this.add_admin_finishTime=null;
      }
    },
    add_do:function(){
      for(let i=0;i<this.subordinate.length;i++){
        if(this.subordinate[i].realName==this.add_do_name){
          this.add_do_id = this.subordinate[i].userId;
        }
      }
      axios.post('http://njuzhy.com:7800/task/do/add',
          {
            taskId:this.task_data.taskId,
            userId:this.add_do_id,
            details:this.add_do_details,
            finishTime:this.add_do_finishTime,
            token:this.$cookies.get('user_data').token,
          }).then((res)=>this.add_do2(res));
    },
    add_do2:function (res){
      console.log(res);
      if(res.data.code==-1){
        this.$toast.show(res.data.data,2);
      }
      else{
        this.$toast.show("添加任务执行者成功",2);
        this.add_do_id=null;
        this.add_do_details=null;
        this.add_do_finishTime=null;
      }
    },
    receive:function (){
      this.receive_data.administerTaskId=this.my_admin_task.administerTaskId;
      this.receive_data.isReceive=true;
      this.receive_data.token=this.$cookies.get('user_data').token;
      axios.post('http://njuzhy.com:7800/task/administer/receive',
          this.receive_data).then((res)=>console.log(res));
      this.my_admin_task.personTaskStatus="EXECUTING";
    },
    refuse:function (){
      this.receive_data.administerTaskId=this.my_admin_task.administerTaskId;
      this.receive_data.isReceive=false;
      this.receive_data.token=this.$cookies.get('user_data').token;
      axios.post('http://njuzhy.com:7800/task/administer/receive',
          this.receive_data).then((res)=>console.log(res));
      this.my_admin_task.personTaskStatus="REFUSED";
    },
    pass:function(doTaskId){
      this.check_data.doTaskId=doTaskId;
      this.check_data.isOk=true;
      this.check_data.token=this.$cookies.get('user_data').token;
      console.log(this.check_data);
      axios.post('http://njuzhy.com:7800/task/administer/checkDo',
          this.check_data).then((res)=>console.log(res));
    },
    no_pass:function(doTaskId){
      this.check_data.doTaskId=doTaskId;
      this.check_data.isOk=false;
      this.check_data.token=this.$cookies.get('user_data').token;
      axios.post('http://njuzhy.com:7800/task/administer/checkDo',
          this.check_data).then((res)=>console.log(res));
    }
  },
  mounted : function(){
    this.task_data=this.$cookies.get('task_info');
    axios.get('http://njuzhy.com:7800/organization/equal',
        {params:{token:this.$cookies.get('user_data').token}}).then((res)=>this.colleagues=res.data.data);
    axios.get('http://njuzhy.com:7800/organization/higher',
        {params:{token:this.$cookies.get('user_data').token}}).then((res)=>this.superior=res.data.data);
    axios.get('http://njuzhy.com:7800/organization/lower',
        {params:{token:this.$cookies.get('user_data').token}}).then((res)=>this.subordinate=res.data.data);
    this.main_admin_task=this.task_data.administerTasks[0];
    for(let i=0;i<this.task_data.administerTasks.length;i++){
      this.admins_id.push(this.task_data.administerTasks[i].userId);
      if(this.task_data.administerTasks[i].userId==this.$cookies.get('user_data').id){
        this.my_admin_task=this.task_data.administerTasks[i];
      }
    }

    for(let i=0;i<this.task_data.doTasks.length;i++){
      this.doers_id.push(this.task_data.doTasks[i].userId);
    }
    console.log(this.task_data);
  }
}
</script>

<style lang="scss">
$light_gray:#eee;
$dark_gray:#889aa4;

.manage-container{
  width: 100%;
  min-height: 100%;
  background-color: white;
}

.block{
  position: relative;
  width: 1000px;
  max-width: 100%;
  padding: 80px 35px 0;
  margin: 0 auto;
  overflow: hidden;
}

.title-container{
  position: relative;

  .title{
    font-size: 26px;
    color: $dark_gray;
    margin: 0px auto 40px auto;
    text-align: center;
    font-weight: bold;
  }
}
.part1{
  margin-top: 80px;
  margin-bottom: 80px;
}
.left_part{
  display: inline;
}
.part4{
  width:auto
}
.title1{
  font-size: 26px;
  margin: 20px;
  text-align: center;
  font-weight: bold;
  alignment: center;
}

.el-carousel__item h3 {
  color: #475669;
  font-size: 14px;
  opacity: 0.75;
  line-height: 150px;
  margin: 0;
}

.el-carousel__item:nth-child(2n) {
  background-color: #99a9bf;
}

.el-carousel__item:nth-child(2n+1) {
  background-color: #d3dce6;
}

</style>
