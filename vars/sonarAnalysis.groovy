def call(boolean abortPipeline, String gitBranch) {
    
    echo "${abortPipeline} - ${gitBranch}"

    if(abortPipeline){
        currentBuild.result = 'ABORTED'
        error('Ejecución abortada por el usuario!')
    }
    def masterBranch = gitBranch.equalsIgnoreCase("master")
    def hotfixBranch = gitBranch.startsWith("hotfix")

    echo "${masterBranch} - ${hotfixBranch}"

    if(masterBranch || hotfixBranch){        
        currentBuild.result = 'ABORTED'        
        error('Ejecución abortada. Rama master o hotfix!')
    }
    echo "SonarQube analysis"
    withSonarQubeEnv(installationName: 'Sonar Local',credentialsId: 'AO_Token') {
        sh "${tool("SonarScanner")}/bin/sonar-scanner -Dsonar.projectKey=threepoints_devops_webserver -Dsonar.projectName=threepoints_devops_webserver"
    }    
    
    timeout(time: 5, unit: 'MINUTES') {
        def qg = waitForQualityGate abortPipeline: abort
    }
    
    
}