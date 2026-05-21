@REM Maven Wrapper batch file for Windows
@IF "%JAVA_HOME%"=="" (SET JAVACMD=java) ELSE (SET JAVACMD=%JAVA_HOME%\bin\java)
@SET MAVEN_USER_HOME=%USERPROFILE%\.m2
@SET MAVEN_HOME=%MAVEN_USER_HOME%\wrapper\dists\apache-maven-3.9.6-bin\apache-maven-3.9.6

@IF EXIST "%MAVEN_HOME%\bin\mvn.cmd" (
    CALL "%MAVEN_HOME%\bin\mvn.cmd" %*
) ELSE (
    mvn %*
)
