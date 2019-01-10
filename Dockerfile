FROM 10.168.12.103:5001/xujian/java:8

RUN mkdir /app && mkdir /upload
WORKDIR /app
ADD ./target/http-file-server-0.0.1.jar /app/app.jar

ENTRYPOINT exec java -Djava.security.egd=file:/dev/./urandwom \
                     -Dfile.encoding=UTF8 \
                     -Duser.timezone=GMT+08 \
                     -jar /app/app.jar