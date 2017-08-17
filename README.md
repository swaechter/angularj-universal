# Angular Universal for Java

## Background

With the introduction of Angular Universal, a solution for rendering Angular applications on the server side and sending them to the browser as 'already-bootstraped' application, Angular became more interesting for many developers that were using a Node.js environment. It solved the problems of SEO optimization, prerended page previews from Facebook & others and resource management (For more information see https://scotch.io/tutorials/server-side-rendering-in-angular-2-with-angular-universal). For traditional web developers in the Java enterprise environment with technologies like Spring Boot or Java EE, these technologies required the use of a separate Node.js runtime in combination with a web framework like Express.js - requirements that often just weren't possible or not allowed (Two run times with two web sockets, technology fragmentation or just skepticism against Node.js/JavaScript in general etc.).

The author of this project used the benefits and advantages of the Node.js environment and tried to make them accessible in a Java and JVM environment without a having a server framework like Express.js. In the end, he was able to integrate and embed a Node.js instance that is launched and handled by the Java application. This makes it possible to use all the Angular Universal features in Java without the costs of having a local Node.js instance (Everything is integrated in the Java application and also managed). This makes it possible integrate Angular 4+ with Angular Universal into a traditional Java web stack.

## Overview

This repository is divided into several modules:

* angularj-universal-renderer: Render SDK that provides the core functionality and allows a developer to implement an own render solution
* angularj-universal-renderer-v8: Specific render solution that uses a Node.js/V8 engine for rendering
* angularj-universal-application: Traditional Angular application and the required server files
* angularj-universal-example-spring-boot: Example with Spring Boot as web framework

## Getting started

### Phase 0: Overview

The whole getting started process can be divided into three phases:

1. Create a new Java application with Maven as build system
2. Create a new Angular application inside the Java project
3. Integrate the Angular application into the Java project and vice versa

In ths example we will provide Angular as a web application with Spring Boot. If you don't want to use Spring Boot but instead other technologies like Java EE or other frameworks, you have to change the web server related code accordingly (I won't cover that).

### Phase 1: Create a new Java project

Head over to `http://start.spring.io/` and create a new Spring Boot project with Maven. It is important to select `Ẁeb` as dependency:

```bash
http://start.spring.io/
```

Download and import that Maven project in your favorite Java IDE and add the V8 renderer of AngularJ Unviersal as dependency:

```xml
<dependency>
    <groupId>ch.swaechter</groupId>
    <artifactId>angularj-universal-renderer-v8</artifactId>
    <version>0.0.1</version>
</dependency>
```

Now that we have a project layout, we can continue with phase 2 and create a new Angular application inside the Java project.

### Phase 2: Create a new Angular application

To make the integration with Java as smooth as possible, we will stick to the Maven default layout `https://maven.apache.org/guides/introduction/introduction-to-the-standard-directory-layout.html`. The Angular application will be located in `demoapp/src/main/angular` and the build output in `demoapp/src/main/resources`.

Install the Angular CLI, create a new Angular application and rename the directory:

```bash
cd demoapp/src/main
sudo npm install -g @angular/cli
ng new demoapp
mv demoapp angular
```

Modify your application to create a client and server bundle/build. You can skip 'Testing the bundle' if you would like to, because we will provide an own server later on:

```bash
https://github.com/angular/angular-cli/wiki/stories-universal-rendering
```

Because Angular CLI uses CommonJS as module system for the server bundle, the resulting server bundle isn't relocatable. As soon the module system of the server bundle isn't able to find it's node_modules directory, it will fail to run. This isn't a problem while developing, but as soon as you embed your application into a Java resource and push it into production, your server bundle won't work anymore because it's unable to find the whole node_modules directory to load it's module from.

To get around this problem, we use Webpack to create a relocatable server bundle (We refer to this bundle as relocatable server bundle beside the old server bundle). Create a Webpack configuration `demofile/src/main/angular/webpack.config.js` with the following content:

```js
var path = require('path');

module.exports = {
    target: 'node',
    entry: {
        server: path.resolve(process.cwd(), 'server.js')
    },
    output: {
        path: path.resolve(process.cwd(), 'dist-serverbundle'),
        filename: '[name].bundle.js'
    },
    resolve: {
        modules: [
            'node_modules'
        ],
        extensions: [
            '.js'
        ]
    }
};
```

In addition to that we also need a new file to integrate the server bundle into the newly built relocatable server bundle. Create a new server bundle file `demoapp/src/main/angular/server.js` with the following content:

```js
require("reflect-metadata");
require("zone.js/dist/zone-node");

var renderModuleFactory = require("@angular/platform-server").renderModuleFactory;
var AppServerModuleFactory = require("./dist-server/main.bundle.js").AppServerModuleNgFactory;

registerRenderElements(renderModuleFactory, AppServerModuleFactory);
```

That file will include the Angular Renderer `renderModuleFactory` that provides the functionality for rendering a module as well the precompiled (AOT) module `AppServerModuleFactory` from the server bundle. These two variables will be passed to a at the moment non existing function called `registerRenderElements` which we will later on provide within our Java JVM.

If you take a closer look at the imports in your server.js you will see, that we are importing the server bundle as `dist-server/main.bundle.js`. This file doesn't exist because Angular CLI hashes the file name by default. To overcome this problem and to stick to the Java conventions for a valid project layout we need to change the output directories. But first of all we have to install operating system independent tools like `mkdirp` and `ncp`:

```bash
cd demoapp/src/main/angular
npm install --save-dev ncp mkdirp
```

After that we disable the hashing for the server bundle and set the output directory to `demoapp/src/main/resources` in our `demoapp/src/main/angular/package.json` (Snipped build section):

```json
"scripts": {
    "build": "npm run build-directory && npm run build-client && npm run build-server && npm run build-serverbundle",
    "build-directory": "mkdirp ../resources",
    "build-client": "ng build --app 0 --prod --output-path ../resources/public",
    "build-server": "ng build --app 1 --prod --output-hashing none",
    "build-serverbundle": "webpack && ncp dist-serverbundle/server.bundle.js ../resources/server.bundle.js"
},
```

Now we can build all three bundles with the NPM build command:

```bash
cd demoapp/src/main/angular
npm run build
```

As mentioned at the beginning, all final build output should land in `demoapp/src/main/resources`. This path is accessible from inside the Java application later on. The subdirectory `public` will be publicly accessible by the Spring Boot web server (If you are not using Spring Boot change `public` accordingly).

Now we can continue with phase 3 and integrate the Angular application into the Java project.

### Phase 3: Integrate the Angular application into the Java project

Now we switch back to the Java part and create a web application with a controller and a service that is going to render our application.

Create a new Java class file `demoapp/src/main/java/com/example/demoapp/DemoApplication.java` with the following content:

```java
package com.example.demoapp;

import ch.swaechter.angularjuniversal.renderer.Renderer;
import ch.swaechter.angularjuniversal.renderer.assets.RenderAssetProvider;
import ch.swaechter.angularjuniversal.renderer.assets.ResourceProvider;
import ch.swaechter.angularjuniversal.v8renderer.V8RenderEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.Future;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Controller
    public class DemoController {

        private final DemoService demoservice;

        @Autowired
        public DemoController(DemoService demoservice) {
            this.demoservice = demoservice;
        }

        @ResponseBody
        @GetMapping("/")
        public String showIndex() throws Exception {
            return demoservice.renderPage("/").get();
        }
    }

    @Service
    public class DemoService {

        private final Renderer renderer;

        public DemoService() throws IOException {
            // Get our index.html template and the relocatable server bundle
            InputStream indexinputstream = getClass().getResourceAsStream("/public/index.html");
            InputStream serverbundleinputstream = getClass().getResourceAsStream("/server.bundle.js");

            // Pass these streams to an asset provider for the renderer
            RenderAssetProvider provider = new ResourceProvider(indexinputstream, serverbundleinputstream, StandardCharsets.UTF_8);
            // Or for a real file system: = new FilesystemProvider(new File("<index file path>"), new File("<server bundle file path>"), StandardCharsets.UTF_8);

            // Create a V8 render engine and pass it to the renderer
            V8RenderEngine v8renderengine = new V8RenderEngine();
            this.renderer = new Renderer(v8renderengine, provider);

            // Start the renderer
            renderer.startEngine();
        }

        public Future<String> renderPage(String uri) {
            // Render a request and return a resolvable future
            return renderer.renderRequest(uri);
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

* You have to build the Angular application with `npm run build`, otherwise the AngularJ Universal won't file the index template and relocatable server bundle and will fail
* If you rebuild the Angular application you have to restart/reload the web application
* The render asset provider `FilesystemProvider` which is loading it's files from the file system and not the Java resource has a watch mode you can use during development (If the file change date changes, the render engine will reload itself)
* If you run into a problem, you can also check out the `angularj-universal-application` and `angularj-universal-example-spring-boot` modules. The application is separated from the web server to reuse the the generated files in other modules for testing.

## Issues and Questions

If you encounter a problem/bug or something isn't documented well enough, feel free to create an issue in the issue tracker (https://github.com/swaechter/angularj-universal/issues) or send me a mail (waechter.simon@gmail.com)

## Credits

This project was heavily influenced by many other projects (A huge thank you to the authors):

* https://github.com/bennylut/hello-angular2-universal-j2v8 for providing an Angular 2 example that shows how the J2V8 library and NodeJS can be used to render pages
* https://github.com/blacksonic/angular2-aot-cli-webpack-plugin for providing an example of a relocatable webpack bundle
