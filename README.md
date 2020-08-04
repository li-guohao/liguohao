# 小豪的个人网站

* 站点：[https:liguohao.cn](https:liguohao.cn)
* 代码托管：[https://github.com/li-guohao/liguohao](https://github.com/li-guohao/liguohao)
* 视频演示: [BV1o54y1U7Ra](https://bilibili.com/video/BV1o54y1U7Ra)
* portal: 前台门户-[文档](portal/README.MD)
* api: 后台接口-[文档](api/README.MD)
* tool: 工具箱栈-[文档](tool/README.MD)


# 设计

## 总览
- 文件储存：计划支持七牛云（优先）、又拍云、WebDAV、添加本地文件夹、阿里OSS、腾讯COS、
- 用户模块：支持邮箱登陆，支持自己配置SMTP发件服务器，支持QQ登陆
- 文章模块：博客功能，允许评论，有标签，有根据标签分类功能。

## V1版本

- 文件储存：七牛云存储
- 用户模块：支持邮箱登陆，
- 文章模块：博客功能，有标签

## V2版本

- 用户模块：支持QQ登陆
- 文章模块：允许评论（QQ用户登陆后） 添加分享功能，同步QQ空间好友评论


## V3版本
- 文章模块：连接[黑客派](https://hacpai.com/member/liguohao)，同步黑客派用户评论
- 用户模块：支持自己配置SMTP发件服务器，用于找回密码
- 用户模块：支持QQ快速注册




# 技术栈

- 数据库：MySql
- SpringBoot
- SpringDataJpa(Hinernate)
- SpringMVC
- Vue
- Element-UI

# 进度

## 2020-07-20
- 总的来讲 V1版本基本完成
- 文件模块实现了七牛云存储 ，只需要后台配置对应的信息就OK了。
- 前台显示优化了一些细节，去掉了移动端适配。
- 基本上大部分的网站信息都可以在后台修改
- 添加了后台文章的置顶功能，保存到草稿功能，草稿文章不会在前台显示
- 置顶功能只允许置顶一篇文章
- 首页只会显示最新更新的6篇文章

## 2020-07-22
- 实现QQ第三方登陆
- 优化首页meta

## 2020-07-24
- 配置好后台接口docker部署
- 移除邮箱密码登陆

# 2020-07-24

* 鄙人自觉知识不足，去复习基础和学习了，项目暂时停止，一定不会放弃的，后面会重启。



