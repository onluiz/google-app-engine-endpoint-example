Projeto Google Cloud Endpoint.

Objetivo: criar template de um projeto onde seja possível gerar uma API para a utilização em aplicativos mobile
e web, utilizando o Google App Engine.

Pré-requisitos:
- java7;
- maven 3.3.9;
- Google Cloud SDK;
- java-appengine-sdk;
- paciência.

Tutorial em inglês: https://cloud.google.com/endpoints/docs/frameworks/java/quickstart-frameworks-java

Instruções básicas:
- No pom.xml, alterar **endpoints.project.id** para o ID do seu app no google app engine;
- Executar **mvn clean package** no diretório do pom.xml;
- Executar mvn exec:java -DGetSwaggerDoc para gerar o arquivo openapi.json. Esse arquivo posteriormente é enviado para o
Google App Engine. Ele é um JSON com todas os detalhes da sua API;
- gcloud service-management deploy openapi.json;
- Rodar a API localmente: **mvn appengine:run**;
- Fazer deploy da API: **mvn appengine:deploy**.
