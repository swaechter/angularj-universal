# Angular Server Side Rendering in Java

## Introduction

This project tries to provide a technical prototype on how to use Angular 4 with server side rendering (@angular/platform-server) in Java (For example Angular 4 provided by a Java web server with Spring Boot + Tomcat). Of course it's also possible to use other technologies like Java EE or other web micro frameworks. It's even possible to run the example as a command line application if you like to see server side rendered HTML in your temrinal (You never know).

Altough this repository is available as Maven library (https://mvnrepository.com/artifact/ch.swaechter/angularj-ssr), the whole project is a proof of concept and work is required.

## Overview

The repository is divided into two sections:

1. Root directory: Contains the real angularj-ssr project and provides the Java library
2. /example directory: A working application that uses the angularj-ssr library and making some HTTP requests to the backend

At the moment the library uses J2V8 to render the request, but for the future other technologies like an IPC connection to a local NodeJS instance are also possible (Please open an issue if you need something. I am open for new ideas/workflows!).

## Setup

Follow these instructions to start the application. The J2V8 bindings can be tricky, so if you encounter any issues feel free to create an issue:

    # Download the repository
    git clone https://github.com/swaechter/angularj-ssr
    cd angularj-ssr

    # Go to the example application and build it (Grab a coffee because the build is automated - no local NodeJS or NPM are required)
    cd example
    mvn clean package

    # Start the application
    java -jar target/angularj-ssr-example-0.0.1.jar

    # Open a web browser and head to these URLS
    http://localhost:8080 (Main page)
    http://localhost:8080/api/keyword (REST API)

## Architecture

The workflow of the prototype is divided into two phases: 1.) Bootstraping the setup and 2.) serving normal page requests. You can replace Spring Boot with every Java technology you like and also J2V8 is interchangable with another technlogie that is able to communicate with NodeJS (For example IPC). It's just mentioned that people understand the workflow of a web-ish example.

Workflow of the bootstrap process based on the Spring Boot example (In :

1. Spring Boot is firing up and all components are getting initialized
2. The Java class RenderService is trying to read the index.html and server.bundle.js and creates a local file copy of both files (NodeJS only works with files and not with Java input stream)
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

All problems and ideas are discussed in this thread: https://github.com/angular/universal/issues/280

There are a few problems and many ideas:

* Problem: The author loses track of his work. Please hunt him down, send a mail every week or hit him with a stick
* Problem: Sometimes, the J2V8 engine/NodeJS hangs up or isn't able to work in parallel. This needs some further investigation. On Linux it works really well, on Windows problems can occur
* Feature: Add preboot.js to capture and replay events. This is quite complicated, so it might be more interessting to integrate angular-ssr  that supports preboot.js (https://github.com/clbond/angular-ssr) into angularj-ssr

## Contact and feedback

This is a prototype that was published after a few days of work. If you encounter any problems or think something is poorly implemented, please create an issue or write me a mail (waechter.simon@gmail). I am really open to feedback as long it is constructive!

## Credits

 The prototype is heavily influenced by these projects (A huge thank you to the authors!):

* https://github.com/bennylut/hello-angular2-universal-j2v8 for proving an Angular 2 example that shows how the J2V8 library and NodeJS can be used to render pages
* https://github.com/evertonrobertoauler/cli-universal-demo for providing a server side example of Angular 2 that makes it possible to enable AOT compilation.
* https://github.com/blacksonic/angular2-aot-cli-webpack-plugin for providing an example of a relocatable webpack bundle
