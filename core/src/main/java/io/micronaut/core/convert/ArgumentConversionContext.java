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
package io.micronaut.core.convert;

import io.micronaut.core.annotation.AnnotationMetadata;
import io.micronaut.core.annotation.AnnotationMetadataProvider;
import io.micronaut.core.type.Argument;

import java.util.Map;

/**
 * Extended version of the {@link ConversionContext} specifically for conversion {@link Argument} instances.
 *
 * @param <T> The type
 * @author Graeme Rocher
 * @since 1.0
 */
public interface ArgumentConversionContext<T> extends ConversionContext, AnnotationMetadataProvider {

    /**
     * @return The {@link Argument} being converted
     */
    Argument<T> getArgument();

    @Override
    default Argument[] getTypeParameters() {
        return getArgument().getTypeParameters();
    }

    @Override
    default Map<String, Argument<?>> getTypeVariables() {
        return getArgument().getTypeVariables();
    }

    @Override
    default AnnotationMetadata getAnnotationMetadata() {
        return getArgument().getAnnotationMetadata();
    }

    /**
     * Augment this context with annotation metadata.
     *
     * @param annotationMetadata The annotation metadata
     * @return The conversion context
     */
    @SuppressWarnings("unchecked")
    default ArgumentConversionContext<T> with(AnnotationMetadata annotationMetadata) {

        ConversionContext thisContext = this;
        return new DefaultArgumentConversionContext(getArgument(), thisContext.getLocale(), thisContext.getCharset()) {
            @Override
            public AnnotationMetadata getAnnotationMetadata() {
                return annotationMetadata;
            }
        };
    }
}
