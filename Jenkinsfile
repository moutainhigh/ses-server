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
                    echo '开始编译代码'
                    sh 'pwd'
                    sh 'rm -rf /root/java_service/pre/libs'
                    sh 'mvn clean package -Dmaven.test.skip=true -Ppre'
                    sh 'tree /root/java_service/pre/libs'
                    echo '代码编译完成'
                  }
            }

            stage ('Deploy Code') {
                  steps {
                    echo '开始部署代码'
                    sh 'cd /root/java_service/pre'
                    sh 'pwd'
                  }
            }

            stage ('Message notification') {
                  steps {
                    echo '即将发布消息通知'
                  }
            }
      }

      post {
      		success {
      			dingTalk accessToken:'https://oapi.dingtalk.com/robot/send?access_token=f9088064f2541cacabc6d4df24ce3c09a4de482e12b041d7e813cf4ffa9e3b9c',
      			imageUrl:'图片地址',
      			jenkinsUrl:'https://ci.redelectric.tech/',
      			message:'PRE环境已部署完成!!!',
      			notifyPeople:''
      		}
      		failure {
      			dingTalk accessToken:'https://oapi.dingtalk.com/robot/send?access_token=f9088064f2541cacabc6d4df24ce3c09a4de482e12b041d7e813cf4ffa9e3b9c',
      			imageUrl:'图片地址',
      			jenkinsUrl:'https://ci.redelectric.tech/',
      			message:'PRE环境部署失败，请登录检查失败原因!!!',
      			notifyPeople:''
      		}
      }

}