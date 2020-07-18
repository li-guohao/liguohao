<template>
  <div class="root">
    <div class="main">
      <div class="moduleBgChange">
        <div class="moudle-header">查询结果</div>
         <!-- 查到数据为0条 -->
        <div class="part" v-if="articleListData.total===0 && essayListData.total===0">
            暂无符合条件的数据
        </div>
        <!-- 查询到了数据 -->
        <div v-if="articleListData.total!==0  || essayListData.total!==0">
            <!-- 分页 -->
            <el-pagination
            background
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
            :current-page="articleListData.currentPage"
            :page-size="articleListData.pageSize"
            :page-sizes="[3, 6, 12, 24, 48]"
            layout="total,sizes, prev, pager, next"
            :total="articleListData.total"
            ></el-pagination>
            <el-row :gutter="20">
              <!-- 显示动态信息 -->
              <el-col  v-for="(article) in essayListData.dataArray" :key="article.aid" :xs="24" :sm="12" :md="8" :lg="6" :xl="6">
                <div class="moduleBgChange mouseOverBoxShade" >
                  <div class="part">
                      <!-- 动态内容 -->
                      <div class="essay-content">
                        {{article.content}}
                      </div>
                      
                      <!-- 动态更新时间 -->
                      <div class="essay-updateTime">
                        <a @click="toArticleInfo(article.aid)">更多</a>
                        更新于{{article.updateTime}}
                      </div>
                  </div>
                </div>
              </el-col>
            </el-row>
            <el-row :gutter="20">
              <!-- 文章列表 -->
              <el-col :xs="24" :sm="24" :md="12" :lg="8" :xl="8"
              v-for="article in articleListData.dataArray"
              :key="article.aid"
              >
              <div class="moduleBgChange mouseOverBoxShade">
                <a @click="toArticleInfo(article.aid)">
                    <div class="part">
                    <!-- 标题 -->
                    <a @click="toArticleInfo(article.aid)">
                        <div class="title">
                        <span v-if="article.title !== ''">{{article.title}}</span>
                        <span v-else>暂无标题</span>
                        </div>
                    </a>
                    <!-- 数据 -->
                    浏览-{{article.readCount === null?0:article.readCount}}
                    评论-{{article.commentCount === null?0:article.commentCount}}
                    标签:<span v-for="tag in article.tags" :key="tag.tid">{{tag.name}}</span>
                    <br>
                    更新于{{article.updateTime}}
                    <br>
                    <!-- 简介 -->
                    {{article.description}}
                    <!-- 图片 -->
                    <img v-if="article.thumbnail !== null" :src="article.thumbnail" alt="丛雨天下第一" />
                    <img
                        v-if="article.thumbnail === null"
                        src="https://resource.tobeshrek.com/images/galgame/senrenbanka/1.jpg"
                        alt="丛雨天下第一"
                    />
                    </div>
                </a>
              </div>
              </el-col>
            
            </el-row>
            <!-- 分页 -->
            <div>
            <el-pagination
                background
                @size-change="handleSizeChange"
                @current-change="handleCurrentChange"
                :current-page="articleListData.currentPage"
                :page-size="articleListData.pageSize"
                :page-sizes="[3, 6, 12, 24]"
                layout="total,sizes, prev, pager, next"
                :total="articleListData.total"
            ></el-pagination>
            </div>
        </div>
        
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      // 查询结果数据
      articleListData: {
        total: 0,
        currentPage: 1,
        pageSize: 6,
        dataArray: []
      },
      essayListData:{
            total:0,
            currentPage:1,
            pageSize:8,
            dataArray:[]
      }
    };
  },
  created() {
    
  },
  computed: {
    // 查询条件
    term() {
      return this.$route.params.term;
    }
  },
  mounted() {
    // 根据条件获取文章数据
    this.getArticleListData()
    // 根据条件获取动态数据
    this.getEssayListData()

  },
  methods: {
    //根据tid查询所有包含该标签的文章数据，按照更新时间顺序排列
    async getArticlesByTid(tid) {
      const { data: res } = await this.$http.get(
        `blog/article/list/tag/${this.articleListData.currentPage}/${this.articleListData.pageSize}/${this.term}`
      );
      console.log(res);
      if (res.meta.status !== 200)
        return this.$message.error(
          "后台接口异常，获取所有文章失败！返回信息：" + res.meta.msg
        );
      //this.$message.success('获取所有文章数据成功')
      this.articleListData = res.data;
    },
    //根据tid查询所有包含该标签的动态数据，按照更新时间顺序排列
    async getEssayListData(tid){
      const { data: res } = await this.$http.get(
        `blog/article/list/${this.articleListData.currentPage}/${this.articleListData.pageSize}/${this.term}`
      );
      console.log(res);
      if (res.meta.status !== 200)
        return this.$message.error(
          "后台接口异常，获取所有动态失败！返回信息：" + res.meta.msg
        );
      this.essayListData = res.data;
    },
    // 根据文章标题片段模糊查询文章，按照更新时间排序
    async getArticleByArticleTitle(partTitle) {
      const { data: res } = await this.$http.get(
        `blog/article/list/like/${this.articleListData.currentPage}/${this.articleListData.pageSize}/${this.term}`
      );
      console.log(res);
      if (res.meta.status !== 200)
        return this.$message.error(
          "后台接口异常，获取所有文章失败！返回信息：" + res.meta.msg
        );
      //this.$message.success('获取所有文章数据成功')
      this.articleListData = res.data;

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
    // 根据条件获取文章数据
    async getArticleListData(){
        // console.log(this.term);
        var flag = /[1-9][0-9]*/.test(this.term); //判断是否为数字,第一位不能为0
        if (flag) {
        //为数字, 便签的tid
        //根据tid查询所有包含该标签的文章数据，按照更新时间顺序排列
        this.getArticlesByTid(this.term);
        } else {
        //为字符, 文章的标题片段
        // 根据文章标题片段模糊查询文章，按照更新时间排序
        this.getArticleByArticleTitle(this.term);
        }
    },
    // 跳转到文章详情页
    toArticleInfo(aid){
        console.log('查询的文章AID为：'+aid)
        this.$router.push(`/article/info/${aid}`)
    }
  }
};
</script>

<style lang="less" scoped>
// 动态样式
.moduleBgChange{
    .el-col{
      // border: skyblue 1px solid;
      .part{
        
            // 动态内容样式
            .essay-content{
              overflow: hidden;
              text-align: center;
              font-size: medium;
              padding: 10px;
              height: 100px;
            }

            // 动态更新时间样式
            .essay-updateTime{
              font-size: smaller;
              text-align: right;
              padding: 5px;
            }
        }
    }
  }

.block{
  display: block;
}
</style>


