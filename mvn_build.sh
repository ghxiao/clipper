#!/bin/sh

rm -rf clipper-reasoner/target/generated-sources/antlr3/
mvn install -Dmaven.test.skip=true 
cd clipper-distribution/
mvn assembly:assembly 
#rm -rf target/clipper
unzip -o target/clipper-bin.zip -d target/
cd ..
