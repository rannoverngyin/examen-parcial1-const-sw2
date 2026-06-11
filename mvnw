#!/bin/sh
# Maven Wrapper Script
# Licensed to the Apache Software Foundation (ASF) under one
# or more contributor license agreements.
MAVEN_PROJECTBASEDIR=${MAVEN_BASEDIR:-"$( cd "$( dirname "$0" )" && pwd )"}
exec mvn "$@"
