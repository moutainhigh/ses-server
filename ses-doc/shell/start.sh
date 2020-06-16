#! /bin/shell
source /etc/profile

base_path=/root/java_service/prod/
pkill_path=prod/lib/

cd ${base_path}

sleep 1
echo "starting project ses-service-foundation"
pkill -f "${pkill_path}ses-service-foundation.jar"
>${base_path}nohup.out
nohup java -server -XX:MetaspaceSize=128m -XX:MaxMetaspaceSize=128m -Xms256m -Xmx256m -Xmn256m -Xss256k -XX:SurvivorRatio=8 -XX:+UseConcMarkSweepGC -jar ${base_path}lib/ses-service-foundation.jar >${base_path}nohup.out &
echo "start project ses-service-foundation success"

sleep 3
echo "starting project ses-service-proxy"
pkill -f "${pkill_path}ses-service-proxy.jar"
>${base_path}nohup.out
nohup java -server -XX:MetaspaceSize=128m -XX:MaxMetaspaceSize=128m -Xms256m -Xmx256m -Xmn256m -Xss256k -XX:SurvivorRatio=8 -XX:+UseConcMarkSweepGC -jar ${base_path}lib/ses-service-proxy.jar >${base_path}nohup.out &
echo "start project ses-service-proxy success"

sleep 3
echo "starting project ses-service-scooter"
pkill -f "${pkill_path}ses-service-scooter.jar"
>${base_path}nohup.out
nohup java -server -XX:MetaspaceSize=128m -XX:MaxMetaspaceSize=128m -Xms256m -Xmx256m -Xmn256m -Xss256k -XX:SurvivorRatio=8 -XX:+UseConcMarkSweepGC -jar ${base_path}lib/ses-service-scooter.jar >${base_path}nohup.out &
echo "start project ses-service-scooter success"

sleep 3
echo "starting project ses-service-hub"
pkill -f "${pkill_path}ses-service-hub.jar"
>${base_path}nohup.out
nohup java -server -XX:MetaspaceSize=128m -XX:MaxMetaspaceSize=128m -Xms256m -Xmx256m -Xmn256m -Xss256k -XX:SurvivorRatio=8 -XX:+UseConcMarkSweepGC -jar ${base_path}lib/ses-service-hub.jar >${base_path}nohup.out &
echo "start project ses-service-hub success"

sleep 3
echo "starting project ses-service-mobile-b"
pkill -f "${pkill_path}ses-service-mobile-b.jar"
>${base_path}nohup.out
nohup java -server -XX:MetaspaceSize=128m -XX:MaxMetaspaceSize=128m -Xms256m -Xmx256m -Xmn256m -Xss256k -XX:SurvivorRatio=8 -XX:+UseConcMarkSweepGC -jar ${base_path}lib/ses-service-mobile-b.jar >${base_path}nohup.out &
echo "start project ses-service-mobile-b success"

sleep 3
echo "starting project ses-service-mobile-c"
pkill -f "${pkill_path}ses-service-mobile-c.jar"
>${base_path}nohup.out
nohup java -server -XX:MetaspaceSize=128m -XX:MaxMetaspaceSize=128m -Xms256m -Xmx256m -Xmn256m -Xss256k -XX:SurvivorRatio=8 -XX:+UseConcMarkSweepGC -jar ${base_path}lib/ses-service-mobile-c.jar >${base_path}nohup.out &
echo "start project ses-service-mobile-c success"

sleep 3
echo "starting project ses-mobile-client"
pkill -f "${pkill_path}ses-mobile-client.jar"
>${base_path}nohup.out
nohup java -server -XX:MetaspaceSize=128m -XX:MaxMetaspaceSize=128m -Xms256m -Xmx256m -Xmn256m -Xss256k -XX:SurvivorRatio=8 -XX:+UseConcMarkSweepGC -jar ${base_path}lib/ses-mobile-client.jar >${base_path}nohup.out &
echo "start project ses-mobile-client success"

sleep 3
echo "starting project ses-web-ros"
pkill -f "${pkill_path}ses-web-ros.jar"
>${base_path}nohup.out
nohup java -server -XX:MetaspaceSize=128m -XX:MaxMetaspaceSize=128m -Xms256m -Xmx256m -Xmn256m -Xss256k -XX:SurvivorRatio=8 -XX:+UseConcMarkSweepGC -jar ${base_path}lib/ses-web-ros.jar >${base_path}nohup.out &
echo "start project ses-web-ros success"

sleep 3
echo "starting project ses-web-delivery"
pkill -f "${pkill_path}ses-web-delivery.jar"
>${base_path}nohup.out
nohup java -server -XX:MetaspaceSize=128m -XX:MaxMetaspaceSize=128m -Xms256m -Xmx256m -Xmn256m -Xss256k -XX:SurvivorRatio=8 -XX:+UseConcMarkSweepGC -jar ${base_path}lib/ses-web-delivery.jar >${base_path}nohup.out &
echo "start project ses-web-delivery success"

sleep 3
echo "starting project ses-job-admin"
pkill -f "${pkill_path}ses-job-admin.jar"
>${base_path}nohup.out
nohup java -server -XX:MetaspaceSize=128m -XX:MaxMetaspaceSize=128m -Xms256m -Xmx256m -Xmn256m -Xss256k -XX:SurvivorRatio=8 -XX:+UseConcMarkSweepGC -jar ${base_path}lib/ses-job-admin.jar >${base_path}nohup.out &
echo "start project ses-job-admin success"

sleep 3
echo "starting project ses-instance-one"
pkill -f "${pkill_path}ses-instance-one.jar"
>${base_path}nohup.out
nohup java -server -XX:MetaspaceSize=128m -XX:MaxMetaspaceSize=128m -Xms256m -Xmx256m -Xmn256m -Xss256k -XX:SurvivorRatio=8 -XX:+UseConcMarkSweepGC -jar ${base_path}lib/ses-instance-one.jar >${base_path}nohup.out &
echo "start project ses-instance-one success"

