
```$xslt
██████╗ ███████╗██████╗ ███████╗    ███████╗ ██████╗ ██████╗  ██████╗ ████████╗███████╗██████╗
██╔══██╗██╔════╝██╔══██╗██╔════╝    ██╔════╝██╔════╝██╔═══██╗██╔═══██╗╚══██╔══╝██╔════╝██╔══██╗
██████╔╝█████╗  ██║  ██║█████╗      ███████╗██║     ██║   ██║██║   ██║   ██║   █████╗  ██████╔╝
██╔══██╗██╔══╝  ██║  ██║██╔══╝      ╚════██║██║     ██║   ██║██║   ██║   ██║   ██╔══╝  ██╔══██╗
██║  ██║███████╗██████╔╝███████╗    ███████║╚██████╗╚██████╔╝╚██████╔╝   ██║   ███████╗██║  ██║
╚═╝  ╚═╝╚══════╝╚═════╝ ╚══════╝    ╚══════╝ ╚═════╝ ╚═════╝  ╚═════╝    ╚═╝   ╚══════╝╚═╝  ╚═╝                                                                                  
SV 1.2.0
```
      

<p align="center">
	 [![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg?label=license)](https://github.com/mrjerryli/ses-server/blob/master/LICENSE) 
</p>
                             
###### <div align=right>RedE Scooter 2019
# <div align=center>[RedE Server.多租户分布式架构 SV1.2.0](https://github.com/mrjerryli/ses-server) :confused:	
 

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
- 其他
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
  ├── ses-api
  │   ├── ses-api-common
  │   ├── ses-api-foundation
  │   ├── ses-api-hub
  │   ├── ses-api-mobile-b
  │   ├── ses-api-mobile-c
  │   ├── ses-api-proxy
  │   └── ses-api-scooter
  ├── ses-app
  │   ├── ses-admin-dev
  │   ├── ses-app-common
  │   ├── ses-mobile-client
  │   ├── ses-web-delivery
  │   ├── ses-web-repair
  │   └── ses-web-ros
  ├── ses-doc
  ├── ses-job
  │   ├── doc
  │   ├── ses-job-admin
  │   ├── ses-job-core
  │   └── ses-job-executor
  ├── ses-monitor-admin
  ├── ses-service
  │   ├── ses-service-base
  │   │   ├── ses-service-proxy
  │   │   ├── ses-service-scooter
  │   │   └── ses-service-foundation
  │   ├── ses-service-common
  │   ├── ses-service-hub
  │   ├── ses-service-mobile-b
  │   ├── ses-service-mobile-c
  │   └── ses-service-security
  ├── ses-starter
  │   ├── ses-starter-common
  │   ├── ses-starter-db
  │   ├── ses-starter-dubbo
  │   ├── ses-starter-logs
  │   ├── ses-starter-poi
  │   ├── ses-starter-redis
  │   └── ses-starter-swagger
  ├── ses-tool
  └── ses-txlcn-manager
```
---

#### 四、RedE架构体系图

![rede体系图](https://github.com/mrjerryli/ses-server/blob/master/ses-doc/images/rede.png)

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
