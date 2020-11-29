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
        <h2>{{my_do_task.personTaskStatus}}</h2>
      </div>
      <div v-if="my_do_task.personTaskStatus=='NOT_RECEIVE'">
        <input v-model="receive_data.receiveMsg" type="textarea" placeholder="请输入信息"></input>
        <button v-on:click="receive">接受</button>
        <button v-on:click="refuse">拒绝</button>
      </div>
      <div v-if="my_do_task.personTaskStatus=='EXECUTING'">
        <input v-model="commit_data.upload" type="textarea" placeholder="请输入	检查上传文件URL或描述"></input>
        <button v-on:click="commit1">提交</button>
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
      my_do_task:{},
      task_data:{},
      tasks:[],
      receive_data:{
        token:'',
        doTaskId:'',
        isReceive:'',
        receiveMsg:''
      },
      commit_data:{
        token:'',
        doTaskId:'',
        upload:''
      }
    }
  },
  methods:{
    receive:function (){
      this.receive_data.doTaskId=this.my_do_task.doTaskId;
      this.receive_data.isReceive=true;
      this.receive_data.token=this.$cookies.get('user_data').token;
      axios.post('http://njuzhy.com:7800/task/do/receive',
          this.receive_data).then((res)=>console.log(res));
      this.my_do_task.personTaskStatus="EXECUTING";
    },
    refuse:function (){
      this.receive_data.doTaskId=this.my_admin_task.doTaskId;
      this.receive_data.isReceive=false;
      this.receive_data.token=this.$cookies.get('user_data').token;
      axios.post('http://njuzhy.com:7800/task/do/receive',
          this.receive_data).then((res)=>console.log(res));
      this.my_do_task.personTaskStatus="REFUSED";
    },
    commit1:function (){
      this.commit_data.doTaskId=this.my_do_task.doTaskId;
      this.commit_data.token=this.$cookies.get('user_data').token;
      axios.post('http://njuzhy.com:7800/task/do/check',this.commit_data).then((res)=>console.log(res));
      this.my_do_task.personTaskStatus="CHECKING";
    }
  },
  mounted : function(){
    this.task_data=this.$cookies.get('task_info');
    console.log(this.task_data);
    for(let i=0;i<this.task_data.doTasks.length;i++){
      if(this.task_data.doTasks[i].userId==this.$cookies.get('user_data').id){
        this.my_do_task=this.task_data.doTasks[i];
      }
    }

    for(let i=0;i<this.task_data.doTasks.length;i++){
      this.doers_id.push(this.task_data.doTasks[i].userId);
    }
    console.log(this.task_data);

    if(this.$cookies.get('personTaskStatus_change')!="NULL"){
      this.my_do_task.personTaskStatus=this.$cookies.get('personTaskStatus_change');
      this.$cookies.set('personTaskStatus_change',"NULL");
    }
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
  width: 520px;
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
