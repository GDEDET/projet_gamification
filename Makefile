# Executables (local)
DOCKER_COMP = docker compose

DOCKER = docker

# Docker containers
MAVEN_CONT = $(DOCKER) exec -it projet_gamification-maven-1
DB_CONT = $(DOCKER) exec -it projet_gamification-db-1

# Executables
MAVEN      = $(MAVEN_CONT) mvn

# Misc
.DEFAULT_GOAL = help
.PHONY        : help build up start down logs sh composer vendor sf cc

## —— 🎵 🐳 The Symfony Docker Makefile 🐳 🎵 ——————————————————————————————————
help: ## Outputs this help screen
	@grep -E '(^[a-zA-Z0-9\./_-]+:.*?##.*$$)|(^##)' $(MAKEFILE_LIST) | awk 'BEGIN {FS = ":.*?## "}{printf "\033[32m%-30s\033[0m %s\n", $$1, $$2}' | sed -e 's/\[32m##/[33m/'

## —— Docker 🐳 ————————————————————————————————————————————————————————————————
build: ## Builds the Docker images
	@$(DOCKER_COMP) build --pull --no-cache

up: ## Start the docker hub in detached mode (no logs)
	@$(DOCKER_COMP) up --detach

start: build up ## Build and start the containers

down: ## Stop the docker hub
	@$(DOCKER_COMP) down --remove-orphans

logs: ## Show live logs
	@$(DOCKER_COMP) logs --tail=0 --follow

sh-maven: ## Connect to the MAVEN container
	@$(MAVEN_CONT) sh

sh-db:
	@$(DB_CONT) sh

mvn:
	@$(eval c ?=)
	@$(MAVEN) $(c)

mvn-test:
	mvn clean test -Dtest=$(c)