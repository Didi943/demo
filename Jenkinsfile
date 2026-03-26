pipeline {
     agent 
    {
        // Environnrement Node, npm, navigateur chromium, git
        
        docker {
            image  'selenium/standalone-chrome:nightly'
            args '--user=root --entrypoint=""'
        }
          

    }

    // paramètre pour ajouter les tags et rapport
    // tags
    // rapport
    parameters{
         
        choice(name:"browser",choices:["chrome","firefox","edge"],description:"choisissez le navigateur")
       
    }
    // browser if browser == chromium on lance les testes sinon on affiche aucun test est lancé

    stages{
        stage('install dépendence'){
            steps{
                sh 'mvn --version'
                sh 'mvn clean compile'
            }
        }
        stage('vérifier les pré-requis'){
            steps{
                         sh "mvn test -Dtest=Authentification -Dbrowser=${params.browser}"
                 
            }
        }
    }


}