# maze-generator\
This is an API that generator square maze image up to 100 size.
#How to run locally:
1. mvn package
2. java -jar target/dependency/webapp-runner.jar target/*.war

Request sample:
GET http://127.0.0.1:5000/maze?width=50&height=50
GET http://127.0.0.1:5000/maze?width=20&height=20&showMainPath=true