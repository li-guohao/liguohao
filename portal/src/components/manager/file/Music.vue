<template>
    <div class="root">
        <el-card class="box-card">
            <div slot="header" class="clearfix">
                 <!-- 面包屑导航区域 -->
                <el-breadcrumb separator-class="el-icon-arrow-right">
                <el-breadcrumb-item :to="{ path: '/index' }">首页</el-breadcrumb-item>
                <el-breadcrumb-item>文件管理</el-breadcrumb-item>
                <el-breadcrumb-item>音频管理</el-breadcrumb-item>
                </el-breadcrumb>
                
            </div>
            <!-- 音乐播放器 -->
            <div  style="width:300px" >
                <aplayer :autoplay='false' :listFolded="true" controls :volume='0.01' :float='false' :mini='false'
                    repeat='repeat-all'
                    :music="{
                        title: 'secret base~君がくれたもの~',
                        artist: 'Silent Siren',
                        src: 'https://moeplayer.b0.upaiyun.com/aplayer/secretbase.mp3',
                        pic: 'https://moeplayer.b0.upaiyun.com/aplayer/secretbase.jpg'
                    }"
                />
            </div>
            
            
        </el-card>
    </div>
</template>

<script>
import aplayer from 'vue-aplayer'

export default {
    // 组件
    components: {
        aplayer
    },
}
</script>

<style lang="less" scoped>

</style>
