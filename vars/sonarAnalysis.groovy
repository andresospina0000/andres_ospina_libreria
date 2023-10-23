def call(boolean abortPipeline = false) {
    def abort = abortPipeline
    withSonarQubeEnv(installationName: 'Sonar Local',credentialsId: 'AO_Token') {
        sh "${tool("SonarScanner")}/bin/sonar-scanner -Dsonar.projectKey=threepoints_devops_webserver -Dsonar.projectName=threepoints_devops_webserver"
    }
    options {
        timeout(time: 1, unit: 'MINUTES')
    }
    waitForQualityGate abortPipeline: false
}