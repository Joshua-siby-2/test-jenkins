pipeline {
    agent any

    environment {
        DOCKER_IMAGE = 'joshuasiby/test-jenkins-project'
        DOCKER_CREDENTIALS_ID = 'dockerhub-credentials-id'
        GIT_CREDENTIALS_ID = 'github-credentials-id'
    }

    stages {
        stage('Checkout Code') {
            steps {
                git credentialsId: env.GIT_CREDENTIALS_ID, url: 'https://github.com/Joshua-siby-2/test-jenkins.git', branch: 'main'
            }
        }

        stage('Build and Test') {
            steps {
                sh '''
                    chmod +x mvnw
                    ./mvnw clean package
                '''
            }
        }

        stage('Run JUnit Tests') {
            steps {
                sh '''
                    chmod +x mvnw
                    ./mvnw test
                '''
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
                    sh '''
                        docker stop java-app || true
                        docker rm java-app || true
                        docker run -d -p 8081:8081 --name java-app ${DOCKER_IMAGE}:${BUILD_NUMBER}
                    '''
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
