pipeline {
    agent any  // Use any available agent (in this case, your Windows machine)

	
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
                bat 'mvn clean package'
            }
        }

        stage('Run JUnit Tests') {
            steps {
                bat 'mvn test'
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    bat "docker build -t ${DOCKER_IMAGE}:${BUILD_NUMBER} ."
                }
            }
        }

        stage('Push Docker Image') {
            steps {
                script {
                    docker.withRegistry('', env.DOCKER_CREDENTIALS_ID) {
                        bat "docker push ${DOCKER_IMAGE}:${BUILD_NUMBER}"
                    }
                }
            }
        }

        stage('Deploy Application') {
            steps {
                script {
                    bat """
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
