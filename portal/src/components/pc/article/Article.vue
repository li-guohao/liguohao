<template>
    <div class="root">
        <div class="main">
            <!-- 所有文章 -->
          <div class="moduleBgChange">
            <div class="moudle-header"> 
              所有文章
            </div>
            <!-- 分页 -->
            <div >
              <el-pagination background
                @size-change="handleSizeChange"
                @current-change="handleCurrentChange"
                :current-page="articleListData.currentPage"
                :page-size="articleListData.pageSize"
                :page-sizes="[3, 6, 12, 24, 48]"
                layout="total,sizes, prev, pager, next"
                :total="articleListData.total">
              </el-pagination>
            </div>
            <!-- 文章列表 -->
            
            <el-row :gutter='20' >
              
              <el-col  :xs="24" :sm="24" :md="12" :lg="8" :xl="8" v-for="article in articleListData.dataArray" :key="article.aid">
                <div class="moduleBgChange mouseOverBoxShade" >
                  <div class="part">
                    <!-- 标题 -->
                    <a @click="toArticleInfo(article.aid)"><div class="title">
                      <span v-if="article.title !== ''">{{article.title}}</span>
                      <span v-else>暂无标题</span>
                    </div></a>
                    <!-- 数据 -->
                    <div >
                        <span class="label label-zan"><i class="fa fa-eye"></i> {{article.readCount === null?0:article.readCount}} ℃</span>
                        <span class="label label-zan"><i class="fa fa-comments"></i>{{article.commentCount === null?0:article.commentCount}}</span>
                        <span class="label label-zan"><i class="fa fa-calendar"></i> {{article.updateTime | formatDate}}</span>
                        <span class="label label-zan"><i class="fa fa-tags"></i> 
                            <span v-for="tag in article.tags" :key="tag.tid">{{tag.name}}</span>
                        </span>
                    </div>
                    <!-- 简介 -->
                    <div class="articleDesc">
                      {{article.description}}
                    </div>
                    <!-- 图片 -->
                    <div class="thumbnail">
                      <img v-if='article.thumbnail !== null' :src="article.thumbnail" alt="丛雨天下第一">
                      <img v-if='article.thumbnail === null' src="https://resource.tobeshrek.com/images/galgame/senrenbanka/1.jpg" alt="丛雨天下第一">
                    </div>
                  </div>
                </div>
              </el-col>
              
            </el-row>
            
            <!-- 分页 -->
            <div >
              <el-pagination background
                @size-change="handleSizeChange"
                @current-change="handleCurrentChange"
                :current-page="articleListData.currentPage"
                :page-size="articleListData.pageSize"
                :page-sizes="[3, 6, 12, 24]"
                layout="total,sizes, prev, pager, next"
                :total="articleListData.total">
              </el-pagination>
            </div>
          </div>
        </div>
    </div>
</template>

<script>
export default {
    data(){
      return{
        // 文章列表数据
        articleListData:{
            total:0,
            currentPage:1,
            pageSize:6,
            dataArray:[]
        }
      }
    },
    filters: {
        // 日期格式化日
        formatDate(time) {
            return time.substring(0,10)
        }
    },
    created(){
      // 初始化操作
      // 获取文章数据
      this.getArticleListData()
    },
    methods:{
      // 获取文章列表数据
      async getArticleListData(){
          const {data: res} = await this.$http.get(`blog/article/list/${this.articleListData.currentPage}/${this.articleListData.pageSize}`)
          console.log(res)
          if(res.meta.status !== 200) return this.$message.error('后台接口异常，获取所有文章失败！返回信息：'+res.meta.msg)
          //this.$message.success('获取所有文章数据成功')
          this.articleListData = res.data
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
      },
      // 跳转到文章详情页
      toArticleInfo(aid){
        console.log('查询的文章AID为：'+aid)
        this.$router.push(`/article/info/${aid}`)
      }
    }

}
</script>

<style lang="less" scoped>

.el-row{

  .el-col{
    min-height: 400px;
  }
}

  // 标签
.label {
    display: inline;
    padding: .2em .6em .3em;
    font-size: 75%;
    font-weight: bold;
    line-height: 1;
    color: #ffffff;
    text-align: center;
    white-space: nowrap;
    vertical-align: baseline;
    border-radius: .25em;
    margin: 0 5px;
}
.label-zan {
    background-color: rgba(10,10,0,0.7);
}

.articleDesc{
  margin: 5px 0;
}
</style>
