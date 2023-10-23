def call(boolean abortPipeline = false) {
    def abort = abortPipeline
    withSonarQubeEnv(installationName: 'Sonar Local',credentialsId: 'AO_Token') {
        sh "${tool("SonarScanner")}/bin/sonar-scanner -Dsonar.projectKey=threepoints_devops_webserver -Dsonar.projectName=threepoints_devops_webserver"
    }
    
    // timeout(time: 1, unit: 'MINUTES'){
    //     def qg = waitForQualityGate abortPipeline: abort
    //     if (qg.status != 'OK') {
    //         error "Pipeline aborted due to quality gate failure: ${qg.status}"
    //     }
    // }
    
}