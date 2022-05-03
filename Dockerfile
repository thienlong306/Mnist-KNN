FROM maven:latest
RUN mkdir /mnist
WORKDIR /minst
COPY . .
CMD export JVM_ARGS = “- Xms1024m-Xmx1024m
CMD mvn clean install
CMD mvn spring-boot:run
