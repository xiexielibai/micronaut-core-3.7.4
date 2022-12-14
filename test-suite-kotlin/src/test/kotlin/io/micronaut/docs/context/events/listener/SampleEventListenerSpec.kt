package io.micronaut.docs.context.events.listener

import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe
import io.micronaut.context.ApplicationContext
import io.micronaut.docs.context.events.SampleEventEmitterBean

// tag::class[]
class SampleEventListenerSpec : AnnotationSpec() {

    @Test
    fun testEventListenerWasNotified() {
        val context = ApplicationContext.run()
        val emitter = context.getBean(SampleEventEmitterBean::class.java)
        val listener = context.getBean(SampleEventListener::class.java)
        listener.invocationCounter.shouldBe(0)
        emitter.publishSampleEvent()
        listener.invocationCounter.shouldBe(1)

        context.close()
    }
}
// end::class[]
