FROM tomcat:9-jre8-alpine

ENV JPDA_TRANSPORT=dt_socket\
    JPDA_ADDRESS=8000

RUN rm -rf /usr/local/tomcat/webapps/*

COPY ./target/auction.war /usr/local/tomcat/webapps/ROOT.war

ENTRYPOINT ["catalina.sh"]
CMD ["run"]
