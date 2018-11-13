# To-Do App Server


# Requirements

- Java 1.8
- Maven


# How to build

Run below command to generate .jar file
```
mvn package
```


# How to run

Run below command and use second project ToDoAppClient with your favourite browser.
```
java -jar backenddemo-0.0.1-SNAPSHOT.jar
```

# How to run with Docker

Open your docker shell and locate to project directory. Run below command and build docker image.
```
./build.sh 
```

After that run below command to start docker containers. 
``` 
docker-compose up
```

That's all. You can now use endpoint on http://localhost:5000 with your favourite browser!

Note: If you are using docker on windows. You should write your docker-machine's ip instead of localhost. Because exposing ports on windows hosts is directed to virtual machine which is between windows hosts and docker daemon.
