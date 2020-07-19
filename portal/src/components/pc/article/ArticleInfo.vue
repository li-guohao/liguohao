<template>
    <div class="root" id="root">
        <div class="main" >
            <div class="articleInfo " >
                <el-row :gutter='20'>
                    <!-- 左边文章区域 -->
                    <el-col :span="20" >
                        <div class="part mouseOverBoxShade">
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
                            <span class="label label-zan"><i class="fa fa-calendar"></i> {{article.updateTime | formatDate}}</span>
                            <span class="label label-zan"><i class="fa fa-tags"></i> 
                                <span v-for="tag in article.tags" :key="tag.tid">{{tag.name}}</span>
                            </span>
                        </div>
                        <!-- 简介 -->
                        <div class="info"> 
                          <p>{{article.description}}</p>
                        </div> 
                        <!-- 文章封面 -->
                        <img :src="article.thumbnail" width="100%">
                    <!-- 内容 -->
                    <mavon-editor class="mavonEditor" 
                        :value="article.content"
                        :subfield = "false"
                        :defaultOpen = "'preview'"
                        :toolbarsFlag = "false"
                        :editable="false"
                        :scrollStyle="true"
                        :ishljs = "true"
                        >
                    </mavon-editor>
                    </div>
                    </el-col>
                    <!-- 右边页面内快速定位栏 -->
                    <el-col :span="4">
                        <!-- <div class="right" >
                            <h2><font color="black">右边快捷导航区域 </font></h2>
                        </div> -->
                    </el-col>
                </el-row>
            </div>
            <!-- 右边页面内快速定位栏 -->
            <div class="right" >
                <a href="#root">返回顶部</a>
            </div>
        </div>
    </div>
</template>

<script>
import aplayer from 'vue-aplayer'

export default {
    // 组件
    components: {
        aplayer
    },
    filters: {
        // 日期格式化日
        formatDate(time) {
            return time.substring(0,10)
        }
    },
    data(){
        return{
            // 文章对象
            article:{}
        }
    },
    created(){
        //初始化获取文章数据
        this.getArticleData()
    },
    // 数据更新后
    updated(){
        console.log("数据更新了")
        // 更新快速定位数据
        this.updateQuickPosition()
    },
    methods:{
        //获取文章数据
        async getArticleData(){
            // 获取文章列表组件传递过来的aid
            var aid = this.$route.params.aid;
            console.log(aid)
            if(aid === null) {
                this.$message.error('未获取到文章ID，系统将立刻跳转到文章列表页面。')
                this.$router.push('/article')
            }else{
                const {data:res} = await this.$http.get(`blog/article/${aid}`)
                console.log(res)
                if(res.meta.status !== 200) return this.$message.error('后台接口异常，获取资源失败！返回信息：'+res.meta.msg)
                // 给页面文章数据赋值
                this.article = res.data
            }
        },
        // 跳转到文章详情页
        toArticleInfo(aid){
            console.log('查询的文章AID为：'+aid)
            // 往session中存入对象
            window.sessionStorage.setItem('articleSearchAid',aid)
            window.location.reload()
        },
        // 更新快速定位数据
        async updateQuickPosition(){
            var hArr =  document.querySelectorAll('h1,h2')
            console.log(hArr)
            for(var i=0;i<hArr.length/2;i++){ //这里不知道为什么 hArr获取的h1,h2标签数组为原来的两倍，所以这里除以2
                hArr[i].id = hArr[i].innerText
                // document.getElementsByClassName('right')[0].
                var a = document.createElement('a')
                a.href='#'+hArr[i].innerText
                a.innerText = hArr[i].innerText
                // if(hArr[i].tagName === 'h2'){  //h2
                // }else{          //h1
                    
                // }
                a.style = `
                    color: black;
                    display: block;
                    margin: 5px;
                `
                // 添加子元素
                document.getElementsByClassName('right')[0].appendChild(a)
            }
        }
    }
}
</script>

<style lang="less" scoped>

.articleInfo{
    width: 90%;
    margin-top: 5px;
    margin-left: 5%;
    text-align: center;
}

// 右边页面内快速定位栏
.right{
    width: 15%;
    max-height: 85%;
    position: fixed;
    right:5px;
    top:10%;
    background-color: whitesmoke;
    margin: 5px;
    padding: 0;
    border-radius: 5px;
    background-color: rgba(245, 245, 245, 0.5);
    transition: all 0.5s ease-in-out; /* 缓慢变化效果 */
    overflow :auto
}


a{
    color: black;
    display: block;
    text-align: center;
    margin: 5px;
    transition: all 0.5s ease-in-out; /* 缓慢变化效果 */
    
}

a:hover, a:focus {
    color: skyblue;
    background-color: slateblue;
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
// 标题
.title{
    font-size: 30px;
    color: rgba(8, 80, 189, 0.7);
    transition: all 0.5s ease-in-out; /* 缓慢变化效果 */
}

.part {
    img {
        margin-top: 5px;
        width: 100%;
    }
}
</style>
