pipeline {
  agent any
  tools { jdk 'temurin-21'; nodejs 'node-24' }
  stages {
    stage('Traceability') { steps { sh './scripts/traceability-gate.sh HEAD' } }
    stage('Backend') { steps { sh './mvnw verify' } }
    stage('Frontend') { steps { sh 'npm --prefix frontend ci && npm --prefix frontend test -- --watch=false && npm --prefix frontend run build' } }
    stage('Delivery descriptors') { steps { sh 'docker compose config --quiet' } }
  }
}
