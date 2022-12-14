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
package io.micronaut.runtime.event.annotation;

import io.micronaut.context.event.StartupEvent;
import io.micronaut.scheduling.annotation.Async;
import jakarta.inject.Singleton;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Mono;
import io.micronaut.core.async.annotation.SingleResult;

@Singleton
public class AsyncListener {

    boolean completableInvoked = false;

    @EventListener
    @Async
    @SingleResult
    Publisher<Void> onCompletableStartup(StartupEvent event) {
        try {
            Thread.currentThread().sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        completableInvoked = true;
        return Mono.empty();
    }
}
