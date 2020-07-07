#!/bin/bash

lein uberjar && aws lambda update-function-code \
		    --function-name {{name}} \
		    --zip-file fileb://./target/{{name}}-0.1.0-SNAPSHOT-standalone.jar
