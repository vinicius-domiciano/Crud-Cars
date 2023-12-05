@ECHO OFF
TITLE compile and start program
:: This CMD script provides you with your operating system, hardware and network information.
ECHO Please wait... It start "clean and install" project.
ECHO =========================
docken

ECHO =========================
ECHO your project is installed
ECHO =========================
ECHO run docker compile

docker build -t server-cars ./

PAUSE