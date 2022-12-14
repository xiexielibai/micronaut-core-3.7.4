package io.micronaut.docs.http.server.exception

import io.micronaut.context.ApplicationContext
import io.micronaut.context.BeanContext
import io.micronaut.context.annotation.Requires
import io.micronaut.context.env.Environment
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Error
import io.micronaut.http.annotation.Get
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.exceptions.HttpClientResponseException
import io.micronaut.http.hateoas.JsonError
import io.micronaut.runtime.server.EmbeddedServer
import jakarta.inject.Singleton
import spock.lang.AutoCleanup
import spock.lang.Shared
import spock.lang.Specification

class SimpleControllerSpec extends Specification {

    @Shared @AutoCleanup EmbeddedServer embeddedServer = ApplicationContext.run(EmbeddedServer, [
            'spec.name': SimpleControllerSpec.simpleName
    ], Environment.TEST)

    @AutoCleanup @Shared HttpClient client = embeddedServer.applicationContext.createBean(HttpClient, embeddedServer.getURL())


    def "should not go into infinite loop when exception occurs in bean initialization"() {
        when:
        client.toBlocking().exchange(HttpRequest.GET("/v1/simple"))

        then:
        def e = thrown(HttpClientResponseException)
        e.status == HttpStatus.INTERNAL_SERVER_ERROR
    }

    def "exception should be handled by local Error handler inside the controller"() {
        when:
        client.toBlocking().exchange(HttpRequest.GET("/v2/simple"))

        then:
        def e = thrown(HttpClientResponseException)
        e.status == HttpStatus.BAD_REQUEST
        e.response.reason() == "Fix it!"
    }

    @Requires(property = 'spec.name', value = 'SimpleControllerSpec')
    @Controller("/v1/simple")
    static class ConstructorErrorController {

        final ThrowsAnErrorService throwsAnErrorService

        ConstructorErrorController(ThrowsAnErrorService throwsAnErrorService) {
            this.throwsAnErrorService = throwsAnErrorService
        }

        @Get("/")
        HttpResponse index() {
            return HttpResponse.ok()
        }

        @Error
        HttpResponse exceptionHandler(HttpRequest request, Throwable throwable) {
            JsonError error = new JsonError("Invalid: " + throwable.getMessage())
            return HttpResponse.<JsonError>status(HttpStatus.BAD_REQUEST, "Fix it!")
                    .body(error)
        }
    }

    @Requires(property = 'spec.name', value = 'SimpleControllerSpec')
    @Controller("/v2/simple")
    static class MethodErrorController {

        final BeanContext beanContext

        MethodErrorController(BeanContext beanContext) {
            this.beanContext = beanContext
        }

        @Get("/")
        HttpResponse index() {
            beanContext.getBean(ThrowsAnErrorService)
            return HttpResponse.ok()
        }

        @Error
        HttpResponse throwableExceptionHandler(HttpRequest request, Throwable throwable) {
            JsonError error = new JsonError("Invalid: " + throwable.getMessage())
            return HttpResponse.<JsonError>status(HttpStatus.BAD_REQUEST, "throwableExceptionHandler")
                    .body(error)
        }

        @Error
        HttpResponse exceptionHandler(HttpRequest request, RuntimeException throwable) {
            JsonError error = new JsonError("Invalid: " + throwable.getMessage())
            return HttpResponse.<JsonError>status(HttpStatus.BAD_REQUEST, "Fix it!")
                    .body(error)
        }

        @Error
        HttpResponse exceptionExceptionHandler(HttpRequest request, Exception throwable) {
            JsonError error = new JsonError("Invalid: " + throwable.getMessage())
            return HttpResponse.<JsonError>status(HttpStatus.BAD_REQUEST, "exceptionExceptionHandler")
                    .body(error)
        }
    }

    @Requires(property = 'spec.name', value = 'SimpleControllerSpec')
    @Singleton
    static class ThrowsAnErrorService {
        ThrowsAnErrorService() {
            throw new RuntimeException("Custom Error")
        }
    }

}
