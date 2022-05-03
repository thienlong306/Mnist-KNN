FROM maven:latest
RUN mkdir /mnist
WORKDIR /minst
COPY . .
RUN mvn clean install
CMD mvn spring-boot:run
