package io.micronaut.inject.context

import io.micronaut.annotation.processing.test.AbstractTypeElementSpec

class NoPackageSpec extends AbstractTypeElementSpec {

    void "test creating a bean in the default package"() {
        when:
        buildBeanDefinition('Bean', '''

import io.micronaut.context.annotation.*;

@jakarta.inject.Singleton
class Bean {

}
''')
        then:
        def ex = thrown(RuntimeException)
        ex.message.contains("Micronaut beans cannot be in the default package")
    }
}
