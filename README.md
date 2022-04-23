# Service Status Page

## Usage

Run MySQL:

```bash
docker-compose up
```

Run the application:

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

Run tests:

```bash
./gradlew test
```