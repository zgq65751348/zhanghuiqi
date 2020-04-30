# Docker image for springboot file run
# VERSION 0.0.1
# Author: zhangguoqi

FROM centos:7
MAINTAINER  zhangguoqi<65751348@qq.com>

# 创建一个新目录来存储jdk文件
RUN mkdir /usr/local/java
RUN mkdir /usr/local/rsa

#将jdk压缩文件复制到镜像中，它将自动解压缩tar文件
ADD jdk-11.0.6_linux-x64_bin.tar.gz /usr/local/java/

# 设置环境变量
ENV JAVA_HOME /usr/local/java/jdk-11.0.6
ENV PATH $JAVA_HOME/bin:$PATH

# VOLUME 指定了临时文件目录为/tmp
# 其效果是在主机 /var/lib/docker 目录下创建了一个临时文件，并链接到容器的/tmp
VOLUME /tmp

#拷贝公钥和私钥到容器内部指定文件夹
COPY public_key_rsa.pub /usr/local/rsa
COPY private_key_rsa /usr/local/rsa

# 将jar包添加到容器中并更名为app.jar
ADD auth-1.0-SNAPSHOT.jar /app.jar

# 运行jar包
RUN bash -c 'touch /app.jar'
ENTRYPOINT ["java","-jar","/app.jar"]
