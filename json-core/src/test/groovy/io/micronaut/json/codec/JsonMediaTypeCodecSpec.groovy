/*
 * Copyright 2017-2019 original authors
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
package io.micronaut.json.codec

import io.micronaut.context.ApplicationContext
import io.micronaut.http.MediaType
import spock.lang.Specification

class JsonMediaTypeCodecSpec extends Specification {

    void "test additional type configuration"() {
        given:
        ApplicationContext ctx = ApplicationContext.run([
                'micronaut.codec.json.additionalTypes': ['text/javascript']
        ])

        when:
        JsonMediaTypeCodec codec = ctx.getBean(JsonMediaTypeCodec)

        then:
        codec.mediaTypes.size() == 2
        codec.mediaTypes.contains(MediaType.of("text/javascript"))
        codec.mediaTypes.contains(MediaType.APPLICATION_JSON_TYPE)

        cleanup:
        ctx.close()
    }
}
