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
      <a href="#/" class="logo">{{logo}}</a>
      <!-- 主链接 -->
      <a :key='index' v-for='(item,index) in headerUrls' :href="item.url" ><i :class="item.iconClass"></i>{{item.name}}</a>
      <el-button class="barButton"  type="info" icon="fa fa-bars" circle @click="switchHeaderStatus"></el-button>
      <!-- 下拉菜单 -->
      <div class="bars">
        <a :key='index' v-for='(item,index) in headerBarUrls' :href="item.url"><i :class="item.iconClass"></i>{{item.name}}</a>
      </div>
    </div>
    <!-- 主体内容路由占位符 -->
    <router-view/>
    <!-- 尾部声名 -->
    <div class="footer " v-html="footerInfo"></div>    
    <!-- 后台接口异常时处理判断 -->
    <div  v-if="footerInfo === ''"  class="footer ">
      <p><a href="https://tobeshrek.com">小站</a>  &nbsp;&nbsp;&nbsp;&nbsp;  <a href="https://tobeshrek.com">@tobeshrek.com</a>
      <font color="white">  Made By </font> 
      <a href="https://tobeshrek.com"> tobeshrek</a>
      <br>
      <a  href="http://beian.miit.gov.cn/" target="_blank">备案号：赣ICP备19013362号-1</a>
      </p>
    </div>
  </div>
</template>

<script>
export default {
  
  created(){
    // 获取footer处的HTML片段信息
    //this.getFooterInfo()
    // 继承vue对象
    const that = this 
    this.changeStyleByScreenWidth()
    // 将根据屏幕宽度改变样式函数挂载到window对象上
    window.onresize = () => {
      that.changeStyleByScreenWidth()
    }
  },
  // 挂载数据
  mounted(){
    
  },
  // 计算函数
  computed:{
  },
  data(){
    return {
      // 后台状态标识符
      apiStatusFlag: false,
      // logo文本
      logo:'Tobeshrek',
      // 顶部导航栏数据
      headerUrls:[
        {url:'/', iconClass:'fa fa-home', name:'首页'},
        {url:'/essay', iconClass:'fa fa-refresh', name:'动态'},
        {url:'/article', iconClass:'fa fa-server', name:'文章'},
        {url:'/tags', iconClass:'fa fa-tags', name:'标签墙'},
        {url:'/archive', iconClass:'fa fa-archive', name:'归档'},
        {url:'/friendLinks', iconClass:'fa fa-users', name:'友人账'},
        {url:'/about', iconClass:'fa fa-user-circle', name:'关于我'},
        {url:'http://tool.tobeshrek.com', iconClass:'fa fa-wrench', name:'工具箱'},
        {url:'/login', iconClass:'fa fa-cog', name:'登陆'}
      ],
      // 顶部导航栏下拉菜单数据
      headerBarUrls:[
        {url:'/essay', iconClass:'fa fa-refresh', name:'动态'},
        {url:'/article', iconClass:'fa fa-server', name:'文章'},
        {url:'/tags', iconClass:'fa fa-tags', name:'标签墙'},
        {url:'/archive', iconClass:'fa fa-archive', name:'归档'},
        {url:'/friendLinks', iconClass:'fa fa-users', name:'友人账'},
        {url:'/about', iconClass:'fa fa-user-circle', name:'关于我'},
        {url:'http://tool.tobeshrek.com', iconClass:'fa fa-wrench', name:'工具箱'},
        {url:'/login', iconClass:'fa fa-cog', name:'登陆'},
      ],
      // 尾部HTML代码
      footerInfo: ``
    }
  },
  methods:{
    // 获取footer处的HTML片段信息
    async getFooterInfo(){
      const {data: res} = await this.$http.get(`setting/footer`);
      console.log(res)
      if( res.meta.status !== 200) return this.$message.error('后台接口异常，返回信息：'+res.meta.msg)
      // else this.$message.success(res.meta.msg)
      this.footerInfo = res.data.value
      // 修改后台接口状态
      this.apiStatusFlag = true;
    },
    // 根据屏幕宽度，调整样式
    changeStyleByScreenWidth(){
      const screenWidth = document.body.clientWidth
      if(screenWidth<1000){ //移动设备
        this.headerUrls.splice(1,7)
      }else{  //PC设备
        this.headerUrls = [
            {url:'/', iconClass:'fa fa-home', name:'首页'},
            {url:'/essay', iconClass:'fa fa-refresh', name:'动态'},
            {url:'/article', iconClass:'fa fa-server', name:'文章'},
            {url:'/tags', iconClass:'fa fa-tags', name:'标签墙'},
            {url:'/archive', iconClass:'fa fa-archive', name:'归档'},
            {url:'/friendLinks', iconClass:'fa fa-users', name:'友人账'},
            {url:'/about', iconClass:'fa fa-user-circle', name:'关于我'},
            {url:'http://tool.tobeshrek.com', iconClass:'fa fa-wrench', name:'工具箱'},
            {url:'/login', iconClass:'fa fa-cog', name:'登陆'}
          ]
      }
    },
    // 切换状态
    switchHeaderStatus(){
      // document.getElementsByClassName('bars')[0].style.display = "inline";
      const display = document.getElementsByClassName('bars')[0].style.display
      if(display === 'none'){
        document.getElementsByClassName('bars')[0].style.display = "inline"
      }else{
        document.getElementsByClassName('bars')[0].style.display = "none"
      }
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
