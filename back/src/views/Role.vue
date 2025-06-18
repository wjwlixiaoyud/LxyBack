<template>
  <div>

    <div style="margin: 10px 0">
      <el-input  style="width: 200px;" placeholder="请输入名称" suffix-icon="el-icon-search" v-model="name"></el-input>
      <el-button style="margin-left: 5px" type="primary" @click="load">搜索</el-button>
      <el-button type="warning" @click="reset">重置</el-button>
    </div>
    <div style="margin: 10px 0">
      <el-button type="primary" @click="handadd">新增 <i class="el-icon-circle-plus-outline"></i></el-button>
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
    <el-table :data="tableData" border stripe :header-cell-class-name="headBg" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55"></el-table-column>
      <el-table-column prop="id" label="ID" width="80">
      </el-table-column>
      <el-table-column prop="name" label="名称" >
      </el-table-column>
      <el-table-column prop="description" label="描述" >
      </el-table-column>
      <el-table-column v-slot:default="scope" label="操作" width="300px">
        <el-button type="info" @click="selectMenu(scope.row.id)" >分配菜单 <i class="el-icon-menu"></i></el-button>
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
    <div style="padding: 10px 0">
      <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="pageNum"
          :page-sizes="[2,5,10,20]"
          :page-size="pageSize"
          layout="total,sizes,prev,pager,next,jumper"
          :total="total">
      </el-pagination>
    </div>


    <el-dialog title="角色信息" :visible.sync="dialogFormVisible" width="30%">
      <el-form label-width="60px" size="small">
        <el-form-item label="名称">
          <el-input v-model="form.name" autocomplete="off"></el-input>
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

    <el-dialog title="菜单信息" :visible.sync="Menudialog" width="30%">
      <el-tree
          :props="props"
          :data="Menudata"
          show-checkbox
          node-key="id"
          ref="tree"
          :default-expanded-keys="expends"
          :default-checked-keys="checks"
          @check-change="handleCheckChange">
         <span class="custom-tree-node" slot-scope="{ node, data }">
              <span><i :class="data.icon"></i> {{ data.name }}</span>
          </span>
      </el-tree>
      <div slot="footer" class="dialog-footer">
        <el-button @click="Menudialog = false">取 消</el-button>
        <el-button type="primary" @click="saveRoleMenu">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name:"Role",
  data(){
    return{
      name:"",
      dialogFormVisible:false,
      Menudialog: false,
      multipleSelection:[],
      form:{},
      pageNum:1,
      pageSize:5,
      total:0,
      tableData: [],
      Menudata: [],
      props:{
        label:"name"
      },
      roleId:0,
      expends:[],
      checks:[]
    }
  },
  created() {
    this.load()
  },
  methods:{
    load(){
      this.request.get("/role/page", {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          name:this.name
        }
      }).then(res=> {
        this.tableData=res.data.records
        this.total=res.data.total
      })


    },
    handleSelectionChange(val){
      console.log(val)
      this.multipleSelection=val
    },
    delBatch(){
      let ids=this.multipleSelection.map(v => v.id)
      this.request.post("/role/del/batch",ids).then(res =>{
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
      this.request.delete("/role/"+id).then(res =>{
        if(res.code==='200'){
          this.$message.success("删除成功")
          // this.dialogFormVisible=false
          this.load()
        }else{
          this.$message.error(res.msg)
        }
      })
    },
    handEdit(row){
      this.form = row
      this.dialogFormVisible=true
    },
    save(){
      this.request.post("/role",this.form).then(res=>{
        if(res.code==='200'){
          this.$message.success("保存成功")
          this.dialogFormVisible=false
          this.load()
        }else{
          this.$message.error("保存失败")
        }
      })
    },
    saveRoleMenu(){
      this.request.post("/role/rolemenu/"+this.roleId,this.$refs.tree.getCheckedKeys()).then(res=>{
        if(res.code==='200'){
          this.$message.success("绑定成功")
          this.Menudialog=false
        }else{
          this.$message.error("绑定失败")
        }
      })
    },
    handadd(){
      this.dialogFormVisible=true
      this.form={}
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
    selectMenu(roleId){
      this.Menudialog=true
      this.roleId=roleId
      this.request.get("/menu", {
      }).then(res=> {
        this.Menudata=res.data
        this.expends=this.Menudata.map(v => v.id)
      })
      this.request.get("/role/rolemenu/"+roleId, {
      }).then(res=> {
        this.checks=res.data
      })
    },
    handleCheckChange(data, checked, indeterminate) {
      console.log(data, checked, indeterminate);
    },
  }
}
</script>


<style >
.headBg{
  background: #ccc !important;
}

</style>