# String resources

Convention plugin provides completely custom [AndroidPoEditorPlugin](./convention/src/main/kotlin/AndroidPoEditorPlugin.kt) which can import shared string
resources
from [PoEditor](https://poeditor.com/), converts them accordingly and also copy appropriates strings
to appropriates modules based on `cz.kotox.android.poeditor.ResourceModuleMapping` file.

## Naming conventions for shared resources in the project:

1) shared: `strings_*_shared.xml` - those strings are shared with iOs platform. Those files are
   generated and **must not be updated manually**. Any update has to be done
   via [PoEditor](https://poeditor.com/projects/)
2) local: `strings_*.xml` - **those strings should be avoided** since all strings should be in
   PoEditor.
   **Use such string resources temporarily just to speed up development only!**

## Naming conventions on the PoEditor side

Following conventions are given by the specific situation where all strings in PoEditor are imported
from iOs app, so they have an iOs key format. Also there is not more projects separation to
Android/iOs, but current AndroidPoEditorPlugin can convert iOs format to Android.

If there is Android specific string, the resource key in the PoEditor should have `_android` suffix
so it will not
be distributed to the iOs resources.
Similiar situation is for iOs specific string, the resource key in the PoEditor should have `_ios`
suffix so it will
not be distributed to the Android resources.

Mobile platform specific keys (`_android`,`_ios`) are removed by the plugin during import so they
are not part of the final Android key in resources.

## How to connect project module to use shared resources

In order to add module as a recipient of shared string resources do following steps:

1) Ensure `build.gradle.kts` of that module defines `id("cz.kotox.android.poeditor")` gradle plugin.
2) Ensure proper mapping of specific resource key expression to the target module 
   in the `cz.kotox.android.poeditor.ResourceModuleMapping.kt`

With above mentioned two steps fulfilled there will be proper `strings_*_shared.xml` generated in
the module resources with `./gradlew importPoEditorStrings`

## How to Import string resources to connected modules

1) Ensure you have API_TOKEN and PROJECTID set in the environment variables.
    1) On MacOs it might be in `~/.zshenv`
    2) Add line: export `POEDITOR_API_TOKEN=`here_write_your_api_token
    3) Add line: export `POEDITOR_PROJECT_ID=`here_write_your_project_id
    4) Ensure those variables are loaded in to environment (e.g. restart your computer)
2) Execute import via gradle command: `./gradlew importPoEditorStrings` in the root of the project
3) Check for new files, there should be imported string resources after successful run of the gradle
   command

  

## How AndroidPoEditorPlugin works

Let's take a closer look under the plugin hood. Everything starts in [PoEditorImportController](./convention/src/main/kotlin/cz/kotox/android/poeditor/PoEditorImportController.kt))

### PoEditorPlugin gradle plugin

PoEditorPlugin functional code is based in convention plugin in it's own
package: `cz.kotox.android.poeditor.*`.
PoEditorPlugin definition is then available in: `cz.kotox.android.AndroidPoEditorPlugin.kt`.

Custom PoEditorPlugin works in two phases:

1) Download source file from https://poeditor.com/docs/api#openapi
2) Post-process source file and distribute final strings.xml files to modules.

#### PoEditorPlugin post-processing

After android resource file type is downloaded from openapi, there needs to be done some
post-processing since there is a lot of iOs specific which would not work on Android side.

Post procesing of source file downloaded from openapi stands for several points:

1) Avoid processing of iOs specific resources (by `_ios` suffix)
2) Determine targetResource module/file based on resource key. The mapping is specified
   in `cz.kotox.android.poeditor.ResourceModuleMapping`.
3) Fix resource key. Transform original iOs form (separated by dots) to Android form (separated by
   underscore).
4) Remove eventual Android specific resource key suffix `_android`
5) Fix resource parameter placeholders. Transform original iOs placeholders to
   Android [placeholders](https://poeditor.com/kb/placeholder-validation)
6) Copy processed nodes to determined targets.


## PoEditor slides

In the second part of slides from [Android Talks 2023/5 slides](../extras/documents/StringResrouces-slides.pdf) there is a section about this PoEditor plugin.

