/*
 * Copyright 2022 The Android Open Source Project
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       https://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */

//import com.google.firebase.crashlytics.buildtools.gradle.CrashlyticsExtension
import cz.kotox.android.extensions.catalog
import cz.kotox.android.extensions.library
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class AndroidApplicationFirebaseConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.google.gms.google-services")
                //apply("com.google.firebase.firebase-perf")
                //apply("com.google.firebase.crashlytics")
            }

            dependencies {
                //val bom = libs.findLibrary("firebase-bom").get()
                val bom = catalog.findLibrary("firebase-bom").get()
                add("implementation", platform(bom))
                add("implementation", catalog.library("firebase.auth"))
            }

        }
    }
}
