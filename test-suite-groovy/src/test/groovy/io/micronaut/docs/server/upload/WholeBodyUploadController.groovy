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
package io.micronaut.docs.server.upload

// tag::class[]
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Post
import io.micronaut.http.multipart.CompletedFileUpload
import io.micronaut.http.multipart.CompletedPart
import io.micronaut.http.server.multipart.MultipartBody
import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription
import reactor.core.publisher.Mono

import static io.micronaut.http.MediaType.MULTIPART_FORM_DATA
import static io.micronaut.http.MediaType.TEXT_PLAIN

@Controller("/upload")
class WholeBodyUploadController {

    @Post(value = "/whole-body", consumes = MULTIPART_FORM_DATA, produces = TEXT_PLAIN) // <1>
    Mono<String> uploadBytes(@Body MultipartBody body) { // <2>

        Mono.<String>create({ emitter ->
            body.subscribe(new Subscriber<CompletedPart>() {
                private Subscription s

                @Override
                void onSubscribe(Subscription s) {
                    this.s = s
                    s.request(1)
                }

                @Override
                void onNext(CompletedPart completedPart) {
                    String partName = completedPart.name
                    if (completedPart instanceof CompletedFileUpload) {
                        String originalFileName = completedPart.filename
                    }
                }

                @Override
                void onError(Throwable t) {
                    emitter.error(t)
                }

                @Override
                void onComplete() {
                    emitter.success("Uploaded")
                }
            })
        })
    }
}
// end::class[]
