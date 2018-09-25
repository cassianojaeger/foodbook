# FoodBook BOLADO

### Setup

-  Postgres DB
    * No docker:
        * sudo docker pull postgres
        * sudo docker run --name postgres -p 5432:5432 -e POSTGRES_PASSWORD=1234 -d postgres
        * sudo start postgres
    - No projeto:
        - spring.datasource.url=jdbc:postgresql://localhost:**<database_port>**/**<database_name>**
        - spring.datasource.username=**<postgres_user>**
        - spring.datasource.password=**<postgres_password>**


- Lombok Project
    - Instalar o plugin para o Intellij do Lombok Project.
    - **File** >> **Settings** >> **Plugins** >> **Browse Repo** 

- Só de subir o projeto, o banco ja vai estar setado corretamente, mas precisamos popular os usuários e authorities manualmente.
(Trust me, foi a maneira mais facil que achei, ja que pra importar as tabelas do oAuth2 nós estamos usando um framework de migração de dados full bugada)
    - **Resources** >> **DB** >> **standalone** >> **insert_auth_users.sql**
    
Acho que é isso ai. Valeuss


