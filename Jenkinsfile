pipeline {
      agent any
      stages {
            stage ('Pull Code') {
                  steps {
                    echo '从github上获取源码'
                    sh 'pwd'
                    checkout([$class: 'GitSCM', branches: [[name: '*/website-price']], extensions: [], userRemoteConfigs: [[credentialsId: 'db9dab4d-01b6-40fb-bb35-63de8f6955b7', url: 'git@github.com:redescooter/ses-server.git']]])
                    echo '代码拉取完成'
                  }
            }
            stage ('Build Code') {
                  steps {
                    echo '----------------------执行编译-----------------------'
                    sh 'sh /root/java_service/pre/deploy.sh stop'
                    sh 'pwd'
                    sh 'rm -rf /root/java_service/pre/libs'
                    sh 'mvn clean package -Dmaven.test.skip=true -Ppre'
                    sh 'tree /root/java_service/pre/libs'
                    echo '----------------------编译完成-----------------------'
                  }
            }
            stage ('Deploy Code') {
                  steps {
                    echo '-----------------------执行部署----------------------'
                    sh 'pwd'
                    sh 'cd /root/java_service/pre'
                    sh 'pwd'
                    sh 'sh /root/java_service/pre/deploy.sh rinit'
                    sh 'sh /root/java_service/pre/deploy.sh start'
                    sh 'sh /root/java_service/pre/deploy.sh status'
                    echo '-----------------------部署完成----------------------'
                  }
            }
            stage ('Send Message') {
                  steps {
                    echo '-----------------------链接钉钉----------------------'
                    echo '-----------------------执行消息推送----------------------'
                    echo '-----------------------消息下发完成----------------------'
                  }
            }
      }

}