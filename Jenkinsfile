pipeline {
    agent any

    tools {
            jdk 'JDK-25'
            maven 'Maven'
        }

    stages {
        stage('build') {
            steps {
                withEnv(["JAVA_HOME=${tool 'JDK-25'}"]) {
                                    sh 'mvn clean verify'
                                }
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
