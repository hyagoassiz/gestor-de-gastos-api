# 1 - Imagem base com Java 17 (ou a versão que seu projeto usa)
FROM eclipse-temurin:17-jdk

# 2 - Pasta onde o app vai ficar dentro do container
WORKDIR /app

# 3 - Copia o .jar gerado pelo Maven para dentro do container
COPY target/*.jar app.jar

# 4 - Diz como o container deve rodar seu backend
ENTRYPOINT ["java", "-jar", "app.jar"]
