<template>
    <div class="root">
        <div class="main">
          <div class=" moduleBgChange mouseOverBoxShade" >
            <div class="moduleBgChange" v-html='friendLinksInfoHTML'>
            </div>
            <div >
              <!-- <ul>
                <li><a href="http://liguohao.cn" title="http://liguohao.cn">
                  <img class="friendImg" src="https://resource.tobeshrek.com/images/avatar.jpg" alt="头像" >
                  <p>李国豪的个人小站</p>
                </a></li>
              </ul> -->
            </div>
            <!-- 未设置时显示的内容 -->
            <div class="moduleBgChange" v-if="friendLinksInfoHTML === ``">
                <p>暂未设置此页面数据</p>
                <p>设置方法：请在后台/系统管理/系统设定 中 添加 名称为 friends 的HTML代码端</p>
            </div>
          </div>
        </div>
    </div>
</template>

<script>
export default {
    data(){
      return{
        //  友人帐页面HTML数据
        friendLinksInfoHTML: ``
      }
    },
    created(){
      // 获取友人账页面HTML数据
      this.getFriendLinksInfoHTML()
    },
    methods:{
      // 获取友人账页面HTML数据
      async getFriendLinksInfoHTML(){
        const {data: res} = await this.$http.get(`setting/friends`)
        console.log(res)
        if(res.meta.status !== 200) return this.$message.error('后台接口异常，获取友人账页面HTML信息失败！返回信息：'+res.meta.msg)
        //this.$message.success('获取首页公告数据成功'+res.meta.msg)
        this.friendLinksInfoHTML = res.data.value
      }
    }
}
</script>

<style lang="less" scoped>

.main{
  padding: 0 10%;

  ul {

    li {
      display: inline;
      .friendImg{
        width: 50px;
        height: 50px;
        overflow: hidden;
        border-radius: 50%;

      }
    }
  }
  
}

</style>
