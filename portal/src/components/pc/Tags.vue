<template>
    <div class="root">
        <div class="main">
            <div class="moduleBgChange mouseOverBoxShade" >
              <div class="moudle-header"><i class="fa fa-tags"></i>标签墙</div>
              <div class="tags">
                <el-tag @click="toSearchPage(tag.tid)" :key="tag.tid" v-for="(tag) in tagsPagingData.dataArray" :hit="true">{{tag.name}}</el-tag>
              </div>
            </div>
        </div>
    </div>
</template>

<script>
export default {
    data(){
      return{
        // 所有标签数据
        tagsPagingData:{
            total:0,
            currentPage:1,
            pageSize:200,
            dataArray:[]
        }
      }
    },
    created(){
      // 获取所有标签数据
      this.getTagsPagingData()
    },
    methods:{
      // 获取所有标签数据
      async getTagsPagingData(){
        const {data: res} = await this.$http.get(`blog/tag/list/${this.tagsPagingData.currentPage}/${this.tagsPagingData.pageSize}`)
        if(res.meta.status !== 200) return this.$message.error('后台接口异常，获取所有标签数据失败！返回信息：' + res.meta.msg)
        this.tagsPagingData = res.data
      },
      toSearchPage(tid){
        this.$router.push(`/search/${tid}`)
      }
    }
}
</script>

<style lang="less" scoped>

.main{
  padding: 0 10%;


  .tags{
    padding: 2%;
    margin: 0 5%;
    .el-tag{
      margin: 5px;
      cursor: url(../../assets/images/background/pointer.png),url(../../assets/images/background/pointer.png),auto;
    }

  }
  
  .tags:hover,.tags:focus{

    .el-tag{opacity: 1;}
  }
  
}

</style>
