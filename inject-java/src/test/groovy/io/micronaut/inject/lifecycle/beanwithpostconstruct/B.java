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
package io.micronaut.inject.lifecycle.beanwithpostconstruct;

import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

@Singleton
public class B {

    boolean setupComplete = false;
    boolean injectedFirst = false;

    @Inject
    protected A another;
    private A a;

    @Inject
    public void setA(A a ) {
        this.a = a;
    }

    public A getA() {
        return a;
    }

    @PostConstruct
    public void setup() {
        if(a != null && another != null) {
            injectedFirst = true;
        }
        setupComplete = true;
    }
}
