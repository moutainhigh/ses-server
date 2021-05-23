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
      }

      stages {
            stage ('Build Code') {
                  steps {
                    echo '开始编译代码'
                    sh 'pwd'
                    sh 'mvn clean package -Dmaven.test.skip=true -Ppre'
                    sh 'tree /root/java_service/pre/libs'
                    echo '代码编译完成'
                  }
            }
      }

      stages {
            stage ('Deploy Code') {
                  steps {
                    echo '开始部署代码'
                    sh 'cd /root/java_service/pre'
                    sh 'pwd'
                  }
            }
      }

      stages {
            stage ('Message notification') {
                  steps {
                    echo '即将发布消息通知'
                  }
            }
      }
}