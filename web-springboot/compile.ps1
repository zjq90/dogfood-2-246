$projectDir = "d:\Aijava\javaproject\260319\dogfood-2-246\glm\web-springboot"
Set-Location $projectDir

[System.Environment]::SetEnvironmentVariable("MAVEN_PROJECTBASEDIR", $projectDir, "Process")

$javaArgs = @(
    "-Dmaven.multiModuleProjectDirectory=$projectDir",
    "-classpath",
    ".mvn\wrapper\maven-wrapper.jar",
    "org.apache.maven.wrapper.MavenWrapperMain",
    "compile"
)

& java $javaArgs
