docker run --name cp-keycloak -p 8001:8080 -e KEYCLOAK_ADMIN=admin -e KEYCLOAK_ADMIN_PASSWORD=admin -e TZ=Australia/Sydney quay.io/keycloak/keycloak:latest start-dev