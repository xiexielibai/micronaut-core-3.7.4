/*
 * Copyright 2017-2020 original authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.micronaut.inject.ast;

import java.util.Locale;

/**
 * An enum of modifier names now tied to the reflection API.
 *
 * @author graemerocher
 * @since 1.0.3
 */
public enum ElementModifier {
    PUBLIC,
    PROTECTED,
    PRIVATE,
    ABSTRACT,
    DEFAULT,
    STATIC,
    FINAL,
    TRANSIENT,
    VOLATILE,
    SYNCHRONIZED,
    NATIVE,
    STRICTFP;

    /**
     * @return The name of the modifier as presented in source code.
     * @since 3.0.0
     */
    @Override
    public String toString() {
        return name().toLowerCase(Locale.ENGLISH);
    }
}
