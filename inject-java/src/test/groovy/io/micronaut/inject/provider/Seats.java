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
package io.micronaut.inject.provider;

import io.micronaut.core.annotation.Nullable;
import org.atinject.jakartatck.auto.Drivers;
import org.atinject.jakartatck.auto.Seat;
import org.atinject.jakartatck.auto.Tire;

import jakarta.inject.Named;
import jakarta.inject.Provider;
import jakarta.inject.Singleton;

@Singleton
public class Seats {
    Provider<Seat> driversSeatProvider;
    Provider<Tire> spareTireProvider;

    Seats(@Drivers Provider<Seat> driversSeatProvider,
          @Named("spare") Provider<Tire> spareTireProvider,
          @Nullable Provider<NotABean> notABeanProvider) {
        this.driversSeatProvider = driversSeatProvider;
        this.spareTireProvider = spareTireProvider;
        assert notABeanProvider == null;
    }
}
