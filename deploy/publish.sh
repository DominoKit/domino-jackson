#!/bin/bash

if [[ $TRAVIS_PULL_REQUEST == "false" ]]; then
    mvn clean deploy -DskitTests --settings $GPG_DIR/settings.xml -Dci=true -q
    exit $?
fi
