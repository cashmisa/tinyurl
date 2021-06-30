
#!/bin/bash
set -x #echo on

cd ./tinyurl-f

quasar build

cp -Rf ./dist/spa/* ../tinyurl/src/main/resources/static/

cp -Rf ./dist/spa/index.html ../tinyurl/src/main/resources/templates

cd ..
cd tinyurl

mvn package -DskipTests=true
