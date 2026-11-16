pipeline {
    agent any

    tools {
            maven 'Maven'
        }

    stages {
        stages {

                stage('Archive Results') {
                    steps {
                        archiveArtifacts artifacts: 'jqassistant-sarif-report.json',
                                       fingerprint: true,
                                       onlyIfSuccessful: true
                    }
                }
        }
    }

    post {
        always {
             recordIssues(
                tool: sarif(pattern: 'jqassistant-sarif-report.json'),
                id: 'jQAssistant',
                name: 'jQAssistant'
            )
        }
    }
}
