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
package io.micronaut.http.client.docs.annotation.retry;

import io.micronaut.http.client.docs.annotation.Pet;
import io.micronaut.http.client.docs.annotation.PetOperations;
import io.micronaut.retry.annotation.Fallback;
import reactor.core.publisher.Mono;

/**
 * @author graemerocher
 * @since 1.0
 */
// tag::class[]
@Fallback
public class PetFallback implements PetOperations {
    @Override
    public Mono<Pet> save(String name, int age) {
        Pet pet = new Pet();
        pet.setAge(age);
        pet.setName(name);
        return Mono.just(pet);
    }
}
// end::class[]
