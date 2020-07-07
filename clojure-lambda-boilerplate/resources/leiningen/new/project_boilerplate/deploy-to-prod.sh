#!/bin/bash

set -euo pipefail

build() {
    lein uberjar
}

update-code() {
    aws lambda update-function-code \
	--function-name {{name}} \
	--zip-file fileb://./target/{{name}}-0.1.0-SNAPSHOT-standalone.jar
}

set-prod-env() {
    aws lambda update-function-configuration \
	--function-name {{name}} \
	--environment Variables={ENV=prod}
}

deploy-prod() {
    aws lambda update-alias \
	--function-name {{name}} \
	--name prod \
	--function-version $(aws lambda publish-version --function-name {{name}} --query Version --output text)
}

revert-staging-env() {
    aws lambda update-function-configuration \
	--function-name {{name}} \
	--environment Variables={ENV=staging}
}

build && update-code && set-prod-env && deploy-prod && revert-staging-env
