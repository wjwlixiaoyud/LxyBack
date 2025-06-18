<script>
export default {
  name:"Header",
  data(){
    return{
      user:localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) :{}
    }
  },
  computed:{
    currentPathName(){
      return this.$store.state.currentPathName
    }
  },
  watch:{
    currentPathName(newVal,oldVal){
      console.log(newVal)
    }
  },
  props:{
    pathName:String,
    collapseBtnClass:String,
    collapse:Function
  },
  methods:{
    logout(){
      this.$router.push("/login")
      localStorage.removeItem("user")
      this.$message.success("退出成功")
    }
  }
}
</script>

<template>
  <div style="display: flex;line-height: 60px">
    <div style="flex: 1;font-size: 20px">
      <span class="el-icon-s-unfold" style="cursor: pointer;" @click="collapse"></span>
        <el-breadcrumb separator="/" style="display: inline-block;margin-left: 10px">
          <el-breadcrumb-item :to="'/'">首页</el-breadcrumb-item>
          <el-breadcrumb-item>{{ currentPathName }}</el-breadcrumb-item>
        </el-breadcrumb>
    </div>
    <el-dropdown style="width: auto;cursor: pointer;text-align: right">
      <div>
        <img :src="user.avatarUrl"
        style="width: 30px;border-radius: 50%;position: relative;top: 10px;right: 5px">
        <span>{{ user.nickname }}</span>
        <i class="el-icon-arrow-down" style="margin-left: 5px"></i>
      </div>
      <el-dropdown-menu slot="dropdown" style="width: 100px;text-align: center">
        <el-dropdown-item style="font-size: 14px;padding: 5px 0">
          <router-link to="/person" style="text-decoration: none">个人信息</router-link>
        </el-dropdown-item>
        <el-dropdown-item style="font-size: 14px;padding: 5px 0">
          <span style="text-decoration: none" @click="logout">退出</span>
        </el-dropdown-item>
      </el-dropdown-menu>
    </el-dropdown>
  </div>
</template>

<style>

</style>