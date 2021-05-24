pipeline {
      agent any
      stages {
            stage ('PULL CODE') {
                  steps {
                    echo '从github上获取源码'
                    sh 'pwd'
                    checkout([$class: 'GitSCM', branches: [[name: '*/website-price']], extensions: [], userRemoteConfigs: [[credentialsId: 'db9dab4d-01b6-40fb-bb35-63de8f6955b7', url: 'git@github.com:redescooter/ses-server.git']]])
                    echo '代码拉取完成'
                  }
            }

            stage ('INIT ENV') {
                  steps {
                    echo '-----------------------资源回收----------------------'
                    sh 'pwd'
                    sh 'cd /root/java_service/pre'
                    sh 'pwd'
                    sh 'sh /root/java_service/pre/deploy.sh stop'
                    echo '-----------------------回收完成----------------------'
                  }
            }
            stage ('BUILD CODE') {
                  steps {
                    echo '----------------------执行编译-----------------------'
                    sh 'pwd'
                    sh 'rm -rf /root/java_service/pre/libs'
                    sh 'mvn clean package -Dmaven.test.skip=true -Ppre'
                    sh 'tree /root/java_service/pre/libs'
                    echo '----------------------编译完成-----------------------'
                  }
            }

            stage ('DEPLOY CODE') {
                  steps {
                    echo '-----------------------执行部署----------------------'
                    sh 'cd /root/java_service/pre'
                    sh 'pwd'
                    sh 'sh /root/java_service/pre/deploy.sh rinit'
                    sh 'sh /root/java_service/pre/deploy.sh start'
                    sh 'sh /root/java_service/pre/deploy.sh status'
                    echo '-----------------------部署完成----------------------'
                  }
            }
            stage ('SEND MESSAGE') {
                  steps {
                    echo '-----------------------链接钉钉----------------------'
                    echo '-----------------------执行消息推送----------------------'
                       dingtalk (
                            robot: 'RedEGroup',
                            type: 'LINK',
                            title: '部署成功通知',
                            atAll: true,
                            text: [
                                'AWS PRE环境【ses服务】',
                                '部署成功'
                            ],
                            messageUrl: 'https://pre.redelectric.fr/',
                            picUrl: 'https://rede.oss-cn-shanghai.aliyuncs.com/1621830838698.png'
                        )
                    echo '-----------------------消息下发完成----------------------'
                  }
            }
      }

      post{
          success{
               dingtalk (
                    robot: 'RedEGroup',
                    type: 'LINK',
                    title: '部署成功通知',
                    text: [
                        'AWS PRE环境【ses服务】',
                        '部署成功'
                    ],
                    messageUrl: 'https://pre.redelectric.fr/',
                    picUrl: 'https://rede.oss-cn-shanghai.aliyuncs.com/1621830838698.png'
                )
          }
          failure{
               dingtalk (
                    robot: 'RedEGroup',
                    type: 'LINK',
                    title: '部署失败通知',
                    text: [
                        'AWS PRE环境【ses服务】',
                        '部署失败',
                        '请登录检查'
                    ],
                    messageUrl: 'https://ci.redelectric.tech/',
                    picUrl: 'https://rede.oss-cn-shanghai.aliyuncs.com/1621830862654.png'
                )
          }
      }

}