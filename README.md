
# Desafio SoftDesign

No cooperativismo, cada associado possui um voto e as decisões são tomadas em assembleias,
por votação. Imagine que você deve criar uma solução para dispositivos móveis para gerenciar
e participar dessas sessões de votação.

Essa solução deve ser executada na nuvem e promover as seguintes funcionalidades através de
uma API REST.

    ● Cadastrar uma nova pauta

    ● Abrir uma sessão de votação em uma pauta (a sessão de votação deve ficar aberta por
    um tempo determinado na chamada de abertura ou 1 minuto por default)

    ● Receber votos dos associados em pautas (os votos são apenas Sim/Não. Cada
    associado é identificado por um id único e pode votar apenas uma vez por pauta)

    ● Contabilizar os votos e dar o resultado da votação na pauta

## 🚀 Get Started
*  Clone o repositório

        https://github.com/Victor0494/desafioSoftDesign.git


* Dependências:

    Java na versão 21:
    [download](https://download.oracle.com/java/21/latest/jdk-21_windows-x64_bin.exe)

    Redis:
    [download](https://redis.io/downloads/)
    
    Mysql:
    [download](https://www.mysql.com/downloads/)

## ⚙️ Observações de Configuração ⚠️
 * Aplicação rodando em um banco H2, em memória.
 * Foi dispobilizado um docker-compose com o **Redis** e um **banco de dados MySQL** na raiz do projeto
 * A api para validação de CPF dispobilizada no desafio não funciona, pois o serviço parece ter sido removido do ar, porém realizei a implementação do mesmo, mas também realizei a criação de um modulo que replicasse o comportamento descrito no desafio, ou seja há duas implementações para validação do CPF.
 * Para realizar a implementação descrita no desafio, basta passar a variável **'local'** como **false**, no endpoint **/v1/vote/{topicId}**.
 * Caso queira seguir com a implementação que eu criei, basta rodar o modulo **Generate**, dentro do projeto

## Build do projeto
* Gere o arquivo softDesign-0.0.1-SNAPSHOT.jar no diretório target. Observe que o arquivo gerado não é executável.

       mvn package
* Para rodar o projeto execute o seguinte comando

        java -jar target/softDesign-0.0.1-SNAPSHOT

## 📜 Documentação da API
A documentação da API, pode ser acessada pela URL 
    
    http://server:port/context-path/v3/api-docs ou http://localhost:8080/v3/api-docs, caso localmente
* Swagger
   
    O Swagger pode ser acesso atráves http://server:port/context-path/swagger-ui.html

* Metricas ou saúde do API
    http://server:port/actuator

