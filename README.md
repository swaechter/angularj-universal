# Spring Boot Angular Render

## Introduction

This project tries to provide a technical prototype on how to use Angular 4 with server side rendering (angular-platform-server)  in Spring Boot. Spring Boot is only used because I am familiar with the framework, but it's also possible to use the render in any other context/framework (For example even in a desktop application).

Please be aware that this is a proof of concept and not a production ready example. The prototype is heavily influenced by these projects (A huge thank you to the authors!):

* https://github.com/bennylut/hello-angular2-universal-j2v8 for proving an Angular 2 example that shows how the J2V8 library and NodeJS can be used to render pages
* https://github.com/evertonrobertoauler/cli-universal-demo for providing a server side example of Angular 2 that makes it possible to enable AOT compilation.

## Overview

Based on the requirements, the Maven project is divided into two modules:

1. Module application that serves the frontend and passes all input/user interaction to the renderer
2. Module renderer that renders all request with the help of J2V8 and a NodeJS instance.

## Setup

After one day of invested time, this is the way to get the example working. There are many caveats that have to be fixed or improved for the future:

    # Download the repository
    git clone https://github.com/swaechter/spring-boot-angular-renderer

    # Use the right J2V8 bindings for your platform
    nano renderer/pom.xml --> Search for com.eclipsesource.j2v8 and change the artifact so it matches your platform (See https://mvnrepository.com/artifact/com.eclipsesource.j2v8)

    # Point Java to your server.js and NodeJS node_modules directory, because not all dependencies are integrated into the Webpack build
    nano application/src/main/java/ch/swaechter/springular/application/RendererService.java --> change the value SERVER_FILE so it points to the Angular directory and add the suffix dist/server.js

    # Build the project
    mvn clean package

Now you can start the project via your IDE or from the command line.

## Architecture

The workflow of the prototype is divided into to phases: Bootstraping the setup and serving normal page requests:

Workflow of the bootstrap process:

1. Spring Boot is firing up and all components are getting initialized
2. The render service is trying to read the index.html and finding the server.js that is used to parse page requests
3. These values are sent to the V8RenderEngine class which provides a wrapper around the J2V8 library
4. The wrapper is creating a NodeJS instance and executes the server.js (At this the node_module directory has to be present or the process will fail!)
5. During the script execution, the script passes a render engine object to the calling Java JVM that used the angular-platform-server functionality. The class V8RenderEngine receives this method call and saves the render engine object. This render engine object is later used to parse the file template.
6. Now the engine is ready to receive requests

The workflow of a page request is as follow:

1. A user is requesting a page through his browser/request tool
2. Spring Boot handles the request and passes a request to the V8RenderEngine. This requests includes the URI (For example / or /about) that is used to choose the right Angular component if routing is used
3. The V8RenderEngine passes an asynchronous request to the saved render engine. The request includes the file template, the URI and a callback that is called as soon as the template is rendered. The implementation of the callback is a second method that is implemented in Java, so we are able to receive the result
4. In the meantime the caller that sent the request is polling for the result (See problems). As soon a result is available, the result is used
5. The result is sent back to the client where he will see a server side rendered page

## Problems

As already mentioned, there are many problems that require improvements or a fix:

* Bundle all dependencies into the Webpack build, so server.js doesn't depend  on it's node_modules directory and a developer doesn't have to set the path SERVER_FILE
* Add a mechanism to catch the rendered output and assign it to the correct request that triggered the render process. At the moment the caller that triggers the render mechanism is polling and hence real multithreading support is not possible at all
* Choose the right J2V8 engine via Maven
* Catch user input that is made before the rendered page is delivered (Preboot.js)
* Add live reload functionality
* Add the possibility to properly reload the J2V8 engine with the Spring Boot developer tools (If you are not using Spring Boot, this doesn't have to concern you)
* Get the prototype production ready and try to get feedback

## Contact and feedback

This is a prototype that was published after a day of work. If you encounter problems or think something is poorly implemented please create an issue or write me a mail (waechter.simon@gmail). I am really open to feedback as long it is constructive!
