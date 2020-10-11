#!/bin/bash
docker build . -t joke
mkdir -p build
docker run --rm --entrypoint cat joke  /home/application/function.zip > build/function.zip
