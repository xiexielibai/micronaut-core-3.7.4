package io.micronaut.inject.annotation

import io.micronaut.core.annotation.NonNull
import io.micronaut.core.annotation.Nullable
import io.micronaut.core.annotation.AnnotationMetadata
import io.micronaut.core.annotation.AnnotationUtil
import io.micronaut.annotation.processing.test.AbstractTypeElementSpec

class AnnotationRemapperSpec extends AbstractTypeElementSpec {

    void "test nullable"() {
        given:
        AnnotationMetadata metadata = buildMethodArgumentAnnotationMetadata('''
package test;

import io.micronaut.core.annotation.Nullable;
@jakarta.inject.Singleton
class Test {

    void test(@Nullable String id) {
    
    }
}
''', 'test', 'id')

        expect:
        metadata != null
        !metadata.empty
        metadata.hasAnnotation(AnnotationUtil.NULLABLE)
        !metadata.hasAnnotation(Nullable)
    }

    void "test nonnull"() {
        given:
        AnnotationMetadata metadata = buildMethodArgumentAnnotationMetadata('''
package test;

import io.micronaut.core.annotation.NonNull;
@jakarta.inject.Singleton
class Test {

    void test(@NonNull String id) {
    
    }
}
''', 'test', 'id')

        expect:
        metadata != null
        !metadata.empty
        metadata.hasAnnotation(AnnotationUtil.NON_NULL)
        !metadata.hasAnnotation(NonNull)
    }

    void "test argument is null"() {
        given:
        def definition = buildBeanDefinition('test.Test', '''
package test;

import io.micronaut.core.annotation.NonNull;
@jakarta.inject.Singleton
class Test {

    @io.micronaut.context.annotation.Executable
    void test(@NonNull String one, @io.micronaut.core.annotation.Nullable String two) {
    
    }
}
''')
        def method = definition.executableMethods.first()

        expect:
        method.arguments[0].isNonNull()
        method.arguments[0].isDeclaredNonNull()
        !method.arguments[0].isNullable()
        !method.arguments[0].isDeclaredNullable()
        method.arguments[1].isNullable()
        method.arguments[1].isDeclaredNullable()
        !method.arguments[1].isNonNull()
        !method.arguments[1].isDeclaredNonNull()
    }
}
