pipeline {

    environment {
    imagerepo = '/kumarmanvit/CodeExampleReverse'
    imagename = 'codetest'
	UNIQUE_ID = "${env.BRANCH_NAME}-${env.BUILD_NUMBER}"
	RELEASE_VERSION = getReleaseVersion()

	SERVICE_NAME = 'CodeExample'
	SERVICE_APP_NAME = "${SERVICE_NAME}-service"
	ImageNameTag  =  "${imagerepo}/${imagename}:${RELEASE_VERSION}"
	pullCredential = 'regcred'
    pushCredential = 'regcred'


    }

    agent any

	 stages {

        stage('Build'){
            steps {
                container('docker') {
                    script {
                        currentBuild.description = 'Building docker image ...'
                        docker.withRegistry("https://" + imagerepo, pullCredential) {

                            serviceImage = docker.build("${imagename}","--no-cache  -f ./${SERVICE_APP_NAME}/docker/Dockerfile .")
                        }
                        currentBuild.description = 'DockerDocker image successfully built.'

                    }

                }
            }
        }

       stage('Publish Service Image') {
            when {
                allOf {
                    branch pattern: RELEASE_BRANCH_PATTERN, comparator: "REGEXP"
                }
            }
            steps {
                script {
                    container('docker') {
                        docker.withRegistry("https://" + imagerepo, pushCredential) {
                            sh "docker tag ${imagename} ${ImageNameTag}"
                            sh "docker push ${ImageNameTag}"
                        }
                    }
                }
                markCertifiedImage()
                echo "Successfully uploaded service image to staging repository with tags ${ImageNameTag}"
            }
        }

	}
}

  post {
    always {
			cleanWs()

			deleteDir()

            dir("${env.WORKSPACE}@tmp") {
                deleteDir()
            }

            dir("${env.WORKSPACE}@script") {
                deleteDir()
            }

            dir("${env.WORKSPACE}@script@tmp") {
                deleteDir()
            }


    }
  }