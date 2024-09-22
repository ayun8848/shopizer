#!/bin/bash
#TOMCAT_HOME=""
#echo "scanning tomcat home im /usr"
#for path in `sudo find /usr -maxdepth 3 -type d -name \*tomcat\*`
#do
#        if [ -d "$path" ] && [ -e "${path}/bin/catalina.sh" ]; then
#            echo "found tomcat home: ${path}"
#            TOMCAT_HOME=$path
#            break
#        fi
#done
#
#if [ -z "$TOMCAT_HOME" ]; then
#        echo "tomcat home not found"
#else
#        echo "start tomcat..."
#        $TOMCAT_HOME/bin/startup.sh
#fi

APP_DATA_DIR="/data/shopizer"
if [ ! -d $APP_DATA_DIR ]; then
  echo "create application data directory: $APP_DATA_DIR"
  mkdir -p $APP_DATA_DIR
fi

[ -z "$TOMCAT_HOME" ] && TOMCAT_HOME="/usr/local/tomcat"

if [ -f "$TOMCAT_HOME/startup.sh" ]; then
  sh -c "$TOMCAT_HOME/startup.sh"
fi