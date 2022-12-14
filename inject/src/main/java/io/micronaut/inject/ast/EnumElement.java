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

import java.util.Collections;
import java.util.List;

/**
 * Models an enum type.
 *
 * @author graemerocher
 * @since 1.0
 */
public interface EnumElement extends ClassElement {

    /**
     * The values that make up this enum.
     *
     * @return The values
     */
    List<String> values();

    /**
     * The enum constant elements that make up this enum.
     *
     * @return The enum constant elements
     *
     * @since 3.6.0
     */
    default List<EnumConstantElement> elements() {
        return Collections.emptyList();
    }
}
