# WebCMS

## Introduction

This document will give you an overview about what WebCMS is, how you can set it up, develop new features and how to deploy it into production.

## Overview

TODO

## Setup

TODO

## Deployment

Switch to a command line and execute a Maven build:

    mvn clean package

Now you can copy the config directory and run the application from the command line:

    cp -R config/ webcms-application/target/
    ./webcms-application/target/webcms-application-<version>.jar

You can ship this executable in combination with the config directory and the application.properties file to another computer or server. If you want to edit the application settings, just change the properties and restart the application.
