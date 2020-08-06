# 前台门户文档

安装依赖
```
npm install
```

运行服务
```
npm run serve
```

编译项目
```
npm run build
```

编译好后的文件(dist文件夹里的) 可直接放在nginx站点根目录下运行

## 注意
如运行服务时报错如下
```
Cannot find module '@babel/compat-data/corejs3-shipped-proposals'
```

则执行
```
npm install @babel/compat-data
```
