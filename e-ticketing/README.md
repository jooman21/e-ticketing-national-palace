# National Palace Museum Ticketing System

## Configure database in Docker
### 1. Pull the PostgreSQL Docker Image
Run the following command to pull the latest PostgreSQL image from Docker Hub:
```
docker pull postgres:latest
```
### 2. Run a PostgreSQL Container
Use the following command to create and start a PostgreSQL container:
```
docker run -d --name museum_db  --env POSTGRES_USER=root   --env POSTGRES_PASSWORD=museum123  --env POSTGRES_DB=palace  -p 5432:5432  -v pgdata:/var/lib/postgresql/data  postgres:latest
```
### 3. Pull the Redis Docker Image
```
docker pull redis
```
### 4. Run a Redis Container
Use the following command to create and start a Redis container:
```
docker run -d --name palace-redis -p 6379:6379 redis
```
## Gradle Build
This build includes a copy of the Gradle wrapper. You don't have to have Gradle installed on your system to build the project. Simply execute the wrapper along with the appropriate task, for example
```
./gradlew clean
```
## Run the project
```
./gradlew bootRun
```
### If you see this, it means the Application is running successfully
```
Application Started successfully!
.----------------------------------.       
'----------------------------------'      
   [    ]  [    ]  [    ]  [    ]         
    ||||    ||||    ||||    ||||          
    ||||    ||||    ||||    ||||           
    ||||    ||||    ||||    ||||           
    ||||    ||||    ||||    ||||          
    ||||    ||||    ||||    ||||           
    ||||    ||||    ||||    ||||           
   [    ]  [    ]  [    ]  [    ]          
.----------------------------------.       
'----------------------------------'       
```