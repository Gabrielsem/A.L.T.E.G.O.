@ECHO OFF
for /f "delims=" %%i in ('dir /b /a-d /od "*.svg"') do set "LatestModifiedFile=%%~i"
java -jar S2F.jar "%LatestModifiedFile%" "map.fxml"
pause