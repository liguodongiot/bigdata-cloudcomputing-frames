

### 手动打包到本地Maven仓库
```
mvn install:install-file -Dfile=D:\jar\commons-lang-2.6.0.redhat-6.jar -DgroupId=commons-lang -DartifactId=commons-lang -Dversion=2.6.0.redhat-6 -Dpackaging=jar -DgeneratePom=true -DcreateChecksum=true

mvn install:install-file -Dfile=D:\firefox\commons-lang-2.6.0.redhat-6.jar -DgroupId=commons-lang -DartifactId=commons-lang -Dversion=2.6.0.redhat-6 -Dpackaging=jar -DgeneratePom=true -DcreateChecksum=true
```

### deploy到远程仓库
```
mvn clean package deploy
```
