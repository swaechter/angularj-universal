# Angular Universal for Java [![Build Status](https://travis-ci.org/swaechter/angularj-universal.svg?branch=master)](https://travis-ci.org/swaechter/angularj-universal)

## Background

With the introduction of Angular Universal, a solution for dynamically prerendering Angular applications on the server side (SSR) and sending the content directly to the browser as 'already-bootstrapped' application, Angular became more interesting for many developers that were using a Node.js environment. It solved the problems of SEO optimization, empty page previews from Facebooks & others and resource management (For more information see https://scotch.io/tutorials/server-side-rendering-in-angular-2-with-angular-universal).

For traditional web developers in the Java enterprise environment with technologies like Spring Boot or Java EE, these technologies required the use of a separate Node.js runtime in combination with a web framework like Express.js or even worse, inter process communication between two servers. Requirements that often just weren't possible or not allowed: Two run times with two server sockets, technology fragmentation or just skepticism against Node.js on the server side or JavaScript in general. All in all, Java developers weren't able to use the benefits made in the Node.js ecosystem.

This project provides a Java API to a local Node.js instance that is responsible for server side rendering the Angular application. For this, the JNI language bindings of the J2V8 projects were used. This makes it possible to use the latest Angular Universal features without having a second [JavaScript] web server (Only a second VM by Node.js) - an integration of a prerendered Angular application into a pure Java stack is now possible.

## Overview

This repository is divided into several modules that all share the same Angular application

| Module name                                   | Content and responsibility                                                                                                                                                                                                                                              |
| --------------------------------------------- | ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| angularj-universal-application                | Contains the main Angular application with several pages and a non working login system  |
| angularj-universal-renderer                   | Contains the main render SDK that provides the core functionality and allows a developer to implement an own render solution |
| angularj-universal-renderer-v8                | Contains a specific render solution that uses a Node.js instance via J2V8 language bindings for rendering requests  |
| angularj-universal-example-spring-boot        | Contains a Spring Boot web application that serves the Angular application with the help of a custom written Spring Boot starter (See module bellow)  |
| angularj-universal-example-spring-boot-simple | Contains a Spring Boot web application that serves the Angular application without the help of the Spring Boot starter. This leads to a more simple example, but a lot more boiler plated code is required. For simplicity, this module is used as example  |
| angularj-universal-example-servlet            | TODO: Provide a servlet example  |
| angularj-universal-spring-boot-starter        | Custom Spring Boot starter that allows are better integration of a server side rendered application into the Spring Boot ecosystem |

## Getting started

### Phase -1: Foreword

It is really hard to cover all possible work flows and frameworks, so we are focusing on Spring Boot in this example, but other examples are provided as source code. Please don't hesitate to open a new issue and ask for help with a new framework. With your help we will be able to provide an example for your framework!

If you want to skip this tutorial, checkout the working example  https://github.com/swaechter/angularj-universal/tree/master/angularj-universal-example-spring-boot-simple

### Phase 0: Overview

The whole getting started process can be divided into three phases:

1. Create a new Java application with Spring Boot as framework and Maven as build system
2. Create a new Angular application inside the Java project
3. Integrate the Angular application into the Java project

As mentioned, you can adapt these steps for your framework. If you want to skip the tutorial, just take a look at the example in `angularj-universal-example-spring-boot-simple` and the Angular application `angularj-universal-application`.

### Phase 1: Create a new Java project

Head over to `http://start.spring.io/` and create a new Spring Boot project with Maven. It is important to select `Web` and `DevTools` as dependency:

```bash
http://start.spring.io/
```

Download and import that Maven project in your favorite Java IDE. In addition, add the AngularJ Universal dependencies:

```xml
<dependency>
    <groupId>ch.swaechter</groupId>
    <artifactId>angularj-universal-renderer</artifactId>
    <version>0.0.2</version>
</dependency>
<dependency>
    <groupId>ch.swaechter</groupId>
    <artifactId>angularj-universal-renderer-v8</artifactId>
    <version>0.0.2</version>
</dependency>
```

Now that we have a project layout and the required Java dependencies, we can continue with phase 2 and create a new Angular application inside the Java project.

### Phase 2: Create a new Angular application

To make the integration with Java as smooth as possible, we will stick to the Maven default layout `https://maven.apache.org/guides/introduction/introduction-to-the-standard-directory-layout.html`. The Angular application will be located in `angularj-universal-example-spring-boot-simple/src/main/angular` and the build output in `angularj-universal-example-spring-boot-simple/src/main/resources`.

Install the Angular CLI and create a new Angular application:

```bash
sudo npm install -g @angular/cli
cd angularj-universal-example-spring-boot-simple/src/main
ng new angular
```

This will create a traditional single page application (SPA) that needs some adjustment and preparations for server side rendering. Modify your application with the link bellow and execute steps 1 - 3 and 5, but skip 4 (See notes bellow):

```bash
https://github.com/angular/angular-cli/wiki/stories-universal-rendering
```

But keep a few things in mind:

 1. In the step "Step 3: Create a new project in .angular-cli.json", set the output path of your second application to `"outDir": "../resources/public",` to respect the Maven project layout
 2. Step 4 isn't required because we provide an own Java webserver in addition with some server.ts glue code later on
 3. In step 5, only copy the `webpack.config.js` but change the bundle entry to `entry: {server: './library/server.ts'},` and the bundle output to `path: path.join(__dirname, '../resources'),`. We write the suitable NPM scripts later on

Because the server side rendering code in the wiki article uses Express.js as we framework, we will write our own server code.

Create a file `library/renderadapter.ts` that provides the API (I should move this file to an own, consumable NPM library):

```typescript
require('zone.js/dist/zone-node');

import {renderModuleFactory} from "@angular/platform-server";

export type RenderCallback = (uuid: string, html: string, error: any) => void;

export class RenderAdapter {

    private appservermodulengfactory: any;

    private callback: RenderCallback;

    private html: string;

    constructor(appservermodulengfactory: any, callback: RenderCallback) {
        this.appservermodulengfactory = appservermodulengfactory;
        this.callback = callback;
        this.html = "<app-root></app-root>";
    }

    setHtml(html: string) {
        this.html = html;
    }

    renderPage(uuid: string, uri: string) {
        renderModuleFactory(this.appservermodulengfactory, {document: this.html, url: uri}).then(html => {
            this.callback(uuid, html, null);
        });
    }
}
```

The create another file `library/server.ts` that will consume the API:

```typescript
import {RenderAdapter, RenderCallback} from "./renderadapter";

const AppServerModuleNgFactory = require('./../dist/main.bundle').AppServerModuleNgFactory;

export declare function registerRenderAdapter(renderadapter: RenderAdapter): void;

export declare function receiveRenderedPage(uuid: string, html: string, error: any): void;

export const rendercallback: RenderCallback = (uuid: string, html: string, error: any) => {
receiveRenderedPage(uuid, html, error);
};

const renderadapter = new RenderAdapter(AppServerModuleNgFactory, rendercallback);
registerRenderAdapter(renderadapter);
```

This file will create a new render adapter and register this adapter via `registerRenderAdapter` - a method that doesn't exist and will be provided by the Java virtual machine - this is the real glue code. In addition, there is another method `receiveRenderedPage` that will be also be provided by the Java virtual machine and receives all finished render requests.

Now let's update our Node scripts for building all three applications (Snipped from `package.json`:

```json
"scripts": {
    "start": "npm run build",
    "build": "ng build --app 0 --prod --build-optimizer; ng build --app 1 --prod --output-hashing none; webpack --config webpack.server.config.js"
},
```

Now run 'npm start' or 'npm run build' and the traditional SPA application will land in 'angularj-universal-example-spring-boot-simple/src/main/resources/public', the publicly accessible part of our web server. In addition, we will locate our server side bundle with the API to the Java virtual machine in 'angularj-universal-example-spring-boot-simple/src/main/resources/server.js', the not publicly accessible part of our web server (Only the subdirectory is).

Now we can continue with the third phase and integrate the Angular application into the Java application and automate the NPM build (Because In cause you don't execute it, your application will fail).

### Phase 3: Integrate the Angular application into the Java project

Now we switch back to the Java part and create a web application with a controller and a service that is going to render our application.

Create a new Java class file `angularj-universal-example-spring-boot-simple/src/main/java/ch/swaechter/angularjuniversal/example/springboot/simple/DemoApplication.java` with the following content bellow:

__Note 1:__ In this example we only provide the `/` route, but the example in `angularj-universal-application` has several pages and hence routes (Login, Home etc.)

__Note 2:__ Check out the Spring Boot integration `angularj-universal-example-spring-boot` to avoid the manual routes (In the integration you specify them in a property file)

```java
package ch.swaechter.angularjuniversal.example.springboot.simple;

import ch.swaechter.angularjuniversal.renderer.Renderer;
import ch.swaechter.angularjuniversal.renderer.configuration.RenderConfiguration;
import ch.swaechter.angularjuniversal.renderer.engine.RenderEngineFactory;
import ch.swaechter.angularjuniversal.renderer.utils.RenderUtils;
import ch.swaechter.angularjuniversal.v8renderer.V8RenderEngineFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.Future;

@SpringBootApplication
public class WebApplication {


    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class, args);
    }

    @RestController
    public class ContentController {

        private final RenderService renderservice;

        @Autowired
        public ContentController(RenderService renderservice) {
            this.renderservice = renderservice;
        }

        @ResponseBody
        @GetMapping("/")
        public String showIndex() throws Exception {
            return renderservice.renderPage("/").get();
        }

        // Note: For every Angular route you have to provide a Java Spring Boot route - or just provide a wildcard route
    }

    @Service
    public class RenderService {

        private final Renderer renderer;

        public RenderService() throws IOException {
            // Load the template and create a temporary server bundle file from the resource (This file will of course never change until manually edited)
            InputStream templateinputstream = getClass().getResourceAsStream("/public/index.html");
            InputStream serverbundleinputstream = getClass().getResourceAsStream("/server.js");
            String templatecontent = RenderUtils.getStringFromInputStream(templateinputstream, StandardCharsets.UTF_8);
            File serverbundlefile = RenderUtils.createTemporaryFileFromInputStream("serverbundle", "tmp", serverbundleinputstream);
            // File localserverbundlefile = new File("<Local server bundle on the file system>"); --> Also enable auto reload in the configuration

            // Create the configuration. For real live reloading, don't use a temporary file but the real generated on from the file system
            RenderConfiguration configuration = new RenderConfiguration(templatecontent, serverbundlefile, 4, false);

            // Create the V8 render engine factory for spawning render engines
            RenderEngineFactory factory = new V8RenderEngineFactory();

            // Create and start the renderer
            this.renderer = new Renderer(factory, configuration);
            this.renderer.startRenderer();
        }

        Future<String> renderPage(String uri) {
            // Render a request and return a resolvable future
            return renderer.addRenderRequest(uri);
        }
    }
}
```

Now start the Spring Boot application and head to the URL:

```bash
http://localhost:8080
```

You can test if the server side rendering is working if:

* You see the page in general
* You disable JavaScript temporary, reload and still see the page
* You inspect the source code of the current page

### Final notes

Be aware of these information:

* You have to build the Angular application with `npm run build`, otherwise the AngularJ Universal won't find the index template and relocatable server bundle and will fail. In case you want to integrate this into your regular Maven build, take a look at the Maven NPM/Node plugin `eirslett/frontend-maven-plugin` or just take a look at the usage in `angularj-universal-application/pom.xml`
* If you rebuild your Angular application you have to restart/reload the web application by default. You can avoid this problem if you use the the server bundle from the file system in the `RenderConfiguration` and enable the reload flag
* All examples use the Angular application in `angularj-universal-application` to avoid duplication of Angular applications. Just be aware of these fact in case you are reading the source code and asking yourself where the application is. In case you want to use the examples for your own project, just copy away the example and integrate the `angularj-universal-application` content and adjust the `pom.xml`

## Issues and Questions

If you encounter a problem/bug or something isn't documented well enough, feel free to create an issue in the issue tracker (https://github.com/swaechter/angularj-universal/issues) or send me a mail (waechter.simon@gmail.com)

## Credits

This project was heavily influenced by many other projects (A huge thank you to the authors):

* https://github.com/bennylut/hello-angular2-universal-j2v8 for providing an Angular 2 example that shows how the J2V8 library and Node.js can be used to render pages
* https://github.com/blacksonic/angular2-aot-cli-webpack-plugin for providing an example of a relocatable webpack bundle
