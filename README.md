docker run --publish 5432:5432 --name masdb databasemas:1.0 
docker start masdb
docker-compose up --build