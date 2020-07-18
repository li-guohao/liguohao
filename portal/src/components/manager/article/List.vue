<template>
    <div class="root">
        <el-card class="box-card">
            <div slot="header" class="clearfix">
                 <!-- 面包屑导航区域 -->
                <el-breadcrumb separator-class="el-icon-arrow-right">
                <el-breadcrumb-item :to="{ path: '/index' }">首页</el-breadcrumb-item>
                <el-breadcrumb-item>文章管理</el-breadcrumb-item>
                <el-breadcrumb-item>所有文章</el-breadcrumb-item>
                </el-breadcrumb>
            </div>
            <!-- 根据标题匹配文章 -->
            <el-input @change="getArticleListData" clearable  placeholder="根据标题匹配文章,光标在输入框内按回车搜索" v-model="searchArticleTitle" >
            </el-input>
            <el-table stripe 
                :data="articleListData.dataArray"
                style="width: 100%">
                <!-- checkbox -->
                <el-table-column
                    type="selection"
                    width="55">
                </el-table-column>
                <!-- 文章AID -->
                <el-table-column sortable
                    prop="aid"
                    label="ID"
                    width="80">
                </el-table-column>
                <!-- 标题 -->
                <el-table-column
                    prop="title"
                    label="标题"
                    width="180">
                </el-table-column>
                <!-- 标签 -->
                <el-table-column
                    prop="tags"
                    label="标签">
                <template  slot-scope="scope" >
                    <el-tag :key="tag.tid" v-for="tag in scope.row.tags" >{{tag.name}}</el-tag>
                </template>
                </el-table-column>
                <!-- 最后一次更新时间 -->
                <el-table-column sortable
                    prop="updateTime"
                    label="更新时间"
                    width="180">
                </el-table-column>
                <!-- 作者 -->
                <el-table-column 
                    prop="author"
                    label="作者"
                    width="100">
                </el-table-column>
                <!-- 状态 -->
                <el-table-column :formatter="formatIsOpen"
                    prop="isOpen"
                    label="状态">
                </el-table-column>
                <!-- 浏览数 -->
                <el-table-column 
                    prop="readCount"
                    label="浏览数"
                    width="50">
                </el-table-column>
                <!-- 评论数 -->
                <el-table-column 
                    prop="commentCount"
                    label="评论数"
                    width="50">
                </el-table-column>
                <el-table-column  align='center'
                    label="操作">
                    <template slot-scope="scope">
                        <el-button size="mini" type="primary" icon="el-icon-edit" @click="editArticle(scope.row.aid)">编辑</el-button>
                        <el-button size="mini" type="danger" icon="el-icon-delete" @click="deleArticle(scope.row.aid)">删除</el-button>
                    </template>
                </el-table-column>
            </el-table>
            <br>
            <!-- 分页 -->
            <el-pagination background
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
            :current-page="articleListData.currentPage"
            :page-sizes="[2,5, 10, 20, 40]"
            :page-size="articleListData.pageSize"
            layout="total, sizes, prev, pager, next, jumper"
            :total="articleListData.total">
            </el-pagination>

        </el-card>
    </div>
</template>

<script>
export default {
    name: 'ArticleList',
    data(){
        return{
            // 文章列表数据
            articleListData:{
                total:10,
                currentPage:1,
                pageSize:5,
                dataArray:[]
            },
            // 文章查询的标题
            searchArticleTitle:''
        }
    },
    created(){
        this.getArticleListData()
    },
    methods:{
        // 获取文章列表数据
        async getArticleListData(){
            const {data: res} = await this.$http.get(`blog/article/list/like/${this.articleListData.currentPage}/${this.articleListData.pageSize}/${this.searchArticleTitle}`)
            console.log(res)
            if(res.meta.status !== 200) return this.$message.error('后台接口异常，获取所有文章失败！返回信息：'+res.meta.msg)
            else this.$message.success(res.meta.msg)
            this.articleListData = res.data
        },
        // 格式化状态
        formatIsOpen(row, column, cellValue){
            if (cellValue == 0) return '暂存稿'
            if (cellValue == 1 ) return '已发布'
        },
        // 编辑文章
        editArticle(val){
            console.log(val)
            // 往session中存入对象
            window.sessionStorage.setItem('articleEditAid',val)
            this.$router.push('/manager/article/edit')
        },
        // 根据AID删除文章
        async deleArticle(val){
            // this.$message.info(val+'')
            this.$confirm('此操作将永久删除该文章, 是否继续?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
            }).then(async () => {
                // 请求后台，删除文章
                const {data:res} = await this.$http.delete(`blog/article/${val}`)
                console.log(res)
                if(res.meta.status!=200) this.$message.error(res.meta.msg)
                else this.$message.success(res.meta.msg)
                this.getArticleListData()
            }).catch(() => {
                this.$message({
                    type: 'info',
                    message: '已取消删除'
                });          
            });
            
        },
        // 分页-每页显示条数改变
        handleSizeChange(val){
            this.articleListData.pageSize = val
            this.getArticleListData()
        },
        // 分页-当前页改变
        handleCurrentChange(val){
            this.articleListData.currentPage = val
            this.getArticleListData()
        }
    }
}
</script>

<style lang="less" scoped>
    .el-tag{
        margin: 0 2px;
    }
</style>