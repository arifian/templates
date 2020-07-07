# {{name}}

FIXME: description

## Installation

### initialize with :

aws lambda create-function --function-name {{name}} --handler {{sanitized}}.core::handler --runtime java8 --memory 512 --timeout 100 --role put-your-role-arn-here --zip-file fileb://./target/{{name}}-0.1.0-SNAPSHOT-standalone.jar

## Usage

FIXME: explanation

## License

Copyright © 2019 FIXME

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
