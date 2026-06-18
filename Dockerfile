FROM eclipse-temurin:17-jre
   WORKDIR /app
   COPY target/*.jar app.jar
   ENV SPRING_PROFILES_ACTIVE=prod
   ENV APP_ENV=prod
   ENV APP_VERSION=1.0.0
   EXPOSE 8080
   ENTRYPOINT ["java", "-jar", "app.jar"]