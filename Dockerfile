FROM maven:latest
RUN mkdir /mnist
WORKDIR /minst
COPY . .
CMD JAVA_OPTS="-Xms512m -Xmx1024m -XX:MetaspaceSize=512M -XX:MaxMetaspaceSize=1024m -Djava.net.preferIPv4Stack=true"
CMD mvn clean install
CMD mvn spring-boot:run
