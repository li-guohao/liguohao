<template>
    <div class="root">
        <div class="main">
          <div class=" moduleBgChange mouseOverBoxShade" >
            <div class="moduleBgChange" v-html='archiveInfoHTML'>
            </div>
            <!-- 未设置时显示的内容 -->
            <div class="moduleBgChange" v-if="archiveInfoHTML === ``">
                <p>暂未设置此页面数据</p>
                <p>设置方法：请在后台/系统管理/系统设定 中 添加 名称为 archive 的HTML代码端</p>
            </div>
          </div>
        </div>
    </div>
</template>

<script>
export default {
    data(){
      return{
        // 归档页面HTML数据
        archiveInfoHTML:``
      }
    },
    created(){
      // 获取归档页面HTML数据
      this.getArchiveInfoHTML()
    },
    methods:{
      // 获取归档页面HTML数据
      async getArchiveInfoHTML(){
        const {data: res} = await this.$http.get(`setting/archive`)
        console.log(res)
        if(res.meta.status !== 200) return this.$message.error('后台接口异常，获取归档页面HTML信息失败！返回信息：'+res.meta.msg)
        //this.$message.success('获取首页公告数据成功'+res.meta.msg)
        this.archiveInfoHTML = res.data.value
      }
    }
}
</script>

<style lang="less" scoped>

.main{
  padding: 0 10%;


}

</style>
