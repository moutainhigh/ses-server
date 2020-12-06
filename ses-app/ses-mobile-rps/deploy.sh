#!/bin/bash
#Date   : 2019/05/30
#Author : jerry
#Mail   : jerry@redescooter.com
#Function : ses-mobile-rps启动
#Version  ： V1.1
#这里可替换为你自己的执行程序，其他代码无需更改
APP_NAME=ses-mobile-rps.jar

APP_PACH=/root/java_service/pre/lib/
readonly APP_PACH

#使用说明，用来提示输入参数
usage() {
  echo "Usage: sh deploy.sh [start|stop|restart|status]"
  exit 1
}

#检查程序是否在运行
is_exist() {
  # shellcheck disable=SC2009
  pid=$(ps -ef | grep ${APP_NAME} | grep -v grep | awk '{print $2}')
  #如果不存在返回1，存在返回0
  if [ -z "${pid}" ]; then
    return 1
  else
    return 0
  fi
}

#启动方法
start() {
  is_exist
  # shellcheck disable=SC2181
  if [ $? -eq "0" ]; then
    echo "${APP_NAME} is already running. pid=${pid} ."
  else
    nohup java -server -jar -XX:MetaspaceSize=128m -XX:MaxMetaspaceSize=128m -Xms1024m -Xmx1024m -Xmn256m -Xss1m -XX:SurvivorRatio=8 -XX:+UseConcMarkSweepGC ${APP_PACH}${APP_NAME} > ${APP_PACH}+'nohup.out' 2>&1 &
    echo "${APP_NAME} is  running....."
  fi
}

#停止方法
stop() {
  is_exist
  # shellcheck disable=SC2181
  if [ $? -eq "0" ]; then
    # shellcheck disable=SC2086
    kill -9 $pid
  else
    echo "${APP_NAME} is not running"
  fi
}

#输出运行状态
status() {
  is_exist
  # shellcheck disable=SC2181
  if [ $? -eq "0" ]; then
    echo "${APP_NAME} is running. Pid is $pid"
  else
    echo "${APP_NAME} is not running."
  fi
}

#重启
restart() {
  stop
  start
}

#根据输入参数，选择执行对应方法，不输入则执行使用说明
case "$1" in
"start")
  start
  ;;
"stop")
  stop
  ;;
"status")
  status
  ;;
"restart")
  restart
  ;;
*)
  usage
  ;;
esac
