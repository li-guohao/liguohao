<template>
    <div class="root">
        <el-card class="box-card">
            <div slot="header" class="clearfix">
                 <!-- 面包屑导航区域 -->
                <el-breadcrumb separator-class="el-icon-arrow-right">
                <el-breadcrumb-item :to="{ path: '/index' }">首页</el-breadcrumb-item>
                <el-breadcrumb-item>文章管理</el-breadcrumb-item>
                <el-breadcrumb-item>所有标签</el-breadcrumb-item>
                </el-breadcrumb>
                
            </div>
            <el-button @click="deleTagsForNotUse" icon="fa fa-trash" type="primary">一键移除未被引用标签</el-button>
            <el-table stripe  
                :data="entityListData.dataArray"
                style="width: 100%">
                <!-- checkbox -->
                <el-table-column
                    type="selection"
                    width="55">
                </el-table-column>
                <!-- 评论CID -->
                <el-table-column sortable
                    prop="tid"
                    label="ID"
                    width="80">
                </el-table-column>
                
                <!-- 标签名称 -->
                <el-table-column sortable
                    prop="name"
                    label="标签名称"
                    width="180">
                </el-table-column>
                <!-- <el-table-column
                    prop="name"
                    label="标签名称"
                    width="180">
                    <template slot-scope="scope">
                    <el-input v-model="scope.row.name" @blur="updateEntity(scope.row)"></el-input>
                </template> -->

                </el-table-column>
                <!-- 创建时间 -->
                <el-table-column
                    prop="createTime"
                    label="时间"
                    width="180">
                </el-table-column>
                <!-- 被引用次数 -->
                <el-table-column
                    prop="referenceCount"
                    label="被引用次数">
                </el-table-column>
                
                <!-- <el-table-column  align='center'
                    label="操作">
                    <template slot-scope="scope">
                        
                        <el-button size="mini" type="danger" icon="el-icon-delete" @click="deleEntity(scope.row.tid)">删除</el-button>
                    </template>
                </el-table-column> -->
            </el-table>
            <br>
            <!-- 分页 -->
            <el-pagination background
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
            :current-page="entityListData.currentPage"
            :page-sizes="[2, 5, 10, 20, 40]"
            :page-size="entityListData.pageSize"
            layout="total, sizes, prev, pager, next, jumper"
            :total="entityListData.total">
            </el-pagination>
        </el-card>
    </div>
</template>

<script>
export default {
    data(){
        return{
            // 评论列表数据
            entityListData:{
                total:10,
                currentPage:1,
                pageSize:5,
                dataArray:[]
            }
        }
    },
    created(){
        this.getEntityListData()
    },
    methods:{
        // 获取列表数据
        async getEntityListData(){
            const {data: res} = await this.$http.get(`blog/tag/list/${this.entityListData.currentPage}/${this.entityListData.pageSize}`)
            console.log(res)
            if(res.meta.status !== 200) return this.$message.error('后台接口异常，获取所有评论失败！返回信息：'+res.meta.msg)
            this.$message.success('获取所有评论数据成功')
            this.entityListData = res.data
        },
        // 根据ID删除评论
        async deleEntity(val){
            this.$message.info(val+'')
            // 请求后台，删除评论
            const {data:res} = await this.$http.delete(`blog/tag/${val}`)
            console.log(res)
            if(res.meta.status!=200) this.$message.error(res.meta.msg)
            this.$message.success(res.meta.msg)
            this.getEntityListData()
        },
        // 分页-每页显示条数改变
        handleSizeChange(val){
            this.entityListData.pageSize = val
            this.getEntityListData()
        },
        // 分页-当前页改变
        handleCurrentChange(val){
            this.entityListData.currentPage = val
            this.getEntityListData()
        },
        // 更新
        async updateEntity(entity){
            const {data:res} = await this.$http.post(`blog/tag/save`,entity)
            console.log(res)
            if(res.meta.status!=200) this.$message.error(res.meta.msg)
            this.$message.success(res.meta.msg)
            this.getEntityListData()
        },
        // 一键移除未被引用的标签
        async deleTagsForNotUse(){
            // tag/deleteByNotUse
            const {data:res} = await this.$http.delete(`blog/tag/deleteByNotUse`)
            console.log(res)
            if(res.meta.status!=200) this.$message.error(res.meta.msg)
            this.$message.success(res.meta.msg)
            this.getEntityListData()
        }
    }
}
</script>

<style lang="less" scoped>
</style>