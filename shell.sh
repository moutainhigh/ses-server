#! /bin/shell
source /etc/profile

echo “starting project ses-service-foundation”
pkill -f "prod/lib/ses-service-foundation.jar"
> /root/java_service/prod/nohup.out
nohup java -jar /root/java_service/prod/lib/ses-service-foundation.jar > /root/java_service/prod/nohup.out &
echo "start project ses-service-foundation success"

sleep 3
echo “starting project ses-service-proxy”
pkill -f "prod/lib/ses-service-proxy.jar"
> /root/java_service/prod/nohup.out
nohup java -jar /root/java_service/prod/lib/ses-service-proxy.jar > /root/java_service/prod/nohup.out &
echo "start project ses-service-proxy success"

sleep 3
echo “starting project ses-service-scooter”
pkill -f "prod/lib/ses-service-scooter.jar"
> /root/java_service/prod/nohup.out
nohup java -jar /root/java_service/prod/lib/ses-service-scooter.jar > /root/java_service/prod/nohup.out &
echo "start project ses-service-scooter success"

sleep 3
echo “starting project ses-service-hub”
pkill -f "prod/lib/ses-service-hub.jar"
> /root/java_service/prod/nohup.out
nohup java -jar /root/java_service/prod/lib/ses-service-hub.jar > /root/java_service/prod/nohup.out &
echo "start project ses-service-hub success"

sleep 3
echo “starting project ses-service-mobile-b”
pkill -f "prod/lib/ses-service-mobile-b.jar"
> /root/java_service/prod/nohup.out
nohup java -jar /root/java_service/prod/lib/ses-service-mobile-b.jar > /root/java_service/prod/nohup.out &
echo "start project ses-service-mobile-b success"

sleep 3
echo “starting project ses-service-mobile-c”
pkill -f "prod/lib/ses-service-mobile-c.jar"
> /root/java_service/prod/nohup.out
nohup java -jar /root/java_service/prod/lib/ses-service-mobile-c.jar > /root/java_service/prod/nohup.out &
echo "start project ses-service-mobile-c success"

sleep 3
echo “starting project ses-mobile-client”
pkill -f "prod/lib/ses-mobile-client.jar"
> /root/java_service/prod/nohup.out
nohup java -jar /root/java_service/prod/lib/ses-mobile-client.jar > /root/java_service/prod/nohup.out &
echo "start project ses-mobile-client success"

sleep 3
echo “starting project ses-web-ros”
pkill -f "prod/lib/ses-web-ros.jar"
> /root/java_service/prod/nohup.out
nohup java -jar /root/java_service/prod/lib/ses-web-ros.jar > /root/java_service/prod/nohup.out &
echo "start project ses-web-ros success"

sleep 3
echo “starting project ses-web-delivery”
pkill -f "prod/lib/ses-web-delivery.jar"
> /root/java_service/prod/nohup.out
nohup java -jar /root/java_service/prod/lib/ses-web-delivery.jar > /root/java_service/prod/nohup.out &
echo "start project ses-web-delivery success"

sleep 3
echo “starting project ses-job-admin”
pkill -f "prod/lib/ses-job-admin.jar"
> /root/java_service/prod/nohup.out
nohup java -jar /root/java_service/prod/lib/ses-job-admin.jar > /root/java_service/prod/nohup.out &
echo "start project ses-job-admin success"

sleep 3
echo “starting project ses-instance-one”
pkill -f "prod/lib/ses-instance-one.jar"
> /root/java_service/prod/nohup.out
nohup java -jar /root/java_service/prod/lib/ses-instance-one.jar > /root/java_service/prod/nohup.out &
echo "start project ses-instance-one success"
