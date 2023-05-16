# kotox-android

MJ's personal Android knowledge base and playground mono-repo

## Android apps

Repository contains multiple applications presenting specific feature/core modules functionality.

### Kotox Media (kotox-mobile-media)

App presenting different media related modules:

* [Advanced custom camera (including zoom feature via pinch to zoom, toggle & slider)](https://github.com/kotoMJ/kotox-android/tree/main/camera-ui/)
* tbd.

### Kotox Task (kotox-mobile-task)

Simple master-detail demo app presenting different kind of test cases.

1. Unit testing

* [Hilt ViewModelTesting](https://github.com/kotoMJ/kotox-android/blob/main/kotox-mobile-task/src/test/kotlin/cz/kotox/task/list/ui/MainViewModelUnitTest.kt)
* tbd.

2. Integration Testing

* [UI Smoke Testing](https://github.com/kotoMJ/kotox-android/blob/main/kotox-mobile-task/src/androidTest/kotlin/cz/kotox/task/list/TaskMainScreenSmokeTest.kt)
* tbd.

### Kotox Playground (kotox-playground)

Playground related to different (Compose UI mainly) code samples.
For more info check [README-PLAYGROUND.md](./kotox-mobile-playground/README-PLAYGROUND.md)

## Convention plugins

### PoEditor plugin  

Custom plugin with the purpose to import shared strings to the app from the [PoEditor](https://poeditor.com/projects/) project.
More about this custom plugin in [README-POEDITOR.md](./build-logic/README-POEDITOR.md)

