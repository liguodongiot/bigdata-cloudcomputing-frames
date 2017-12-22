
### 手动打包到本地 Maven 仓库
```
mvn install:install-file -Dfile=D:\jar\commons-lang-2.6.0.redhat-6.jar -DgroupId=commons-lang -DartifactId=commons-lang -Dversion=2.6.0.redhat-6 -Dpackaging=jar -DgeneratePom=true -DcreateChecksum=true

mvn install:install-file -Dfile=D:\\jave-1.0.2.jar -DgroupId=it.sauronsoftware.jave -DartifactId=sauronsoftware -Dversion=1.0.2 -Dpackaging=jar

```

### 上传到私服
```
mvn deploy:deploy-file -Dfile=D:\\jave-1.0.2.jar -DgroupId=it.sauronsoftware.jave -DartifactId=sauronsoftware -Dversion=1.0.2 -Durl=http://xxxx/nexus/content/repositories/releases/ -Dpackaging=jar -DrepositoryId=xxxx-releases
-Dfile:文件路径
-DgroupId：groupId
-DartifactId：artifactId
-Dversion:version
-Durl:要提交到私库的url
-Dpackaging：jar
-DrepositoryId：仓库id号，就是settings.xml中给私库设置的id号


```

### deploy 到远程仓库
```
mvn clean package deploy
```

### 打包，忽略测试
```
mvn clean package -Pdev  -Dmaven.test.skip=true
```

### 查看依赖树
```
mvn dependency:tree > tree.log
```





