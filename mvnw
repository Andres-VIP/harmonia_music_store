#!/bin/sh
# ----------------------------------------------------------------------------
# Maven Wrapper
# ----------------------------------------------------------------------------

DIR="$(cd "$(dirname "$0")" && pwd)"
MVNW_REPOURL="https://repo.maven.apache.org/maven2"
MVNW_VERSION="3.9.6"
WRAPPER_DIR="$DIR/.mvn/wrapper"

if [ ! -f "$WRAPPER_DIR/maven-wrapper.jar" ]; then
  echo "Downloading Maven $MVNW_VERSION ..."
  mkdir -p "$WRAPPER_DIR"
  curl -s -o "$WRAPPER_DIR/maven-wrapper.jar" "$MVNW_REPOURL/org/apache/maven/wrapper/maven-wrapper/3.1.1/maven-wrapper-3.1.1.jar"
fi

java -jar "$WRAPPER_DIR/maven-wrapper.jar" "$@"
