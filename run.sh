#!/bin/bash

export $(grep -v '^#' .env | xargs)

echo "Conectando a base de datos en $DB_HOST:$DB_PORT..."

./gradlew.bat bootRun
