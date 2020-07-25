<template>
  <div class="root">
    <!-- 背景轮播图 -->
    <div class="tbs-bg"></div>
    <div class="tbs-bg tbs-bg--1"></div>
    <div class="tbs-bg tbs-bg--2"></div>
    <div class="tbs-bg tbs-bg--3"></div>
    <div class="tbs-bg tbs-bg--4"></div>
    <div class="tbs-bg tbs-bg--5"></div>

    <!-- 头部导航栏 -->
    <div class="header" >
      <!-- logo -->
      <a href="/" class="logo">{{logo}}</a>
      <!-- 主链接 -->
      <a :key="'headerUrl'+index"  v-for='(item,index) in headerUrls' 
          :href="item.url"  @click=" headUrlClick(item)"><i :class="item.iconClass"></i>{{item.name}}</a>
      <!-- 后台自定义导航链接 -->
      <a :key="'link'+link.lid" v-for='link in linkList' :href="link.targetUrl" >
        <img  :src="link.img" alt="" class="headerLinkIcon">
        {{link.name}}
      </a>
    </div>
    <!-- 主体内容路由占位符 -->
    <router-view/>
    <!-- 尾部声名 -->
    <div class="footer " v-html="footerInfo"></div>    
    <!-- 后台接口异常时处理判断 -->
    <div  v-if="footerInfo === ''"  class="footer ">
      <p>
        <a href="https://liguohao.cn">小站</a>  &nbsp;&nbsp;&nbsp;&nbsp;  <a href="https://liguohao.cn">@liguohao.cn</a>
        <font color="white">  Made By </font> 
        <a href="https://liguohao.cn"> li-guohao</a>
        <br>
        <a  href="http://beian.miit.gov.cn/" target="_blank">备案号：赣ICP备19013362号-3</a>
      </p>
    </div>
  </div>
</template>

<script>
import globalVar from '@/Global'
export default {
  metaInfo(){
    return {
      title: this.title, // set a title
      meta: [{                 // set meta
        name: this.keyWords,
        content: this.content
      }],
      link: [{                 // set link
        rel: 'icon',
        href: this.linkIcon
      },{
        rel: 'shortcut icon',
        href: this.linkIcon
      }]
    }
  },
  created(){
    // 获取导航链接
    this.getLinkList()
    // 获取footer处的HTML片段信息
    this.getFooterInfo()
    // 获取meta相关信息
    this.getMetaInfo()
  },
  // 挂载数据
  mounted(){
    
  },
  // 计算函数
  computed:{
  },
  data(){
    return {
      apiBaseUrl:globalVar.apiBaseUrl, //导入的全局变量
      title:'',
      keyWords:'',
      content:'',
      linkIcon:'',
      // 后台状态标识符
      apiStatusFlag: false,
      // logo文本
      logo:'小豪的个人小站',
      // 顶部导航栏数据
      headerUrls:[
        {url:'/', iconClass:'fa fa-home', name:'首页'},
        {url:'/article', iconClass:'fa fa-server', name:'文章'},
        {url:'/tags', iconClass:'fa fa-tags', name:'标签墙'},
        {url:'#', iconClass:'fa fa-qq', name:'登陆'}
      ],
      // 尾部HTML代码
      footerInfo: ``,
      // 导航链接
      linkList:[]
    }
  },
  methods:{
    // 获取meta相关信息
    async getMetaInfo(){
      // title
      const {data: res1} = await this.$http.get(`system/option/one/siteInfo/title`);
      //console.log(res1)
      if( res1.meta.status !== 200) return this.$message.error('后台接口异常，返回信息：'+res1.meta.msg)
      // else this.$message.success(res1.meta.msg)
      this.title = res1.data.optionValue

      // keyWords
      const {data: res2} = await this.$http.get(`system/option/one/siteInfo/metaKeyWords`);
      //console.log(res2)
      if( res2.meta.status !== 200) return this.$message.error('后台接口异常，返回信息：'+res2.meta.msg)
      // else this.$message.success(res2.meta.msg)
      this.keyWords = res2.data.optionValue
      
      // content
      const {data: res3} = await this.$http.get(`system/option/one/siteInfo/metaDescription`);
      //console.log(res2)
      if( res3.meta.status !== 200) return this.$message.error('后台接口异常，返回信息：'+res3.meta.msg)
      // else this.$message.success(res3.meta.msg)
      this.content = res3.data.optionValue
      
      // linkIcon
      const {data: res4} = await this.$http.get(`system/option/one/siteInfo/linkIcon`);
      //console.log(res4)
      if( res4.meta.status !== 200) return this.$message.error('后台接口异常，返回信息：'+res4.meta.msg)
      // else this.$message.success(res4.meta.msg)
      this.linkIcon = res4.data.optionValue
    },
    // 获取footer处的HTML片段信息
    async getFooterInfo(){
      const {data: res} = await this.$http.get(`system/option/one/siteInfo/footer`);
      //console.log(res)
      if( res.meta.status !== 200) return this.$message.error('后台接口异常，返回信息：'+res.meta.msg)
      // else this.$message.success(res.meta.msg)
      this.footerInfo = res.data.optionValue
      // 修改后台接口状态
      this.apiStatusFlag = true;
    },
    // 获取导航链接
    async getLinkList(){
       const {data: res} = await this.$http.get(`blog/link/list`)
      //console.log(res)
      if(res.meta.status !== 200) return this.$message.error('后台接口异常，返回信息：'+res.meta.msg)
      //else this.$message.success(res.meta.msg)
      this.linkList = res.data
    },
    // 用户登陆
    headUrlClick(e){
      if(e.iconClass === 'fa fa-qq' && e.name === '登陆'){ this.QQLogin()}
    },
    // 通过QQ登陆
    QQLogin(){
      var qqUrl =this.apiBaseUrl+"/system/user/qq/login";

      window.open(qqUrl, 'newwindow', 'height=500, width=500, top=100, left=250, toolbar=no, menubar=no, scrollbars=no, resizable=no,location=n o, status=no')
      // 通过监听，父页面可以拿到子页面传递的token，父(前端页面)，子(小窗)
      
      window.addEventListener('message',this.QQLoginCallBakc,false);

    },
    // qq登陆回调
    QQLoginCallBakc(e){
      //console.log(e)
      //console.log(e.data)
      if(e.data!==null && ''!==e.data){
        var strArr = e.data.split('&');
        var UID = strArr[0];
        var token = strArr[1];
        window.sessionStorage.setItem('token', token)
        window.sessionStorage.setItem('UID',UID)
        // 请求后台查询用户信息
        this.getUserInfo(UID);
        this.$message.success('登陆成功,请稍后')
        setTimeout(()=>{
          this.$router.push('/manager')
        },1000)
      }else{
        this.$message.error('登陆失败')  
      }
    },
    // 获取用户信息
    async getUserInfo(uid){
      const {data:res} = await this.$http.get(`system/user/info/${uid}`)
      //console.log(res)
      if(res.meta.status !== 200) return this.$message.error('后台接口异常，获取用户信息！返回信息：'+ res.meta.msg)
      window.sessionStorage.setItem('user',JSON.stringify(res.data))
    }
  }
}
</script>

<style lang="less" scoped>
// 头部
.header {
  background-color: rgba(0, 8, 10, 0.5);
  text-align: left;
  padding: 0 10%;
  
  /* 头部的a标签 */
  a {
    height: 50px;
    width: 50px;
    line-height: 50px;
    padding:  15px ;
    margin: 0 2px;
    font-size: 14px;
    color: white;

    .headerLinkIcon{
      padding: 2px;
      width: 20px;
      max-width: 100%;
      vertical-align: middle;
      border: 0;
      height: auto;
      -ms-interpolation-mode: bicubic;
      overflow: hidden;
      font-size: 12px;
    }
  }
  /* 头部的a标签 鼠标移动 鼠标聚焦 */
  a:focus, a:hover{
    color: white;
    // background-color: rgba(114,102,186,0.8);
    background-color: rgba(138,43,226,0.7);
  }
  // 头部下拉菜单控制按钮
  .barButton{
      background-color: rgba(0, 8, 10, 0.1);
      display: inline;
  }
  

  // 下拉菜单区域
  .bars{
    display: none;
    // display: inline;
    position: relative;
    margin: 0;
    padding: 0;
    text-align: center;
    
    a{
      display: block;
      width: 100%;
      line-height: 0;
      height: auto;
    }

  }
  

  .logo{
    height: 54px;
    line-height: 24px;
    font-size: 20px;
    opacity: 1;
    background-color: rgba(0,0,0,0);
    text-shadow: 0 0 5px #fff, 0 0 10px #fff, 0 0 15px #fff, 0 0 20px #228DFF, 0 0 35px #228DFF, 0 0 40px #228DFF, 0 0 50px #228DFF, 0 0 75px #228DFF;
  }
}

// 尾部声名
.footer{
	/* border: blueviolet 1px solid; */
	text-align: center;
	background-color: rgba(10,10,0,0.3);
	padding: 0 10%;
  overflow: hidden;
  margin-bottom: 0px;
  
  
  // 尾部的a标签
  a {
    color: white;
  }
  a:focus, a:hover{
    color: #B9DEF0;
  }
}

/* 背景图片轮播样式定义 */
.tbs-bg{
	height: 100%;
	width: 100%;
	position: fixed;
	top: 0;
	left: 0;
	background-image: url(./assets/images/background/1.jpg);
	background-size: cover;
	background-position: center center;
	z-index: -1;/* Z-index -1 拥有更低的优先级。 */
	-webkit-animation: imageAnimation 48s linear infinite 0s;
	animation: imageAnimation 48s linear infinite 0s;
	opacity: 0;
}
.tbs-bg--1 {
	background-image: url(./assets/images/background/2.jpg);
	-webkit-animation-delay: 8s;
	animation-delay: 8s;
}
.tbs-bg--2{
	background-image: url(./assets/images/background/3.jpg);
	-webkit-animation-delay: 16s;
	animation-delay: 16s;
}
.tbs-bg--3{
	background-image: url(./assets/images/background/4.jpg);
	-webkit-animation-delay: 24s;
	animation-delay: 24s;
}
.tbs-bg--4{
	background-image: url(./assets/images/background/5.jpg);
	-webkit-animation-delay: 32s;
	animation-delay: 32s;
}
.tbs-bg--5{
	background-image: url(./assets/images/background/6.jpg);
	-webkit-animation-delay: 45s;
	animation-delay: 45s;
}



/* 定义动画动态变化规则 */
@-webkit-keyframes imageAnimation /* Safari and Chrome */
{
	0%   {
		opacity: 0;
		-webkit-animation-timing-function: ease-in;
		animation-timing-function: ease-in;
	}
	8%  {
		opacity: 1;
		-webkit-transform: scale(1.05);
		transform: scale(1.05);
		-webkit-animation-timing-function: ease-out;
		animation-timing-function: ease-out;
	}
	17%  {
		opacity: 1;
		-webkit-transform: scale(1.1) rotate(0deg);
		transform: scale(1.1) rotate(0deg);
	}
	25% {
		opacity: 0;
		-webkit-transform: scale(1.1) rotate(0deg);
		transform: scale(1.1) rotate(0deg);
	}
	100% {
		opacity: 0;
		-webkit-transform: scale(1.1) rotate(0deg);
		transform: scale(1.1) rotate(0deg);
	}
}
</style>
