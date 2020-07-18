<template>
    <div class="root">
        <el-card class="box-card">
            <div slot="header" class="clearfix">
                 <!-- 面包屑导航区域 -->
                <el-breadcrumb separator-class="el-icon-arrow-right">
                <el-breadcrumb-item :to="{ path: '/index' }">首页</el-breadcrumb-item>
                <el-breadcrumb-item>系统管理</el-breadcrumb-item>
                <el-breadcrumb-item>系统设置</el-breadcrumb-item>
                </el-breadcrumb>
                
            </div>
            <el-table stripe  
                :data="optionListData.dataArray"
                style="width: 100%">
                <!-- checkbox -->
                <el-table-column
                    type="selection"
                    width="55">
                </el-table-column>
                <!-- 系统ID -->
                <!-- <el-table-column sortable
                    prop="oid"
                    label="ID"
                    width="80">
                </el-table-column> -->
                <!-- 描述 -->
                <el-table-column
                    prop="optionDesc"
                    label="描述"
                    width="180"
                    >
                    <template slot-scope="scope">
                        <el-input 
                        type="textarea"
                        :rows="2"
                        placeholder="请输入此项系统设置的描述信息"
                        v-model="scope.row.optionDesc" @blur="updateOption(scope.row)">
                        </el-input>
                    </template>
                </el-table-column>
                <!-- 名称 -->
                <el-table-column
                    prop="optionName"
                    label="名称"
                    width="180">
                </el-table-column>
                <!-- 分类 -->
                <el-table-column
                    prop="optionCategory"
                    label="分类"
                    width="180">
                </el-table-column>
                <!-- 设置值 -->
                <el-table-column
                    prop="optionName"
                    label="设置值"
                    >
                    <template slot-scope="scope">
                        <el-input 
                        type="textarea"
                        :rows="4"
                        placeholder="请输入设置值"
                        v-model="scope.row.optionValue" @blur="updateOption(scope.row)">
                        </el-input>
                    </template>
                </el-table-column>
                
              
            </el-table>
            <br>
            <!-- 分页 -->
            <el-pagination background
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
            :current-page="optionListData.currentPage"
            :page-sizes="[2, 5, 10, 20, 40]"
            :page-size="optionListData.pageSize"
            layout="total, sizes, prev, pager, next, jumper"
            :total="optionListData.total">
            </el-pagination>
        </el-card>
    </div>
</template>
<script>
export default {
    data(){
        return {
            // 系统设置数组
            optionListData:{
                total:0,
                currentPage:1,
                pageSize:5,
                dataArray:[]
            },
            // 系统设置对象
            option:{
                optionId:'',
                optionDesc: '',
                optionCategory:'',
                optionName:'',
                optionValue:''
            }
        }
    },
    created(){
        // 获取所有系统设置数据
        this.getOptionListData()
    },
    methods:{
        // 获取所有系统设置数据
        async getOptionListData(){
            const {data: res} = await this.$http.get(`system/option/list/${this.optionListData.currentPage}/${this.optionListData.pageSize}`)
            console.log(res)
            if(res.meta.status !== 200) return this.$message.error('后台接口异常，返回信息：'+res.meta.msg)
            else this.$message.success(res.meta.msg)
            this.optionListData = res.data
        },
        // 分页-每页显示条数改变
        handleSizeChange(val){
            this.optionListData.pageSize = val
            this.getOptionListData()
        },
        // 分页-当前页改变
        handleCurrentChange(val){
            this.optionListData.currentPage = val
            this.getOptionListData()
        },
        // 保存系统信息
        async save(){
            const {data: res} = await this.$http.post(`system/option/save`,this.option)
            console.log(res)
            if(res.meta.status !== 200) return this.$message.error('后台接口异常，返回信息：'+res.meta.msg)
            else this.$message.success(res.meta.msg)
            this.setting = {}
            this.addDialogVisible = false;
            this.getOptionListData()
        },
        // 更新列表系统信息
        async updateOption(option){
            console.log(option)
            const {data: res} = await this.$http.post(`system/option/save`,option)
            console.log(res)
            if(res.meta.status !== 200) return this.$message.error('后台接口异常，返回信息：'+res.meta.msg)
            else this.$message.success(res.meta.msg)
            this.getOptionListData()
        }
    }
}
</script>


<style lang="less" scoped>
.el-button{
    float: left;
    margin-top: 5px;
}

</style>