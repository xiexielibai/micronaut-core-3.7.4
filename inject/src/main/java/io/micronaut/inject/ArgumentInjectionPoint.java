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
package io.micronaut.inject;

import io.micronaut.core.type.Argument;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.core.type.ArgumentCoercible;

/**
 * An injection point for a method or constructor argument.
 *
 * @param <B> The declaring bean type
 * @param <T> The argument type
 * @author graemerocher
 * @since 1.0
 */
public interface ArgumentInjectionPoint<B, T> extends InjectionPoint<B>, ArgumentCoercible<T> {

    /**
     * @return The outer injection point (method or constructor)
     */
    @NonNull CallableInjectionPoint<B> getOuterInjectionPoint();

    /**
     * @return The argument that is being injected
     */
    @NonNull Argument<T> getArgument();

    @Override
    default Argument<T> asArgument() {
        return getArgument();
    }
}
