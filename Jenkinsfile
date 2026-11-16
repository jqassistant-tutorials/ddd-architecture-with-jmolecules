pipeline {
    agent any

    tools {
            maven 'Maven'
        }

    stages {
        stage('build') {
            steps {
                sh 'mvn clean verify'
            }
        }
        stage('Archive Results') {
            steps {
                archiveArtifacts artifacts: 'target/jqassistant/report/sarif/jqassistant-sarif-report.json',
                                 fingerprint: true,
                                 onlyIfSuccessful: true
            }
        }
    }

    post {
        always {
             recordIssues(
                tool: sarif(pattern: 'target/jqassistant/report/sarif/jqassistant-sarif-report.json'),
                id: 'jQAssistant',
                name: 'jQAssistant'
            )
        }
    }
}
