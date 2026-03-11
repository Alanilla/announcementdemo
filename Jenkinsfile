pipeline {
    agent any

    environment {
        APP_NAME = 'announcementdemo'
        APP_PORT = '8088'
        JAR_PATH = 'target\\announcementdemo-0.0.1-SNAPSHOT.jar'
        PID_FILE = 'app.pid'
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/Alanilla/announcementdemo.git'
            }
        }

        stage('Build') {
            steps {
                bat '.\\mvnw.cmd clean package -DskipTests'
            }
        }

        stage('Stop Old App') {
            steps {
                bat '''
                if exist %PID_FILE% (
                    set /p PID=<%PID_FILE%
                    taskkill /PID %PID% /F
                    del %PID_FILE%
                ) else (
                    echo No old app PID file found.
                )
                '''
            }
        }

        stage('Start New App') {
            steps {
                bat '''
                powershell -Command "$p = Start-Process java -ArgumentList '-jar %JAR_PATH%' -PassThru -WindowStyle Hidden; $p.Id | Out-File -Encoding ascii %PID_FILE%"
                '''
            }
        }
    }
}