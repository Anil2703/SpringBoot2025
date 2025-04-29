pipeline {
    agent any

    tools {
        maven 'Maven 3'   // Match the name you configured in Jenkins global tools
    }

    environment {
        JAR_NAME = "app.jar"
    }

    stages {
        stage('Clone') {
            steps {
                git branch: 'main', url: 'https://github.com/your-username/your-repo.git'
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Deploy') {
            steps {
                echo 'Deploying Spring Boot app...'
                sh 'pkill -f app.jar || true' // Stop any previous app
                sh 'nohup java -jar target/*.jar > app.log 2>&1 &'
            }
        }
    }

    post {
        success {
            echo '✅ App deployed locally!'
        }
        failure {
            echo '❌ Build or deploy failed.'
        }
    }
}