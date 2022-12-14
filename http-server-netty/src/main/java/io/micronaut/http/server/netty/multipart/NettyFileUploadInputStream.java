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
package io.micronaut.http.server.netty.multipart;


import io.micronaut.core.annotation.Internal;
import io.micronaut.core.annotation.NonNull;
import io.netty.handler.codec.http.multipart.FileUpload;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * An input stream for a netty file upload that allows
 * for releasing the file after the stream is closed.
 *
 * @author James Kleeh
 * @since 1.3.4
 */
@Internal
class NettyFileUploadInputStream extends FileInputStream {

    @NonNull
    private final FileUpload file;
    private final boolean releaseOnClose;
    private final AtomicBoolean closed = new AtomicBoolean();

    /**
     * @param file The netty file upload
     * @param releaseOnClose Whether to release the file after the stream is closed
     * @throws IOException If an error occurred getting the underlying {@link java.io.File}
     */
    NettyFileUploadInputStream(@NonNull FileUpload file, boolean releaseOnClose) throws IOException {
        super(file.getFile());
        this.file = file;
        this.releaseOnClose = releaseOnClose;
    }

    @Override
    public void close() throws IOException {
        try {
            super.close();
        } finally {
            if (releaseOnClose && closed.compareAndSet(false, true)) {
                file.release();
            }
        }
    }
}
