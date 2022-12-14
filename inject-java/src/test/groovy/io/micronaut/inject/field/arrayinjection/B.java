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
package io.micronaut.inject.field.arrayinjection;

import io.micronaut.context.BeanContext;
import jakarta.inject.Inject;
import java.util.Arrays;
import java.util.List;

public class B {
    @Inject
    private A[] all;
    @Inject
    protected A[] all2;
    @Inject
    protected BeanContext beanContext;

    List<A> getAll() {
        return Arrays.asList(this.all);
    }
}
