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
package io.micronaut.docs.replaces;

import io.micronaut.context.ApplicationContext;
import io.micronaut.docs.replaces.qualifiers.annotations.beans.SomeInterfaceClientReplaceQualifiers;
import io.micronaut.docs.replaces.qualifiers.annotations.beans.SomeInterfaceReplaceQualifiersImplOneReplacement;
import io.micronaut.docs.replaces.qualifiers.annotations.beans.SomeInterfaceReplaceQualifiersImplTwoReplacement;
import io.micronaut.docs.replaces.qualifiers.named.beans.SomeInterfaceClientReplaceNamed;
import io.micronaut.docs.replaces.qualifiers.named.beans.SomeInterfaceReplaceNamedImplOneReplacement;
import io.micronaut.docs.replaces.qualifiers.named.beans.SomeInterfaceReplaceNamedImplTwoReplacement;
import io.micronaut.docs.requires.Book;
import org.junit.Assert;
import org.junit.Test;

public class ReplacesTest {

    @Test
    public void testReplaces() {
        ApplicationContext applicationContext = ApplicationContext.run();

        Assert.assertTrue(
            applicationContext.getBean(BookService.class) instanceof MockBookService
        );
        Assert.assertEquals("An OK Novel", applicationContext.getBean(Book.class).getTitle());
        Assert.assertEquals("Learning 305", applicationContext.getBean(TextBook.class).getTitle());

        final SomeInterfaceClientReplaceQualifiers someInterfaceClientReplaceQualifiers = applicationContext.getBean(SomeInterfaceClientReplaceQualifiers.class);
        Assert.assertTrue(someInterfaceClientReplaceQualifiers.getSomeInterfaceOne() instanceof SomeInterfaceReplaceQualifiersImplOneReplacement);
        Assert.assertTrue(someInterfaceClientReplaceQualifiers.getSomeInterfaceTwo() instanceof SomeInterfaceReplaceQualifiersImplTwoReplacement);

        final SomeInterfaceClientReplaceNamed someInterfaceClientReplaceNamed = applicationContext.getBean(SomeInterfaceClientReplaceNamed.class);
        Assert.assertTrue(someInterfaceClientReplaceNamed.getSomeInterfaceOne() instanceof SomeInterfaceReplaceNamedImplOneReplacement);
        Assert.assertTrue(someInterfaceClientReplaceNamed.getSomeInterfaceTwo() instanceof SomeInterfaceReplaceNamedImplTwoReplacement);

        applicationContext.stop();
    }
}
