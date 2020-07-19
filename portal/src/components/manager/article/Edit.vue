<template>
    <div class="root">
        <el-card class="box-card">
            <div slot="header" class="clearfix">
                 <!-- 面包屑导航区域 -->
                <el-breadcrumb separator-class="el-icon-arrow-right">
                <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
                <el-breadcrumb-item>文章管理</el-breadcrumb-item>
                <el-breadcrumb-item>编辑文章</el-breadcrumb-item>
                </el-breadcrumb>
            </div>
            <!-- 文章添加表单 -->
            <el-form ref="articleFormRef" 
                    :model="articleForm" label-width="0">
                <!-- 标题 -->
                <el-form-item  prop="title">
                    <el-input v-model="articleForm.title" placeholder="请输入文章标题"></el-input>
                </el-form-item>
                <!-- 描述 -->
                <el-form-item prop="description">
                    <el-input v-model="articleForm.description" placeholder="请输入文章简介"></el-input>
                </el-form-item>
                <!-- 首页图Url -->
                <el-form-item prop="thumbnail">
                    <el-input v-model="articleForm.thumbnail" placeholder="请输入文章首页图片URL"></el-input>
                </el-form-item>
                
                <!-- 富文本编辑器 的 文章内容区域 -->
                <mavon-editor v-model="articleForm.content"  ref=md @imgAdd="$imgAdd" @imgDel="$imgDel"
                style="min-height:500px" :toolbars="toolbars"/>

                <!-- 标签 -->
                <el-select class="articleTags" v-model="articleTagsNameArr"  multiple filterable allow-create default-first-option placeholder="请选择文章标签">
                    <el-option v-for="dataTag in dataTags" :key="dataTag.tid" :label="dataTag.name" :value="dataTag.name">
                    </el-option>
                </el-select>
                
                <!-- 按钮区域 -->
                <el-form-item class="btns">
                    <el-button type="primary"  @click="articleFromCommit(0)" >草稿</el-button>
                    <el-button type="info" @click="articleFromCommit(1)" >发布</el-button>
                </el-form-item>
            </el-form>
        </el-card>


        

    </div>
</template>

<script>

export default {
    name: 'ArticleAdd',
    data() {
        return {
            // 标签数据
            dataTags: [
            ],
            // 文章数据表单
            articleForm: {
                title: '',
                description: '',
                content: '',
                status: 2,   //默认文章保存为已发布
                type: null, //默认文体为文章
                tags:[]
            },
            // 文章标签值数组
            articleTagsNameArr:[

            ],
            // 标签名数组
            tagNames:[],
            // 表单的验证规则对象
            articleFormRules: {
                title: [
                    { required: true, message: `请输入文章标题`, trigger: 'blur' },
                    { min: 3, max: 20, message: '长度在 3 到 20 个字符', trigger: 'blur' }
                ],
                description: [
                    { required: true, message: '请输入文章描述', trigger: 'blur' },
                    { min: 3, max: 50, message: '长度在 3 到 50 个字符', trigger: 'blur' }
                
                ]
            },
            // 富文本编辑器 工具栏
            toolbars: {
                bold: true, // 粗体
                italic: true, // 斜体
                header: true, // 标题
                underline: true, // 下划线
                strikethrough: true, // 中划线
                mark: true, // 标记
                superscript: true, // 上角标
                subscript: true, // 下角标
                quote: true, // 引用
                ol: true, // 有序列表
                ul: false, // 无序列表
                link: true, // 链接
                imagelink: true, // 图片链接
                code: true, // code
                table: true, // 表格
                fullscreen: true, // 全屏编辑
                readmodel: true, // 沉浸式阅读
                htmlcode: true, // 展示html源码
                help: true, // 帮助
                /* 1.3.5 */
                undo: true, // 上一步
                redo: true, // 下一步
                trash: true, // 清空
                save: false, // 保存（触发events中的save事件）
                /* 1.4.2 */
                navigation: true, // 导航目录
                /* 2.1.8 */
                alignleft: true, // 左对齐
                aligncenter: true, // 居中
                alignright: true, // 右对齐
                /* 2.2.1 */
                subfield: true, // 单双栏模式
                preview: true, // 预览
            }
        }
    },
    // 初始化操作
    async created(){
        // 给标签数组赋值
        this.getTagList()
        // 获取session中兄弟组件设置的文章编辑对象
        const articleEditAid =  window.sessionStorage.getItem('articleEditAid')
        console.log('-------------------')
        console.log(articleEditAid)
        if(articleEditAid !== null && articleEditAid !== 'null'){        //证明是从文章列表组件传递过来的
            // 根据文章AID获取文章对象，赋值给表单对象articleForm
            const {data:res} = await this.$http.get(`blog/article/${articleEditAid}`)
            console.log(res)
            if(res.meta.status !== 200) return this.$message.error('后台接口异常，获取文章信息失败！返回信息：'+res.meta.msg)
            // res.data.tags.forEach(tag =>{
            //     this.tagNames.push(tag.name)
            // })
            this.articleForm = res.data
            // 将对象数组 转化成字符数组
            var articleEditTags = this.articleForm.tags
            this.articleForm.tags = []
            articleEditTags.forEach(tag => {
                this.articleTagsNameArr.push(tag.name)
            })
            
        }
    },
    methods: {
        // 文章表单提交
        async articleFromCommit(isOpen){
            // 给表单赋值
            this.articleForm.isOpen = isOpen
            // 将文章标签 字符数组 转化成 对象数组
            var articleTagsObjArr = []
            console.log('转化前')
            console.log(this.articleTagsNameArr)
            this.articleTagsNameArr.forEach(tagName => {
                articleTagsObjArr.push({aid:null,name:tagName})
            })
            this.articleForm.tags = articleTagsObjArr
            console.log('转化后')
            console.log(this.articleForm.tags)
            // 校验表单
            var _this = this
            this.$refs.articleFormRef.validate(async valid => {
                // 检验未通过
                if (!valid) {
                    this.$message.warning('表单校验失败，请检查是否漏了什么！')
                    return
                }
                // 校验通过,请求后端保存数据
                const {data: res} = await this.$http.post('blog/article/save', this.articleForm)
                console.log(res)
                if (res.meta.status !== 200) return this.$message.error('后台接口异常，添加文章失败！返回信息：'+res.meta.msg)
                this.$message.success(res.meta.msg)
                // 获取表单类型
                var type = this.articleForm.type
                // 重置文章表单数据 和 session内值
                this.articleForm = {
                    title: '',
                    description: '',
                    content: '',
                    isOpen: 1,   //默认文章保存为已发布
                    tags:[]
                }
                window.sessionStorage.setItem('articleEditAid',null)
                // 跳转到对应列表页面
                // console.log(type)
                // console.log(typeof type)
                this.$emit('childToParentAvtivePath','/manager/article/list')
                this.$router.push('/manager/article/list')
                
                
            })

        },
        // 获取标签数据
        async getTagList(){
            const {data: res} = await this.$http.get('blog/tag/list/1/20')
            console.log(res)
            if(res.meta.status !== 200) return this.$message.error('后台接口异常，获取标签信息失败！返回信息：'+res.meta.msg)
            this.$message.success('获取标签数据成功')
            this.dataTags = res.data.dataArray
        },
        // 测试跳转
        test(){
            this.$emit('childToParentAvtivePath','/manager/article/list')
            this.$router.push('/manager/article/list')
        },
        // 富文本编辑器图片上传
        async $imgAdd(pos, $file){
           this.$message.info('开始上传图片')
            // 第一步.将图片上传到服务器.
           var formdata = new FormData();
           formdata.append('file', $file);
           const {data: res} = await this.$http.post('file/upload',formdata);
           if(res.meta.status !== 200) return this.$message.error('后台接口异常，上传图片失败！返回信息：'+res.meta.msg)
            // 文件上传成功提示信息
            this.$message.success('文件上传成功')
            // 修改原文链接为图片直链
            this.$refs.md.$img2Url(pos, res.data.url);
        },
        // 图片移除
        async $imgDel(pos, $file){
            //this.$message.success('移除图片')
            // 并未连接后台，后面再写
        }
    }
}
</script>

<style lang="less" scoped>
    .root{

        .box-card{
            
            .el-form-item {
                position: static;
                width: auto;


            }
        }
    }
    
    

    // 表单项
    .btns {
      margin-top: 15px;
      display: block;
      float: right;
    }

    // 文章标签
    .articleTags{
        margin-top: 15px;
        width: 55%;
        display: block;
        float: left;
    }

    // 文章类型
    .articleType{
        margin-top: 15px;
        float: left;
        display: inline;
    }

    .mavon-editor{
        width: 600px;
    }
</style>