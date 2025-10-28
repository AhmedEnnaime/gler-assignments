pipeline {
    agent {
        docker {
            image 'maven:3.9.8-eclipse-temurin-17'
            args '-v /root/.m2:/root/.m2'
        }
    }

    environment {
        DB_HOST = "db"
        DB_PORT = "5432"
        DB_NAME = "assignment"
        DB_USER = "postgres"
        DB_PASSWORD = "postgres"
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build & Test') {
            steps {
                sh 'mvn clean verify -DskipITs=false'
            }
        }

        stage('Archive Coverage Report') {
            steps {
                archiveArtifacts artifacts: 'assignment-backend/target/site/jacoco/index.html', fingerprint: true
            }
        }
    }

    post {
        success {
            echo "Build passed and coverage >= 70%!"
        }
        failure {
            echo "Build failed (possibly coverage < 70%)"
        }
    }
}
