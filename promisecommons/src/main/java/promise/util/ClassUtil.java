/*
 *
 *  * Copyright 2017, Peter Vincent
 *  * Licensed under the Apache License, Version 2.0, Promise.
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  * http://www.apache.org/licenses/LICENSE-2.0
 *  * Unless required by applicable law or agreed to in writing,
 *  * software distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 */

package promise.util;


import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * Created on 12/6/17 by yoctopus.
 */

public class ClassUtil {

    public static int getIntFieldIfExists(Class<?> klass,
                                          String fieldName,
                                          Class<?> obj,
                                          int defaultVal) {
        try {
            Field f = klass.getDeclaredField(fieldName);
            return f.getInt(obj);
        } catch (Exception e) {
            return defaultVal;
        }
    }

   /* public static Map<String, Class<?>> getFields(Class<?> klass, Class<?> object){
        List<Field> fields = new List<>(klass.getDeclaredFields());
    }*/

    public static boolean hasField(Class<?> klass,
                                   String fieldName) {
        try {
            klass.getDeclaredField(fieldName);
            return true;
        } catch (NoSuchFieldException e) {
            return false;
        }
    }

    public static boolean hasMethod(String className,
                                    String methodName,
                                    Class<?>... parameterTypes) {
        try {
            return hasMethod(Class.forName(className), methodName, parameterTypes);
        } catch (Throwable th) {
            return false;
        }
    }

    public static boolean hasMethod(Class<?> klass,
                                    String methodName,
                                    Class<?>... paramTypes) {
        try {
            klass.getDeclaredMethod(methodName, paramTypes);
            return true;
        } catch (NoSuchMethodException e) {
            return false;
        }
    }

    public static boolean hasAnnotation(Class<?> klass, Class<? extends Annotation> annotation) {
        return klass.isAnnotationPresent(annotation);
    }
}
