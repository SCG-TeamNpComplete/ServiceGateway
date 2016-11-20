FROM tomcat:8-jre8
ADD	target/SG_MICROSERVICE_SERVICEGATEWAY.war /usr/local/tomcat/webapps
EXPOSE 11000