# Backend of Crabnet

This is backend of social media app project Crabnet


[Checkout frontend written with React](https://github.com/mateuszcer/crabnet-frontend)


## Run Locally

Clone the project


```bash
  git clone https://github.com/mateuszcer/crabnet-backend.git
```

Go to the project directory

```bash
  cd crabnet-backend
```

Build project

```bash
  ./gradlew buil
```

Run jar file

```bash
  java -jar build/libs/crabnet-1.0.0.jar
```

## Containerize 

```bash
  docker-compose build 
```

```bash
  docker-compose run 
```


## Environment Variables

To run this project, you will need to add the following environment variables to your .env file

`DATABASE_HOST`

`DATABASE_NAME`

`DATABASE_PORT`

`DATABASE_PASSWORD`

`DATABASE_USER` 

`EMAIL_USER`

`EMAIL_PASSWORD`

For now we use Google Gmail client so you can use your gmail account and create new App password


## Populating databse upon creation
You need to insert roles to database when it is created

```sql
INSERT INTO roles(name) VALUES('ROLE_USER');
INSERT INTO roles(name) VALUES('ROLE_MODERATOR');
INSERT INTO roles(name) VALUES('ROLE_ADMIN');

```



## Features

- Creating account
- Following other users
- Sharing, liking and removing posts
- Searching for users
- Setting profile picture from set of defaults