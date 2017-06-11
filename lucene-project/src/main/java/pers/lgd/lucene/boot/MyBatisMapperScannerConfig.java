//package pers.lgd.lucene.boot;
//
//import org.mybatis.spring.mapper.MapperScannerConfigurer;
//import org.springframework.boot.autoconfigure.AutoConfigureAfter;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
///**
// * Describe:
// *
// * @author: liguodong
// * @datetime: 2017/6/10 23:17
// */
//@Configuration
//// 因为这个对象的扫描，需要在MyBatisConfig的后面注入，所以加上下面的注解
//@AutoConfigureAfter(MyBatisConfig.class)
//public class MyBatisMapperScannerConfig {
//    @Bean
//    public MapperScannerConfigurer mapperScannerConfigurer() {
//        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
//        //获取之前注入的beanName为sqlSessionFactory的对象
//        mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
//        //指定xml配置文件的路径
//        mapperScannerConfigurer.setBasePackage("com.framework.msg.mapper");
//        return mapperScannerConfigurer;
//    }
//}
