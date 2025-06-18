<template>
<div class="backgrd">
  <div class="loginbackground">
      <div style="margin: 20px 0;font-size: 24px;text-align: center;color: white;font-weight: bold">登录</div>
      <el-form :model="user" :rules="rules" ref="userForm">
        <el-form-item prop="username">
          <el-input  size="medium"  style="margin: 10px 0;" prefix-icon="el-icon-user" v-model="user.username"></el-input>
        </el-form-item>
        <el-form-item prop="password"  >
          <el-input size="medium" style="margin: 10px 0;" prefix-icon="el-icon-lock" show-password  v-model="user.password"></el-input>
        </el-form-item>
        <div style="margin: 10px 0;text-align: right">
          <el-button type="primary" size="small" autocomplete="off" @click="login">登录</el-button>
          <el-button type="warning" size="small" autocomplete="off" @click="$router.push('/resgister')">注册</el-button>
        </div>
      </el-form>
  </div>
</div>
</template>

<script>
export  default {
  name:"Login",
  data(){
    return {
      user:{},
      rules:{
        username:[
          {required: true,message:"请输入用户名称",trigger: "blur"},
          {min:3,max:6,message: "长度在3到6个字符",trigger: "blur"}
        ],
        password:[
          {required: true,message:"请输入密码",trigger: "blur"},
          {min:5,max:10,message: "长度在5到10个字符",trigger: "blur"}
        ]
      }
    }
  },
  methods:{
    login(){
      this.$refs['userForm'].validate((valid) =>{
        if(valid){
          this.request.post("/user/login",this.user).then(res =>{
            if(res.code==='200'){
              localStorage.setItem("user",JSON.stringify(res.data))//存储用户信息到浏览器
              localStorage.setItem("menus",JSON.stringify(res.data.menus))
              this.$router.push("/")
              this.$message.success("登录成功")
            }else{
              this.$message.error(res.msg)
            }
          })
        }
      })
    }
  }
}
</script>


<style>
.backgrd{
  height: 100vh;
  overflow: hidden;
  background-image: linear-gradient(to bottom right,#Fc466B,#3F5EFB);
}

.loginbackground{
  margin: 200px auto;
  border-radius: 10px;
  padding: 20px;
  background-color: #31ad75;
  width: 350px;
  height: 280px;
}

</style>