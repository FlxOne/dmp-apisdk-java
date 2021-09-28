pipeline {
    agent any
    options {
        timestamps()
        disableConcurrentBuilds()
        buildDiscarder logRotator(numToKeepStr: '5')
    }
    stages {
        stage('Execute tests') {
            steps {
                script {
                    def exitCode = sh returnStatus: true, script: "mvn test"
                    if (exitCode > 0) {
                        error("Testing failed")
                    }
                }
            }
        }
    }
    post {
//         success {
//             slackSend channel: '#dmp-dev', message: "Build ${env.CHANGE_BRANCH} ${BUILD_URL}"
//         }
//         failure {
//             slackSend channel: '#dmp-dev', message: "Build failed ${env.CHANGE_BRANCH} ${BUILD_URL}"
//         }
    }
}

