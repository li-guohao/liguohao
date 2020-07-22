<template>
    <div class="root">
        <div class="main">
            <!-- 登陆盒子 -->
          <div class="login-box ">
              <!-- 头像区域 -->
              <div class="avatar_box">
                  <img  src="https://file.liguohao.cn/api/v3/file/get/382/avatar.jpg?sign=LY43OZ_YtgTi1Nu3Lj-NGhcf3Iz9MOQ-vD6Og4Hnk_E%3D%3A0" alt="">
              </div>
              <!-- 登录表单区域 -->
              <el-form :model="loginForm" ref="loginFormRef" :rules="loginFormRules"   label-width="0"  class="login-form" >
                <!-- 用户名 -->
                <el-form-item prop="email">
                  <el-input placeholder="请输入邮箱" v-model="loginForm.email"  prefix-icon="fa fa-user"></el-input>
                </el-form-item>
                <!-- 密码 -->
                <el-form-item prop="password">
                  <el-input placeholder="请输入密码" v-model="loginForm.password"  prefix-icon="fa fa-lock" type="password"></el-input>
                </el-form-item>
                <!-- 按钮区域 -->
                <el-form-item class="btns">
                  <el-button type="success" icon="fa fa-qq" @click="QQLogin" >QQ登录</el-button>
                  <el-button type="primary"  @click="login">登录</el-button>
                  <!-- <el-button type="info" @click="resetLoginForm">重置</el-button> -->
                </el-form-item>
              </el-form>
             
          </div>
        </div>
      
        
    </div>
</template>

<script>
export default {
  name: 'Login',
  data() {
      return {
        // 用户表单
        loginForm:{
          username: '',
          password: ''
        },
        // 这是表单的验证规则对象
        loginFormRules: {
          // 验证邮箱是否合法
          email: [
            { required: true, message: '请输入邮箱地址', trigger: 'blur' },
            {
              type: 'email',//要检验的类型（number，email，date等）
              message: '请输入正确的邮箱地址',
              trigger: ['blur', 'change']
            }
          ],
          // 验证密码是否合法
          password: [
            { required: true, message: '请输入登录密码', trigger: 'blur' },
            { min: 3, max: 15, message: '长度在 3 到 15 个字符', trigger: 'blur' }
          ]
        }
      }
  },
  methods: {
    // 重置表单
    resetLoginForm(){
      this.$refs.loginFormRef.resetFields()
      this.$message.success('表单属性已经重置')
    },
    // 用户登陆
    login(){
      // 校验表单
      this.$refs.loginFormRef.validate(async valid => {
        // 检验未通过
        if (!valid) return
        // 校验通过
        const { data: res } = await this.$http.post('system/user/login', this.loginForm)
        console.log(this.loginForm)
        console.log(res)
        if (res.meta.status !== 200) return this.$message.error('登录失败！')
        this.$message.success('登录成功')
        // 1. 将登录成功之后的 token，保存到客户端的 sessionStorage 中
        //   1.1 项目中出了登录之外的其他API接口，必须在登录之后才能访问
        //   1.2 token 只应在当前网站打开期间生效，所以将 token 保存在 sessionStorage 中
        window.sessionStorage.setItem('token', res.data.token)
        window.sessionStorage.setItem('UID',res.data.uid)
        // 往session中储存用户信息
        window.sessionStorage.setItem('user',JSON.stringify(res.data))
        // 2. 通过编程式导航跳转到后台主页，路由地址是 /home
        this.$router.push('/manager')
      })
    },
    // 通过QQ登陆
    QQLogin(){
      var url = 'https://graph.qq.com/oauth2.0/authorize?response_type=code&client_id=101889588&redirect_uri=https%3A%2F%2Fapi.liguohao.cn%2Fsystem%2Fuser%2Fqq%2Fcallback&state=api.liguohao.cn&scope=get_user_info';
      window.location=url;
    }
  }
}
</script>

<style lang="less" scoped>
// scoped 限定样式当前组件有效
.bgImg{
    margin: 0;
    padding: 0;
    width: 100%;
}

// 登陆盒子 
.login-box{
  width: 450px;
  height: 360px;
  background-color: #fff;
  border-radius: 3px;
  position: absolute;
  left: 50%;
  top: 50%;
  transform: translate(-50%, -50%);
  border-radius: 10px;


  // 头像区域
  .avatar_box{
    height: 120px;
    width: 120px;
    border: 1px solid #eee;
    margin-left: 50%;
    border-radius: 50%;
    padding: 10px;
    box-shadow: 0 0 10px #ddd;
    transform: translate(-50%);
    background-color: #fff;
    img {
      width: 100%;
      height: 100%;
      border-radius: 50%;
      background-color: #eee;
    }
  }

  // 登录表单区域
  .login-form{
    position: absolute;
    bottom: 0;
    width: 100%;
    padding: 0 20px;
    box-sizing: border-box;
    
    .el-form-item{
      display: block;
    }
    
    // 表单项
    .btns {
      display: flex;
      justify-content: flex-end;
    }

  }

  // 其它登陆
  .otherLogin{
    width: 100%;
    display: inline;
  }

}
</style>
