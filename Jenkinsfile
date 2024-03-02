pipeline {
    agent any
    tools {
        ansible 'Ansible'
        maven 'Apache Maven'
    }

	environment {
        DOCKERHUB_CRED = credentials('dockerhub-cred')
        DOCKER_IMAGE_NAME = "calcy-image"
        REPOSITORY_NAME = "calcy-repo"
        CONTAINER_NAME = "calcy-container"
        DOCKER_FILE_PATH = "Dockerfile"
        DOCKER_IMAGE_TAG = "latest"
        DOCKER_FULL_IMAGE_NAME = "${DOCKERHUB_CRED_USR}/${REPOSITORY_NAME}:${DOCKER_IMAGE_TAG}"
    }

    stages {
        stage('Checkout') {
            steps {
                cleanWs()
                git branch: 'dev', credentialsId: 'dockerhub-cred', url: 'https://github.com/phoenixsac/calcy.git'
            }
        }

        stage('Build') {
            steps {
                    sh "pwd"
                    println sh(script: 'mvn --version', returnStdout: true)
                    sh 'mvn clean install'

            }
        }

        stage('Test') {
            steps {
                    sh "pwd"
                    sh 'mvn test'
            }
        }

		stage('Build Docker Image and add Tags') {

            steps {
                script {
                    echo "Docker Image Name: ${DOCKER_FULL_IMAGE_NAME}"
                    docker.build("${DOCKER_IMAGE_NAME}", '.')
                    sh 'docker tag ${DOCKER_IMAGE_NAME}:${DOCKER_IMAGE_TAG} ${DOCKER_FULL_IMAGE_NAME}'
                }
            }
        }

        stage('Push to Dockerhub') {

                steps {
                    script {
                        echo "Docker Image Name: ${DOCKER_FULL_IMAGE_NAME}"
                        sh 'docker push ${DOCKER_FULL_IMAGE_NAME}'
                    }
                }
            }

	    stage('Stop and remove existing container') {
	    steps {
		script {
		    sh 'docker stop calcy-container || true'
		    sh 'echo "Running containers:"'
                    sh 'docker ps'
		    sh 'docker rm -f calcy-container || true'
		    sh 'echo "All containers: "'
                    sh 'docker ps -a'
		}
	    }
	}


		stage('Deploy with Ansible') {
            steps {
                    script{
                        sh 'wsl ansible --version'
                        sh 'wsl ansible-playbook -vvv deploy.yaml'
                    }
             }
        }

    }
}
