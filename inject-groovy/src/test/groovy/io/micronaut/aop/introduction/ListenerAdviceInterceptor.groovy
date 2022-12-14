/*
 * Copyright 2017-2020 original authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.micronaut.aop.introduction

import io.micronaut.aop.MethodInterceptor
import io.micronaut.aop.MethodInvocationContext
import io.micronaut.core.annotation.Nullable
import jakarta.inject.Singleton

/**
 * @author graemerocher
 * @since 1.0
 */
@Singleton
class ListenerAdviceInterceptor implements MethodInterceptor<Object,Object> {

    private Set<Object> recievedMessages = new HashSet<>()

    @Override
    int getOrder() {
        return  -10
    }

    Set<Object> getRecievedMessages() {
        return recievedMessages
    }

    @Nullable
    @Override
    Object intercept(MethodInvocationContext<Object, Object> context) {
        if(context.getMethodName().equalsIgnoreCase("onApplicationEvent")) {
            Object v = context.getParameterValues()[0]
            recievedMessages.add(v)
            return null
        }
        else {
            return context.proceed()
        }
    }
}

