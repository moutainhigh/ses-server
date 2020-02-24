
![RedEScooter](https://rede.oss-cn-shanghai.aliyuncs.com/project/redescooter.png)      
###### <div align=right>RedE Scooter 2019

<div align=center>

[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg?label=license)](https://github.com/mrjerryli/ses-server/blob/master/LICENSE) 
[![Maven Central](https://img.shields.io/maven-central/v/org.apache.maven/apache-maven.svg?label=Maven%20Central)](https://search.maven.org/#search%7Cgav%7C1%7Cg%3A%22org.apache.maven%22%20AND%20a%3A%22apache-maven%22)

</div>

## <div align=center>[多租户分布式架构(dubbo版)](https://github.com/mrjerryli/ses-server) `Sv 1.2.0` 
 
#### 一、技术选型

- 语言框架
	- JDK 1.8
	- Spring Boot 2.1.9.RELEASE
    - Spring Security
    - Spring security Oauth2
    - Spring Security OAuth2 SSO
	- Dubbo 2.7.3
	- Zookeeper 3.4.9
	- Redis 2.9.0
	- Poi 4.1.0
	- MySQL
	- Mybatis Plus  3.1.2
	- Swagger 2.8.0
	- Xxl job 
	- TX-LCN分布式事务框架
- 构建工具
	- Intellij IDEA Ultimate 2019.2
	- Maven
- 系统
	- Windows 10
	- Mac Os

---

#### 二、框架搭建

- [x] 基础框架
    - [x] 父模块、通用模块创建
    - [x] 公共starter
    - [x] 公共工具类
    - [x] xxl-job分布式任务集成
    - [X] 整合Swagger
    - [X] 搭建微服务消费者
    - [X] 搭建微服务生产者
    - [ ] lcn分布式事务监控平台
    - [ ] 搭建微服务网关Soul
- [ ] 架构完善
    - [X] 异常处理
    - [X] 跨域处理
    - [ ] 参数配置化
    - [ ] 分布式事务处
    - [ ] Spring Security
    - [ ] Spring security Oauth2
    - [ ] Spring Security OAuth2 SSO
    
- [ ] 业务完善
    - [X] BUOOB整合各服务
    - [X] 整合第三方服务
    - [ ] ROS v1.0.0
    - [ ] 完善登录流程

---

#### 三、项目结构说明
```text
ses-server
  ├── ses-api                                                         接口API暴露层
  │   ├── ses-api-common                                              RedE API公共资源
  │   ├── ses-api-foundation                                          RedE 底层基础支持
  │   ├── ses-api-hub                                                 RedE 多数据源支持API
  │   ├── ses-api-mobile-b                                            RedE B端移动支持
  │   ├── ses-api-mobile-c                                            RedE C端移动支持
  │   ├── ses-api-proxy                                               RedE 三方服务底层
  │   └── ses-api-scooter                                             RedE 车辆API
  ├── ses-app                                                         应用层
  │   ├── ses-admin-dev                                               RedE 开发管理平台
  │   ├── ses-app-common                                              RedE 应用公共资源 
  │   ├── ses-mobile-client                                           RedE 移动端服务支持
  │   ├── ses-web-delivery                                            RedE SaaS管理系统
  │   ├── ses-web-repair                                              RedE 维修管理系统
  │   └── ses-web-ros                                                 RedE内部管理系统
  ├── ses-doc                                                         rede文档
  ├── ses-job                                                         分布式定时任务
  │   ├── doc                                                         分布式定时任务文件
  │   ├── ses-job-admin                                               分布式管理平台
  │   ├── ses-job-core                                                分布式任务核心
  │   └── ses-job-executor                                            任务执行器
  ├── ses-monitor-admin                                               rede监控系统
  ├── ses-service                                                     生产者服务提供层
  │   ├── ses-service-base                                            底层基础服务
  │   │   ├── ses-service-proxy                                       三方基础支持
  │   │   ├── ses-service-scooter                                     车辆基础服务
  │   │   └── ses-service-foundation                                  SaaS底层基础服务
  │   ├── ses-service-common                                          服务层公共资源
  │   ├── ses-service-hub                                             多数据源调度平台
  │   ├── ses-service-mobile-b                                        B端移动服务支持
  │   ├── ses-service-mobile-c                                        C端移动服务支持
  │   └── ses-service-security                                        权限安全
  ├── ses-starter                                                     rede组件层
  │   ├── ses-starter-common                                          RedE基础依赖
  │   ├── ses-starter-db                                              数据库组件
  │   ├── ses-starter-dubbo                                           dubbo RPC组件
  │   ├── ses-starter-logs                                            日志组件
  │   ├── ses-starter-poi                                             表格组件
  │   ├── ses-starter-redis                                           Redis缓存组件
  │   └── ses-starter-swagger                                         swagger在线文档组件
  ├── ses-tool                                                        公共工具类
  └── ses-txlcn-manager                                               分布式事务管理
```
---

#### 四、RedE架构体系图

![rede体系图](https://rede.oss-cn-shanghai.aliyuncs.com/project/rede.png)

#### 四、相关文档

| Name | Documentation|
| ---------- | ---------------------------------------- |
| [springboot](https://docs.spring.io/spring-boot/docs/) | https://docs.spring.io/spring-boot/docs/ |
| [dubbo](http://dubbo.apache.org/en-us/) | http://dubbo.apache.org/ |      
| [MyBatis-Plus](https://mp.baomidou.com/) |https://mp.baomidou.com/|
| [Swagger](https://swagger.io/#)|https://swagger.io/|
| [TX-LCN](http://www.txlcn.org/)|http://www.txlcn.org/|
| [Spring Security](https://docs.spring.io/spring-security/site/docs/5.2.2.BUILD-SNAPSHOT/reference/htmlsingle/)|https://docs.spring.io/spring-security/site/docs/5.2.2.BUILD-SNAPSHOT/reference/htmlsingle/|
| [Spring Security Oauth](https://projects.spring.io/spring-security-oauth/docs/oauth2.html)|https://projects.spring.io/spring-security-oauth/docs/oauth2.html|


#### 五、相关命令

1. maven 多环境打包命令
```shell script
mvn clean package -Dmaven.test.skip=true -Pprod

```
#### 六、Git分支规范
#####分支命名
* master 分支
```text
master 为主分支，也是用于部署生产环境的分支，确保master分支稳定性
master 分支一般由develop以及hotfix分支合并，任何时间都不能直接修改代码
```


* develop 分支
```text
develop 为开发分支，始终保持最新完成以及bug修复后的代码
一般开发的新功能时，feature分支都是基于develop分支下创建的
```


* feature 分支
```text
开发新功能时，以develop为基础创建feature分支
分支命名: feature/ 开头的为特性分支， 命名规则: feature/user_module、 feature/cart_module

```

* release分支
```text
release 为预上线分支，发布提测阶段，会release分支代码为基准提测

当有一组feature开发完成，首先会合并到develop分支，进入提测时，会创建release分支。
如果测试过程中若存在bug需要修复，则直接由开发者在release分支修复并提交。
当测试完成之后，合并release分支到master和develop分支，此时master为最新代码，用作上线。
```

* hotfix 分支
```text
分支命名: hotfix/ 开头的为修复分支，它的命名规则与 feature 分支类似
线上出现紧急问题时，需要及时修复，以master分支为基线，创建hotfix分支，修复完成后，需要合并到master分支和develop分支
```

* Commit messages的基本语法
当前业界应用的比较广泛的是 Angular Git Commit Guidelines  
具体格式为:
```git
<type>: <subject>
<BLANK LINE>
<body>
<BLANK LINE>
<footer>
```

> - type: 本次 commit 的类型，诸如 bugfix docs style 等
> - scope: 本次 commit 波及的范围
> - subject: 简明扼要的阐述下本次 commit 的主旨，在原文中特意强调了几点 1. 使用祈使句，是不是很熟悉又陌生的一个词，来传送门在此 祈使句 2. 首字母不要大写 3. 结尾无需添加标点
> - body: 同样使用祈使句，在主体内容中我们需要把本次 commit 详细的描述一下，比如此次变更的动机，如需换行，则使用 |
> - footer: 描述下与之关联的 issue 或 break change，详见案例

* Type的类别说明：

> - feat: 添加新特性
> - fix: 修复bug
> - docs: 仅仅修改了文档
> - style: 仅仅修改了空格、格式缩进、都好等等，不改变代码逻辑
> - refactor: 代码重构，没有加新功能或者修复bug
> - perf: 增加代码进行性能测试
> - test: 增加测试用例
> - chore: 改变构建流程、或者增加依赖库、工具等

