FROM registry.cn-hongkong.aliyuncs.com/destroyer/destroyer-api-auto:base AS builder
ENV TZ=Asia/Shanghai LANG="C.UTF-8"
RUN \
    rm /etc/localtime && \
    ln -s /usr/share/zoneinfo/Asia/Shanghai /etc/localtime

# add pom.xml and source code
ADD ./pom.xml pom.xml
ADD ./src src/
# package jar
RUN mvn -P pro clean package -Dmaven.test.skip=true

# Second stage: minimal runtime environment
FROM openjdk:8-jre-alpine
ENV TZ=Asia/Shanghai LANG="C.UTF-8"
#below 3 lines cause failure cause alphine does not have the file /etc/localtime !
#RUN \
#    rm /etc/localtime && \
#    ln -s /usr/share/zoneinfo/Asia/Shanghai /etc/localtime
    
RUN echo -e "https://mirror.tuna.tsinghua.edu.cn/alpine/v3.4/main\n\
https://mirror.tuna.tsinghua.edu.cn/alpine/v3.4/community" > /etc/apk/repositories

RUN apk --update add curl bash ttf-dejavu && \
      rm -rf /var/cache/apk/*

# copy jar from the first stage
COPY --from=builder target/destroyer-api.jar app.jar
# run jar
CMD ["java", "-jar", "app.jar"]
