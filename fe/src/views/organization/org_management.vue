
<template>

  <div class="manage-container">
    <el-form ref="form" :model="org_data" label-width="120px" class="manage-form">
      <div class="title-container">
        <h1 class="title">Welcome to org management Vue!</h1>
        <h2 class="title">{{user_data.name}}</h2>
      </div>
      <el-form-item label="组织名称" >
        <el-input v-model="org_data.name" placeholder="请填写组织名称"></el-input>
      </el-form-item>
      <el-form-item label="负责人用户名">
        <el-input v-model="org_data.username" placeholder="请填写负责人用户名"></el-input>
      </el-form-item>
      <el-form-item label="负责人真实姓名">
        <el-input v-model="org_data.realName" placeholder="请填写负责人真实姓名"></el-input>
      </el-form-item>
      <el-form-item label="负责人密码">
        <el-input v-model="org_data.password" placeholder="请填写负责人密码"></el-input>
      </el-form-item>
      <el-form-item label="负责人头像">
        <el-input v-model="org_data.url" placeholder="请填写头像Url"></el-input>
      </el-form-item>
      <el-form-item label="负责人职位">
        <el-input  v-model="org_data.departmentName" placeholder="请输入负责人职位"></el-input>
      </el-form-item>
      <el-form-item label="负责人描述">
        <el-input type="textarea" v-model="org_data.content" placeholder="请选择负责人描述"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="add_org">创建组织</el-button>
      </el-form-item>


    </el-form>
  </div>


</template>

<script>
const axios = require('axios');
export default {
name: "org_management",
  data(){
    return{
      user_data:{},
      org_data:{
        name:'',
        username:'',
        password:'',
        content:'',
        url:'',
        departmentName:'',
        realName:''
      },
      pos_data:{
        organizationId:'',
        name:'',
        token:'',
        higherName:''
      },
      employee_data:{
        name:'',
        password:'',
        content:'',
        url:'',
        departmentId:'',
        token:''
      },
      position: []
    }
  },
  methods:{
    add_org:function(){
      // this.$alert('创建成功', '提示', {
      //   confirmButtonText: '确定',
      //   callback: action => {
      //     this.$message({
      //       type: 'info',
      //       message: `action: ${ action }`
      //     });
      //   }
      // });
      axios.post('http://njuzhy.com:7800/organization/add',this.org_data).then((res)=>this.add_org2(res));
    },
    add_org2:function(data){
      console.log(data);
      if(data.data.code==-1){
        this.$toast.show(data.data.data,2);
      }
      else {
        this.$toast.show('添加组织成功', 2);
        this.org_data = {
          name: '',
          username: '',
          password: '',
          content: '',
          url: '',
          departmentName: '',
          realName: ''
        }
      }
    },
    add_pos:function(){
      this.pos_data.organizationId = this.user_data.organizationId;
      this.pos_data.token = this.user_data.token;
      axios.post('http://njuzhy.com:7800/department/add',this.pos_data).then((res)=>this.add_pos2(res.data));
    },
    add_pos2:function(data){
      console.log(data);
      if(data.code==-1){
        this.$toast.show(data.data,2);
      }
      else{
        this.$toast.show('添加职位成功',2);
      }
    },
    add_emp:function(){
      this.employee_data.token = this.user_data.token;
      axios.post('http://njuzhy.com:7800/organization/addEmployee',this.employee_data).then((res)=>console.log(res));
    }

  },
  mounted: function () {
    this.user_data = this.$cookies.get('user_data')
    axios.get('http://njuzhy.com:7800/department/getAll',
        {params:{token:this.user_data.token,organizationId:this.user_data.organizationId}}).then((res)=>this.position=res.data.data).then((res)=>console.log(res));
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
