<template>
  <div>

    <div style="margin: 10px 0">
      <el-input  style="width: 200px;" placeholder="请输入名称" suffix-icon="el-icon-search" v-model="name"></el-input>
      <el-button style="margin-left: 5px" type="primary" @click="load">搜索</el-button>
      <el-button type="warning" @click="reset">重置</el-button>
    </div>
    <div style="margin: 10px 0">
      <el-button type="primary" @click="handadd()">新增 <i class="el-icon-circle-plus-outline"></i></el-button>
      <el-popconfirm
          style="margin-left: 5px"
          confirm-button-text='确定'
          cancel-button-text='我再想想'
          icon="el-icon-info"
          icon-color="red"
          title="您确定批量删除数据吗？"
          @confirm="delBatch"
      >
        <el-button type="danger" slot="reference">批量删除 <i class="el-icon-remove-outline"></i></el-button>
      </el-popconfirm>
    </div>
    <el-table :data="tableData" border stripe :header-cell-class-name="headBg" row-key="id"
              default-expand-all @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55"></el-table-column>
      <el-table-column prop="id" label="ID" width="80">
      </el-table-column>
      <el-table-column prop="name" label="名称" >
      </el-table-column>
      <el-table-column prop="path" label="路径" >
      </el-table-column>
      <el-table-column prop="pagepath" label="网页路径" >
      </el-table-column>
      <el-table-column label="图标">
        <template slot-scope="scope">
          <i :class="scope.row.icon" style="font-size: 20px;margin-left: 40%"></i>
        </template>
      </el-table-column>
      <el-table-column prop="description" label="描述" >
      </el-table-column>
      <el-table-column v-slot:default="scope" label="操作" width="300" align="center">
        <el-button type="primary" @click="handadd(scope.row.id)" v-if="!scope.row.pid && !scope.row.path">新增子菜单 <i class="el-icon-plus"></i></el-button>
        <el-button type="success" @click="handEdit(scope.row)">编辑 <i class="el-icon-edit"></i></el-button>
        <el-popconfirm
            style="margin-left: 5px"
            confirm-button-text='确定'
            cancel-button-text='我再想想'
            icon="el-icon-info"
            icon-color="red"
            title="您确定删除吗？"
            @confirm="del(scope.row.id)"
        >
          <el-button type="danger" slot="reference">删除 <i class="el-icon-remove-outline"></i></el-button>
        </el-popconfirm>
      </el-table-column>
    </el-table>


    <el-dialog title="角色信息" :visible.sync="dialogFormVisible" width="35%">
      <el-form label-width="60px" size="small">
        <el-form-item label="名称">
          <el-input v-model="form.name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="路径">
          <el-input v-model="form.path" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="网页路径" label-width="70px">
          <el-input v-model="form.pagepath" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="图标">
          <el-select clearable v-model="form.icon" placeholder="请选择" style="width: 100%">
            <el-option v-for="item in options" :key="item.name" :label="item.name" :value="item.value">
              <i :class="item.value"/>{{item.name}}
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="描述">
          <el-input v-model="form.description" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="save">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name:"Menu",
  data(){
    return{
      name:"",
      options:[],
      dialogFormVisible:false,
      multipleSelection:[],
      form:{},
      pageNum:1,
      pageSize:5,
      total:0,
      tableData: [],
    }
  },
  created() {
    this.load()
  },
  methods:{
    load(){
      this.request.get("/menu", {
        params: {
          name:this.name
        }
      }).then(res=> {
        this.tableData=res.data
      })
    },
    handleSelectionChange(val){
      console.log(val)
      this.multipleSelection=val
    },
    delBatch(){
      let ids=this.multipleSelection.map(v => v.id)
      this.request.post("/menu/del/batch",ids).then(res =>{
        if(res.code==='200'){
          this.$message.success("批量删除成功")
          // this.dialogFormVisible=false
          this.load()
        }else{
          this.$message.error("删除失败")
        }
      })
    },
    del(id){
      this.request.delete("/menu/"+id).then(res =>{
        if(res.code==='200'){
          this.$message.success("删除成功")
          // this.dialogFormVisible=false
          this.load()
        }else{
          this.$message.error("删除失败")
        }
      })
    },
    handEdit(row){
      this.form = JSON.parse(JSON.stringify(row))
      this.dialogFormVisible=true
      //请求图标的数据
      this.request.get("/menu/icons").then(res =>{
        this.options=res.data
      })
    },
    save(){
      this.request.post("/menu",this.form).then(res=>{
        if(res.code==='200'){
          this.$message.success("保存成功")
          this.dialogFormVisible=false
          this.load()
        }else{
          this.$message.error("保存失败")
        }
      })
    },
    handadd(pid){
      this.dialogFormVisible=true
      this.form={}
      if(pid){
        this.form.pid =pid
      }
    },
    reset(){
      this.name=""
      this.load()
    },
    handleSizeChange(pageSize){
      console.log(pageSize)
      this.pageSize=pageSize
      this.load()
    },
    handleCurrentChange(pageNum){
      console.log(pageNum)
      this.pageNum=pageNum
      this.load()
    },
  }
}
</script>


<style >
.headBg{
  background: #ccc !important;
}

</style>