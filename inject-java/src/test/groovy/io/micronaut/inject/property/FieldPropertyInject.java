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
package io.micronaut.inject.property;

import io.micronaut.core.annotation.Nullable;
import io.micronaut.context.annotation.Property;
import io.micronaut.context.annotation.Requires;
import io.micronaut.core.convert.format.MapFormat;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import java.util.List;
import java.util.Map;

@Requires(property = "spec.name", value = "PropertyAnnotationSpec")
@Singleton
public class FieldPropertyInject {


    @Property(name = "my.map")
    @MapFormat(transformation = MapFormat.MapTransformation.FLAT)
    Map<String, String> values;

    @Property(name = "my.map")
    Map<String, String> defaultInject;

    @Property(name = "my.multi-value-map")
    Map<String, List<String>> multiMap;

    @Property(name = "my.string")
    @Inject
    String str;

    @Property(name = "my.int")
    int integer;

    @Property(name = "my.nullable")
    @Nullable
    String nullable;

    public Map<String, String> getValues() {
        return values;
    }

    public String getStr() {
        return str;
    }

    public int getInteger() {
        return integer;
    }

    public String getNullable() {
        return nullable;
    }


}
