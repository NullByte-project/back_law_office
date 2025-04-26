#!/bin/bash

export $(grep -v '^#' .env | xargs)

echo "Conectando a base de datos en $DB_URL..."

./gradlew.bat bootRun --daemon
