

pipeline {
    agent any
    tools {
        // Use the Maven installation defined in Global Tool Configuration
        ansible 'Ansible'
        maven 'Apache Maven'
    }

	environment {

	   // DOCKERHUB_USERNAME = credentials('dockerhub-cred').username
    //     DOCKERHUB_PASSWORD = credentials('dockerhub-cred').password
        DOCKERHUB_CRED = credentials('dockerhub-cred')
        DOCKER_IMAGE_NAME = "calcy-image"
        REPOSITORY_NAME = "calcy-repo"
        CONTAINER_NAME = "calcy-container"
        DOCKER_FILE_PATH = "Dockerfile"
        DOCKER_IMAGE_TAG = "latest"
        DOCKER_FULL_IMAGE_NAME = "${DOCKERHUB_CRED_USR}/${REPOSITORY_NAME}:${DOCKER_IMAGE_TAG}"
       // Define placeholder values for Docker Hub credentials
    }

    stages {
        //  stage('Set Environment Variables') {
        //      steps {
        //          script {
        //             // Retrieve Docker Hub credentials dynamically
        //             DOCKERHUB_USERNAME = credentials('dockerhub-cred').username
        //             DOCKERHUB_PASSWORD = credentials('dockerhub-cred').password
        //             // Use the credentials to construct the full image name
        //             DOCKER_FULL_IMAGE_NAME = "${DOCKERHUB_USERNAME}/${REPOSITORY_NAME}:${DOCKER_IMAGE_TAG}"
        //         }
        //      }
        //  }
        stage('Checkout') {
            steps {
                cleanWs()
                git branch: 'dev', credentialsId: 'dockerhub-cred', url: 'https://github.com/phoenixsac/calcy.git'
            }
        }

        stage('Build') {
            steps {
                // Use Maven to build the project
                    // sh "pwd"
                    // println sh(script: 'mvn --version', returnStdout: true)
                    sh 'mvn clean install'

            }
        }

        stage('Test') {
            steps {
                // Run tests using Maven
                    sh "pwd"
                    sh 'mvn test'
            }
        }

		stage('Build Docker Image and add Tags') {

            steps {
                script {
                    //docker.build("${DOCKER_IMAGE_NAME}", "-f ${DOCKER_FILE_PATH} .")
                    echo "Docker Image Name: ${DOCKER_FULL_IMAGE_NAME}"
                    sh 'docker build("${DOCKER_IMAGE_NAME}", '.')
                    // sh 'docker tag ${DOCKER_IMAGE_NAME}:${DOCKER_IMAGE_TAG} ${DOCKER_FULL_IMAGE_NAME}'
                    //sh 'docker push ${DOCKER_FULL_IMAGE_NAME}'
					//docker.build("${DOCKER_FULL_IMAGE_NAME}:latest", "-f ${DOCKER_FILE_PATH} .")
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


		stage('Deploy with Ansible') {
            steps {
                // Run Ansible playbook to deploy the Docker container
                    script{
                        sh 'wsl ansible --version'
                        sh 'wsl ansible-playbook -vvvv deploy.yaml'
                    }
            }
        }

    }
}
