# Setting up the BackEnd

## Using Docker

### 1. Clone the repository

```sh
git clone https://github.com/dbms-24/Inventory-Manager-Backend.git
```

### 2. Change directory to the project folder

```sh
cd Inventory-Manager-Backend
```

### 3. Build the docker image

```sh
docker compose up --build
```

### Now we can check the health of the application by visiting the following URL in the browser / Postman

```sh
http://localhost:8080/health
```
