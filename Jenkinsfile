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
                        currentBuild.description = 'Docker image successfully built.'

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
        stage('Update Manifest') {
                            echo 'Updating deployment scripts'
            steps {
               withCredentials([usernamePassword(credentialsId: 'github-cicd-user', variable: 'USERPASS')]) {
                    sh "git clone https://github.com/kondakumar/CodeExampleReverse-ops.git"
                    sh "cd CodeExampleReverse-ops"
                    dir('CodeExampleReverse-ops') {
                       sh "sed -i 's/newTag.*/newTag: v${BUILD_NUMBER}/g' overlays/*/*kustomization.yaml"
                       sh "git config user.email kumar9.konda@gmail.com"
                       sh "git config user.name kondakumar"
                       sh "git add https://github.com/kondakumar/CodeExampleReverse-ops/overlays/*/*kustomization.yaml"
                       sh "git commit -m 'Update image version to: ${BUILD_NUMBER}'"
                       sh"git push https://$USERPASS@github.com/kondakumar/CodeExampleReverse-ops.git HEAD:main -f"
                    }
               }
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