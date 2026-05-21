#!/bin/sh
# ----------------------------------------------------------------------------
# Licensed to the Apache Software Foundation (ASF) under one
# or more contributor license agreements.  See the NOTICE file
# distributed with this work for additional information
# regarding copyright ownership.  The ASF licenses this file
# to you under the Apache License, Version 2.0 (the
# "License"); you may not use this file except in compliance
# with the License.  You may obtain a copy of the License at
#
#    https://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing,
# software distributed under the License is distributed on an
# "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
# KIND, either express or implied.  See the License for the
# specific language governing permissions and limitations
# under the License.
# ----------------------------------------------------------------------------
# Maven Wrapper Script

# Prefer MVNW_REPOURL env var, otherwise default to Maven Central
if [ -z "$MVNW_REPOURL" ]; then
    MVNW_REPOURL="https://repo.maven.apache.org/maven2"
fi

# Determine JAVA_HOME
if [ -z "$JAVA_HOME" ]; then
    JAVACMD="java"
else
    JAVACMD="$JAVA_HOME/bin/java"
fi

# Find or download wrapper jar
MAVEN_PROJECTBASEDIR="${MAVEN_BASEDIR:-$(pwd)}"
MAVEN_WRAPPER_PROPERTIES="$MAVEN_PROJECTBASEDIR/.mvn/wrapper/maven-wrapper.properties"

DISTRIBUTION_URL=$(grep "distributionUrl" "$MAVEN_WRAPPER_PROPERTIES" | sed 's/.*distributionUrl=//')
WRAPPER_URL=$(grep "wrapperUrl" "$MAVEN_WRAPPER_PROPERTIES" | sed 's/.*wrapperUrl=//' 2>/dev/null || echo "")

MAVEN_USER_HOME="${MAVEN_USER_HOME:-$HOME/.m2}"
MAVEN_WRAPPER_JAR="$MAVEN_USER_HOME/wrapper/maven-wrapper.jar"

# Download wrapper jar if needed
if [ ! -f "$MAVEN_WRAPPER_JAR" ]; then
    mkdir -p "$(dirname "$MAVEN_WRAPPER_JAR")"
    if command -v curl > /dev/null; then
        curl -sLo "$MAVEN_WRAPPER_JAR" "$WRAPPER_URL" 2>/dev/null || true
    fi
fi

# Extract Maven distribution if needed
MAVEN_HOME_NAME=$(basename "$DISTRIBUTION_URL" -bin.zip)
MAVEN_HOME="$MAVEN_USER_HOME/wrapper/dists/$MAVEN_HOME_NAME"

if [ ! -d "$MAVEN_HOME/bin" ]; then
    mkdir -p "$MAVEN_HOME"
    TMP_ZIP="$MAVEN_HOME/dist.zip"
    if command -v curl > /dev/null; then
        curl -sLo "$TMP_ZIP" "$DISTRIBUTION_URL"
        unzip -q "$TMP_ZIP" -d "$MAVEN_HOME/.."
        rm -f "$TMP_ZIP"
    fi
fi

exec "$MAVEN_HOME/bin/mvn" "$@"
