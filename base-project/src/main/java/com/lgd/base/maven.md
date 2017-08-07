
### 手动打包到本地 Maven 仓库
```
mvn install:install-file -Dfile=D:\jar\commons-lang-2.6.0.redhat-6.jar -DgroupId=commons-lang -DartifactId=commons-lang -Dversion=2.6.0.redhat-6 -Dpackaging=jar -DgeneratePom=true -DcreateChecksum=true
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