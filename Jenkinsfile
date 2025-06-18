pipeline {
    agent any

    tools {
            maven 'Maven 3.9.10'
    }

    environment {
        DOCKER_IMAGE = 'notepad:latest'
    }

    stages {

        stage('Unit Test') {
            steps {
                bat 'mvn clean test'
            }
        }

        stage('Build Docker Image') {
            steps {
                bat 'docker build -t %DOCKER_IMAGE% .'
            }
        }
    }

    post {
        always {
            echo 'Pipeline finished.'
        }
        success {
            echo 'Build and tests successful!'
        }
        failure {
            echo 'Build or tests failed.'
        }
    }
}