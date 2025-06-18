<template>
<div class="backgrd">
  <div class="loginbackground">
      <div style="margin: 20px 0;font-size: 24px;text-align: center;color: white;font-weight: bold">注册</div>
      <el-form :model="user" :rules="rules" ref="userForm">
        <el-form-item prop="username">
          <el-input placeholder="请输入账号" size="medium"  style="margin: 10px 0;" prefix-icon="el-icon-user" v-model="user.username"></el-input>
        </el-form-item>
        <el-form-item prop="password"  >
          <el-input placeholder="请输入密码" size="medium" style="margin: 10px 0;" prefix-icon="el-icon-lock" show-password  v-model="user.password"></el-input>
        </el-form-item>
        <el-form-item prop="confirmPassword"  >
          <el-input placeholder="请确认密码" size="medium" style="margin: 10px 0;" prefix-icon="el-icon-lock" show-password  v-model="user.confirmPassword"></el-input>
        </el-form-item>
        <div style="margin: 10px 0;text-align: right">
          <el-button type="primary" size="small" autocomplete="off" @click="login">确定</el-button>
          <el-button type="warning" size="small" autocomplete="off" @click="$router.push('/login')">返回登录</el-button>
        </div>
      </el-form>
  </div>
</div>
</template>

<script>
export  default {
  name:"Rsegister",
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
        ],
        confirmPassword:[
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
          if(this.user.password !== this.user.confirmPassword){
            this.$message.error("两次输入密码不一致")
            return false
          }
          this.request.post("/user/resgister",this.user).then(res =>{
            if(res.code==='200'){
              this.$message.success("注册成功")
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
  margin: 100px auto;
  border-radius: 10px;
  padding: 20px;
  background-color: #31ad75;
  width: 350px;
  height: 350px;
}

</style>