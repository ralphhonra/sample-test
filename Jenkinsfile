pipeline {
    agent {
        // We use the Python image because we need to run the Python script
        docker { image 'python:3.9-slim' }
    }

    stages {
        stage('Checkout Target') {
            steps {
                // 1. This checks out YOUR PROJECT (The one being scanned)
                checkout scm
            }
        }

        stage('Fetch RepoGuard Engine') {
            steps {
                // 2. This downloads the SCANNER from your other repo
                // Replace the URL with your actual RepoGuard-Engine URL
                git url: 'https://github.com/ralphhonra/repoguard-engine.git', branch: 'main'

                // Install dependencies
                sh 'pip install --no-cache-dir -r requirements.txt'
            }
        }

        stage('Run Security Scan') {
            steps {
                // 3. Inject the keys we saved earlier
                withCredentials([
                    string(credentialsId: 'GITHUB_TOKEN', variable: 'GITHUB_TOKEN'),
                    string(credentialsId: 'GITLAB_TOKEN', variable: 'GITLAB_TOKEN'),
                    string(credentialsId: 'OPENAI_API_KEY', variable: 'OPENAI_API_KEY')
                ]) {
                    // 4. Run the engine!
                    // It will automatically detect it's running inside "MyWebApp"
                    sh 'python -m src.main'
                }
            }
        }
    }
}