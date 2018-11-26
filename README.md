# DesafioBackEndB2W
Desafio Back End B2W (API Star Wars)

## Sobre
API desenvolvida para o desafio back-end Star Wars da B2W. A API perimite fazer requisições para adicionar, remover, listar e buscar planetas dos filmes Star Wars, cujos dados são armazenados no banco de dados da aplicação.

## Tecnologias utilizadas
Java 8, Mongo DB, Apache Tomcat 9 e Eclipse Java EE IDE.

## Configurações
Devem ser instalados Tomcat 9, JAVA JDK 8, Mongo DB e Eclipse Java EE IDE. O Mongo DB deverá rodar na porta 27017. Deve ser feito o download dos arquivos do projeto para o workspace do Eclipse. Após isso, no Eclipse, deve ser criado servidor Tomcat 9 e adicionada aplicação a esse servidor. Esse servidor deverá ser "startado" para rodar a aplicação.

## Utilização da API
A porta utilizada nos exemplos é a 8000. Caso seja desejável utilizar outra, basta substutuí-la na URL.

### Listando planetas
Para listar planetas, deve-se fazer uma requisição GET, sem adicionar parâmetros, para a URL:
http://localhost:8000/DesafioBackEndB2W/ws/starwarsplanetsapi/listplanets

Os planetas serão retornados em um array de JSONs com id, nome, clima, terreno e número de aparições de cada planeta em filmes da franquia.

### Buscando planetas por nome
Para listar planetas, deve-se fazer uma requisição GET, com parâmetro nome na URL, para a URL:
http://localhost:8000/DesafioBackEndB2W/ws/starwarsplanetsapi/searchbyname/

Caso exista, será retornado um JSON com id, nome, clima, terreno e número de aparições do planeta em filmes da franquia. Do contrário, será retornado status code 204.

### Buscando planetas por id
Para listar planetas, deve-se fazer uma requisição GET, com parâmetro id na URL, para a URL:
http://localhost:8000/DesafioBackEndB2W/ws/starwarsplanetsapi/searchbyid/

Caso exista, será retornado um JSON com id, nome, clima, terreno e número de aparições do planeta em filmes da franquia. Do contrário, será retornado status code 204.

### Deletando planetas
Para listar planetas, deve-se fazer uma requisição DELETE, com parâmetro id na URL, para a URL:
http://localhost:8000/DesafioBackEndB2W/ws/starwarsplanetsapi/deleteplanet/

Caso exista no banco de dados, será removido o planeta e retornado status code 200. Do contrário, será retornado status code 204.

### Adicionando planetas
Para listar planetas, deve-se fazer uma requisição POST para a URL:
http://localhost:8000/DesafioBackEndB2W/ws/starwarsplanetsapi/addplanet/

O conteúdo do corpo da requisição deve ser um JSON com nome, clima e terreno do planeta. Será verificado o nome do planeta na SWAPI (Star Wars API) para saber se o planeta existe e em quantos filmes aparece. Se existir na SWAPI, será verificado se o planeta já está no banco de dados. Se não estiver, será incluído e o servidor retornará status code 201. Caso exista no banco de dados, será retornado status code 200. E se não existir na SWAPI, será retornado status code 204.
