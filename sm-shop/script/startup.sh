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

if [ -f "/usr/local/tomcat/bin/startup.sh" ]; then
  sh -c "/usr/local/tomcat/bin/startup.sh"
fi