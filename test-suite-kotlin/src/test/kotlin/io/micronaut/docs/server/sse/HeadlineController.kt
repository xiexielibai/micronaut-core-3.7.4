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
package io.micronaut.docs.server.sse

// tag::imports[]
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.sse.Event
import io.micronaut.scheduling.TaskExecutors
import io.micronaut.scheduling.annotation.ExecuteOn
import org.reactivestreams.Publisher
import reactor.core.publisher.Flux
import reactor.core.publisher.SynchronousSink
import java.util.concurrent.Callable
import java.util.function.BiFunction

// end::imports[]

// tag::class[]
@Controller("/headlines")
class HeadlineController {

    @ExecuteOn(TaskExecutors.IO)
    @Get(produces = [MediaType.TEXT_EVENT_STREAM])
    fun index(): Publisher<Event<Headline>> { // <1>
        val versions = arrayOf("1.0", "2.0") // <2>
        return Flux.generate(
            { 0 },
            BiFunction { i: Int, emitter: SynchronousSink<Event<Headline>> ->  // <3>
                if (i < versions.size) {
                    emitter.next( // <4>
                        Event.of(
                            Headline(
                                "Micronaut " + versions[i] + " Released", "Come and get it"
                            )
                        )
                    )
                } else {
                    emitter.complete() // <5>
                }
                return@BiFunction i + 1
            })
    }
}
// end::class[]
