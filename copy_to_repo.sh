#!/bin/bash
mvn clean deploy -DskipTests # this will result in an error, which is fine..
cp -rf target/mvn-repo/com/teradata/dmp/apisdk/* com/teradata/dmp/apisdk/
git add -f com/teradata/dmp/apisdk/
git add .
git commit -m "Deploy"
git push
