#!/bin/bash
mvn clean deploy # this will result in an error, which is fine..
cp -rf target/mvn-repo/com/teradata/dmp/apisdk/* com/teradata/dmp/apisdk/
