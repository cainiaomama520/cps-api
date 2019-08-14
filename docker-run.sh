#!/bin/bash
mvn -P pro clean package -Dmaven.test.skip=true
docker login --username=testmepro512 -p mex12345  registry.cn-hongkong.aliyuncs.com
docker build -f ./Dockerfile-local -t registry.cn-hongkong.aliyuncs.com/destroyer/destroyer-api-auto .
docker push registry.cn-hongkong.aliyuncs.com/destroyer/destroyer-api-auto