FROM openjdk:20-jdk-oraclelinux7
COPY arachni-controller-storage-api/target/arachni-controller-storage.jar arachni-controller-storage.jar

ENV JAVA_OPTS = ""

EXPOSE 8080
CMD ["sh", "-c", "java -jar $JAVA_OPTS arachni-controller-storage.jar"]