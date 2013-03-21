#!/bin/bash

mvn -o clean -Pios package

open ios/toddPuzzleShapes.sln
