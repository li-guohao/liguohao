<template>
  <div class="root">
    <div class="main">
      <el-container class="home-container " >
      <!-- 头部区域 -->
      <el-header>
        <div>
          <span>shrek个人站点后台</span>
        </div>
        <font>尊敬的：{{user.nickname}} 欢迎您访问后台管理界面</font>
        <el-button  type="info" @click="logout">退出</el-button>
      </el-header>
      <!-- 页面主体区域 -->
      <el-container>
        <!-- 侧边栏 -->
        <el-aside :width="isCollapse ? '64px' : '200px'">
          <div class="toggle-button" @click="toggleCollapse">|||</div>
          <!-- 侧边栏菜单区域 -->
          <el-menu background-color="whitesmoke" text-color="black" active-text-color="#409EFF" unique-opened :collapse="isCollapse" :collapse-transition="false" router :default-active="activePath">
            <!-- 一级菜单 -->
            <el-submenu :index="item.id + ''" v-for="item in menulist" :key="item.id">
              <!-- 一级菜单的模板区域 -->
              <template slot="title">
                <!-- 图标 -->
                <i :class="iconsObj[item.id]"></i>
                <!-- 文本 -->
                <span>{{item.authName}}</span>
              </template>

              <!-- 二级菜单 -->
              <el-menu-item :index="'/' + subItem.path" v-for="subItem in item.children" :key="subItem.id" @click="saveNavState('/' + subItem.path)">
                <template slot="title">
                  <!-- 图标 -->
                  <i class="el-icon-tickets"></i>
                  <!-- 文本 -->
                  <span>{{subItem.authName}}</span>
                </template>
              </el-menu-item>
            </el-submenu>
          </el-menu>
        </el-aside>
        <!-- 右侧内容主体 -->
        <el-main>
          <!-- 路由占位符 -->
          <router-view @childToParentAvtivePath=childChangeAvtivePath></router-view>
        </el-main>
      </el-container>
    </el-container>
    </div>
  </div>
  
</template>

<script>

export default {
  data() {
    return {
      // 用户信息数据
      user:{},
      // 左侧菜单数据
      menulist: [
        {
            "id": 1,
            "authName": "文章管理",
            "path": "users",
            "children": [
                {
                    "id": 11,
                    "authName": "编辑文章",
                    "path": "manager/article/edit",
                    "children": [],
                    "order": null
                },
                {
                    "id": 12,
                    "authName": "所有文章",
                    "path": "manager/article/list",
                    "children": [],
                    "order": null
                },
                {
                    "id": 14,
                    "authName": "所有评论",
                    "path": "manager/comment/list",
                    "children": [],
                    "order": null
                },
                {
                    "id": 15,
                    "authName": "标签管理",
                    "path": "manager/tag/manager",
                    "children": [],
                    "order": null
                }
            ],
            "order": 1
        },
        {
            "id": 2,
            "authName": "文件管理",
            "path": "users",
            "children": [
                {
                    "id": 21,
                    "authName": "图片管理",
                    "path": "manager/file/img",
                    "children": [],
                    "order": 1
                }
                // ,
                // {
                //     "id": 22,
                //     "authName": "音频管理",
                //     "path": "manager/file/music",
                //     "children": [],
                //     "order": 2
                // },
                // {
                //     "id": 23,
                //     "authName": "视频管理",
                //     "path": "manager/file/video",
                //     "children": [],
                //     "order": 3
                // }
                
            ],
            "order": 1
        },
        {
            "id": 3,
            "authName": "系统管理",
            "path": "users",
            "children": [
                {
                    "id": 31,
                    "authName": "个人中心",
                    "path": "manager/user/userInfo",
                    "children": [],
                    "order": null
                },
                {
                    "id": 32,
                    "authName": "系统设定",
                    "path": "manager/system/option",
                    "children": [],
                    "order": null
                }
                ,
                {
                    "id": 33,
                    "authName": "导航链接",
                    "path": "manager/system/links",
                    "children": [],
                    "order": null
                }
            ],
            "order": 3
        }
      ],
      iconsObj: {
        '125': 'iconfont icon-user',
        '103': 'iconfont icon-tijikongjian',
        '101': 'iconfont icon-shangpin',
        '222': 'iconfont icon-danju',
        '1222': 'iconfont icon-baobiao',
        '1': 'el-icon-menu',
        '2': 'el-icon-menu',
        '3': 'el-icon-setting'
      },
      // 是否折叠
      isCollapse: false,
      // 被激活的链接地址
      activePath: ''
    }
  },
  created() {
    // this.getMenuList()
    this.activePath = window.sessionStorage.getItem('activePath')
    this.user = JSON.parse(window.sessionStorage.getItem('user'))
  },
  methods: {
    logout() {
      window.sessionStorage.clear()
      this.$router.push('/')
    },
    // // 获取所有的菜单
    // async getMenuList() {
    //   const { data: res } = await this.$http.get('menus')
    //   if (res.meta.status !== 200) return this.$message.error(res.meta.msg)
    //   this.menulist = res.data
    //   console.log(res)
    // },
    // 点击按钮，切换菜单的折叠与展开
    toggleCollapse() {
      this.isCollapse = !this.isCollapse
    },
    // 保存链接的激活状态
    saveNavState(activePath) {
      window.sessionStorage.setItem('activePath', activePath)
      this.activePath = activePath
    },
    // 子组件改变父组件链接激活状态值
    childChangeAvtivePath(data){this.activePath=data}
  }
}
</script>

<style lang="less" scoped>
.home-container {
  min-height: 800px;
  height: auto;
}
.el-header {
  background-color: whitesmoke;
  display: flex;
  justify-content: space-between;
  padding-left: 0;
  align-items: center;
  color: black;
  font-size: 20px;
  border: 1px solid white;
  > div {
    display: flex;
    align-items: center;
    span {
      margin-left: 15px;
    }
  }

  .el-button{
    background-color: white;
    color: black;
  }

}

.el-aside {
  background-color: whitesmoke;
  border: 1px solid white;
  .el-menu {
    border-right: none;
  }
}

.el-main {
  background-color: whitesmoke;
}

.iconfont {
  margin-right: 10px;
}

.toggle-button {
  background-color: whitesmoke;
  font-size: 10px;
  line-height: 24px;
  color: black;
  text-align: center;
  letter-spacing: 0.2em;
  cursor: pointer;
}
</style>
