<template>
  <div class="login-container">
      <el-form class="login-form">
        <div class="title-container">
          <h1 class="title">{{ msg }}</h1>
        </div>
        <el-form-item prop="name">
          <span class="little-title">
            Username:
          </span>
          <el-input
            ref="username"
            v-model="name"
            placeholder="Username"
            name="username"
            type="text"
          />
        </el-form-item>

        <el-form-item prop="password">
          <span class="little-title">
            Password:
          </span>
          <el-input
            ref="password"
            v-model="password"
            placeholder="Password"
            name="password"
            type="password"
          />
        </el-form-item>

        <el-button type="primary" style="width:100%;margin-bottom:30px;" @click.native.prevent="my_login">Log in</el-button>

      </el-form>
    </div>

</template>

<script>


  const axios = require('axios');
  export default {
    name: 'HelloWorld',
    data: function () {
      return {
        mode: 'login',
        name: '',
        password: '',
        content: '',
        url: '',
        msg: 'Welcome to Tasker!',
        login_data: {}
      }
    },
    methods: {
      my_login:function(){
        axios.post('http://njuzhy.com:7800/user/login',
          {name:this.name,password:this.password}).then((res)=>this.login_data=res.data).then((res)=>console.log(res)).then((res)=>this.my_login2());
      },
      my_login2:function(){
        if(this.login_data.code==0){
          this.$toast.show('登录成功',2);
          this.$cookies.set('user_data',this.login_data.data,1000*60*60);
          this.$router.push({
            path: '/Home',
          });
        }
        else if(this.login_data.code==-1)  {
          console.log(this.login_data.data);
          this.$toast.show(this.login_data.data,2);
        }
      }
    }
  }
</script>


<style lang="scss" scoped>
  $bg:#2d3a4b;
  $dark_gray:#889aa4;
  $light_gray:#eee;

  .login-container {
    min-height: 100%;
    width: 100%;
    background-color: $bg;

    .login-form {
      position: relative;
      width: 520px;
      max-width: 100%;
      padding: 160px 35px 0;
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
    .little-title{
      font-size: 15px;
      color: $light_gray;
      font-weight: bold;
    }
  }
</style>
