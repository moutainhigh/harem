cd /d %~dp0..
call mvn clean package -DskipTests

call autoconfig %~dp0..\target\palace-web.war -u %~dp0test-jiuxiu145.properties
@pause