FROM maven:3.6.3-jdk-11
COPY ./pom.xml ./pom.xml
RUN mvn dependency:go-offline -B
COPY ./src ./src
RUN mvn package
EXPOSE 8080
CMD java -jar target/BeerBar-0.0.1-SNAPSHOT.jar