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
package io.micronaut.aop.factory;

import io.micronaut.aop.simple.Mutating;
import io.micronaut.context.annotation.Factory;
import io.micronaut.context.annotation.Primary;
import io.micronaut.context.annotation.Prototype;

import jakarta.inject.Named;

/**
 * @author Graeme Rocher
 * @since 1.0
 */
@Factory
public class ConcreteClassFactory {

    @Prototype
    @Mutating("name")
    @Primary
    ConcreteClass concreteClass() {
        return new ConcreteClass(new AnotherClass());
    }

    @Prototype
    @Mutating("name")
    @Named("another")
    ConcreteClass anotherImpl() {
        return new ConcreteClass(new AnotherClass());
    }

}
