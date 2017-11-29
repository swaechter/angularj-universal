# Changelog for AngularJ Universal

## 0.0.2 (29.11.2017)

### New Features

* Introduction of a new configuration class `RenderConfiguration` that contains the index template, the file path to the server bundle, the number of render engines and the flag for live reload
* Introduction of a multi threaded renderer with multiple render engines. The number of engines can be defined in the configuration. For this the class `RenderEngine` was refactored and a new class `RenderEngineFactory` introduced
* Load the index template and server bundle directly from the configuration and not an asset provider anymore

### Breaking Changes

* The default name of the server bundle is now `server.js` and not `server.bundle.js` anymore
* The render engine interface was refactored
* The old assets provider were removed

### Known Issues

* The login functionality in the Angular example application is not working/implemented
* Lazy loading Angular modules is not supported

## 0.0.1 (17.08.2017)

* Initial functionality for rendering an Angular application on the server side

### New Features

### Breaking Changes

* None, initial release

### Known Issues

* None, initial release
