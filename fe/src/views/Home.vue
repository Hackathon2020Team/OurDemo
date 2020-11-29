<template>
  <div class="home-container">

    <div class="table-form">
      <div class="title-container">
        <h1>Welcome {{this.user_data.name}}!</h1>
      </div>
      <div class="table1">
        <div class="table-title">
          <h2 class="title">管理任务</h2>
        </div>
        <el-table
                :data="tasks"
                style="width: 100%"
                >
          <el-table-column
                  prop="name"
                  label="任务名称"
                  width="180">
          </el-table-column>
          <el-table-column
                  prop="details"
                  label="细节"
                  width="180">
          </el-table-column>
          <el-table-column
                  prop="status"
                  label="状态"
                  width="180">
          </el-table-column>
          <el-table-column
                  label="操作"
                  width="100">
            <template slot-scope="scope">
              <el-button @click="jmp_task_details(scope.row)" type="text" size="small">查看</el-button>
              <el-button @click="finish_task(scope.row)" type="text" size="small">完成</el-button>
            </template>
          </el-table-column>
        </el-table>
      </div>
      <div class="table2">
        <div class="table-title">
          <h2 class="title">执行任务</h2>
        </div>
        <el-table
                :data="do_tasks"
                style="width: 100%"
                >
          <el-table-column
                  prop="name"
                  label="任务名称"
                  width="180">
          </el-table-column>
          <el-table-column
                  prop="doTasks[0].details"
                  label="细节"
                  width="180">
          </el-table-column>
          <el-table-column
                  prop="doTasks[0].personTaskStatus"
                  label="状态"
                  width="180">
          </el-table-column>
          <el-table-column
                  label="操作"
                  width="100">
            <template slot-scope="scope">
              <el-button @click="jmp_task_details2(scope.row)" type="text" size="small">查看</el-button>
            </template>
          </el-table-column>
        </el-table>
      </div>
      <div class="table3">
        <div class="table-title">
          <h2 class="title">新建任务</h2>
        </div>
        <div>
          <img :src="'https://twitter-content.oss-cn-shanghai.aliyuncs.com/su%27s%20meat%20ball.gif?imageView2/1/w/80/h/80'" class="user-avatar">
          <el-button type="primary" @click="jmp_new_task" class="create-button">新建任务</el-button>
        </div>
      </div>

    </div>
  </div>
</template>

<script>
const axios = require('axios');
  export default {
    name: "Home",
    data(){
      return{
        user_data:{},
        tasks:[],
        do_tasks:[]
      }
    },
    methods:{
      jmp_org_management:function(){
        this.$router.push({
          path:'/org_management',
          name:'org_management',

        })
        console.log();
      },
      finish_task:function (item){
        axios.post('http://njuzhy.com:7800/task/finish',{token:this.user_data.token,taskId:item.taskId}).then((res)=>this.finish_task2(res))
      },
      finish_task2:function(res){
        console.log(res)
        if(res.data.code==-1){
          this.$toast.show(res.data.data,2);
        }
        else{
          this.$toast.show('任务完成！',2);
        }
      },
      jmp_task_details:function(item){
        console.log(item);
        this.$router.push({
          path:'/task_details',
          name:'task_details',
          params:item
        })
        this.$cookies.set('task_info',item);
      },

      jmp_task_details2:function(item){
        this.$router.push({
          path:'/do_task_details',
          name:'do_task_details',
          params:item
        })
        this.$cookies.set('task_info',item);
      },
      jmp_new_task:function(){
        this.$cookies.set('do_tasks_for_newTask',{do_tasks:this.do_tasks});
        this.$router.push({
          path:'/new_task',
          name:'new_task'
        })
      },
      do_tasks_init:function(){
        console.log(this.do_tasks);
        for(let i=0;i<this.do_tasks.length;i++){
          for (let j =0;j<this.do_tasks[i].doTasks.length;j++){
            if(this.do_tasks[i].doTasks[j].userId!=this.user_data.id){
              this.do_tasks[i].doTasks.splice(j,1);
            }
          }
        }
        console.log(this.do_tasks);
      }
    },

    mounted: function () {
      this.user_data=this.$cookies.get('user_data');
      axios.get('http://njuzhy.com:7800/task/my',
          {params:{token:this.user_data.token}}).then((res)=>this.tasks=res.data.data).then((res)=>console.log(this.tasks));
      axios.get('http://njuzhy.com:7800/task/myDo',
          {params:{token:this.user_data.token}}).then((res)=>this.do_tasks=res.data.data).then((res)=>this.do_tasks_init());

    }
  }
</script>

<style lang="scss">
  $bg:#2d3a4b;
  $dark_gray:#889aa4;
  $light_gray:#eee;

  .home-container {
    min-height: 100%;
    width: 100%;
    background-color: white;

    .table-form {
      position: relative;
      width: 900px;
      max-width: 100%;
      padding: 50px 35px 0;
      margin: 0 auto;
    }

    .title-container {
      position: relative;

      .title {
        font-size: 26px;
        color: $light_gray;
        margin: 0px auto 40px auto;
        text-align: center;
        font-weight: bold;
      }
    }

    .table1{
      margin-top: 50px;
      margin-bottom: 50px;
    }
    .table2{
      margin-bottom: 50px;

    }

    .el-table .warning-row {
      background: oldlace;
    }

    .el-table .success-row {
      background: #f0f9eb;
    }

    .user-avatar {
      cursor: pointer;
      width: 100px;
      height: 100px;
      border-radius: 20px;
    }
    .create-button{
      display: flex;
      width: 100px;
      height: 40px;
      border-radius: 20px;
    }
  }
</style>
