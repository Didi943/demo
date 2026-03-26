pipeline {
    agent any

    parameters { 
        choice(
            name: 'BROWSER', 
            choices: ['chrome', 'firefox', 'edge'], 
            description: 'Select the browser to run the tests'
        )
    }

    stages {

        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build') {
            agent {
                docker {
                    image 'maven:3.8.4-openjdk-17'
                    args '-v /root/.m2:/root/.m2'
                }
            }
            steps {
                   dir('demo') {    // équivalent to "cd demo" in a shell
                        sh 'mvn clean compile'
                    }
               
            }
        }

        stage('Run Selenium Tests') {
            steps {
                script {
                    browserImage = ""  

                    if (params.BROWSER == "chrome") {
                        browserImage = "selenium/standalone-chrome:latest"
                    } else if (params.BROWSER == "firefox") {
                        browserImage = "selenium/standalone-firefox:latest"
                    } else if (params.BROWSER == "edge") {
                        browserImage = "selenium/standalone-edge:latest"
                    }

                    docker.image(browserImage).inside('-v /dev/shm:/dev/shm') {
                        sh 'mvn test'
                    }
                }
            }
        }
    }
}