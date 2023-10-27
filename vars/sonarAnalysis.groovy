def call(boolean abortPipeline = false) {
    def abort = abortPipeline
    echo abort
    withSonarQubeEnv(installationName: 'Sonar Local',credentialsId: 'AO_Token') {
        sh "${tool("SonarScanner")}/bin/sonar-scanner -Dsonar.projectKey=threepoints_devops_webserver -Dsonar.projectName=threepoints_devops_webserver"
    }
    
    timeout(time: 5, unit: 'MINUTES') {
        def qg = waitForQualityGate abortPipeline: abort
    }
    
}