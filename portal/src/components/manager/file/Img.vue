<template>
    <div class="root">
        <el-card class="box-card">
            <div slot="header" class="clearfix">
                 <!-- 面包屑导航区域 -->
                <el-breadcrumb separator-class="el-icon-arrow-right">
                <el-breadcrumb-item :to="{ path: '/index' }">首页</el-breadcrumb-item>
                <el-breadcrumb-item>文件管理</el-breadcrumb-item>
                <el-breadcrumb-item>图片管理</el-breadcrumb-item>
                </el-breadcrumb>
                
            </div>
            <!-- 分页 -->
            <el-pagination background
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
            :current-page="fileListData.currentPage"
            :page-sizes="[6,13, 27, 41, 62]"
            :page-size="fileListData.pageSize"
            layout="total, sizes, prev, pager, next, jumper"
            :total="fileListData.total">
            </el-pagination>
            <br>
            <el-upload
            accept=".jpg,.png,.bmp,.gif"
            action=""
            :http-request="uploadFile"
            list-type="picture-card"
            :file-list="fileListData.dataArray"
            :on-preview="handlePictureCardPreview"
            :on-remove="deleFile">
            <i class="el-icon-plus"></i>
            </el-upload>
            <el-dialog :visible.sync="dialogFile.visible" size="tiny" :title="dialogFile.file.name">
                <el-alert
                  title="文件直链"
                  type="success"
                  :closable="false"
                  :description="dialogFile.file.url">
                </el-alert>
                <br>
                <img width="100%" :src="dialogFile.file.url" :alt="dialogFile.file.description">
            </el-dialog>
            
        </el-card>
    </div>
</template>

<script>
  export default {
    data() {
      return {
        // 文件数据
        fileListData:{
            total:10,
            currentPage:1,
            pageSize:27,
            dataArray:[]
        },
        dialogFile:{
            visible: false, //是否显示
            file:{} //数据
        },
        dialogImageUrl: '',
        dialogVisible: false
      };
    },
    created(){
        this.getFileListData()
    },
    watch: {
        
    },
    methods: {
      // 删除文件
      async deleFile(file, fileList) {
        this.$message.warning('将要删除文件'+ file.fid)
        const {data: res} = await this.$http.delete(`file/${file.fid}`)
        console.log(res)
        if(res.meta.status !== 200) return this.$message.error('后台接口异常，删除此文件失败！返回信息：'+res.meta.msg)
        else this.$message.success(res.meta.msg)
      },
      //   显示图片大图
      handlePictureCardPreview(file) {
        this.dialogFile.file = file;
        this.dialogFile.visible = true;
      },
      // 上传文件
      async uploadFile(param){
        var formdata = new FormData();
        formdata.append("file", param.file);
        this.$message.info('开始上传图片')
        const {data: res} = await this.$http.post('file/upload',formdata);
        console.log(res)
        if(res.meta.status !== 200) return this.$message.error('后台接口异常，上传图片失败！返回信息：'+res.meta.msg)
        // 文件上传成功提示信息
        this.$message.success('文件上传成功')
      },
      // 获取文件数据
      async getFileListData(){
        const {data: res} = await this.$http.get(`file/list/img/${this.fileListData.currentPage}/${this.fileListData.pageSize}`)
        console.log(res)
        if(res.meta.status !== 200) return this.$message.error('后台接口异常，获取所有文件失败！返回信息：'+res.meta.msg)
        else this.$message.success(res.meta.msg)
        this.fileListData = res.data
      },
      // 分页 每页显示条数改变
      handleSizeChange(val){
          this.fileListData.pageSize = val;
          this.getFileListData()
      },
      // 分页，当前页改变
      handleCurrentChange(val){
          this.fileListData.currentPage = val;
          this.getFileListData()
      }
    }
  }
</script>

