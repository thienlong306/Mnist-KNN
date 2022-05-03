FROM maven:latest
RUN mkdir /mnist
WORKDIR /minst
COPY . .
CMD mvn clean install
CMD mvn spring-boot:run
