@echo off
javac src/cards/*.java src/units/*.java src/ui/*.java src/utils/*.java src/main/*.java
java -cp src main.Main
