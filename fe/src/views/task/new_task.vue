<template>
<div class="createtask-container">
  <div class="manage-form">
    <div class="title-container">
      <h1 class="title">Welcome to New Task Vue!</h1>
    </div>

    <el-form ref="form" :model="new_task_data" label-width="100px" >
      <el-form-item label="上级任务" >
        <el-select  v-model="temp_name" placeholder="无">
          <el-option v-for="item in do_tasks" :key="item.name" :value="item.name">{{item.name}}(任务编号:{{item.taskId}})</el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="任务名称">
        <el-input v-model="new_task_data.name" placeholder="请填写任务名称"></el-input>
      </el-form-item>
      <el-form-item label="细节描述">
        <el-input v-model="new_task_data.details" placeholder="请填写细节描述"></el-input>
      </el-form-item>
      <el-form-item label="DDL">
        <el-date-picker
                v-model="new_task_data.deadLine"
                type="datetime"
                placeholder="选择日期时间"
                value-format="timestamp"
        >
        </el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button  type="primary" @click="add_task" >新建任务</el-button>
      </el-form-item>
    </el-form>
  </div>
</div>
</template>

<script>
const axios = require('axios');
export default {
name: "new_task",
  data(){
    return{
      new_task_data:{
        superTaskId:'',
        name:'',
        details:'',
        deadLine:'',
        token:''
      },
      temp_name:'无',
      new_result:{},
      do_tasks:[]
    }
  },
  methods:{
    add_task:function(){
      if(this.temp_name=='无'){
        this.new_task_data.superTaskId=-1;
      }
      else{
        for(let i =0;i<this.do_tasks.length;i++){
          if(this.temp_name==this.do_tasks[i].name){
            this.new_task_data.superTaskId=this.do_tasks[i].taskId;
          }
        }
      }
      this.new_task_data.token=this.$cookies.get('user_data').token;
      console.log(this.new_task_data)
      axios.post('http://njuzhy.com:7800/task/add',
          this.new_task_data).then((res)=>this.add_task2(res));
    },
    add_task2:function(res){
      console.log(this.new_result);
      console.log(res);
      if(res.data.code==0){
        this.$router.push({
          path:'/Home',
          name:'Home'
        })
      }
      else{
        console.log(res.data.data);
      }
    }
  },
  mounted:function () {
    if(this.$cookies.get('do_tasks_for_newTask')!=null){
      this.do_tasks= this.$cookies.get('do_tasks_for_newTask').do_tasks;
    }
    console.log(this.do_tasks);
  }
}
</script>

<style lang="scss">
$light_gray:#eee;
$dark_gray:#889aa4;

.createtask-container{
  width: 100%;
  min-height: 100%;
  background-color: white;
}

.manage-form{
  position: relative;
  width: 520px;
  max-width: 100%;
  padding: 100px 35px 0;
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

</style>
