#!/bin/bash
# author jerry

nohup java -server -XX:MetaspaceSize=512m -XX:MaxMetaspaceSize=512m -Xms256m -Xmx256m -Xmn256m -Xss256k -XX:SurvivorRatio=8 -XX:+UseConcMarkSweepGC -jar "$1" >"$1.log" 2>&1 &
tail -1000f "$1.log"

################################################################
# 使用方式
# ./boot-jar.sh target/service-application-0.0.1-SNAPSHOT.jar
# 建议：boot-jar.sh应用程序启动脚本位置尽量放在与Jar同级目录下。
################################################################