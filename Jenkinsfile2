// pipeline {
//      agent 
//     {
//         // Environnrement Node, npm, navigateur chromium, git
        
//         docker {
//             image  'selenium/standalone-chrome:nightly'
//             args '--user=root --entrypoint=""'
//         }
          

//     }

//     // paramètre pour ajouter les tags et rapport
//     // tags
//     // rapport
//     parameters{
         
//         choice(name:"browser",choices:["chrome","firefox","edge"],description:"choisissez le navigateur")
       
//     }
//     // browser if browser == chromium on lance les testes sinon on affiche aucun test est lancé

//     stages{
//         stage('install dépendence'){
//             steps{
//                 sh 'mvn --version'
//                 sh 'mvn clean compile'
//             }
//         }
//         stage('vérifier les pré-requis'){
//             steps{
//                          sh "mvn test -Dtest=Authentification -Dbrowser=${params.browser}"
                 
//             }
//         }
//     }


// }
pipeline {
    agent {
        docker {
            // Image Maven officielle avec JDK 17
            image 'maven:3.9.6-eclipse-temurin-17'
            args '''
                -v /root/.m2:/root/.m2
                --shm-size=2g
                -u root
            '''
        }
    }

    // ─────────────────────────────────────────
    // PARAMÈTRES
    // ─────────────────────────────────────────
    parameters {
        choice(
            name: 'BROWSER',
            choices: ['chrome', 'firefox', 'edge'],
            description: 'Navigateur utilisé pour les tests Selenium'
        )
        string(
            name: 'BASE_URL',
            defaultValue: 'https://example.com',
            description: 'URL de base de l\'application à tester'
        )
        string(
            name: 'MAVEN_PROFILE',
            defaultValue: 'selenium',
            description: 'Profil Maven à activer (ex: selenium, regression, smoke)'
        )
        string(
            name: 'TEST_CLASS',
            defaultValue: '',
            description: 'Classe de test spécifique à exécuter (laisser vide pour tout lancer)'
        )
        booleanParam(
            name: 'HEADLESS',
            defaultValue: true,
            description: 'Lancer le navigateur en mode headless (sans interface graphique)'
        )
        string(
            name: 'SELENIUM_GRID_URL',
            defaultValue: '',
            description: 'URL du Selenium Grid (optionnel, laisser vide pour local)'
        )
        choice(
            name: 'ENVIRONMENT',
            choices: ['dev', 'staging', 'prod'],
            description: 'Environnement cible'
        )
    }

    // ─────────────────────────────────────────
    // VARIABLES D'ENVIRONNEMENT
    // ─────────────────────────────────────────
    environment {
        BROWSER              = "${params.BROWSER}"
        BASE_URL             = "${params.BASE_URL}"
        HEADLESS             = "${params.HEADLESS}"
        SELENIUM_GRID_URL    = "${params.SELENIUM_GRID_URL}"
        APP_ENV              = "${params.ENVIRONMENT}"
        // Répertoire de cache Maven local (persisté via le volume monté)
        MAVEN_OPTS           = "-Dmaven.repo.local=/root/.m2/repository"
        // Rapport Allure / Surefire
        REPORTS_DIR          = "target/surefire-reports"
    }

    options {
        // Durée max du pipeline : 1 heure
        timeout(time: 1, unit: 'HOURS')
        // Conserver les 10 derniers builds
        buildDiscarder(logRotator(numToKeepStr: '10'))
        // Horodatage dans les logs
        timestamps()
        // Pas de checkout concurrent
        disableConcurrentBuilds()
    }

    // ─────────────────────────────────────────
    // ÉTAPES
    // ─────────────────────────────────────────
    stages {

        stage('🔍 Checkout') {
            steps {
                checkout scm
                echo "✅ Sources récupérées — branche : ${env.GIT_BRANCH}"
            }
        }

        stage('🔧 Compilation') {
            steps {
                echo "🛠️  mvn clean compile avec le profil : ${params.MAVEN_PROFILE}"
                sh """
                    mvn clean compile \
                        -P${params.MAVEN_PROFILE} \
                        -Dbrowser=${params.BROWSER} \
                        -Denv=${params.ENVIRONMENT} \
                        --batch-mode \
                        --no-transfer-progress
                """
            }
        }

        stage('🧪 Tests Selenium') {
            steps {
                script {
                    // Construction dynamique de la commande Maven
                    def mvnCmd = "mvn test"
                    mvnCmd    += " -P${params.MAVEN_PROFILE}"
                    mvnCmd    += " -Dbrowser=${params.BROWSER}"
                    mvnCmd    += " -Dheadless=${params.HEADLESS}"
                    mvnCmd    += " -Dbase.url=${params.BASE_URL}"
                    mvnCmd    += " -Denv=${params.ENVIRONMENT}"

                    // Classe spécifique (optionnel)
                    if (params.TEST_CLASS?.trim()) {
                        mvnCmd += " -Dtest=${params.TEST_CLASS}"
                    }

                    // Selenium Grid (optionnel)
                    if (params.SELENIUM_GRID_URL?.trim()) {
                        mvnCmd += " -Dselenium.grid.url=${params.SELENIUM_GRID_URL}"
                    }

                    mvnCmd += " --batch-mode --no-transfer-progress"

                    echo "▶️  Commande : ${mvnCmd}"
                    sh mvnCmd
                }
            }
            post {
                always {
                    // Publication des résultats JUnit
                    junit allowEmptyResults: true,
                          testResults: '**/target/surefire-reports/*.xml'
                }
            }
        }

        stage('📊 Rapports') {
            steps {
                echo "📦 Archivage des rapports de tests..."
                archiveArtifacts artifacts: 'target/surefire-reports/**/*',
                                 allowEmptyArchive: true

                // Si vous utilisez Allure, décommentez les lignes ci-dessous :
                // allure([
                //     includeProperties: false,
                //     jdk: '',
                //     properties: [],
                //     reportBuildPolicy: 'ALWAYS',
                //     results: [[path: 'target/allure-results']]
                // ])
            }
        }
    }

    // ─────────────────────────────────────────
    // NOTIFICATIONS POST-BUILD
    // ─────────────────────────────────────────
    post {
        success {
            echo "✅ Pipeline terminé avec succès — Navigateur : ${params.BROWSER} | Env : ${params.ENVIRONMENT}"
        }
        failure {
            echo "❌ Pipeline échoué — Vérifiez les rapports dans target/surefire-reports/"
            // Décommentez pour envoyer un email :
            // mail to: 'equipe@example.com',
            //      subject: "❌ Build échoué : ${env.JOB_NAME} #${env.BUILD_NUMBER}",
            //      body: "Voir les détails : ${env.BUILD_URL}"
        }
        unstable {
            echo "⚠️  Pipeline instable — certains tests ont échoué."
        }
        always {
            cleanWs()
        }
    }
}
