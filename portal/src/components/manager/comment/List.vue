<template>
    <div class="root">
        <el-card class="box-card">
            <div slot="header" class="clearfix">
                 <!-- 面包屑导航区域 -->
                <el-breadcrumb separator-class="el-icon-arrow-right">
                <el-breadcrumb-item :to="{ path: '/index' }">首页</el-breadcrumb-item>
                <el-breadcrumb-item>评论管理</el-breadcrumb-item>
                <el-breadcrumb-item>所有评论</el-breadcrumb-item>
                </el-breadcrumb>
                
            </div>
            <el-table stripe  
                :data="commentListData.dataArray"
                style="width: 100%">
                <!-- checkbox -->
                <el-table-column
                    type="selection"
                    width="55">
                </el-table-column>
                <!-- 评论CID -->
                <el-table-column sortable
                    prop="cid"
                    label="ID"
                    width="80">
                </el-table-column>
                <!-- 内容 -->
                <el-table-column
                    prop="content"
                    label="内容"
                    width="180"
                    >
                <template slot-scope="scope">
                    <!-- :row-dblclick="editContent(row, event)" -->
                    <el-input v-model="scope.row.content" @blur="updateComment(scope.row)"></el-input>
                </template>
                </el-table-column>
                <!-- 评论时间 -->
                <el-table-column
                    prop="createTime"
                    label="时间"
                    width="180">
                </el-table-column>
                <!-- 评论用户名 -->
                <el-table-column
                    prop="user.nickname"
                    label="用户昵称"
                    width="180">
                </el-table-column>
                <!-- 评论类型 -->
                <el-table-column
                    prop="type"
                    label="类型"
                    width="180"
                    :formatter="formatType">
                </el-table-column>
                
                <el-table-column  align='center'
                    label="操作">
                    <template slot-scope="scope">
                        
                        <el-button size="mini" type="danger" icon="el-icon-delete" @click="deleComment(scope.row.cid)">删除</el-button>
                    </template>
                </el-table-column>
            </el-table>
            <br>
            <!-- 分页 -->
            <el-pagination background
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
            :current-page="commentListData.currentPage"
            :page-sizes="[2, 5, 10, 20, 40]"
            :page-size="commentListData.pageSize"
            layout="total, sizes, prev, pager, next, jumper"
            :total="commentListData.total">
            </el-pagination>
        </el-card>
    </div>
</template>

<script>
export default {
    name: 'CommentList',
    data(){
        return{
            // 评论列表数据
            commentListData:{
                total:10,
                currentPage:1,
                pageSize:5,
                dataArray:[]
            }
        }
    },
    created(){
        this.getCommentListData()
    },
    methods:{
        // 获取评论列表数据
        async getCommentListData(){
            const {data: res} = await this.$http.get(`blog/comment/list/${this.commentListData.currentPage}/${this.commentListData.pageSize}`)
            console.log(res)
            if(res.meta.status !== 200) return this.$message.error('后台接口异常，获取所有评论失败！返回信息：'+res.meta.msg)
            this.$message.success('获取所有评论数据成功')
            this.commentListData = res.data
        },
        // 格式化评论类型
        formatType(row, column, cellValue){
            if(cellValue == 1) return '文章评论'
            else return '未知类型'
        },
        // 编辑评论
        editComment(val){
            console.log(val)
            // 往session中存入对象
            window.sessionStorage.setItem('commentEditCID',val)
            this.$router.push('/index/comment/edit')
        },
        // 根据CID删除评论
        async deleComment(val){
            this.$message.info(val+'')
            // 请求后台，删除评论
            const {data:res} = await this.$http.delete(`blog/comment/${val}`)
            console.log(res)
            if(res.meta.status!=200) this.$message.error(res.meta.msg)
            this.$message.success(res.meta.msg)
            this.getCommentListData()
        },
        // 分页-每页显示条数改变
        handleSizeChange(val){
            this.commentListData.pageSize = val
            this.getCommentListData()
        },
        // 分页-当前页改变
        handleCurrentChange(val){
            this.commentListData.currentPage = val
            this.getCommentListData()
        },
        // 更新评论内容
        async updateComment(row){
            const {data: res} = await this.$http.post(`blog/comment/save`,row)
            console.log(res)
            if(res.meta.status !== 200) return this.$message.error(res.meta.msg)
            this.$message.success(res.meta.msg)
            this.getCommentListData()
        }
    }
}
</script>

<style lang="less" scoped>
</style>