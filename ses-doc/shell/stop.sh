
#! /bin/shell
source /etc/profile

logs_path=/data/java_service/logs/prod/
base_path=/root/java_service/prod/
pkill_path=prod/lib/

cd ${base_path}

pkill -f "${pkill_path}ses-service-foundation.jar"
pkill -f "${pkill_path}ses-service-proxy.jar"
pkill -f "${pkill_path}ses-service-scooter.jar"
pkill -f "${pkill_path}ses-service-hub.jar"
pkill -f "${pkill_path}ses-service-mobile-b.jar"
pkill -f "${pkill_path}ses-service-mobile-c.jar"
pkill -f "${pkill_path}ses-mobile-client.jar"
pkill -f "${pkill_path}ses-web-ros.jar"
pkill -f "${pkill_path}ses-web-delivery.jar"

rm -rf ${logs_path}ses-service-foundation/*.log
rm -rf ${logs_path}ses-service-proxy/*.log
rm -rf ${logs_path}ses-service-hub/*.log
rm -rf ${logs_path}ses-service-mobile-c/*.log
rm -rf ${logs_path}ses-service-mobile-b/*.log
rm -rf ${logs_path}ses-mobile-client/*.log
rm -rf ${logs_path}ses-web-ros/*.log
rm -rf ${logs_path}ses-web-delivery/*.log

