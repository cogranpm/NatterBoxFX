copy /y build\distributions\NatterFX-1.0-SNAPSHOT.zip ..\
rmdir /s /q ..\ApplicationDeploy
mkdir ..\ApplicationDeploy
tar -xf ..\NatterFX-1.0-SNAPSHOT.zip -C ..\ApplicationDeploy
 ..\ApplicationDeploy\NatterFX-1.0-SNAPSHOT\bin\NatterFX.bat