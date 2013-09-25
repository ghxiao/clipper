#!/bin/bash

if [ -n "${JAVA_HOME}" -a -x "${JAVA_HOME}/bin/java" ]; then
	java="${JAVA_HOME}/bin/java"
else
	java=java
fi

# <http://stackoverflow.com/questions/59895/can-a-bash-script-tell-what-directory-its-stored-in>

SOURCE="${BASH_SOURCE[0]}"
while [ -h "$SOURCE" ]; do # resolve $SOURCE until the file is no longer a symlink
  DIR="$( cd -P "$( dirname "$SOURCE" )" && pwd )"
  SOURCE="$(readlink "$SOURCE")"
  [[ $SOURCE != /* ]] && SOURCE="$DIR/$SOURCE" # if $SOURCE was a relative symlink, we need to resolve it relative to the path where the symlink file was located
done

CLIPPER_HOME="$( cd -P "$( dirname "$SOURCE" )" && pwd )"

if [ -z "${clipper_java_args}" ]; then
  clipper_java_args="-Xmx512m"
fi

clipper_cli_jar=`ls ${CLIPPER_HOME}/lib/clipper-cli*.jar`

${java} -cp $(echo ${CLIPPER_HOME}/lib/*.jar | tr ' ' ':') org.semanticweb.clipper.hornshiq.cli.ClipperApp "$@"
#exec ${java} -cp $(echo lib/*.jar | tr ' ' ':') -jar ${clipper_cli_jar} "$@"
