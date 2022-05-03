FROM maven:latest
RUN mkdir /mnist
WORKDIR /minst
COPY . .
CMD java -Xmx256M
CMD mvn clean install
CMD mvn spring-boot:run
