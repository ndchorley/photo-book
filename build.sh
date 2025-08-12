#!/bin/bash

set -e

./gradlew clean test shadowJar
