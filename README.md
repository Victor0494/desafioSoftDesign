
# Desafio NTConsult

O desafio consistia em criar um sistema de reserva de hotéis capaz de lidar com um grande volume de acessos simultâneos que permitirá aos usuários pesquisar comparar e reservar quartos de hotel de forma eficiente.


## Get Started
*  Clone o repositório

        https://github.com/Victor0494/desafio.git


* Dependências:

    Java na versão 17:
    [link](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)

    RabbitMq:
    [link](https://hub.docker.com/_/rabbitmq)

 * Gere o arquivo ntconsult-0.0.1-SNAPSHOT.jar no diretório target. Observe que o arquivo gerado não é executável.

        mvn package
* Para rodar o projeto execute o seguinte comando

        java -jar target/ntconsult-0.0.1-SNAPSHOT

## Documentação da API
A documentação da API, pode ser acessada pela URL 
    
    http://server:port/context-path/v3/api-docs ou http://localhost:8080/v3/api-docs, caso localmente
* Swagger
   
    O Swagger pode ser acesso atráves http://server:port/context-path/swagger-ui.html

* Metricas ou saúde do API
    http://server:port/actuator

