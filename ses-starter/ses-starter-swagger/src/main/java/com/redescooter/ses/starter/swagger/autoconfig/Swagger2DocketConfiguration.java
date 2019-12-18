package com.redescooter.ses.starter.swagger.autoconfig;

import com.github.xiaoymin.swaggerbootstrapui.annotations.EnableSwaggerBootstrapUI;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.redescooter.ses.starter.swagger.properties.Swagger2GroupProperties;
import com.redescooter.ses.starter.swagger.properties.Swagger2Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

@EnableSwagger2
@EnableSwaggerBootstrapUI
@SuppressWarnings("deprecation")
public class Swagger2DocketConfiguration implements BeanFactoryPostProcessor, EnvironmentAware {

    private static final Logger log = LoggerFactory.getLogger(Swagger2DocketConfiguration.class);

    private Environment environment;

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    private Swagger2Properties getSwaggerProperties() {
        try {
            Swagger2Properties existingValue = Binder.get(environment).bind(Swagger2Properties.prefix, Swagger2Properties.class).get();
            return existingValue;
        } catch (Exception e) {
            return new Swagger2Properties();
        }
    }

    private Swagger2Properties getSwagger2Properties() {
        Swagger2Properties existingValue = Binder.get(environment).bind(Swagger2Properties.prefix, Swagger2Properties.class).get();
        return existingValue;
    }

    private String[] splitBasePackages(String basePackage) {
        if (StringUtils.isEmpty(basePackage) || (basePackage = basePackage.trim()).isEmpty()) {
            return null;
        } else {
            return basePackage.split(",");
        }
    }

    private Docket getSwagger2Docket(final Swagger2GroupProperties groupProperties, final List<String> pathUrls) {
        final String[] basePackages = this.splitBasePackages(groupProperties.getBasePackage());

        //在配置好的配置类中增加此段代码即可
        //TODO 后期将其从配置文件中读取
        ParameterBuilder ticketPar = new ParameterBuilder();
        List<Parameter> pars = new ArrayList<Parameter>();
        ticketPar.name("CHP").description("Header校验")//name表示名称，description表示描述
                .modelRef(new ModelRef("string")).parameterType("header")
                .required(false).defaultValue("{\"Content-Type\":\"application/x-www-form-urlencoded\",\"clientType\":\"PC\",\"token\":\"\",\"language\":\"en\",\"timeZone\":\"GMT+0800\",\"version\":\"1.2\"}").build();//required表示是否必填，defaultvalue表示默认值
        pars.add(ticketPar.build());//添加完此处一定要把下边的带***的也加上否则不生效

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(new ApiInfoBuilder().title(groupProperties.getTitle())
                        .description(groupProperties.getDescription()).version(groupProperties.getVersion())
                        .license(groupProperties.getLicense()).licenseUrl(groupProperties.getLicenseUrl())
                        .termsOfServiceUrl(groupProperties.getTermsOfServiceUrl())
                        .contact(groupProperties.getContact().toContact()).build())
                .groupName(groupProperties.getGroupName()).pathMapping(groupProperties.getPathMapping())// 最终调用接口后会和paths拼接在一起
                .select()
                .apis(new Predicate<RequestHandler>() {
                    @Override
                    public boolean apply(RequestHandler input) {
                        if (basePackages == null)
                            return true;
                        String packageName = input.declaringClass().getName();
                        for (String basePackage : basePackages) {
                            if (packageName.startsWith(basePackage) || packageName.matches(basePackage + ".*")) {
                                return true;
                            }
                        }
                        return false;
                    }
                })
                //TODO 后期将其从配置文件中读取
                .paths(Predicates.not(PathSelectors.regex("/error.*")))
                .paths(new Predicate<String>() {
                    @Override
                    public boolean apply(String input) {
                        String pathRegex = groupProperties.getPathRegex();
                        if (StringUtils.isEmpty(pathRegex) || input.matches(pathRegex)) {
                            pathUrls.add(input);
                            return true;
                        } else {
                            return false;
                        }
                    }

                })
                .build()
                .globalOperationParameters(pars);
    }

    private Docket getOtherSwagger2Docket(final List<String> pathUrls) {
        Swagger2GroupProperties otherSwagger = otherSwagger();

        //在配置好的配置类中增加此段代码即可
        //TODO 后期将其从配置文件中读取
        ParameterBuilder ticketPar = new ParameterBuilder();
        List<Parameter> pars = new ArrayList<Parameter>();
        ticketPar.name("CHP").description("Header头")//name表示名称，description表示描述
                .modelRef(new ModelRef("string")).parameterType("header")
                .required(false).defaultValue("{\"Content-Type\":\"application/x-www-form-urlencoded\",\"clientType\":\"PC\",\"token\":\"\",\"language\":\"en\",\"timeZone\":\"GMT+0800\",\"version\":\"1.1\"}").build();//required表示是否必填，defaultvalue表示默认值
        pars.add(ticketPar.build());//添加完此处一定要把下边的带***的也加上否则不生效

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(new ApiInfoBuilder().title(otherSwagger.getTitle()).description(otherSwagger.getDescription())
                        .termsOfServiceUrl(otherSwagger.getTermsOfServiceUrl()).version(otherSwagger.getVersion())
                        .contact(otherSwagger.getContact().toContact()).license(otherSwagger.getLicense())
                        .licenseUrl(otherSwagger.getLicense()).build())
                .groupName(otherSwagger.getGroupName()).pathMapping(otherSwagger.getPathMapping())// 最终调用接口后会和paths拼接在一起
                .select().apis(new Predicate<RequestHandler>() {
                    @Override
                    public boolean apply(RequestHandler input) {
                        return input.isAnnotatedWith(GetMapping.class) || input.isAnnotatedWith(PostMapping.class)
                                || input.isAnnotatedWith(DeleteMapping.class) || input.isAnnotatedWith(PutMapping.class)
                                || input.isAnnotatedWith(RequestMapping.class);
                    }
                })
                .paths(Predicates.not(PathSelectors.regex("/error.*")))
                .paths(new Predicate<String>() {
                    @Override
                    public boolean apply(String input) {
                        return !pathUrls.contains(input);
                    }
                }).build()
                .globalOperationParameters(pars);
    }

    private Swagger2GroupProperties otherSwagger() {
        Swagger2GroupProperties otherSwagger = new Swagger2GroupProperties();
        otherSwagger.setGroupName("OTHER-API");
        otherSwagger.setDescription("以上API中未被包含进来得接口");
        otherSwagger.setPathMapping("/*");
        otherSwagger.setVersion("v1.2.0");
        otherSwagger.setPathRegex("/ ");
        otherSwagger.setTitle("RedE Other RestFull API");
        return otherSwagger;
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        Swagger2Properties swagger2Properties = getSwagger2Properties();
        List<String> pathUrls = new ArrayList<String>();
        if (swagger2Properties.getGroup() != null && !swagger2Properties.getGroup().isEmpty()) {
            Iterator<Entry<String, Swagger2GroupProperties>> it = swagger2Properties.getGroup().entrySet().iterator();
            while (it.hasNext()) {
                Entry<String, Swagger2GroupProperties> entry = (Entry<String, Swagger2GroupProperties>) it
                        .next();
                Docket swagger2Docket = this.getSwagger2Docket(entry.getValue(), pathUrls);
                beanFactory.registerSingleton(entry.getKey(), swagger2Docket);
            }
        }
        beanFactory.registerSingleton("RedE-OTHER-API", this.getOtherSwagger2Docket(pathUrls));
    }
}
