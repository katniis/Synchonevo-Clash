@echo off
REM Switch Windows console to UTF-8
chcp 65001 >nul

REM Compile all Java files using UTF-8 encoding
javac -encoding UTF-8 src/cards/*.java src/boss/*.java src/units/*.java src/ui/*.java src/utils/*.java src/main/*.java

REM Run Java with UTF-8 output
java -Dfile.encoding=UTF-8 -cp src main.Main
