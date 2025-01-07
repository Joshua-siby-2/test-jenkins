pipeline {
    agent {
        docker {
            image 'maven:3.8.7-openjdk-17' // Maven with JDK for Java build
        }
    }

    environment {
        DOCKER_IMAGE = 'your-dockerhub-username/java-app'
        DOCKER_CREDENTIALS_ID = 'dockerhub-credentials-id'
        GIT_CREDENTIALS_ID = 'github-credentials-id'
    }

    stages {
        stage('Checkout Code') {
            steps {
                git credentialsId: env.GIT_CREDENTIALS_ID, url: 'https://github.com/your-organization/your-repo.git'
            }
        }

        stage('Build and Test') {
            steps {
                sh 'mvn clean package'
            }
        }

        stage('Run JUnit Tests') {
            steps {
                sh 'mvn test'
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    sh "docker build -t ${DOCKER_IMAGE}:${BUILD_NUMBER} ."
                }
            }
        }

        stage('Push Docker Image') {
            steps {
                script {
                    docker.withRegistry('', env.DOCKER_CREDENTIALS_ID) {
                        sh "docker push ${DOCKER_IMAGE}:${BUILD_NUMBER}"
                    }
                }
            }
        }

        stage('Deploy Application') {
            steps {
                script {
                    sh """
                        docker stop java-app || true
                        docker rm java-app || true
                        docker run -d -p 8080:8080 --name java-app ${DOCKER_IMAGE}:${BUILD_NUMBER}
                    """
                }
            }
        }
    }

    post {
        success {
            echo 'Pipeline completed successfully.'
        }
        failure {
            echo 'Pipeline failed.'
        }
    }
}
