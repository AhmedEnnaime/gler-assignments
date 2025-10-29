# Makefile

run:
	docker-compose up --build -d

down:
	docker-compose down

test:
	cd assignment-backend && mvn clean verify

build-jenkins:
	docker-compose build jenkins

run-jenkins:
	docker-compose up jenkins -d

stop-jenkins:
	docker-compose stop jenkins
