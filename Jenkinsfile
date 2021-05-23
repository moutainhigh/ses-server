//代码拉取==>代码检测==>代码构建==>代码部署==>消息通知
pipeline {
  agent any
  stages {
    stage('Pull Code') {  //编译阶段
      steps {
        sh 'pwd'
        
        git(url: 'https://xxx.xxx.xxx.xxx/xxxxxxxx/xxxxxxx', poll: true, credentialsId: '0000000-0000-0000-0000-000000000000') //拉取代码
        echo '使用你的编译工具进行编译'  //编译
        archiveArtifacts(artifacts: 'testProject', onlyIfSuccessful: true)  //编译制品归档
      }
    }

    stage('Docker Build')  //编译docker 镜像文件
            agent any
            steps {
                unstash 'test'
                sh "docker login"
                sh "docker build"
                sh "docker push "
                sh "docker rmi"
            }

        }
    stage('Deploy') { //部署阶段
        agent {  //在stage中特别声明agent，该stage就在声明的agent中去执行
            docker {
                image 'image_name'
            }
        }
        steps { //执行步骤
            sh "mkdir -p ~/.kube"
            echo '添加部署步骤完成部署 '
            echo '启动服务'
        }

    stage('Test') { //测试阶段
      steps {
        echo '测试阶段'
        git(url: 'https://xxx.xxx.xxx.xxx/xxxxxxxx/xxxxxxx', poll: true, credentialsId: '0000000-0000-0000-0000-000000000000')
        echo '执行测试用例'
      }
    }