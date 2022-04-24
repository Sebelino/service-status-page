# Service Status Page

This service continuously polls a set of web resources and determines whether each resource is currently available or down.
The results are displayed in a React frontend app, but can also be retrieved in JSON format by HTTP REST.
New services can be added either through the frontend app or by making a POST call directly to the backend application.

![image](https://user-images.githubusercontent.com/837775/164977776-c19f6e8b-9ee9-4c18-bf19-cb9265f3229f.png)

## Usage
Clone the repository:
```bash
git clone https://github.com/Sebelino/service-status-page
cd service-status-page/
```

Run the MySQL database:

```bash
docker-compose up
```

In a separate terminal, run the application:

```bash
./gradlew run
```

Get the status of all services:

```bash
curl "localhost:8888/status"
```

Add a new service:

```bash
curl "localhost:8888/status" -d '{"name": "Twitter", "url":"https://twitter.com"}'
```

Inspect the database:

```bash
mysql -h 127.0.0.1 -P 3309 -psecret -u dev dev
```

In a separate terminal, start the frontend:
```bash
npm install --prefix src/main/frontend
npm start --prefix src/main/frontend
```

Run tests:

```bash
./gradlew test
```

To clear the database:
```bash
docker-compose down
```
