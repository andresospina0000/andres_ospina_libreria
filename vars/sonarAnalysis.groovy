def call(boolean abortPipeline, String gitBranch) {
    
    echo "${abortPipeline} - ${gitBranch}"

    if(abortPipeline){
        abortPipeline: abort
    }
    def masterBranch = gitBranch.equalsIgnoreCase("master")
    def hotfixBranch = gitBranch.startsWith("hotfix")

    echo "${masterBranch} - ${hotfixBranch}"

    if(gitBranch.equalsIgnoreCase("master") || gitBranch.startsWith("hotfix")){
        abortPipeline: true
    }

    withSonarQubeEnv(installationName: 'Sonar Local',credentialsId: 'AO_Token') {
        sh "${tool("SonarScanner")}/bin/sonar-scanner -Dsonar.projectKey=threepoints_devops_webserver -Dsonar.projectName=threepoints_devops_webserver"
    }    
    
    timeout(time: 5, unit: 'MINUTES') {
        def qg = waitForQualityGate abortPipeline: abort
    }
    
    
}