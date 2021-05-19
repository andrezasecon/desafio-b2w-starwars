

![](https://github.com/andrezasecon/desafio-b2w-starWars/blob/develop/img/swapi.png)



Desafio implementado para o processo seletivo da B2W.

Nossos associados são aficionados por Star Wars e com isso, queremos criar um jogo com algumas informações da franquia.

Para possibilitar a equipe de front criar essa aplicação, queremos desenvolver uma API que contenha os dados dos planetas.



### **Requisitos:**

\- A API deve ser REST

\- Para cada planeta, os seguintes dados devem ser obtidos do banco de dados da aplicação, sendo inserido manualmente:

- Nome
- Clima
- Terreno



\- Para cada planeta também devemos ter a quantidade de aparições em filmes, que podem ser obtidas pela API pública do Star Wars: https://swapi.dev/about



### **Funcionalidades desejadas:**

\- Adicionar um planeta (com nome, clima e terreno)

\- Listar planetas

\- Buscar por nome

\- Buscar por ID

\- Remover planeta



### **Tecnologias Utilizadas:** 

\- Java 11

\- Spring Boot 2

\-  OpenFeign

\- MongoDB

\- Mock Server

\- Postman e JUNIT

\- Swagger

### **Como executar o projeto:**

Criar um pasta para o projeto, abrir o terminal nesta pasta e clonar o projeto

```
git clone https://github.com/andrezasecon/desafio-b2w-starWars.git
```



Abrir a pasta do projeto na IDE de sua preferência.

Configurar a VM Options para local

```
-Dspring.profiles.active=local
```



Para o banco de dados, utilizaremos uma docker, tenha instalado em seu ambiente o Docker Compose (https://docs.docker.com/compose/install/). Pelo terminal, acessar a pasta /devops/mongodb na raiz do projeto e executar o comando abaixo para subir a docker 

```
docker-compose up
```

Para verificar se a docker esta no ar, execute o comando abaixo

```
docker ps
```

Se tudo estiver ok as informações da docker serão exibidas

```
CONTAINER ID   IMAGE          COMMAND                  CREATED      STATUS      PORTS                                           NAMES
574ca0ea3a08   mongo:latest   "docker-entrypoint.s…"   8 days ago   Up 6 days   0.0.0.0:27017->27017/tcp, :::27017->27017/tcp   mongodb_mongodb_container_1
```

Agora basta dar um Run na classe ApiPlanetApplication e utilizar o Postman para acessar os endpoints da aplicação.



### **Funcionalidades implementadas**



#### Inserir um planeta

```
http://localhost:8080/api
```

No postman, na aba Body, clicar em raw e setar o tipo para JSON, os dados devem ser inseridos no formato Json

```
{
    "name": "Teste",
    "climate": "arid",
    "terrain": "Montain"
}
```

Retorno da consulta esperado com status 201 Created

![](https://github.com/andrezasecon/desafio-b2w-starWars/blob/develop/img/insert.png)



Retorno esperado caso o planeta já exista na base com status 409 Conflict

```
{
    "name": "Teste",
    "climate": "arid",
    "terrain": "Montain"
}
```



![](https://github.com/andrezasecon/desafio-b2w-starWars/blob/develop/img/insertconflict.png)



#### Listar todos os planetas

```
http://localhost:8080/api
```

Retorno esperado com status 200 ok

![](https://github.com/andrezasecon/desafio-b2w-starWars/blob/develop/img/findAll.png)



#### Buscar um planeta pelo id (GET)

```
http://localhost:8080/api/60a45703ab3d262026fdfcce
```

Retorno esperado com status 200 ok

![](https://github.com/andrezasecon/desafio-b2w-starWars/blob/develop/img/findbyid.png)



Retorno esperado em caso de id inválido 404 Not Found

```
http://localhost:8080/api/60a45703ab3d262026fd
```

![](https://github.com/andrezasecon/desafio-b2w-starWars/blob/develop/img/findbyiderror.png)



#### Buscar um planeta pelo nome

```
http://localhost:8080/api/find/tatooine
```

Retorno esperado com status 200 ok

![](https://github.com/andrezasecon/desafio-b2w-starWars/blob/develop/img/findByName.png)



Retorno esperado para nome inválido com status 204 Not Found

```
http://localhost:8080/api/find/tatooi
```

![](https://github.com/andrezasecon/desafio-b2w-starWars/blob/develop/img/findbynameerror.png)



#### Deletar um planeta pelo id

```
http://localhost:8080/api/
```

Retorno esperado com status 204 Not Found

![](https://github.com/andrezasecon/desafio-b2w-starWars/blob/develop/img/delete.png)



Retorno esperado caso o id não exista na base com status 404 Not Found

```
http://localhost:8080/api/60a47391802bdf5d4fc
```

![](https://github.com/andrezasecon/desafio-b2w-starWars/blob/develop/img/deleteerror.png)

#### 













