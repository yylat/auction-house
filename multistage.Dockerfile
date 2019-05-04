FROM maven:3.6.1-jdk-8-slim as builder
WORKDIR /build
COPY pom.xml .
RUN mvn -B -e -C -T 1C org.apache.maven.plugins:maven-dependency-plugin:3.0.2:go-offline
COPY . .
RUN mvn -B -e -C -T 1C clean package -DskipTests

FROM tomcat:9-jre8-alpine
ENV JPDA_TRANSPORT=dt_socket\
    JPDA_ADDRESS=8000
RUN rm -rf /usr/local/tomcat/webapps/*
COPY --from=builder /build/target/auction.war /usr/local/tomcat/webapps/ROOT.war
ENTRYPOINT ["catalina.sh"]
CMD ["run"]
