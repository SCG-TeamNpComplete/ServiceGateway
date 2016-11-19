FROM tomcat:8-jre8
ADD	ServiceGateway/target/SG_MICROSERVICE_SERVICEGATEWAY-0.0.1-SNAPSHOT.war /usr/local/tomcat/webapps
EXPOSE 11000