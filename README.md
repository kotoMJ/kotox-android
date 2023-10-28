# kotox-android

Android knowledge base and playground built by MJ as monorepo

## Android apps

Repository contains multiple applications which are re-using feature and common modules.

### Module structure

**mobile** - aggregates application modules
**feature** - aggregates feature modules where feature means user related feature (such as screen or multiple screens)
**common** - aggregates common modules where common module means module reusable across feature or mobile modules. Common module can depends on other common
module but there is a hierarchy though.
  
### Kotox Media (kotox-media)

App presenting different media related features:

* [Advanced custom camera (including zoom feature via pinch to zoom, toggle & slider)](https://github.com/kotoMJ/kotox-android/tree/main/camera-ui/)
* tbd.

### Kotox PocTask (kotox-poc-task)

Simple proof of concept of master-detail app __presenting different kind of test cases__.

1. Unit testing

* [Hilt ViewModelTesting](https://github.com/kotoMJ/kotox-android/blob/main/kotox-mobile-task/src/test/kotlin/cz/kotox/task/list/ui/MainViewModelUnitTest.kt)
* tbd.

2. Integration Testing

* [UI Smoke Testing](https://github.com/kotoMJ/kotox-android/blob/main/kotox-mobile-task/src/androidTest/kotlin/cz/kotox/task/list/TaskMainScreenSmokeTest.kt)
* tbd.

### Kotox Playground (kotox-playground)

Playground related to different (Compose UI mainly) code samples.
For more info check [README-PLAYGROUND.md](./mobile/kotox-playground/README-PLAYGROUND.md)

## Convention plugins


### PoEditor plugin

Custom plugin with the purpose to import shared strings to the app from the [PoEditor](https://poeditor.com/projects/) project.
More about this custom plugin in [README-POEDITOR.md](./build-logic/README-POEDITOR.md)

