#!/usr/bin/env bash
set -euo pipefail
docker compose up --build -d
echo "Dashboard: http://localhost:4200"
echo "OpenAPI:   http://localhost:8080/swagger-ui.html"
