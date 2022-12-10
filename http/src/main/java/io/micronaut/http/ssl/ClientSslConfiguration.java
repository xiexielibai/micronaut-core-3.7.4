/*
 * Copyright 2017-2022 original authors
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
package io.micronaut.http.ssl;

import io.micronaut.context.annotation.BootstrapContextCompatible;
import io.micronaut.context.annotation.ConfigurationProperties;
import io.micronaut.context.annotation.Requires;
import io.micronaut.core.annotation.Nullable;
import jakarta.inject.Inject;

/**
 * The default {@link SslConfiguration} used for HTTP clients.
 *
 * @author Graeme Rocher
 * @since 1.0
 */
@ConfigurationProperties(ClientSslConfiguration.PREFIX)
@BootstrapContextCompatible
public class ClientSslConfiguration extends AbstractClientSslConfiguration {

    /**
     * The prefix used to resolve this configuration.
     */
    public static final String PREFIX = "micronaut.http.client.ssl";

    /**
     * Overrides the default constructor and sets {@link #isEnabled()} to true.
     *
     * @param defaultSslConfiguration The default SSL config
     * @param defaultKeyConfiguration The default key config
     * @param defaultKeyStoreConfiguration The default keystore config
     * @param defaultTrustStoreConfiguration The Default truststore config
     */
    @Inject
    public ClientSslConfiguration(
            DefaultSslConfiguration defaultSslConfiguration,
            DefaultSslConfiguration.DefaultKeyConfiguration defaultKeyConfiguration,
            DefaultSslConfiguration.DefaultKeyStoreConfiguration defaultKeyStoreConfiguration,
            SslConfiguration.TrustStoreConfiguration defaultTrustStoreConfiguration) {
        readExisting(defaultSslConfiguration, defaultKeyConfiguration, defaultKeyStoreConfiguration, defaultTrustStoreConfiguration);
        setEnabled(true);
    }

    /**
     * The default client configuration.
     */
    public ClientSslConfiguration() {
        setEnabled(true);
    }

    /**
     * Sets the key configuration.
     *
     * @param keyConfiguration The key configuration.
     */
    @Inject
    void setKey(@Nullable DefaultKeyConfiguration keyConfiguration) {
        if (keyConfiguration != null) {
            super.setKey(keyConfiguration);
        }
    }

    /**
     * Sets the key store.
     *
     * @param keyStoreConfiguration The key store configuration
     */
    @SuppressWarnings("unused")
    @Inject
    void setKeyStore(@Nullable DefaultKeyStoreConfiguration keyStoreConfiguration) {
        if (keyStoreConfiguration != null) {
            super.setKeyStore(keyStoreConfiguration);
        }
    }

    /**
     * Sets trust store configuration.
     *
     * @param trustStore The trust store configuration
     */
    @SuppressWarnings("unused")
    @Inject
    void setTrustStore(@Nullable DefaultTrustStoreConfiguration trustStore) {
        if (trustStore != null) {
            super.setTrustStore(trustStore);
        }
    }

    /**
     * The default {@link io.micronaut.http.ssl.SslConfiguration.KeyConfiguration}.
     */
    @SuppressWarnings("WeakerAccess")
    @ConfigurationProperties(KeyConfiguration.PREFIX)
    @BootstrapContextCompatible
    @Requires(property = ClientSslConfiguration.PREFIX + "." + KeyConfiguration.PREFIX)
    public static class DefaultKeyConfiguration extends KeyConfiguration {
    }

    /**
     * The default {@link io.micronaut.http.ssl.SslConfiguration.KeyStoreConfiguration}.
     */
    @SuppressWarnings("WeakerAccess")
    @ConfigurationProperties(KeyStoreConfiguration.PREFIX)
    @BootstrapContextCompatible
    @Requires(property = ClientSslConfiguration.PREFIX + "." + KeyStoreConfiguration.PREFIX)
    public static class DefaultKeyStoreConfiguration extends KeyStoreConfiguration {
    }

    /**
     * The default {@link io.micronaut.http.ssl.SslConfiguration.TrustStoreConfiguration}.
     */
    @SuppressWarnings("WeakerAccess")
    @ConfigurationProperties(TrustStoreConfiguration.PREFIX)
    @BootstrapContextCompatible
    @Requires(property = ClientSslConfiguration.PREFIX + "." + TrustStoreConfiguration.PREFIX)
    public static class DefaultTrustStoreConfiguration extends TrustStoreConfiguration {
    }
}
