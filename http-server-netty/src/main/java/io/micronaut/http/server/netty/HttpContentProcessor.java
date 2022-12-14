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
package io.micronaut.http.server.netty;

import io.micronaut.core.async.publisher.Publishers;
import io.micronaut.core.util.Toggleable;
import io.netty.buffer.ByteBufHolder;
import org.reactivestreams.Subscriber;

/**
 * A reactive streams {@link org.reactivestreams.Processor} that processes incoming {@link ByteBufHolder} and
 * outputs a given type.
 *
 * @param <T> The type
 * @author Graeme Rocher
 * @since 1.0
 */
public interface HttpContentProcessor<T> extends Publishers.MicronautPublisher<T>, Subscriber<ByteBufHolder>, Toggleable {
}
