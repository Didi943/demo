pipeline {
    agent {
        docker {
            // ✅ OPTION A : image Selenium qui inclut Chrome + ChromeDriver + JDK
            image 'selenium/standalone-chrome:latest'
            args '''
                -v /root/.m2:/root/.m2
                --shm-size=2g
                -u root
                --entrypoint=""
            '''

            // ❌ ANCIENNE IMAGE (pas de Chrome dedans) :
            // image 'maven:3.9.6-eclipse-temurin-17'
        }
    }

    parameters {
        choice(
            name: 'BROWSER',
            choices: ['chrome', 'firefox', 'edge'],
            description: 'Navigateur utilisé pour les tests Selenium'
        )
    }

    stages {

        stage('🔧 Installation Maven') {
            steps {
                // L'image Selenium n'a pas Maven → on l'installe
                sh '''
                    apt-get update -qq && \
                    apt-get install -y maven --no-install-recommends -qq
                '''
                sh 'mvn --version'
            }
        }

        stage('🔧 Compilation') {
            steps {
                sh 'mvn clean compile'
            }
        }

        stage('✅ Vérification Chrome') {
            steps {
                sh 'google-chrome --version'
                sh 'chromedriver --version'
            }
        }

        stage('🧪 Tests Selenium') {
            steps {
                script {
                    def mvnCmd = "mvn test -Dtest=LoginPom -Dbrowser=${params.BROWSER}"
                    echo "Commande : ${mvnCmd}"
                    sh mvnCmd
                }
            }
        }
    }

    post {
        
        failure {
            echo '❌ Pipeline échoué'
        }
        success {
            echo '✅ Pipeline réussi'
        }
    }
}
