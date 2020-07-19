<template>
    <div class="root">
        <el-card class="box-card">
            <div slot="header" class="clearfix">
                 <!-- 面包屑导航区域 -->
                <el-breadcrumb separator-class="el-icon-arrow-right">
                <el-breadcrumb-item :to="{ path: '/index' }">首页</el-breadcrumb-item>
                <el-breadcrumb-item>系统管理</el-breadcrumb-item>
                <el-breadcrumb-item>导航链接</el-breadcrumb-item>
                </el-breadcrumb>
            </div>

            <!-- 导航链接 -->
            <el-table stripe 
                :data="linkList"
                style="width: 100%">
                <el-table-column
                    prop="name"
                    label="标题"
                    width="180">
                </el-table-column>
                <el-table-column
                    label="图像"
                    width="50">
                    <template slot-scope="scope">
                        <div class="linkIcon">
                            <img  :src="scope.row.img" width="50px">
                        </div>
                    </template>
                </el-table-column>
                <el-table-column
                    label="URL">
                    <template slot-scope="scope">
                        <a  :href="scope.row.targetUrl" target="_blank" >{{scope.row.targetUrl}}</a>
                    </template>
                </el-table-column>
                <el-table-column
                    prop="indroduced"
                    label="描述">
                </el-table-column>
                <el-table-column  align='center' width="350"
                    label="操作">
                    <template slot-scope="scope">
                        <el-button size="mini" type="primary" icon="el-icon-edit" @click="updateLink(scope.row)">更新</el-button>
                        <el-button size="mini" type="danger" icon="el-icon-delete" @click="deleLink(scope.row.lid)">删除</el-button>
                    </template>
                </el-table-column>
            </el-table>
            
            <br>
            <!-- 添加表单 -->
            <el-form ref="linkForm" :model="link" label-width="80px">
                <el-form-item label="标题">
                    <el-input v-model="link.name" placeholder="请输入导航链接标题"></el-input>
                </el-form-item>
                <el-form-item label="图标">
                    <el-input v-model="link.img" placeholder="请输入图标的URL"></el-input>
                </el-form-item>
                <el-form-item label="URL">
                    <el-input v-model="link.targetUrl" placeholder="请输入导航链接URL"></el-input>
                </el-form-item>
                <el-form-item label="描述">
                    <el-input v-model="link.indroduced" placeholder="请输入导航链接描述"></el-input>
                </el-form-item>
                <!-- 操作 -->
                <el-form-item>
                    <el-button   type="primary" @click="saveLink">更新</el-button>
                    <el-button @click="reset">重置</el-button>
                </el-form-item>
            </el-form>
        </el-card>
        
    </div>
</template>

<script>
export default {
    data(){
        return{
            linkList:[],
            link:{
                "name":"",
                "img":"",
                "targetUrl":'',
                "indroduced":''
            }
        }
    },
    created(){
        // 获取所有导航链接
        this.getLinkList()
    },
    methods:{
        // 获取所有导航链接
        async getLinkList(){
            const {data: res} = await this.$http.get(`blog/link/list`)
            //console.log(res)
            if(res.meta.status !== 200) return this.$message.error('后台接口异常，返回信息：'+res.meta.msg)
            //else this.$message.success(res.meta.msg)
            this.linkList = res.data
        },
        // 保存链接
        async saveLink(){
            const {data: res} = await this.$http.post(`blog/link/save`,this.link)
            console.log(res)
            if(res.meta.status !== 200) return this.$message.error('后台接口异常，返回信息：'+res.meta.msg)
            else this.$message.success(res.meta.msg)
            this.getLinkList()
            this.reset()
        },
        // 重置表单
        reset(){
            this.link = {
                "name":"",
                "img":"",
                "targetUrl":'',
                "indroduced":''
            }
        },
        // 删除链接
        async deleLink(lid){
            const {data: res} = await this.$http.delete(`blog/link/${lid}`)
            console.log(res)
            if(res.meta.status !== 200) return this.$message.error('后台接口异常，返回信息：'+res.meta.msg)
            else this.$message.success(res.meta.msg)
            this.getLinkList()
        },
        // 更新表单
        updateLink(entity){
            this.link = entity;
        }
    }
}
</script>

<style lang="less" scoped>
a {
    color: black;
    
}
a:hover,a:focus{
    transition: all 0.5s ease-in-out; /* 缓慢变化效果 */
    color: saddlebrown;
}
.linkIcon{
    background-color: rgba(0, 8, 10, 0.5);
    padding: 2px;
    text-align: center;
}
</style>