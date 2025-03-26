.PHONY: build run clean help

help:
	@echo "Available commands:"
	@echo "  make build  - Build the Docker image"
	@echo "  make run    - Run the application in Docker"
	@echo "  make clean  - Stop and remove containers"
	@echo "  make help   - Show this help"

build:
	@echo "Building Docker image..."
	docker-compose build

run:
	@echo "Starting application..."
	docker-compose up -d
	@echo "Application is running on http://localhost:8080"
	@echo "API endpoints:"
	@echo "  POST /api/convert"
	@echo "  GET /api/history?date=YYYY-MM-DD or transactionId=ID"
	@echo "  GET /api/exchange-rate/from/to?"

clean:
	@echo "Stopping and removing containers..."
	docker-compose down