@echo off
color 0a
prompt Rex:$g 
REM runs only if javac command is added to the path variable
:compile_run
@echo on
javac -d . Copy.java
java copy.Copy
pause "Want to test it Again"
@echo off
cls
GOTO compile_run