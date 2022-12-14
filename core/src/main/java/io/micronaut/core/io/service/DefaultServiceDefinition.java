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
package io.micronaut.core.io.service;

import io.micronaut.core.annotation.Internal;
import io.micronaut.core.annotation.Nullable;

import java.util.Optional;
import java.util.ServiceConfigurationError;
import java.util.function.Supplier;

/**
 * Default implementation of {@link ServiceDefinition}.
 *
 * @param <S> The type
 * @author Graeme Rocher
 * @since 1.0
 */
@Internal
final class DefaultServiceDefinition<S> implements ServiceDefinition<S> {
    private final String name;
    @Nullable
    private final Class<S> loadedClass;

    /**
     * @param name        The name
     * @param loadedClass The loaded class
     */
    DefaultServiceDefinition(String name, @Nullable Class<S> loadedClass) {
        this.name = name;
        this.loadedClass = loadedClass;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean isPresent() {
        return loadedClass != null;
    }

    @Override
    @SuppressWarnings("java:S1181")
    public <X extends Throwable> S orElseThrow(Supplier<? extends X> exceptionSupplier) throws X {
        if (loadedClass == null) {
            throw exceptionSupplier.get();
        }
        try {
            return loadedClass.getDeclaredConstructor().newInstance();
        } catch (Throwable e) {
            throw exceptionSupplier.get();
        }
    }

    @Override
    public S load() {
        return Optional.ofNullable(loadedClass).map(aClass -> {
            try {
                return aClass.getDeclaredConstructor().newInstance();
            } catch (Throwable e) {
                throw new ServiceConfigurationError("Error loading service [" + aClass.getName() + "]: " + e.getMessage(), e);
            }
        }).orElseThrow(() -> new ServiceConfigurationError("Call to load() when class '" + name + "' is not present"));
    }
}
