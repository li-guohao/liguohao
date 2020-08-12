<template>
  <div class="root">
    <div class="main">
      <el-row :gutter='0'>
        
        <el-col  :xs="24" :sm="24" :md="17" :lg="17" :xl="17"><div >
          <!-- 公告栏 -->
          <div class="  " >
            <div class="notice mouseOverBoxShade">
              <button type="button" class="close"  @click="hideNoticeDiv()">x</button>
              <div  v-html="notice"></div>
              <div v-if="notice === ''" >
                出现此内容时 建议您多刷新几次 
                <br>
                如果还有问题 建议换浏览器尝试
                <br>
                建议使用 火狐、chrome、QQ浏览器访问
                <br>
                啥都没有的原因：站主没有做老版本兼容适配，老版本浏览器没法获取后台数据
              </div>
            </div>
            
            <div class=" ">
              <!-- 最新文章 -->
              <el-row  >

                <!-- 置顶文章 -->
                <el-col  :span="24"  v-for="article in newestArticles" :key="'topAricle-'+article.aid">
                    <div class="mouseOverBoxShade article" v-if="article.top === 1">
                      <i class="fa fa-bookmark article-stick "></i>
                      <!-- 标题 -->
                      <a @click="toArticleInfo(article.aid)">
                        <div class="title">
                        <span v-if="article.title !== ''">{{article.title}}</span>
                        <span v-else>暂无标题</span>
                      </div></a>
                      <!-- 数据 -->
                      <div >
                        <span class="label label-zan"><i class="fa fa-eye"></i> {{article.readCount === null?0:article.readCount}} ℃</span>
                        <span class="label label-zan"><i class="fa fa-comments"></i>{{article.commentCount === null?0:article.commentCount}}</span>
                        <span class="label label-zan"><i class="fa fa-calendar"></i> {{article.updateTime | formatDate }}</span>
                      </div>
                      
                      <!-- 简介 -->
                      <!-- <p>{{article.description}}</p> -->
                      <!-- 图片 -->
                      <div class="cover mouseOverBoxShade">
                        <!-- <a @click="toArticleInfo(article.aid)"> 
                          <img v-if='article.thumbnail !== null' :src="article.thumbnail" alt="丛雨天下第一">
                          <img v-if='article.thumbnail === null' src="https://resource.tobeshrek.com/images/galgame/senrenbanka/1.jpg" alt="丛雨天下第一">
                        </a> -->
                        <div class="img"> 
                          <img v-if='article.thumbnail !== null' :src="article.thumbnail" alt="丛雨天下第一">
                          <img v-if='article.thumbnail === null' src="http://qiniu.liguohao.cn/2020_7_19_0A74BC02DE9343999834DB32C514887A.png" alt="丛雨天下第一">
                        </div> 
                        <div class="info"> 
                          <p>{{article.description}}</p>
                        </div> 
                      </div>
                    </div>
                </el-col>

                <!-- 其它文章 -->
                <el-col :span="24"  v-for="article in newestArticles" :key="'newestArticle'+article.aid">
                    <div class="mouseOverBoxShade article" v-if="article.top !== 1">
                      <i class="fa fa-bookmark article-stick "></i>
                      <!-- 标题 -->
                      <a @click="toArticleInfo(article.aid)">
                        <div class="title">
                        <span v-if="article.title !== ''">{{article.title}}</span>
                        <span v-else>暂无标题</span>
                      </div></a>
                      <!-- 数据 -->
                      <div >
                        <span class="label label-zan"><i class="fa fa-eye"></i> {{article.readCount === null?0:article.readCount}} ℃</span>
                        <span class="label label-zan"><i class="fa fa-comments"></i>{{article.commentCount === null?0:article.commentCount}}</span>
                        <span class="label label-zan"><i class="fa fa-calendar"></i> {{article.updateTime | formatDate  }}</span>
                        <span class="label label-zan"><i class="fa fa-tags"></i> 
                            <span v-for="tag in article.tags" :key="'articleTags'+tag.tid">{{tag.name}}</span>
                        </span>
                      </div>
                      
                      
                      <div class="cover mouseOverBoxShade">
                        <!-- 图片 -->
                        <div class="img"> 
                          <img v-if='article.thumbnail !== null' :src="article.thumbnail" alt="丛雨天下第一">
                          <img v-if='article.thumbnail === null' src="http://qiniu.liguohao.cn/2020_7_19_0A74BC02DE9343999834DB32C514887A.png" alt="丛雨天下第一">
                        </div> 

                        <!-- 简介 -->
                        <div class="info"> 
                          <p>{{article.description}}</p>
                        </div> 

                      </div>
                      
                    </div>
                </el-col>
              
              </el-row>
            </div>           
            
          </div>
        </div></el-col>
        <el-col  :xs="24" :sm="24" :md="7" :lg="7" :xl="7">
          <div >
          <!-- 全站检索&数据统计 -->
          <div class=" moduleBgChange mouseOverBoxShade" >
            <div class="search"> 
              <input v-model="titlePart" @keypress.enter="searchArticles" type="text" placeholder="请输入搜索文章标题, 按回车键搜索">
              <button @click="searchArticles"> <i class="fa fa-search"></i></button>
            </div>
          </div>
          <!-- 个人信息 -->
          <div class=" moduleBgChange mouseOverBoxShade" >
            <div class="moudle-header"> 
              <i class="fa fa-info" aria-hidden="true"></i>  个人信息
            </div>
            <div class="moudle-body userInfo">
              <div class="avatar">
                <img :src="user.headPortraitUrl !== null ? user.headPortraitUrl : 'http://qiniu.liguohao.cn/2020_7_19_4743BFA6C6B543C2B537A1F6C01F332B.jpg'" alt="图片无法访问" />
              </div>
            
              <!-- 社交链接 -->
              <a :href="user.githubUrl" target="_blank"><i class="fa fa-github-square"></i></a>
              <a href="#" @click="QQDialogVisible = true" ><i class="fa fa-qq"></i></a>
              <a href="#" @click="WechatDialogVisible = true" ><i class="fa fa-weixin"></i></a>
              <a :href="'https://space.bilibili.com/'+user.buid" target="_blank"><img class="icon"  src="@/assets/images/icon/bilibili.png" alt=""></a>
              <!-- <a href="/ss" ><i class="fa fa-steam"></i></a> -->
              <!-- QQ对话框 -->
              <el-dialog title="扫码加QQ" :visible.sync="QQDialogVisible" width="350px">
                <el-alert  center :closable="false"
                  title="申请好友前请在验证信息出注明来源于哪" type="warning"
                  >
                </el-alert>
                <br>
                <img width="200px" :src="user.qqImg" alt="扫码加QQ">
              </el-dialog>
              <!-- 微信对话框 -->
              <el-dialog title="扫描加微信" :visible.sync="WechatDialogVisible" width="350px">
                <el-alert  center :closable="false"
                  title="申请好友前请在验证信息出注明来源于哪" type="warning"
                  >
                </el-alert>
                <br>
                <img width="200px" :src="user.wechatImg" alt="扫描加微信">
              </el-dialog>
              <br>
              <!-- 数据统计 -->
              {{articleListData.total === null?0:articleListData.total}}-文章  
              {{articleReadCount === null?0:articleReadCount}}-浏览 
              <!-- 个人介绍 -->
              <div  v-html="user.description !==  null ? user.description : describe"></div>
              
            </div>
            
          </div>
          <br>
          
          
          <!-- 热门文章 -->
          <div class="moduleBgChange mouseOverBoxShade">
            <div class="moudle-header"> 
              <i class="fa fa-fire"></i>  热门文章
            </div>
            <div class="moudle-body hotArticle">
                <a v-for="article in hottestArticles" :key="'hotArticle-'+article.aid"  :href="'/article/info/'+article.aid">{{article.title}}--{{article.readCount === null?0:article.readCount}}浏览</a>
            </div>
            
          </div>
        </div></el-col>
      </el-row>
  </div>
  </div>
</template>

<script>

export default {
  // 组件
  components: {

  },
  filters: {
      // 日期格式化日
      formatDate(time) {
        return time.substring(0,10)
      }
  },
  data(){
    return {
      // 用户信息
      user: {},
      // QQ对话框
      QQDialogVisible:false,
      // 微信对话框
      WechatDialogVisible:false,
      // 首页热门文章显示条数
      hotest_number: 127,
      // 首页文章显示条数
      article_number:6,
      // 热门文章
      hottestArticles:[],
      // 最新文章
      newestArticles:[],
      // 最新动态
      newestEssay:[],
      // 首页公告
      notice: ``,
      // 文章列表数据
      articleListData:{
          total:0,
          currentPage:1,
          pageSize:1000,
          dataArray:[]
      },
      // 查询用文章标题关键词
      titlePart: ''
    }
  },
  created(){
    // 获取所有文章 用于计数
    this.getArticleListData()
    // 获取首页公告
    this.getNotice()
    // 获取用户信息
    this.getUserInfo()
    // 获取热门文章信息
    this.getHottestArticles()
    // 获取最新文章信息
    this.getNewestArticles()
    // 测试方法
    this.test()
    
  },
  // 计算函数
  computed: {
    // 总浏览量
    articleReadCount(){
      // 临时总浏览量
      var readCount = 0
      // 遍历文章数组，累加浏览量，获得总浏览量
      this.articleListData.dataArray.forEach(e => {
        readCount += e.readCount
      });
      // console.log(readCount)
      // 修改总浏览量
      return readCount
    }
  },
  methods:{
    // 获取所有文章 用于计数
    async getArticleListData(){
      const {data:res} = await this.$http.get(`blog/article/list/${this.articleListData.currentPage}/${this.articleListData.pageSize}`)
      //console.log(res)
      if(res.meta.status !== 200) return this.$message.error('后台接口异常，获取所有文章失败！返回信息：'+ res.meta.msg)
      this.articleListData = res.data
    },
    // 获取用户信息
    async getUserInfo(){
      const {data:res} = await this.$http.get(`system/user/info/1`)
      //console.log(res)
      if(res.meta.status !== 200) return this.$message.error('后台接口异常，获取用户信息！返回信息：'+ res.meta.msg)
      this.user = res.data
    },
    // 获取热门文章信息
    async getHottestArticles(){
      const {data: res} = await this.$http.get(`blog/article/hottest/${this.hotest_number}`)
      // console.log(res)
      if(res.meta.status !== 200) return this.$message.error('后台接口异常，获取热门文章失败！返回信息：'+res.meta.msg)
      //this.$message.success('获取热门文章数据成功'+res.meta.msg)
      this.hottestArticles = res.data
    },
    // 获取最新文章信息
    async getNewestArticles(){
      const {data: res} = await this.$http.get(`blog/article/newest/${this.article_number}`)
      //console.log(res)
      if(res.meta.status !== 200) return this.$message.error('后台接口异常，获取最新文章失败！返回信息：'+res.meta.msg)
      //this.$message.success('获取最新文章数据成功'+res.meta.msg)
      this.newestArticles = res.data
    },
    // 获取首页公告
    async getNotice(){
      const {data: res} = await this.$http.get(`system/option/one/siteInfo/notice`)
      if(res.meta.status !== 200) return this.$message.error('后台接口异常，获取首页公告失败！返回信息：'+res.meta.msg)
      this.notice = res.data.optionValue
    },
    // 跳转到文章详情页
    toArticleInfo(aid){
      console.log('查询的文章AID为：'+aid)
      // 往session中存入对象
      window.sessionStorage.setItem('articleSearchAid',aid)
      this.$router.push(`/article/info/${aid}`)
    },
    // 查询文章
    searchArticles(){
      this.$router.push(`/search/${this.titlePart}`)
    },
    // 隐藏系统公告区域
    hideNoticeDiv(){
      var notice = document.getElementsByClassName("notice")[0];
      console.log(notice);
      notice.style.display="none";
    },
    // 测试用方法
    test(){
      
    }
  }
}
</script>

<style lang="less" scoped>
.main{
  margin: 5px 10%;
  //border: crimson 1px solid;
  text-align: center;
  
  .bilibiliIcno {
    width: 15px;
    height: 15px;
  }
  .el-row{
    width: 100%;
    margin: 0;
    padding: 0;
  }

  .el-col{
    //border: 1px solid blue;
    margin: 0;
    padding: 0;
  }

  
  // 公告区域
  .notice{
    padding: 5px 5%;
    margin: 1%;
    
    background-color: rgba(245, 245, 245, 0.5);

    .close {
        float: right;
        font-size: 14px;
        font-weight: bold;
        color: #25292f;
        text-shadow: 0 1px 0 #ffffff;
    }
    button.close {
        padding: 0;
        cursor: url(https://resource.tobeshrek.com/images/background/pointer.cur),url(https://resource.tobeshrek.com/images/background/pointer.cur),auto;
        background: transparent;
        border: 0;
        -webkit-appearance: none;
        opacity: 0.5;
    }
  }
  .notice:hover, .notice:focus{
    background-color: rgba(245, 245, 245, 0.8);
    transition: all 0.5s ease-in-out; /* 缓慢变化效果 */
  }

  // 首页文章
	.article {
    height: auto;
    margin: 1% ;
    padding: 1%;
    // border-radius: 10px;
    // transition: all 1.5s ease-in-out; /* 缓慢变化效果 */
    
    //置顶标签
    .article-stick {
        position: absolute;
        top: -5px;
        right: 5%;
        font-size: 40px;
        color: rgb(217, 83, 79);
    }

		// 标题
		.title{
			font-size: 30px;
			color: rgba(8, 80, 189, 0.7);
			transition: all 0.5s ease-in-out; /* 缓慢变化效果 */
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
    // 封面
    .cover{
      margin: 2%;
      padding: 2%;
      border-radius: 5px;
      background-color: rgba(230, 238, 232, 0.65);
      // box-shadow: black 0px 0px 8px;

      img{
        width: 100%;
      }
    }
	}


  // 模块样式
  .moduleBgChange{

    // 用户信息
    .userInfo{
      margin: 0;
      padding: 0;
      width: 100%;
      
    
      // 用户头像
      .avatar{
        overflow: hidden;
        padding: 5px 0;
        
        img {
          width: 100px;
          height: 100px;
          border-radius: 50%;
          transition: all 1.0s;
          //opacity: 0.7;
        }

        img:hover, img:focus{
          transform: rotate(360deg);
          width: 120px;
          height: 120px;
          border: 5px skyblue solid;
          //opacity: 1;
        }
      }

      // 社交链接
      a {
        margin: 0 3px;
      }

    }


    // 查询区域
    .search{
      background-color: rgba(0, 8, 10, 0.5);
      // 全局搜索框
      input{
        width: 70%;
        margin: 6px;
        border: #c9302c 1px solid;
        border-radius: 5px;
        line-height: 30px;
        background-color: rgba(0, 8, 10, 0.5);
      }
      imput:hover,input:focus{
        background-color: rgba(245, 245, 245, 1);
      }

      // 搜索按钮
      button{
        background-color: #c9302c;
        border-radius: 5px;
        height: 35px;
        width: 45px;
        border: 1px solid #c9302c;
        cursor: url(https://resource.tobeshrek.com/images/background/pointer.cur),url(https://resource.tobeshrek.com/images/background/pointer.cur),auto;
      }
    
    }

    

    

    // 热门文件
    .hotArticle{
      a{
        display: block;
        margin: 3px 0;
        padding: 3px 3px;
        overflow: hidden;
        color: rgba(27, 27, 27, 0.5);
      }
      a:hover, a:focus{
        background-color: rgba(235, 235, 235, 1);
        color: rgba(27, 27, 27, 1);
      }
    }

  }

	
  



}

</style>