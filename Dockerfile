FROM openjdk:11
COPY target/*.jar sapeadita-0.0.1-SNAPSHOT.jar
#EXPOSE 8092
ENTRYPOINT ["java","-jar","/sapeadita-0.0.1-SNAPSHOT.jar"]