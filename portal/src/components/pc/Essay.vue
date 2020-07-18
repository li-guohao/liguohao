<template>
    <div class="root">
        
        <div class="main">
            <div class="moduleBgChange">
              <div class="moudle-header"> 
              所有动态
              </div>
              <!-- 分页 -->
              <div >
                <el-pagination background
                  @size-change="handleSizeChange"
                  @current-change="handleCurrentChange"
                  :current-page="essayListData.currentPage"
                  :page-size="essayListData.pageSize"
                  :page-sizes="[4, 8, 16, 32, 64]"
                  layout="total,sizes, prev, pager, next"
                  :total="essayListData.total">
                </el-pagination>
              </div>
              <el-row :gutter="20">
                <!-- 显示动态信息 -->
                <el-col v-for="(article) in essayListData.dataArray" :key="article.aid" :xs="24" :sm="12" :md="8" :lg="6" :xl="6">
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
              <!-- 分页 -->
              <div >
                <el-pagination background
                  @size-change="handleSizeChange"
                  @current-change="handleCurrentChange"
                  :current-page="essayListData.currentPage"
                  :page-size="essayListData.pageSize"
                  :page-sizes="[4, 8, 16, 32, 64]"
                  layout="total,sizes, prev, pager, next"
                  :total="essayListData.total">
                </el-pagination>
              </div>
            </div>
        </div>

    </div>
</template>

<script>
export default {
    data(){
      return {
        // 所有动态数据
        essayListData:{
            total:0,
            currentPage:1,
            pageSize:8,
            dataArray:[]
        }
      }
    },
    created(){
      // 初始化操作
      // 获取动态数据
      this.getEssayListData()
    },
    methods:{
      // 获取动态数据
      async getEssayListData(){
        const {data: res} = await this.$http.get(`article/list/1/${this.essayListData.currentPage}/${this.essayListData.pageSize}`)
        console.log(res)
        if(res.meta.status !== 200) return this.$message.error('后台接口异常，获取所有动态失败！返回信息：'+res.meta.msg)
        //this.$message.success('获取所有文章数据成功')
        this.essayListData = res.data
      },
      // 分页-每页显示条数改变
      handleSizeChange(val){
          this.essayListData.pageSize = val
          this.getEssayListData()
      },
      // 分页-当前页改变
      handleCurrentChange(val){
          this.essayListData.currentPage = val
          this.getEssayListData()
      },
      // 跳转到文章详情页
      toArticleInfo(aid){
        console.log('查询的动态AID为：'+aid)
        // 往session中存入对象
        window.sessionStorage.setItem('articleSearchAid',aid)
        this.$router.push(`/article/info/${aid}`)
      }
    }
}
</script>

<style lang="less" scoped>

.main{
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
}

</style>
