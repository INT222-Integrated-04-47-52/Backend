pipeline {
    agent any

    stages {

         stage('stop and remove container, image') {
            steps {
                script {
                    def imageExists = sh(script: 'docker images -q frontend', returnStdout: true) == ""
                    println imageExists

                    if( !imageExists ){
                           sh 'docker stop backend_con'
                           sh 'docker rm backend_con'
                           sh 'docker image rm backend'
                    }else {
                        echo 'Skip this stage '
                    }
                }
            }
        }

        stage('remove whole data') {
            steps {
                sh 'rm -rf *'
            }
        }

        stage('git clone') {
            steps {
                git branch: 'main',
                    credentialsId: 'MKT_Jenkins',
                    url: 'https://github.com/INT222-Integrated-04-47-52/Backend.git'
            }
        }

        stage('(deploy) start contianer') {
            steps {
                sh 'docker-compose up -d'
            }
        }

    }
}
