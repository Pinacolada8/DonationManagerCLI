@echo off
TITLE "Donations CLI"


SET scriptPath=%~dp0
SET jarFilePath=%scriptPath%/dist/DonationsManager.jar
java -jar "%jarFilePath%" %*