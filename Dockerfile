FROM eclipse-temurin:21-jdk

WORKDIR /workspace/SFMP_BE
COPY . .
RUN chmod +x gradlew

EXPOSE 8080
CMD ["sh", "-lc", "./gradlew bootRun --no-daemon"]
