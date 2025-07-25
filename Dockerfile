# Usa imagem base do Tomcat com Java 17
FROM tomcat:10.1-jdk17

# Remove aplicações padrão do Tomcat
RUN rm -rf /usr/local/tomcat/webapps/*

# Copia o .war gerado pelo Maven para dentro do Tomcat
COPY target/Pizzaria.war /usr/local/tomcat/webapps/ROOT.war

# Abre a porta padrão
EXPOSE 8080

# Inicia o Tomcat quando o container for iniciado
CMD ["catalina.sh", "run"]