@echo off
chcp 65001 >nul
cd /d d:\Aijava\javaproject\260319\dogfood-2-246\glm\web-springboot
echo 正在启动豆瓣 Spring Boot 应用...
echo.
java -Dmaven.multiModuleProjectDirectory=d:\Aijava\javaproject\260319\dogfood-2-246\glm\web-springboot -classpath .mvn\wrapper\maven-wrapper.jar org.apache.maven.wrapper.MavenWrapperMain spring-boot:run
pause
