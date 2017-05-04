# Spring Boot Angular Render

## Introduction

This project tries to provide a technical prototype on how to use Angular 4 with server side rendering (@angular/platform-server)  in Spring Boot. Spring Boot is only used because I am familiar with the framework, but it's also possible to use the render in any other context/framework (For example even in a desktop application).

Please be aware that this is a proof of concept and not a production ready example.

## Overview

Based on the requirements, the Maven project is divided into three modules:

1. Module application that serves the frontend and passes all input/user interaction to the renderer. It also contains the Angular application
2. Module renderer that provides the abstract render engine kit
3. Module v8renderer that uses the render engine kit and provides a V8 engine for rendering page requests with the help of J2V8 and NodeJS.

## Setup

Follow these instructions to start the application. The J2V8 bindings can be tricky, so if you encounter any issues feel free to create an issue:

    # Download the repository
    git clone https://github.com/swaechter/spring-boot-angular-renderer

    # Build the project and skip the tests
    mvn clean package -DskipTests

    # Open the project in your IDE and run the test
    Navigate to the v8renderer test and run it

    # Start the application
    Navigate to the application and run it

    # Check the webpage
    Navigate to http://localhost:8080 and see the home page

## Architecture

The workflow of the prototype is divided into two phases: 1.) Bootstraping the setup and 2.) serving normal page requests:

Workflow of the bootstrap process:

1. Spring Boot is firing up and all components are getting initialized
2. The Java class RenderService is trying to read the index.html/server.bundle.js and creates a local file copy of the server.bundle.js
3. The values are sent to the V8RenderEngine class which provides a wrapper around the J2V8 language bindings
4. The V8RenderEngine creates a NodeJS instance and loads the server.bundle.js
5. During the script execution, the script passes a render engine object to the calling Java JVM that uses the @angular/platform-server functionality. The class V8RenderEngine receives this method call and saves the render engine object. This render engine object is later used to parse the file template.
6. Now the engine is ready to receive page requests

The workflow of a page request is as follow:

1. A user is requesting a page through his browser/request tool
2. Spring Boot handles the request and passes the request to the V8RenderEngine. This requests includes the URI (For example / or /about) that is used to choose the right Angular component if routing is used. The render requests provide a Future object (Like a promise), that can be completed (resolved) in the future
3. The V8RenderEngine passes an asynchronous request to the saved render engine. The request includes an unique render request UUID, the file template and the URI.
4. The V8RenderEngine will render the request and pass the result to a method that has to be implemented in Java
5. This method will reidentify the render request based on the UUID and complete the future of the caller
6. The caller now can get the content of the render request and pass it to the client
7. The client will see the page as prerendered HTML

## Problems

At the moment, there are a few problems and many ideas for the future:

* Problem: Sometimes, the J2V8 engine/NodeJS hangs up or isn't able to work in parallel. This needs some further investigation. On Linux it works really well, on Windows problems can occur
* Feature: Add preboot.js to capture user events
* Feature: Add live reload
* Feature: Add a debug hook
* Feature: Provide a library so other can use the project (Also without Spring Boot)

In general, the idea is to get this project production read within a month.

## Contact and feedback

This is a prototype that was published after a few days of work. If you encounter any problems or think something is poorly implemented, please create an issue or write me a mail (waechter.simon@gmail). I am really open to feedback as long it is constructive!

## Credits

 The prototype is heavily influenced by these projects (A huge thank you to the authors!):

* https://github.com/bennylut/hello-angular2-universal-j2v8 for proving an Angular 2 example that shows how the J2V8 library and NodeJS can be used to render pages
* https://github.com/evertonrobertoauler/cli-universal-demo for providing a server side example of Angular 2 that makes it possible to enable AOT compilation.
* https://github.com/blacksonic/angular2-aot-cli-webpack-plugin for providing an example of a relocatable webpack bundle
