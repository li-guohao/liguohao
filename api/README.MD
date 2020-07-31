# 后台接口栈文档

## 官方仓库镜像构建

### 1.本地编译项目构建到远程仓库

参考文章：[SpringBoot部署项目到远程Docker仓库](https://blog.csdn.net/qq_36850813/article/details/92835885)

docker远程仓库需要开发端口 `2375`
``` cmd
mvn clean package  docker:build
```


### 2.远程仓库推送到官方仓库
``` shell
# 登陆Hub
docker login --username=username --password=password
# 推送到官方仓库
docker push liguohaocn/api
``` 

## 项目部署
``` shell
# 拉取镜像
docker pull liguohaocn/api:latest
# 运行容器
docker run --detach --name api --network=host \
    -p 8888:8888 \
    --env spring.datasource.username="root" \
    --env spring.datasource.password="shrek666" \
    --env spring.datasource.driverClassName="com.mysql.cj.jdbc.Driver" \
    --env spring.datasource.url="jdbc:mysql://127.0.0.1:3306/liguohao?useSSL=false&characterEncoding=utf8&serverTimezone=Asia/Shanghai" \
    --restart=always \
    docker.io/liguohaocn/api
```

# 参数说明
* spring.datasource.username 数据库用户名
* spring.datasource.password 数据库密码
* spring.datasource.driverClassName 指定数据库驱动类为MySQL
* spring.datasource.url 数据库连接URL
* restart=always 开机启动容器

其中`数据库连接URL`中需要指定数据库名称，在 `3306/` 后面