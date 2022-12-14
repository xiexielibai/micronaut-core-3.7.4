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
package io.micronaut.http.server.netty.types.files;

import io.micronaut.core.annotation.Internal;
import io.micronaut.http.MediaType;
import io.micronaut.http.MutableHttpResponse;
import io.micronaut.http.server.netty.types.NettyFileCustomizableResponseType;
import io.micronaut.http.server.netty.types.stream.NettyStreamedCustomizableResponseType;
import io.micronaut.http.server.types.files.StreamedFile;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaderValues;

import java.io.InputStream;
import java.net.URL;
import java.util.Optional;

/**
 * Writes a file backed by an {@link InputStream} to the Netty context.
 *
 * @author James Kleeh
 * @since 1.0
 */
@Internal
public class NettyStreamedFileCustomizableResponseType extends StreamedFile implements NettyFileCustomizableResponseType, NettyStreamedCustomizableResponseType {

    private final Optional<StreamedFile> delegate;

    /**
     * @param inputStream The input stream
     * @param name        The file name
     */
    public NettyStreamedFileCustomizableResponseType(InputStream inputStream, String name) {
        super(inputStream, MediaType.forFilename(name));
        this.delegate = Optional.empty();
    }

    /**
     * @param inputStream The input stream
     * @param mediaType   The file media type
     */
    public NettyStreamedFileCustomizableResponseType(InputStream inputStream, MediaType mediaType) {
        super(inputStream, mediaType);
        this.delegate = Optional.empty();
    }

    /**
     * @param url The URL
     */
    public NettyStreamedFileCustomizableResponseType(URL url) {
        super(url);
        this.delegate = Optional.empty();
    }

    /**
     * @param delegate The streamed file
     */
    public NettyStreamedFileCustomizableResponseType(StreamedFile delegate) {
        super(delegate.getInputStream(), delegate.getMediaType(), delegate.getLastModified(), delegate.getLength());
        this.delegate = Optional.of(delegate);
    }

    @Override
    public void process(MutableHttpResponse response) {
        long length = getLength();
        if (length > -1) {
            response.header(HttpHeaderNames.CONTENT_LENGTH, String.valueOf(length));
        } else {
            response.header(HttpHeaderNames.TRANSFER_ENCODING, HttpHeaderValues.CHUNKED);
        }
        delegate.ifPresent(type -> type.process(response));
    }
}
