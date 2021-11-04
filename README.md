# Solutions

- All dependencies are justified in the gradle files by commenting
- Author : Frederic Lemieux
- Continuous integration done with Circle
  ci [![CircleCI](https://circleci.com/gh/TAGASK/techtest/tree/main.svg?style=svg)](https://circleci.com/gh/TAGASK/techtest/tree/main)

- the project respect clean architecture and modular architecture
- modules :
    * app with the both screen required
    * base with all the template class implemented that can be reuse for other (features/modules)

# Android technical test

## Instructions

- Create an **Android App displaying a list items and their detail** when clicking on it
- Data needs to come from an API, you can use https://dummyapi.io/ (or choose one of your choice)
- Display list of users (https://dummyapi.io/data/api/user?limit=100)
- Display details of user when clicking on a user (https://dummyapi.io/data/api/user/{userId})
- Add cache or offline mode.
- Share us the github project

- You can use any dependencies as long as you can justify them.

## What's mandatory

- Compiling project  
- Unit tests
- Architecture
- Clear code
- Cache **or** offline

## What's optional
- Remarkable Ui
- IT
- CI
- Injection
- Modules
- No issues warning/issues at inspection
- Cache **and** offline

## Criterias
- Clarity of code
- Quality of code
- Project structure
- Code coverage and pertinence of UTs
