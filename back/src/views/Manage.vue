<template>
  <div>
    <el-container style="height: 100%;">
      <el-aside width='sc'
                style="background-color:black">
        <Aside :isCollapse="isCollapse" :logoText="logoText"/>
      </el-aside>

      <el-container>
        <el-header style="border-bottom: 1px solid #ccc;" >
          <Header :collapse="collapse"/>
        </el-header>
        <el-main>
          <router-view/>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script>
import Aside from "@/components/Aside.vue";
import Header from "@/components/Header.vue";
export default {
  name: 'HomeView',
  components: {
    Aside,
    Header
  },
  data(){
    return {
      mes: "李霄钰很厉害！",
      sc:200,
      isCollapse: false,
      logoText:true
    }
  },
  methods: {
    collapse() {
      if (this.isCollapse) {
        this.isCollapse = false
        this.logoText = true
      } else {
        this.isCollapse = true
        this.logoText = false
      }
    },
  }
}
</script>
<style>
.el-header {
  background-color: white;
}

.el-aside {
  color: #333;
}
.el-menu-vertical-demo:not(.el-menu--collapse) {
  width: 200px;
  min-height: 400px;
}
.system{
  text-align: center;
  height: 60px;
  line-height: 60px;
}
</style>