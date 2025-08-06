@ECHO OFF
SETLOCAL
SET MVNW_REPOURL=https://repo.maven.apache.org/maven2
SET MVNW_VERSION=3.9.6
SET WRAPPER_DIR=%~dp0.mvn\wrapper

IF NOT EXIST "%WRAPPER_DIR%\maven-wrapper.jar" (
    ECHO Downloading Maven %MVNW_VERSION% ...
    mkdir "%WRAPPER_DIR%" 2>NUL
    curl -s -o "%WRAPPER_DIR%\maven-wrapper.jar" "%MVNW_REPOURL%/org/apache/maven/wrapper/maven-wrapper/3.1.1/maven-wrapper-3.1.1.jar"
)

java -jar "%WRAPPER_DIR%\maven-wrapper.jar" %*
