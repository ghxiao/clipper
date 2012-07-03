#!/bin/sh

if [ -n "${JAVA_HOME}" -a -x "${JAVA_HOME}/bin/java" ]; then
	java="${JAVA_HOME}/bin/java"
else
	java=java
fi

if [ -z ${CLIPPER_HOME} ]; then	 
	echo "CLIPPER_HOME not set" 1>&2
    exit 1	
fi

if [ -z "${clipper_java_args}" ]; then
  clipper_java_args="-Xmx512m"
fi

clipper_cli_jar=`ls ${CLIPPER_HOME}/lib/clipper-cli*.jar`

${java} -cp $(echo ${CLIPPER_HOME}/lib/*.jar | tr ' ' ':') org.semanticweb.clipper.hornshiq.cli.ClipperApp "$@"
#exec ${java} -cp $(echo lib/*.jar | tr ' ' ':') -jar ${clipper_cli_jar} "$@"