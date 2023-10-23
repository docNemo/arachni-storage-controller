FROM openjdk:20-jdk-oraclelinux7
COPY arachni-storage-controller-api/target/arachni-storage-controller.jar arachni-storage-controller.jar

ENV JAVA_OPTS = ""

EXPOSE 8080
CMD ["sh", "-c", "java -jar $JAVA_OPTS arachni-storage-controller.jar"]