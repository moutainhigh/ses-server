//package com.redescooter.ses.web.ros.config;
//
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.env.EnvironmentPostProcessor;
//import org.springframework.core.env.ConfigurableEnvironment;
//import org.springframework.core.env.MutablePropertySources;
//import org.springframework.core.env.PropertiesPropertySource;
//import org.springframework.core.io.FileSystemResource;
//import org.springframework.core.io.support.PropertiesLoaderUtils;
//
//import java.io.File;
//import java.io.IOException;
//import java.util.Properties;
//
///**
// * @ClassNameLocalSettingsEnvironmentPostProcessor
// * @Description
// * @Author aleks
// * @Date2020/6/1 13:30
// * @Version V1.0
// **/
//public class LocalSettingsEnvironmentPostProcessor implements EnvironmentPostProcessor {
//    /**
//     * 第一个配置文件路径为部署环境路径，用于部署环境加载配置文件里的值
//     * 第二个配置文件路径为本地打包环境路径，解决打包时报错问题
//     */
//    private String[] LOCATIONS   = {"/root/java_service/keyFile/jasypt.properties","C:\\Users\\jasypt.properties"};
//
//    @Override
//    public void postProcessEnvironment(ConfigurableEnvironment configurableEnvironment, SpringApplication springApplication) {
//        String osName = System.getProperty("os.name");
//        if(osName.startsWith("Mac OS")){
//            //如果是Mac电脑，用下面的这个(Mac没有所谓的C盘D盘，而且路劲的写法也不一样)
//            LOCATIONS  = new String[]{"/root/java_service/keyFile/jasypt.properties", "/Users/jasypt.properties"};
//        }
//        for(String fileLocation :  LOCATIONS){
//            File file = new File(fileLocation);
//            if (file.exists()) {
//                MutablePropertySources propertySources = configurableEnvironment.getPropertySources();
//                Properties properties = loadProperties(file);
//                propertySources.addFirst(new PropertiesPropertySource("Config", properties));
//                return ;
//            }
//        }
//    }
//
//    private Properties loadProperties(File f) {
//        FileSystemResource resource = new FileSystemResource(f);
//        try {
//            return PropertiesLoaderUtils.loadProperties(resource);
//        }
//        catch (IOException ex) {
//            throw new IllegalStateException("Failed to load local settings from " + f.getAbsolutePath(), ex);
//        }
//    }
//}
