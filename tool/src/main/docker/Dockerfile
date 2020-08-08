FROM java:8
#维护者
MAINTAINER li-guohao  847486306@qq.com
# 定义数据卷位置---存放容器共享文件的位置
VOLUME /home/tmp
ADD *.jar app.jar
RUN bash -c 'touch /app.jar'
#指定配置容器启动后执行的命令
ENTRYPOINT ["java","-jar","/app.jar"]