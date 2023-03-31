# String resources

Convention plugin provides completely custom AndroidPoEditorPlugin which can import shared string
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

## Import string resources

Import of string resrouces is implemented via custom gradle plugin.

Import is exectuted via gradle command: `./gradlew importPoEditorStrings`

### PoEditorPlugin gradle plugin

PoEditorPlugin functional code is based in convention plugin in it's own
package: `cz.kotox.android.poeditor.*`.
PoEditorPlugin definition is then available in: `cz.kotox.android.PoEditorPlugin.kt`.

Custom PoEditorPlugin works in two phases:

1) Download source file from https://poeditor.com/docs/api#openapi
2) Post-process source file and distribute final strings.xml files to modules.

#### PoEditorPlugin post-processing

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

## Adding module as a target for shared string resources

In order to add module as a recipient of shared string resources do following steps:

1) Ensure `build.gradle.kts` of that module defines `id("enter-poeditor")` gradle plugin.
2) Add proper mapping of specific resource key expression to the target
   in `cz.kotox.android.poeditor.ResourceModuleMapping.kt`

With above mentioned two steps fulfilled there will be proper `strings_*_shared.xml` generated in
the module resources with `./gradlew importPoEditorStrings`