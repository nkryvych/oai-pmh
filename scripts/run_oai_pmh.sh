#!/bin/bash

LOG="../logs/oai-pmh.log"

log=${1:-$LOG}

DIR=$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )

cd $DIR

cd ../oai-pmh-interfaces/
mvn -Dmaven.test.skip=true clean install
cd ../demo-impl/
mvn -Dmaven.test.skip=true clean install
cd ../oai-rest/
mvn -Dmaven.test.skip=true clean install

nohup java -jar target/oai-pmh-rest-0.1.0.jar > $log &