# docker-http-fileserver

基于Spring Boot实现本地文件上传，前端使用 vue 和 iview 实现上传测试页面。

## Docker打包

`cd`到项目目录下执行`mvn`打包

```shell 
mvn clean package -DskipTests=true
```

同目录下执行`docker build`

```shell 
docker build --no-cache -t http-fileserver .
```

#### 参考：
[github-spring-boot-demo-upload](https://github.com/xkcoding/spring-boot-demo/tree/master/spring-boot-demo-upload)