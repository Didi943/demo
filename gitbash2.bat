@echo off

REM Récupère tous les paramètres comme message
set msg=%*

REM Vérifie si message vide
if "%msg%"=="" (
    echo  Vous devez entrer un message 
    echo Exemple: gitbash2.bat ajout login
    exit /b 1
)

git add .
git commit -m "%msg%"
git push