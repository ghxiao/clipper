#!/bin/sh

mvn clean
mvn install -Dmaven.test.skip=true 
cd clipper-distribution/
mvn assembly:assembly 
unzip -o target/clipper-bin.zip -d target/
cd ..
