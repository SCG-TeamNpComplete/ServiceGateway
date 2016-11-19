FROM tomcat:8-jre8
ADD	ServiceGateway/target/*.war /usr/local/tomcat/webapps
EXPOSE 11000