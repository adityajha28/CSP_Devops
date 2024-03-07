FROM maven:3.9.5 AS build
WORKDIR /app
COPY . .
RUN mvn clean install -DskipTests=true
ADD target/Promact_Customer_Success-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]
EXPOSE 8081

#COPY . .

#RUN mvn clean install -DskipTests=true

#CMD [ "mvn", "spring-boot:run" ]
# RUN apt install openjdk17

# FROM maven:3.9.5

# WORKDIR /app

# COPY --from=build /app/target/PromactCustomerSuccessApplication.jar .

# CMD ["java", "-jar", "PromactCustomerSuccessApplication.jar"]
