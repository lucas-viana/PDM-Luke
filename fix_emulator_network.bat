@echo off
echo ===========================================
echo    CORRIGINDO CONECTIVIDADE DO EMULADOR
echo ===========================================

echo.
echo 1. Verificando status do emulador...
"%LOCALAPPDATA%\Android\Sdk\platform-tools\adb.exe" devices

echo.
echo 2. Reiniciando ADB server...
"%LOCALAPPDATA%\Android\Sdk\platform-tools\adb.exe" kill-server
"%LOCALAPPDATA%\Android\Sdk\platform-tools\adb.exe" start-server

echo.
echo 3. Configurando DNS no emulador...
"%LOCALAPPDATA%\Android\Sdk\platform-tools\adb.exe" shell "su 0 setprop net.dns1 8.8.8.8"
"%LOCALAPPDATA%\Android\Sdk\platform-tools\adb.exe" shell "su 0 setprop net.dns2 8.8.4.4"

echo.
echo 4. Reiniciando conectividade...
"%LOCALAPPDATA%\Android\Sdk\platform-tools\adb.exe" shell "su 0 stop"
"%LOCALAPPDATA%\Android\Sdk\platform-tools\adb.exe" shell "su 0 start"

echo.
echo 5. Testando conectividade...
"%LOCALAPPDATA%\Android\Sdk\platform-tools\adb.exe" shell ping -c 3 8.8.8.8

echo.
echo ===========================================
echo    SCRIPT FINALIZADO
echo ===========================================
pause
