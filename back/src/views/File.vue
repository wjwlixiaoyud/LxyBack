<template>
  <div>
    <div style="margin: 10px 0">
      <el-upload action="http://localhost:9090/file/upload" :show-file-list="false" :on-success="handleFileUploadSuccess" style="display: inline-block">
        <el-button type="primary" style="margin-left: 5px">上传文件<i class="el-icon-circle-plus-outline"></i></el-button>
      </el-upload>
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
      <el-table-column prop="name" label="文件名称" width="140">
      </el-table-column>
      <el-table-column prop="type" label="文件类型" width="120">
      </el-table-column>
      <el-table-column prop="size" label="文件大小(kb)" width="120">
      </el-table-column>
      <el-table-column label="下载">
        <template slot-scope="scope">
          <el-button type="primary" @click="downloads(scope.row.url)">下载</el-button>
        </template>
      </el-table-column>
      <el-table-column label="启用">
        <template slot-scope="scope">
          <el-switch v-model="scope.row.enable" active-color="#13ce66" inactive-color="#ccc" @change="changeEnable(scope.row)"></el-switch>
        </template>
      </el-table-column>
      <el-table-column v-slot:default="scope" label="操作">
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
  </div>

</template>

<script>
export  default {
  name:"File",
  data(){
    return{
      tableDate:[],
      multipleSelection:[],
      name:'',
      pageSize:5,
      pageNum:1,
      total:0
    }
  },
  created() {
    this.load()
  },
  methods:{
    load(){
      this.request.get("/file/pages", {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          name:this.name,
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
      this.request.post("/file/del/batch",ids).then(res =>{
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
      this.request.post("/file/"+id).then(res =>{
        if(res.code==='200'){
          this.$message.success("删除成功")
          this.load()
        }else{
          this.$message.error("删除失败")
        }
      })
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
    handleFileUploadSuccess(res){
      console.log(res)
      this.$message.success("文件上传成功")
      this.load()
    },
    downloads(url){
      window.open(url)
    },
    changeEnable(row){
      this.request.post("/file/update",row).then(res=>{
        if(res.code==='200'){
          this.$message.success("操作成功")
        }
      })
    }
  }
}
</script>

<style >

</style>