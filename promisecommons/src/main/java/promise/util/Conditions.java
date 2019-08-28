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

import androidx.annotation.Nullable;

/**
 * Created by yoctopus on 11/25/16.
 */

public final class Conditions {

    private Conditions() {

    }

    /**
     * Ensures the truth of an expression involving
     * one or more parameters to the calling method.
     *
     * @param expression a boolean expression
     * @throws IllegalArgumentException if
     *                                  {@code expression} is false
     */
    public static void checkArgument(boolean expression) {
        if (!expression) {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Ensures the truth of an expression
     * involving one or more parameters to the calling method.
     *
     * @param expression   a boolean expression
     * @param errorMessage the exception message
     *                     to use if the check fails;
     *                     will be converted to a
     *                     string using {@link String#valueOf(Object)}
     * @throws IllegalArgumentException if {@code expression} is false
     */
    public static void checkArgument(boolean expression,
                                     @Nullable Object errorMessage) {
        if (!expression) {
            throw new IllegalArgumentException(
                    String.valueOf(errorMessage));
        }
    }

    /**
     * Ensures the truth of an expression
     * involving one or more parameters to the calling method.
     *
     * @param expression           a boolean expression
     * @param errorMessageTemplate a template
     *                             for the exception message
     *                             should the check fail. The
     *                             message is formed by
     *                             replacing each {@code %s}
     *                             placeholder in the template with an
     *                             argument.
     *                             These are matched by position -
     *                             the first {@code %s} gets {@code
     *                             errorMessageArgs[0]}, etc.
     *                             Unmatched arguments
     *                             will be appended to
     *                             the formatted message in
     *                             square braces. Unmatched
     *                             placeholders will be left as-is.
     * @param errorMessageArgs     the arguments
     *                             to be substituted into
     *                             the message template. Arguments
     *                             are converted to strings using
     *                             {@link String#valueOf(Object)}.
     * @throws IllegalArgumentException if
     *                                  {@code expression} is false
     * @throws NullPointerException     if the check
     *                                  fails and either {@code
     *                                  errorMessageTemplate} or
     *                                  {@code errorMessageArgs}
     *                                  is null (don't let this happen)
     */
    public static void checkArgument(boolean expression,
                                     @Nullable String errorMessageTemplate,
                                     @Nullable Object... errorMessageArgs) {
        if (!expression) {
            throw new IllegalArgumentException(
                    format(errorMessageTemplate,
                            errorMessageArgs));
        }
    }

    /**
     * Ensures the truth of an expression
     * involving one or more parameters to the calling method.
     * <p>
     * <p>See {@link #checkArgument(boolean,
     * String, Object...)} for details.
     */
    public static void checkArgument(boolean b,
                                     @Nullable String errorMessageTemplate,
                                     char p1) {
        if (!b) {
            throw new IllegalArgumentException(
                    format(errorMessageTemplate,
                            p1));
        }
    }

    /**
     * Ensures the truth of an expression
     * involving one or more parameters to the calling method.
     * <p>
     * <p>See {@link #checkArgument(
     *boolean, String, Object...)} for details.
     */
    public static void checkArgument(boolean b,
                                     @Nullable String errorMessageTemplate,
                                     int p1) {
        if (!b) {
            throw new IllegalArgumentException(
                    format(errorMessageTemplate,
                            p1));
        }
    }

    /**
     * Ensures the truth of an expression
     * involving one or more parameters to the calling method.
     * <p>
     * <p>See {@link #checkArgument(
     *boolean, String, Object...)} for details.
     */
    public static void checkArgument(boolean b,
                                     @Nullable String errorMessageTemplate,
                                     long p1) {
        if (!b) {
            throw new IllegalArgumentException(
                    format(errorMessageTemplate,
                            p1));
        }
    }

    /**
     * Ensures the truth of an expression
     * involving one or more parameters to the calling method.
     * <p>
     * <p>See {@link #checkArgument(
     *boolean, String, Object...)} for details.
     */
    public static void checkArgument(boolean b,
                                     @Nullable String errorMessageTemplate,
                                     @Nullable Object p1) {
        if (!b) {
            throw new IllegalArgumentException(
                    format(errorMessageTemplate,
                            p1));
        }
    }

    /**
     * Ensures the truth of an expression
     * involving one or more parameters to the calling method.
     * <p>
     * <p>See {@link #checkArgument(
     *boolean, String, Object...)} for details.
     */
    public static void checkArgument(boolean b,
                                     @Nullable String errorMessageTemplate,
                                     char p1,
                                     char p2) {
        if (!b) {
            throw new IllegalArgumentException(
                    format(errorMessageTemplate,
                            p1,
                            p2));
        }
    }

    /**
     * Ensures the truth of an expression
     * involving one or more parameters to the calling method.
     * <p>
     * <p>See {@link #checkArgument(
     *boolean, String, Object...)} for details.
     */
    public static void checkArgument(boolean b,
                                     @Nullable String errorMessageTemplate,
                                     char p1,
                                     int p2) {
        if (!b) {
            throw new IllegalArgumentException(
                    format(errorMessageTemplate,
                            p1,
                            p2));
        }
    }

    /**
     * Ensures the truth of an expression
     * involving one or more parameters to the calling method.
     * <p>
     * <p>See {@link #checkArgument(
     *boolean, String, Object...)} for details.
     */
    public static void checkArgument(boolean b,
                                     @Nullable String errorMessageTemplate,
                                     char p1,
                                     long p2) {
        if (!b) {
            throw new IllegalArgumentException(
                    format(errorMessageTemplate,
                            p1,
                            p2));
        }
    }

    /**
     * Ensures the truth of an expression
     * involving one or more parameters to the calling method.
     * <p>
     * <p>See {@link #checkArgument(
     *boolean, String, Object...)} for details.
     */
    public static void checkArgument(boolean b,
                                     @Nullable String errorMessageTemplate,
                                     char p1,
                                     @Nullable Object p2) {
        if (!b) {
            throw new IllegalArgumentException(
                    format(errorMessageTemplate,
                            p1,
                            p2));
        }
    }

    /**
     * Ensures the truth of an expression
     * involving one or more parameters to the calling method.
     * <p>
     * <p>See {@link #checkArgument(
     *boolean, String, Object...)} for details.
     */
    public static void checkArgument(boolean b,
                                     @Nullable String errorMessageTemplate,
                                     int p1,
                                     char p2) {
        if (!b) {
            throw new IllegalArgumentException(
                    format(errorMessageTemplate,
                            p1,
                            p2));
        }
    }

    /**
     * Ensures the truth of an expression
     * involving one or more parameters to the calling method.
     * <p>
     * <p>See {@link #checkArgument(
     *boolean, String, Object...)} for details.
     */
    public static void checkArgument(boolean b,
                                     @Nullable String errorMessageTemplate,
                                     int p1,
                                     int p2) {
        if (!b) {
            throw new IllegalArgumentException(
                    format(errorMessageTemplate,
                            p1,
                            p2));
        }
    }

    /**
     * Ensures the truth of an expression
     * involving one or more parameters to the calling method.
     * <p>
     * <p>See {@link #checkArgument(
     *boolean, String, Object...)} for details.
     */
    public static void checkArgument(boolean b,
                                     @Nullable String errorMessageTemplate,
                                     int p1,
                                     long p2) {
        if (!b) {
            throw new IllegalArgumentException(
                    format(errorMessageTemplate,
                            p1,
                            p2));
        }
    }

    /**
     * Ensures the truth of an expression
     * involving one or more parameters to the calling method.
     * <p>
     * <p>See {@link #checkArgument(
     *boolean, String, Object...)} for details.
     */
    public static void checkArgument(boolean b,
                                     @Nullable String errorMessageTemplate,
                                     int p1,
                                     @Nullable Object p2) {
        if (!b) {
            throw new IllegalArgumentException(
                    format(errorMessageTemplate,
                            p1,
                            p2));
        }
    }

    /**
     * Ensures the truth of an expression
     * involving one or more parameters to the calling method.
     * <p>
     * <p>See {@link #checkArgument(
     *boolean, String, Object...)} for details.
     */
    public static void checkArgument(boolean b,
                                     @Nullable String errorMessageTemplate,
                                     long p1,
                                     char p2) {
        if (!b) {
            throw new IllegalArgumentException(
                    format(errorMessageTemplate,
                            p1,
                            p2));
        }
    }

    /**
     * Ensures the truth of an expression
     * involving one or more parameters to the calling method.
     * <p>
     * <p>See {@link #checkArgument(
     *boolean, String, Object...)} for details.
     */
    public static void checkArgument(boolean b,
                                     @Nullable String errorMessageTemplate,
                                     long p1,
                                     int p2) {
        if (!b) {
            throw new IllegalArgumentException(
                    format(errorMessageTemplate,
                            p1,
                            p2));
        }
    }

    /**
     * Ensures the truth of an expression
     * involving one or more parameters to the calling method.
     * <p>
     * <p>See {@link #checkArgument(
     *boolean, String, Object...)} for details.
     */
    public static void checkArgument(boolean b,
                                     @Nullable String errorMessageTemplate,
                                     long p1,
                                     long p2) {
        if (!b) {
            throw new IllegalArgumentException(
                    format(errorMessageTemplate,
                            p1,
                            p2));
        }
    }

    /**
     * Ensures the truth of an expression
     * involving one or more parameters to the calling method.
     * <p>
     * <p>See {@link #checkArgument(
     *boolean, String, Object...)} for details.
     */
    public static void checkArgument(boolean b,
                                     @Nullable String errorMessageTemplate,
                                     long p1,
                                     @Nullable Object p2) {
        if (!b) {
            throw new IllegalArgumentException(
                    format(errorMessageTemplate,
                            p1,
                            p2));
        }
    }

    /**
     * Ensures the truth of an expression
     * involving one or more parameters to the calling method.
     * <p>
     * <p>See {@link #checkArgument(
     *boolean, String, Object...)} for details.
     */
    public static void checkArgument(boolean b,
                                     @Nullable String errorMessageTemplate,
                                     @Nullable Object p1,
                                     char p2) {
        if (!b) {
            throw new IllegalArgumentException(
                    format(errorMessageTemplate,
                            p1,
                            p2));
        }
    }

    /**
     * Ensures the truth of an expression
     * involving one or more parameters to the calling method.
     * <p>
     * <p>See {@link #checkArgument(
     *boolean, String, Object...)} for details.
     */
    public static void checkArgument(boolean b,
                                     @Nullable String errorMessageTemplate,
                                     @Nullable Object p1,
                                     int p2) {
        if (!b) {
            throw new IllegalArgumentException(
                    format(errorMessageTemplate,
                            p1,
                            p2));
        }
    }

    /**
     * Ensures the truth of an expression
     * involving one or more parameters to the calling method.
     * <p>
     * <p>See {@link #checkArgument(
     *boolean, String, Object...)} for details.
     */
    public static void checkArgument(boolean b,
                                     @Nullable String errorMessageTemplate,
                                     @Nullable Object p1,
                                     long p2) {
        if (!b) {
            throw new IllegalArgumentException(format(
                    errorMessageTemplate,
                    p1,
                    p2));
        }
    }

    /**
     * Ensures the truth of an expression
     * involving one or more parameters to the calling method.
     * <p>
     * <p>See {@link #checkArgument(
     *boolean, String, Object...)} for details.
     */
    public static void checkArgument(boolean b,
                                     @Nullable String errorMessageTemplate,
                                     @Nullable Object p1,
                                     @Nullable Object p2) {
        if (!b) {
            throw new IllegalArgumentException(
                    format(errorMessageTemplate,
                            p1,
                            p2));
        }
    }

    /**
     * Ensures the truth of an expression
     * involving one or more parameters to the calling method.
     * <p>
     * <p>See {@link #checkArgument(
     *boolean, String, Object...)} for details.
     */
    public static void checkArgument(boolean b,
                                     @Nullable String errorMessageTemplate,
                                     @Nullable Object p1,
                                     @Nullable Object p2,
                                     @Nullable Object p3) {
        if (!b) {
            throw new IllegalArgumentException(
                    format(errorMessageTemplate,
                            p1,
                            p2,
                            p3));
        }
    }

    /**
     * Ensures the truth of an expression
     * involving one or more parameters to the calling method.
     * <p>
     * <p>See {@link #checkArgument(
     *boolean, String, Object...)} for details.
     */
    public static void checkArgument(boolean b,
                                     @Nullable String errorMessageTemplate,
                                     @Nullable Object p1,
                                     @Nullable Object p2,
                                     @Nullable Object p3,
                                     @Nullable Object p4) {
        if (!b) {
            throw new IllegalArgumentException(
                    format(errorMessageTemplate,
                            p1,
                            p2,
                            p3,
                            p4));
        }
    }

    /**
     * Ensures the truth of an expression
     * involving the state of the calling instance, but not
     * involving any parameters to the calling method.
     *
     * @param expression a boolean expression
     * @throws IllegalStateException if {@code expression} is false
     */
    public static void checkState(boolean expression) {
        if (!expression) {
            throw new IllegalStateException();
        }
    }

    /**
     * Ensures the truth of an expression
     * <p>
     * involving the state of the calling instance, but not
     * involving any parameters to the calling method.
     *
     * @param expression   a boolean expression
     * @param errorMessage the exception message
     *                     to use if the check fails;
     *                     will be converted to a
     *                     string using {@link String#valueOf(Object)}
     * @throws IllegalStateException if {@code expression} is false
     */
    public static void checkState(boolean expression,
                                  @Nullable Object errorMessage) {
        if (!expression) {
            throw new IllegalStateException(
                    String.valueOf(errorMessage));
        }
    }

    /**
     * Ensures the truth of an expression involving
     * the state of the calling instance, but not
     * involving any parameters to the calling method.
     *
     * @param expression           a boolean expression
     * @param errorMessageTemplate a template for
     *                             the exception message
     *                             should the check fail. The
     *                             message is formed by replacing
     *                             each {@code %s} placeholder in
     *                             the template with an
     *                             argument. These are matched by
     *                             position - the first {@code %s}
     *                             gets {@code
     *                             errorMessageArgs[0]}, etc.
     *                             Unmatched arguments will be appended
     *                             to the formatted message in
     *                             square braces. Unmatched
     *                             placeholders will be left as-is.
     * @param errorMessageArgs     the arguments to
     *                             be substituted into the
     *                             message template. Arguments
     *                             are converted to strings using
     *                             {@link String#valueOf(Object)}.
     * @throws IllegalStateException if {@code expression} is false
     * @throws NullPointerException  if the check fails and either
     *                               {@code errorMessageTemplate} or
     *                               {@code errorMessageArgs} is
     *                               null (don't let this happen)
     */
    public static void checkState(boolean expression,
                                  @Nullable String errorMessageTemplate,
                                  @Nullable Object... errorMessageArgs) {
        if (!expression) {
            throw new IllegalStateException(
                    format(errorMessageTemplate,
                            errorMessageArgs));
        }
    }

    /**
     * Ensures the truth of an expression
     * involving the state of the calling instance, but not
     * involving any parameters to the calling method.
     * <p>
     * <p>See {@link #checkState(
     *boolean, String, Object...)} for details.
     */
    public static void checkState(boolean b,
                                  @Nullable String errorMessageTemplate,
                                  char p1) {
        if (!b) {
            throw new IllegalStateException(
                    format(errorMessageTemplate,
                            p1));
        }
    }

    /**
     * Ensures the truth of an expression
     * involving the state of the calling instance, but not
     * involving any parameters to the calling method.
     * <p>
     * <p>See {@link #checkState(boolean, String, Object...)} for details.
     */
    public static void checkState(boolean b,
                                  @Nullable String errorMessageTemplate,
                                  int p1) {
        if (!b) {
            throw new IllegalStateException(
                    format(errorMessageTemplate,
                            p1));
        }
    }

    /**
     * Ensures the truth of an expression
     * involving the state of the calling instance, but not
     * involving any parameters to the calling method.
     * <p>
     * <p>See {@link #checkState(
     *boolean, String, Object...)} for details.
     */
    public static void checkState(boolean b,
                                  @Nullable String errorMessageTemplate,
                                  long p1) {
        if (!b) {
            throw new IllegalStateException(
                    format(errorMessageTemplate,
                            p1));
        }
    }

    /**
     * Ensures the truth of an expression
     * involving the state of the calling instance, but not
     * involving any parameters to the calling method.
     * <p>
     * <p>See {@link #checkState(
     *boolean, String, Object...)} for details.
     */
    public static void checkState(boolean b,
                                  @Nullable String errorMessageTemplate,
                                  @Nullable Object p1) {
        if (!b) {
            throw new IllegalStateException(
                    format(errorMessageTemplate,
                            p1));
        }
    }

    /**
     * Ensures the truth of an expression
     * involving the state of the calling instance, but not
     * involving any parameters to the calling method.
     * <p>
     * <p>See {@link #checkState(
     *boolean, String, Object...)} for details.
     */
    public static void checkState(boolean b,
                                  @Nullable String errorMessageTemplate,
                                  char p1,
                                  char p2) {
        if (!b) {
            throw new IllegalStateException(
                    format(errorMessageTemplate,
                            p1,
                            p2));
        }
    }

    /**
     * Ensures the truth of an expression
     * involving the state of the calling instance, but not
     * involving any parameters to the calling method.
     * <p>
     * <p>See {@link #checkState(
     *boolean, String, Object...)} for details.
     */
    public static void checkState(boolean b,
                                  @Nullable String errorMessageTemplate,
                                  char p1,
                                  int p2) {
        if (!b) {
            throw new IllegalStateException(
                    format(errorMessageTemplate,
                            p1,
                            p2));
        }
    }

    /**
     * Ensures the truth of an expression
     * involving the state of the calling instance, but not
     * involving any parameters to the calling method.
     * <p>
     * <p>See {@link #checkState(
     *boolean, String, Object...)} for details.
     */
    public static void checkState(boolean b,
                                  @Nullable String errorMessageTemplate,
                                  char p1,
                                  long p2) {
        if (!b) {
            throw new IllegalStateException(
                    format(errorMessageTemplate,
                            p1,
                            p2));
        }
    }

    /**
     * Ensures the truth of an expression
     * involving the state of the calling instance, but not
     * involving any parameters to the calling method.
     * <p>
     * <p>See {@link #checkState(
     *boolean, String, Object...)} for details.
     */
    public static void checkState(boolean b,
                                  @Nullable String errorMessageTemplate,
                                  char p1,
                                  @Nullable Object p2) {
        if (!b) {
            throw new IllegalStateException(
                    format(errorMessageTemplate,
                            p1,
                            p2));
        }
    }

    /**
     * Ensures the truth of an expression
     * involving the state of the calling instance, but not
     * involving any parameters to the calling method.
     * <p>
     * <p>See {@link #checkState(
     *boolean, String, Object...)} for details.
     */
    public static void checkState(boolean b,
                                  @Nullable String errorMessageTemplate,
                                  int p1,
                                  char p2) {
        if (!b) {
            throw new IllegalStateException(
                    format(errorMessageTemplate,
                            p1,
                            p2));
        }
    }

    /**
     * Ensures the truth of an expression
     * involving the state of the calling instance, but not
     * involving any parameters to the calling method.
     * <p>
     * <p>See {@link #checkState(
     *boolean, String, Object...)} for details.
     */
    public static void checkState(boolean b,
                                  @Nullable String errorMessageTemplate,
                                  int p1,
                                  int p2) {
        if (!b) {
            throw new IllegalStateException(
                    format(errorMessageTemplate,
                            p1,
                            p2));
        }
    }

    /**
     * Ensures the truth of an expression
     * <p>
     * involving the state of the calling instance, but not
     * involving any parameters to the calling method.
     * <p>
     * <p>See {@link #checkState(
     *boolean, String, Object...)} for details.
     */
    public static void checkState(boolean b,
                                  @Nullable String errorMessageTemplate,
                                  int p1,
                                  long p2) {
        if (!b) {
            throw new IllegalStateException(
                    format(errorMessageTemplate,
                            p1,
                            p2));
        }
    }

    /**
     * Ensures the truth of an expression
     * involving the state of the calling instance, but not
     * involving any parameters to the calling method.
     * <p>
     * <p>See {@link #checkState(
     *boolean, String, Object...)} for details.
     */
    public static void checkState(boolean b,
                                  @Nullable String errorMessageTemplate,
                                  int p1,
                                  @Nullable Object p2) {
        if (!b) {
            throw new IllegalStateException(
                    format(errorMessageTemplate,
                            p1,
                            p2));
        }
    }

    /**
     * Ensures the truth of an expression
     * involving the state of the calling instance, but not
     * involving any parameters to the calling method.
     * <p>
     * <p>See {@link #checkState(
     *boolean, String, Object...)} for details.
     */
    public static void checkState(boolean b,
                                  @Nullable String errorMessageTemplate,
                                  long p1,
                                  char p2) {
        if (!b) {
            throw new IllegalStateException(
                    format(errorMessageTemplate,
                            p1,
                            p2));
        }
    }

    /**
     * Ensures the truth of an expression
     * involving the state of the calling instance, but not
     * involving any parameters to the calling method.
     * <p>
     * <p>See {@link #checkState(
     *boolean, String, Object...)} for details.
     */
    public static void checkState(boolean b,
                                  @Nullable String errorMessageTemplate,
                                  long p1,
                                  int p2) {
        if (!b) {
            throw new IllegalStateException(
                    format(errorMessageTemplate,
                            p1,
                            p2));
        }
    }

    /**
     * Ensures the truth of an expression
     * involving the state of the calling instance, but not
     * involving any parameters to the calling method.
     * <p>
     * <p>See {@link #checkState(
     *boolean, String, Object...)} for details.
     */
    public static void checkState(boolean b,
                                  @Nullable String errorMessageTemplate,
                                  long p1,
                                  long p2) {
        if (!b) {
            throw new IllegalStateException(
                    format(errorMessageTemplate,
                            p1,
                            p2));
        }
    }

    /**
     * Ensures the truth of an expression
     * involving the state of the calling instance, but not
     * involving any parameters to the calling method.
     * <p>
     * <p>See {@link #checkState(
     *boolean, String, Object...)} for details.
     */
    public static void checkState(boolean b,
                                  @Nullable String errorMessageTemplate,
                                  long p1,
                                  @Nullable Object p2) {
        if (!b) {
            throw new IllegalStateException(
                    format(errorMessageTemplate,
                            p1,
                            p2));
        }
    }

    /**
     * Ensures the truth of an expression
     * involving the state of the calling instance, but not
     * involving any parameters to the calling method.
     * <p>
     * <p>See {@link #checkState(
     *boolean, String, Object...)} for details.
     */
    public static void checkState(boolean b,
                                  @Nullable String errorMessageTemplate,
                                  @Nullable Object p1,
                                  char p2) {
        if (!b) {
            throw new IllegalStateException(
                    format(errorMessageTemplate,
                            p1,
                            p2));
        }
    }

    /**
     * Ensures the truth of an expression
     * involving the state of the calling instance, but not
     * involving any parameters to the calling method.
     * <p>
     * <p>See {@link #checkState(
     *boolean, String, Object...)} for details.
     */
    public static void checkState(boolean b,
                                  @Nullable String errorMessageTemplate,
                                  @Nullable Object p1,
                                  int p2) {
        if (!b) {
            throw new IllegalStateException(
                    format(errorMessageTemplate,
                            p1,
                            p2));
        }
    }

    /**
     * Ensures the truth of an expression
     * involving the state of the calling instance, but not
     * involving any parameters to the calling method.
     * <p>
     * <p>See {@link #checkState(
     *boolean, String, Object...)} for details.
     */
    public static void checkState(boolean b,
                                  @Nullable String errorMessageTemplate,
                                  @Nullable Object p1,
                                  long p2) {
        if (!b) {
            throw new IllegalStateException(
                    format(errorMessageTemplate,
                            p1,
                            p2));
        }
    }

    /**
     * Ensures the truth of an expression
     * involving the state of the calling instance, but not
     * involving any parameters to the calling method.
     * <p>
     * <p>See {@link #checkState(
     *boolean, String, Object...)} for details.
     */
    public static void checkState(boolean b,
                                  @Nullable String errorMessageTemplate,
                                  @Nullable Object p1,
                                  @Nullable Object p2) {
        if (!b) {
            throw new IllegalStateException(
                    format(errorMessageTemplate,
                            p1,
                            p2));
        }
    }

    /**
     * Ensures the truth of an expression
     * involving the state of the calling instance, but not
     * involving any parameters to the calling method.
     * <p>
     * <p>See {@link #checkState(
     *boolean, String, Object...)} for details.
     */
    public static void checkState(boolean b,
                                  @Nullable String errorMessageTemplate,
                                  @Nullable Object p1,
                                  @Nullable Object p2,
                                  @Nullable Object p3) {
        if (!b) {
            throw new IllegalStateException(
                    format(errorMessageTemplate,
                            p1,
                            p2,
                            p3));
        }
    }

    /**
     * Ensures the truth of an expression
     * involving the state of the calling instance, but not
     * involving any parameters to the calling method.
     * <p>
     * <p>See {@link #checkState(
     *boolean, String, Object...)} for details.
     */
    public static void checkState(boolean b,
                                  @Nullable String errorMessageTemplate,
                                  @Nullable Object p1,
                                  @Nullable Object p2,
                                  @Nullable Object p3,
                                  @Nullable Object p4) {
        if (!b) {
            throw new IllegalStateException(
                    format(errorMessageTemplate,
                            p1,
                            p2,
                            p3,
                            p4));
        }
    }

    /**
     * Ensures that an object reference passed
     * as a parameter to the calling method is not null.
     *
     * @param reference an object reference
     * @return the non-null reference that was validated
     * @throws NullPointerException if {@code reference} is null
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static <T> T checkNotNull(T reference) {
        if (reference == null) {
            throw new NullPointerException();
        }
        return reference;
    }

    /**
     * Ensures that an object reference
     * <p>
     * passed as a parameter to the calling method is not null.
     *
     * @param reference    an object reference
     * @param errorMessage the exception message
     *                     to use if the check fails;
     *                     will be converted to a
     *                     string using {@link String#valueOf(Object)}
     * @return the non-null reference that was validated
     * @throws NullPointerException if {@code reference} is null
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static <T> T checkNotNull(T reference,
                                     @Nullable Object errorMessage) {
        if (reference == null) {
            throw new NullPointerException(
                    String.valueOf(errorMessage));
        }
        return reference;
    }

    /**
     * Ensures that an object reference passed
     * as a parameter to the calling method is not null.
     *
     * @param reference            an object reference
     * @param errorMessageTemplate a template
     *                             for the exception message
     *                             should the check fail. The
     *                             message is formed by
     *                             replacing each {@code %s}
     *                             placeholder in the template with an
     *                             argument.
     *                             These are matched by position - the
     *                             first {@code %s} gets {@code
     *                             errorMessageArgs[0]},
     *                             etc. Unmatched arguments
     *                             will be appended to the
     *                             formatted message in
     *                             square braces. Unmatched
     *                             placeholders will be left as-is.
     * @param errorMessageArgs     the arguments to be
     *                             substituted into the
     *                             message template. Arguments
     *                             are converted to strings using
     *                             {@link String#valueOf(Object)}.
     * @return the non-null reference
     * that was validated
     * @throws NullPointerException if {@code reference}
     *                              is null
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static <T> T checkNotNull(T reference,
                                     @Nullable String errorMessageTemplate,
                                     @Nullable Object... errorMessageArgs) {
        if (reference == null) {
            // If either of these parameters
            // is null, the right thing happens anyway
            throw new NullPointerException(
                    format(errorMessageTemplate,
                            errorMessageArgs));
        }
        return reference;
    }

    /**
     * Ensures that an object reference passed
     * as a parameter to the calling method is not null.
     * <p>
     * <p>See {@link #checkNotNull(
     *Object, String, Object...)} for details.
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static <T> T checkNotNull(T obj,
                                     @Nullable String errorMessageTemplate,
                                     char p1) {
        if (obj == null) {
            throw new NullPointerException(
                    format(errorMessageTemplate,
                            p1));
        }
        return obj;
    }

    /**
     * Ensures that an object reference
     * passed as a parameter to the calling method is not null.
     * <p>
     * <p>See {@link #checkNotNull(
     *Object, String, Object...)} for details.
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static <T> T checkNotNull(T obj,
                                     @Nullable String errorMessageTemplate,
                                     int p1) {
        if (obj == null) {
            throw new NullPointerException(
                    format(errorMessageTemplate,
                            p1));
        }
        return obj;
    }

    /**
     * Ensures that an object reference passed
     * as a parameter to the calling method is not null.
     * <p>
     * <p>See {@link #checkNotNull
     * (Object, String, Object...)} for details.
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static <T> T checkNotNull(T obj,
                                     @Nullable String errorMessageTemplate,
                                     long p1) {
        if (obj == null) {
            throw new NullPointerException(
                    format(errorMessageTemplate,
                            p1));
        }
        return obj;
    }

    /**
     * Ensures that an object reference passed
     * as a parameter to the calling method is not null.
     * <p>
     * <p>See {@link #checkNotNull(
     *Object, String, Object...)} for details.
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static <T> T checkNotNull(T obj,
                                     @Nullable String errorMessageTemplate,
                                     @Nullable Object p1) {
        if (obj == null) {
            throw new NullPointerException(
                    format(errorMessageTemplate,
                            p1));
        }
        return obj;
    }

    /**
     * Ensures that an object reference passed
     * as a parameter to the calling method is not null.
     * <p>
     * <p>See {@link #checkNotNull(
     *Object, String, Object...)} for details.
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static <T> T checkNotNull(T obj,
                                     @Nullable String errorMessageTemplate,
                                     char p1,
                                     char p2) {
        if (obj == null) {
            throw new NullPointerException(
                    format(errorMessageTemplate,
                            p1,
                            p2));
        }
        return obj;
    }

    /**
     * Ensures that an object reference passed
     * as a parameter to the calling method is not null.
     * <p>
     * <p>See {@link #checkNotNull(
     *Object, String, Object...)} for details.
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static <T> T checkNotNull(T obj,
                                     @Nullable String errorMessageTemplate,
                                     char p1,
                                     int p2) {
        if (obj == null) {
            throw new NullPointerException(
                    format(errorMessageTemplate,
                            p1,
                            p2));
        }
        return obj;
    }

    /**
     * Ensures that an object reference
     * passed as a parameter to the calling method is not null.
     * <p>
     * <p>See {@link #checkNotNull(
     *Object, String, Object...)} for details.
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static <T> T checkNotNull(T obj,
                                     @Nullable String errorMessageTemplate,
                                     char p1,
                                     long p2) {
        if (obj == null) {
            throw new NullPointerException(
                    format(errorMessageTemplate,
                            p1,
                            p2));
        }
        return obj;
    }

    /**
     * Ensures that an object reference
     * passed as a parameter to the calling method is not null.
     * <p>
     * <p>See {@link #checkNotNull(
     *Object, String, Object...)} for details.
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static <T> T checkNotNull(T obj,
                                     @Nullable String errorMessageTemplate,
                                     char p1,
                                     @Nullable Object p2) {
        if (obj == null) {
            throw new NullPointerException(
                    format(errorMessageTemplate,
                            p1,
                            p2));
        }
        return obj;
    }

    /**
     * Ensures that an object reference
     * passed as a parameter to the calling method is not null.
     * <p>
     * <p>See {@link #checkNotNull(
     *Object, String, Object...)} for details.
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static <T> T checkNotNull(T obj,
                                     @Nullable String errorMessageTemplate,
                                     int p1,
                                     char p2) {
        if (obj == null) {
            throw new NullPointerException(
                    format(errorMessageTemplate,
                            p1,
                            p2));
        }
        return obj;
    }

    /**
     * Ensures that an object reference
     * passed as a parameter to the calling method is not null.
     * <p>
     * <p>See {@link #checkNotNull(
     *Object, String, Object...)} for details.
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static <T> T checkNotNull(T obj,
                                     @Nullable String errorMessageTemplate,
                                     int p1,
                                     int p2) {
        if (obj == null) {
            throw new NullPointerException(
                    format(errorMessageTemplate,
                            p1,
                            p2));
        }
        return obj;
    }

    /**
     * Ensures that an object reference
     * passed as a parameter to the calling method is not null.
     * <p>
     * <p>See {@link #checkNotNull(
     *Object, String, Object...)} for details.
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static <T> T checkNotNull(T obj,
                                     @Nullable String errorMessageTemplate,
                                     int p1,
                                     long p2) {
        if (obj == null) {
            throw new NullPointerException(
                    format(errorMessageTemplate,
                            p1,
                            p2));
        }
        return obj;
    }

    /**
     * Ensures that an object reference
     * passed as a parameter to the calling method is not null.
     * <p>
     * <p>See {@link #checkNotNull(
     *Object, String, Object...)} for details.
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static <T> T checkNotNull(T obj,
                                     @Nullable String errorMessageTemplate,
                                     int p1,
                                     @Nullable Object p2) {
        if (obj == null) {
            throw new NullPointerException(
                    format(errorMessageTemplate,
                            p1,
                            p2));
        }
        return obj;
    }

    /**
     * Ensures that an object reference
     * passed as a parameter to the calling method is not null.
     * <p>
     * <p>See {@link #checkNotNull(
     *Object, String, Object...)} for details.
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static <T> T checkNotNull(T obj,
                                     @Nullable String errorMessageTemplate,
                                     long p1,
                                     char p2) {
        if (obj == null) {
            throw new NullPointerException(
                    format(errorMessageTemplate,
                            p1,
                            p2));
        }
        return obj;
    }

    /**
     * Ensures that an object reference
     * passed as a parameter to the calling method is not null.
     * <p>
     * <p>See {@link #checkNotNull(
     *Object, String, Object...)} for details.
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static <T> T checkNotNull(T obj,
                                     @Nullable String errorMessageTemplate,
                                     long p1,
                                     int p2) {
        if (obj == null) {
            throw new NullPointerException(
                    format(errorMessageTemplate,
                            p1,
                            p2));
        }
        return obj;
    }

    /**
     * Ensures that an object reference
     * passed as a parameter to the calling method is not null.
     * <p>
     * <p>See {@link #checkNotNull(
     *Object, String, Object...)} for details.
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static <T> T checkNotNull(T obj,
                                     @Nullable String errorMessageTemplate,
                                     long p1,
                                     long p2) {
        if (obj == null) {
            throw new NullPointerException(
                    format(errorMessageTemplate,
                            p1,
                            p2));
        }
        return obj;
    }

    /**
     * Ensures that an object reference
     * passed as a parameter to the calling method is not null.
     * <p>
     * <p>See {@link #checkNotNull(
     *Object, String, Object...)} for details.
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static <T> T checkNotNull(T obj,
                                     @Nullable String errorMessageTemplate,
                                     long p1,
                                     @Nullable Object p2) {
        if (obj == null) {
            throw new NullPointerException(
                    format(errorMessageTemplate,
                            p1,
                            p2));
        }
        return obj;
    }

    /**
     * Ensures that an object reference
     * passed as a parameter to the calling method is not null.
     * <p>
     * <p>See {@link #checkNotNull(
     *Object, String, Object...)} for details.
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static <T> T checkNotNull(T obj,
                                     @Nullable String errorMessageTemplate,
                                     @Nullable Object p1,
                                     char p2) {
        if (obj == null) {
            throw new NullPointerException(
                    format(errorMessageTemplate,
                            p1,
                            p2));
        }
        return obj;
    }

    /**
     * Ensures that an object reference passed
     * as a parameter to the calling method is not null.
     * <p>
     * <p>See {@link #checkNotNull(
     *Object, String, Object...)} for details.
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static <T> T checkNotNull(T obj,
                                     @Nullable String errorMessageTemplate,
                                     @Nullable Object p1,
                                     int p2) {
        if (obj == null) {
            throw new NullPointerException(
                    format(errorMessageTemplate,
                            p1,
                            p2));
        }
        return obj;
    }

    /**
     * Ensures that an object reference
     * passed as a parameter to the calling method is not null.
     * <p>
     * <p>See {@link #checkNotNull(
     *Object, String, Object...)} for details.
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static <T> T checkNotNull(T obj,
                                     @Nullable String errorMessageTemplate,
                                     @Nullable Object p1,
                                     long p2) {
        if (obj == null) {
            throw new NullPointerException(
                    format(errorMessageTemplate,
                            p1,
                            p2));
        }
        return obj;
    }

    /**
     * Ensures that an object reference
     * passed as a parameter to the calling method is not null.
     * <p>
     * <p>See {@link #checkNotNull(
     *Object, String, Object...)} for details.
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static <T> T checkNotNull(T obj,
                                     @Nullable String errorMessageTemplate,
                                     @Nullable Object p1,
                                     @Nullable Object p2) {
        if (obj == null) {
            throw new NullPointerException(
                    format(errorMessageTemplate,
                            p1,
                            p2));
        }
        return obj;
    }

    /**
     * Ensures that an object reference
     * passed as a parameter to the calling method is not null.
     * <p>
     * <p>See {@link #checkNotNull(
     *Object, String, Object...)} for details.
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static <T> T checkNotNull(T obj,
                                     @Nullable String errorMessageTemplate,
                                     @Nullable Object p1,
                                     @Nullable Object p2,
                                     @Nullable Object p3) {
        if (obj == null) {
            throw new NullPointerException(
                    format(errorMessageTemplate,
                            p1,
                            p2,
                            p3));
        }
        return obj;
    }

    /**
     * Ensures that an object reference
     * passed as a parameter to the calling method is not null.
     * <p>
     * <p>See {@link #checkNotNull(
     *Object, String, Object...)} for details.
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static <T> T checkNotNull(T obj,
                                     @Nullable String errorMessageTemplate,
                                     @Nullable Object p1,
                                     @Nullable Object p2,
                                     @Nullable Object p3,
                                     @Nullable Object p4) {
        if (obj == null) {
            throw new NullPointerException(
                    format(errorMessageTemplate,
                            p1,
                            p2,
                            p3,
                            p4));
        }
        return obj;
    }


    /**
     * Ensures that {@code getIndex} specifies a
     * valid <i>element</i> in an array, list or string of size
     * {@code size}. An element getIndex may range
     * from zero, inclusive, to {@code size}, exclusive.
     *
     * @param index a user-supplied getIndex
     *              identifying an element of an array,
     *              list or string
     * @param size  the size of that array, list or string
     * @return the value of {@code getIndex}
     * @throws IndexOutOfBoundsException if
     *                                   {@code getIndex}
     *                                   is negative or is not
     *                                   less than {@code size}
     * @throws IllegalArgumentException  if
     *                                   {@code size} is negative
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static int checkElementIndex(int index,
                                        int size) {
        return checkElementIndex(index,
                size,
                "getIndex");
    }

    /**
     * Ensures that {@code getIndex} specifies
     * a valid <i>element</i> in an array, list or
     * string of size
     * {@code size}. An element getIndex may range
     * from zero, inclusive, to {@code size}, exclusive.
     *
     * @param index a user-supplied getIndex
     *              identifying an element of an array,
     *              list or string
     * @param size  the size of that array,
     *              list or string
     * @param desc  the text to use to describe
     *              this getIndex in an error message
     * @return the value of {@code getIndex}
     * @throws IndexOutOfBoundsException if
     *                                   {@code getIndex}
     *                                   is negative or is not
     *                                   less than {@code size}
     * @throws IllegalArgumentException  if
     *                                   {@code size} is negative
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static int checkElementIndex(int index,
                                        int size,
                                        @Nullable String desc) {
        // Carefully optimized for execution
        // by hotspot (explanatory comment above)
        if (index < 0 ||
                index >= size) {
            throw new IndexOutOfBoundsException(
                    badElementIndex(index,
                            size,
                            desc));
        }
        return index;
    }

    private static String badElementIndex(int index,
                                          int size,
                                          String desc) {
        if (index < 0) {
            return format("%s (%s) must not be negative",
                    desc,
                    index);
        } else if (size < 0) {
            throw new IllegalArgumentException("negative size: " +
                    size);
        } else { // getIndex >= size
            return format("%s (%s) must be less than size (%s)",
                    desc,
                    index,
                    size);
        }
    }

    /**
     * Ensures that {@code getIndex} specifies a
     * valid <i>position</i> in an array,
     * list or string of
     * size {@code size}. A position getIndex may
     * range from zero to {@code size}, inclusive.
     *
     * @param index a user-supplied getIndex
     *              identifying a position
     *              in an array,
     *              list or string
     * @param size  the size of that array,
     *              list or string
     * @return the value of {@code getIndex}
     * @throws IndexOutOfBoundsException if
     *                                   {@code getIndex} is negative
     *                                   or is greater than
     *                                   {@code size}
     * @throws IllegalArgumentException  if
     *                                   {@code size} is negative
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static int checkPositionIndex(int index,
                                         int size) {
        return checkPositionIndex(index,
                size,
                "getIndex");
    }

    /**
     * Ensures that {@code getIndex} specifies a
     * valid <i>position</i> in an array, list or string of
     * size {@code size}. A position getIndex may
     * range from zero to {@code size}, inclusive.
     *
     * @param index a user-supplied getIndex
     *              identifying a position in an array,
     *              list or string
     * @param size  the size of that array,
     *              list or string
     * @param desc  the text to use to describe
     *              this getIndex in an error message
     * @return the value of {@code getIndex}
     * @throws IndexOutOfBoundsException if
     *                                   {@code getIndex} is negative
     *                                   or is greater than
     *                                   {@code size}
     * @throws IllegalArgumentException  if
     *                                   {@code size} is negative
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static int checkPositionIndex(int index,
                                         int size,
                                         @Nullable String desc) {
        // Carefully optimized for execution
        // by hotspot (explanatory comment above)
        if (index < 0 ||
                index > size) {
            throw new IndexOutOfBoundsException(
                    badPositionIndex(index,
                            size,
                            desc));
        }
        return index;
    }

    private static String badPositionIndex(int index,
                                           int size,
                                           String desc) {
        if (index < 0) {
            return format("%s (%s) must not be negative",
                    desc,
                    index);
        } else if (size < 0) {
            throw new IllegalArgumentException("negative size: " +
                    size);
        } else { // getIndex > size
            return format("%s (%s) must not be " +
                            "greater than size (%s)",
                    desc,
                    index,
                    size);
        }
    }

    /**
     * Ensures that {@code start} and
     * {@code end} specify a valid <i>positions</i> in an array, list
     * or string of size {@code size},
     * and are in order. A position getIndex may range from zero to
     * {@code size}, inclusive.
     *
     * @param start a user-supplied getIndex
     *              identifying a starting position in
     *              an array, list or string
     * @param end   a user-supplied getIndex
     *              identifying a ending position in an
     *              array, list or string
     * @param size  the size of that array,
     *              list or string
     * @throws IndexOutOfBoundsException if either getIndex is
     *                                   negative or is greater than {@code size},
     *                                   or if {@code end} is less than
     *                                   {@code start}
     * @throws IllegalArgumentException  if {@code size} is negative
     */
    public static void checkPositionIndexes(int start,
                                            int end,
                                            int size) {
        // Carefully optimized for execution
        // by hotspot (explanatory comment above)
        if (start < 0 ||
                end < start ||
                end > size) {
            throw new IndexOutOfBoundsException(
                    badPositionIndexes(start,
                            end,
                            size));
        }
    }

    private static String badPositionIndexes(int start,
                                             int end,
                                             int size) {
        if (start < 0 ||
                start > size) {
            return badPositionIndex(start,
                    size,
                    "start getIndex");
        }
        if (end < 0 ||
                end > size) {
            return badPositionIndex(end,
                    size,
                    "end getIndex");
        }
        // end < start
        return format("end getIndex (%s) must not be l" +
                        "ess than start getIndex (%s)",
                end,
                start);
    }

    /**
     * Substitutes each {@code %s} in
     * {@code template} with an argument.
     * These are matched by
     * position: the first {@code %s} gets
     * {@code args[0]}, etc. If there are more arguments than
     * placeholders, the unmatched arguments
     * will be appended to the end of the formatted message in
     * square braces.
     *
     * @param template a non-null string
     *                 containing 0 or more {@code %s} placeholders.
     * @param args     the arguments to be
     *                 substituted into the message template.
     *                 Arguments are converted
     *                 to strings using {@link String#valueOf(Object)}.
     *                 Arguments can be null.
     */
    // Note that this is somewhat-improperly
    // used from Verify.java as well.
    static String format(String template,
                         @Nullable Object... args) {
        template = String.valueOf(template); // null -> "null"

        // start substituting the arguments i
        // nto the '%s' placeholders
        StringBuilder builder = new StringBuilder(template.length() +
                16 *
                        args.length);
        int templateStart = 0;
        int i = 0;
        while (i < args.length) {
            int placeholderStart = template.indexOf("%s",
                    templateStart);
            if (placeholderStart == -1) {
                break;
            }
            builder.append(template,
                    templateStart
                    , placeholderStart);
            builder.append(args[i++]);
            templateStart = placeholderStart + 2;
        }
        builder.append(template,
                templateStart,
                template.length());

        // if we run out of placeholders,
        // append the extra args in square braces
        if (i < args.length) {
            builder.append(" [");
            builder.append(args[i++]);
            while (i < args.length) {
                builder.append(", ");
                builder.append(args[i++]);
            }
            builder.append(']');
        }

        return builder.toString();
    }
}
