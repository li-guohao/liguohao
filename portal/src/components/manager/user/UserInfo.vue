<template>
    <div class="root">
        <el-card class="box-card">
            <div slot="header" class="clearfix">
                 <!-- 面包屑导航区域 -->
                <el-breadcrumb separator-class="el-icon-arrow-right">
                <el-breadcrumb-item :to="{ path: '/index' }">首页</el-breadcrumb-item>
                <el-breadcrumb-item>系统管理</el-breadcrumb-item>
                <el-breadcrumb-item>用户信息</el-breadcrumb-item>
                </el-breadcrumb>
                
            </div>
            

            <el-tabs value="headPortraitUrl">
                <el-tab-pane label="用户头像" name="headPortraitUrl">
                    <!-- 用户头像 -->
                    <!-- <el-upload
                        class="avatar-uploader"
                        action="/file/upload/img"
                        :show-file-list="true" :drag="true"
                        :on-success="handleAvatarSuccess"
                        :before-upload="beforeAvatarUpload">
                        <img v-if="imageUrl" :src="imageUrl" class="avatar">
                        <i v-else class="el-icon-plus avatar-uploader-icon"></i>
                    </el-upload> -->
                    <br>
                    <el-input v-model="user.headPortraitUrl" placeholder="请输入头像URL，带http请求，如https://resource.tobeshrek.com/images/avator.jpg"></el-input>
                    <img :src="user.headPortraitUrl" alt="暂未上传头像或者头像无效">
                    <br>
                </el-tab-pane>
        
                <el-tab-pane label="主要信息" name="third">
                    <!-- 邮箱 -->
                    <el-tag>你的邮箱</el-tag> <el-input v-model="user.email" placeholder="邮箱"></el-input>
                    <br>
                    <!-- 密码 -->
                    <el-tag>密码</el-tag> <el-input v-model="user.password" placeholder="密码"></el-input>
                    <br>
                    <!-- 用户昵称 -->
                    <el-tag>用户昵称</el-tag> <el-input v-model="user.nickname" placeholder="昵称"></el-input>
                    <br>
                    <!-- 电话号码 -->
                    <el-tag>电话号码</el-tag> <el-input v-model="user.telephone" placeholder="电话号码"></el-input>
                    <br>
                    
                    <!-- 个人站点 -->
                    <el-tag>个人站点</el-tag> <el-input v-model="user.website" placeholder="个人站点"></el-input>
                    <br>
                    <!-- 个人简述 -->
                    <el-tag>个人简述</el-tag>
                    <br>
                    <el-input style="width:75%;padding: 5px;;"
                        type="textarea"
                        :autosize="{ minRows: 2, maxRows: 4}"
                        placeholder="个人简述"
                        v-model="user.description">
                    </el-input>
                </el-tab-pane>
                <el-tab-pane label="账号绑定" name="fourth">
                    <!-- bilibiliUUID -->
                    <el-tag>B站UUID</el-tag>
                    <el-input v-model="user.buid" placeholder="B站UUID"></el-input>
                    <br>
                    <!-- QQ号 -->
                    <el-tag>QQ号</el-tag>
                    <el-input v-model="user.qq" placeholder="QQ号"></el-input>
                    <br>
                    <!-- 微信号 -->
                    <el-tag>微信号</el-tag>
                    <el-input v-model="user.wechat" placeholder="微信号"></el-input>
                    <br>
                </el-tab-pane>
            </el-tabs>
            <br>
            <el-button type="primary" @click="saveUser">保存</el-button>
        </el-card>
    </div>
</template>

<script>
export default {
    data(){
        return {
            // 用户信息数据
            user: {} ,
            password:'',
            newPassword1:'',
            newPassword2: ''
        }
    },
    created(){
        // 初始化操作
        this.user.uid =  window.sessionStorage.getItem('UID') //从session中获取用户ID并赋值给this.user
        // 从session中获取用户信息（JSON对象格式）
        this.user = JSON.parse( window.sessionStorage.getItem('user') )
    },
    updated(){
        
    },
    methods:{
        // 头像上传成功
        handleAvatarSuccess(response, file, fileList){
            
        },
        // 头像上传前
        beforeAvatarUpload(file){

        },
         // 保存用户
        async saveUser(){
            const {data: res} = await this.$http.post('system/user/save',this.user)
            // 保存失败
            if(res.meta.status !== 200) this.$message.error(res.meta.msg);
            // 保存成功
            this.$message.success(res.meta.msg+' 如果发现用户信息未刷新，请重新登陆下')
            window.sessionStorage.setItem('user',JSON.stringify(res.data))
        }
       
    }
}
</script>

<style lang="less" scoped>

    .el-input{
        width: 75%;
        padding: 5px;
    }

    // 用户头像
    .avatar-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
  }
  .avatar-uploader .el-upload:hover {
    border-color: #409EFF;
  }
  .avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 178px;
    height: 178px;
    line-height: 178px;
    text-align: center;
  }
  .avatar {
    width: 178px;
    height: 178px;
    display: block;
  }

</style>
