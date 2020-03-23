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

package promise.commons

import kotlin.reflect.KClass

/**
 * creates an instance of a class of reified type
 * note by instance created this way are not automatically
 * cleared by GC
 *  They live as long as the process
 * @param T type of class to create
 * @param args constructor arguments
 * @return an instance
 */
@Throws(Exception::class)
inline fun <reified T> createInstance(args: Array<out Any?>? = null): T =
        createInstance(T::class, args)

/**
 * creates an instance of a class of reified type
 * note by instance created this way are not automatically
 * cleared by GC
 *  They live as long as the process
 * @param clazz type of class to create
 * @param args constructor arguments
 * @return an instance
 */
@Throws(Exception::class)
fun <T> createInstance(clazz: KClass<*>, args: Array<out Any?>? = null): T =
    makeInstance(clazz, args) as T

/**
 * creates an instance of a class of reified type
 * note by instance created this way are not automatically
 * cleared by GC
 *  They live as long as the process
 * @param clazz type of class to create
 * @param args constructor arguments
 * @return an instance
 */
@Throws(Exception::class)
fun makeInstance(clazz: KClass<*>, args: Array<out Any?>? = null): Any {
    /**
     * if args not present then just call the first constructor with no arguments
     */
    if (args == null || args.isEmpty()) return clazz.constructors.first { it.parameters.isEmpty() }.call()
    // get all the constructors with parameter size same as number of arguments passed
    val constructors = clazz.constructors.filter { it.parameters.size == args.size }
    // if empty means no constructor can accept the arguments passed
    check(constructors.isNotEmpty()) { "no constructor for ${clazz.simpleName} expects ${args.size} arguments" }
    // get the first constructor matching the arguments length and fail if none found
    // if more than one, only take the first
    val constructor = constructors.firstOrNull { it.parameters.size == args.size }
            ?: throw IllegalStateException("no constructor for ${clazz.simpleName} can accept $args as arguments")
    // map the arguments with the parameters and create the instance finally
    try {
        /*var currentArgIndex = 0
        val arguments = constructor.parameters
            .map { it.type.classifier as KClass<*> }
            .map {
              val arg = args[currentArgIndex]
              LogUtil.e("_StoreRepository", "arg ", args[currentArgIndex])
              currentArgIndex++
              return arg
            }
            .toTypedArray()*/
        return constructor.call(*args)
    } catch (e: Throwable) {
        throw e
    }
}