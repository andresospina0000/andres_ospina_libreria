def call(boolean abortPipeline) {
    
    echo "${abortPipeline}"

    if(abortPipeline){
        currentBuild.result = 'ABORTED'
        error('Ejecución abortada por el usuario!')
    }
    
    echo "SonarQube analysis"
    withSonarQubeEnv(installationName: 'Sonar Local',credentialsId: 'AO_Token') {
        sh "${tool("SonarScanner")}/bin/sonar-scanner -Dsonar.projectKey=threepoints_devops_webserver -Dsonar.projectName=threepoints_devops_webserver"
    }    
    
    timeout(time: 5, unit: 'MINUTES') {
        def qg = waitForQualityGate()
    } 
}