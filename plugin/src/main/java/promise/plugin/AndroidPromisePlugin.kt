/*
 * Copyright 2017, Peter Vincent
 * Licensed under the Apache License, Version 2.0, Android Promise.
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package promise.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.DependencyResolutionListener
import org.gradle.api.artifacts.ResolvableDependencies

class AndroidPromisePlugin : Plugin<Project> {
  override fun apply(project: Project) {
    val implementationDeps = project.configurations.getByName("implementation").dependencies
    val mainDepedency = project.dependencies.create("com.github.android-promise:commons:1.1-beta02")
    val rxJavaDep = project.dependencies.create("io.reactivex.rxjava2:rxjava:2.2.17")
    if (!implementationDeps.contains(mainDepedency) || !implementationDeps.contains(rxJavaDep)) project.gradle.addListener( object :DependencyResolutionListener {
      override fun beforeResolve(p0: ResolvableDependencies) {
        if (!implementationDeps.contains(mainDepedency)) implementationDeps.add(mainDepedency)
        if (!implementationDeps.contains(rxJavaDep)) implementationDeps.add(rxJavaDep)
        project.gradle.removeListener(this)
      }
      override fun afterResolve(p0: ResolvableDependencies) { }
    })
  }
}
