package io.micronaut.docs.context.env

import io.kotest.core.spec.style.StringSpec
import io.micronaut.context.ApplicationContext
import io.micronaut.context.env.Environment
import org.junit.Assert.assertFalse

class DefaultEnvironmentSpec : StringSpec({

    // tag::disableEnvDeduction[]
    "test disable environment deduction via builder"() {
        val ctx = ApplicationContext.builder().deduceEnvironment(false).start()
        assertFalse(ctx.environment.activeNames.contains(Environment.TEST))
        ctx.close()
    }
    // end::disableEnvDeduction[]
})
