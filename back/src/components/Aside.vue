<template>
  <el-menu :default-openeds="['1', '3']"
           background-color="rgb(48,65,86)"
           style="height: 100vh"
           text-color="#fff"
           active-text-color="blue"
           class="el-menu-vertical-demo"
           :collapse="isCollapse"
            router
            @select="handleSelect">
    <div class="system">
      <img src="../assets/logo.png" alt="" style="height: 20px;position: relative;top: 4px">
      <b style="color: #fff" v-show="logoText">后台管理系统</b>
    </div>
    <div>
      <div v-for="item in menus" :key="item.id">
        <div v-if="item.path">
          <el-menu-item :index="item.path">
            <template ><i :class="item.icon"></i>
              <span slot="title">{{item.name}}</span>
            </template>
          </el-menu-item>
        </div>
        <div v-else>
          <el-submenu :index="item.id">
            <template slot="title"><i :class="item.icon"></i>
              <span slot="title" v-show="logoText">{{item.name}}</span>
            </template>
            <div v-for="subItem in item.children" :key="subItem.id">
              <el-menu-item :index="subItem.path">
                <template ><i :class="subItem.icon"></i>
                  <span slot="title">{{subItem.name}}</span>
                </template>
              </el-menu-item>
            </div>
          </el-submenu>
        </div>
      </div>
    </div>
  </el-menu>
</template>
<script>
export  default {
  name:"Aside",
  props:{
    isCollapse:Boolean,
    logoText:Boolean
  },
  methods:{
    handleSelect(){
    }
  },
  data(){
    return{
      menus: localStorage.getItem("menus")? JSON.parse(localStorage.getItem("menus")):[]
    }
  },
}
</script>
<style scoped></style>