#Stage 1 Build the application
FROM maven:3.8.5-eclipse-temurin-17 AS build
#set the working directory in the container
WORKDIR /app
#Copy the pom.xml file to the container and download the dependencies
COPY pom.xml .
#copy the entire source code to the container
COPY src ./src
#Build the application jar and skip the tests
RUN mvn clean package -DskipTests

#Stage 2 Run the application
FROM eclipse-temurin:21-jdk-jammy

#Set the working directory in the container
WORKDIR /app
#Copy the jar file from the build stage to the runtime stage
COPY --from=build /app/target/*.jar app.jar
#Expose the port that the application will run on
EXPOSE 8080
#Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]