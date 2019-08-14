#!/bin/bash

ENV=$1
BR=$2
app_home=/data/build/crm/mex-crm-api

if [ ! -n "$1" ]; then
  echo "profile IS NULL, ./build-api.sh test|pro"
  exit -100
fi

cd $app_home
git checkout ${BR}
git pull
mvn clean install -Dmaven.test.skip=true -P$ENV

echo "--------------------------------------------------------------"
sleep 1
echo "--------------------------------------------------------------"


install_test()
{
  echo "destroyer-api.jar copy to install dir: /data/app/crm"
  cp -f target/destroyer-api.jar  /data/app/crm

   echo "restart test crm"
  /data/app/crm/run.sh restart
}

install_pro(){
  echo "scp destroyer-api.jar to dir: ugc@ugcmgr:/data/app/crm/crm-api"
  scp  target/destroyer-api.jar ugc@ugcmgr:/data/app/crm/crm-api
  ssh ugc@ugcmgr '/data/app/crm/crm-api/run-api.sh restart'
  echo "restart pro crm status=$?"
}

if [ "$1" == "test" ]; then
  install_test
elif [ "$1" == "pro" ]; then
  while true; do
    read -p "Do you wish to install this program (y/n)?" yn
    case $yn in
        [Yy]* ) install_pro
                break;;
        [Nn]* ) echo "bye! " && exit;;
        * ) echo "Please answer yes or no.";;
    esac
   done
fi