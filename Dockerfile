FROM maven:3.8 as maven

WORKDIR /app

COPY ./pom.xml ./pom.xml

RUN mvn dependency:go-offlinegit

COPY ./src ./src

CMD ["mvn", "spring-boot:run"]

FROM postgres as postgres