
FROM centos:7
MAINTAINER  zhangguoqi<65751348@qq.com>

#下载NGINX
ADD https://nginx.org/download/nginx-1.14.0.tar.gz .

#RUN 执行以下命令
RUN yum install -y pcre-devel wget net-tools gcc zlib zlib-devel make openssl-devel
RUN useradd -M -s /sbin/nologin nginx
RUN tar -zxvf nginx-1.14.0.tar.gz
RUN mkdir -p /usr/local/nginx
RUN cd nginx-1.14.0 && ./configure --prefix=/usr/local/nginx --user=nginx --group=nginx --with-http_stub_status_module && make && make install
RUN ln -s /usr/local/nginx/sbin/* /usr/local/sbin/

EXPOSE 80

RUN mkdir /usr/local/java
RUN mkdir /usr/local/rsa

ADD jdk-11.0.6_linux-x64_bin.tar.gz /usr/local/java/

ENV JAVA_HOME /usr/local/java/jdk-11.0.6
ENV PATH $JAVA_HOME/bin:$PATH

VOLUME /tmp

COPY public_key_rsa.pub /usr/local/rsa

ADD auth-1.0-SNAPSHOT.jar /app.jar

CMD ["nginx", "-g", "daemon off;"]

RUN bash -c 'touch /app.jar'
ENTRYPOINT ["java","-jar","/app.jar"]