package ddd_example.application.api.graphql

import ddd_example.domain.example.read.entity.ReadUser
import ddd_example.domain.example.read.repository.ReadUserRepository
import io.micrometer.core.instrument.kotlin.asContextElement
import io.micrometer.observation.ObservationRegistry
import kotlinx.coroutines.reactor.awaitSingle
import kotlinx.coroutines.reactor.mono
import org.springframework.graphql.server.WebGraphQlInterceptor
import org.springframework.graphql.server.WebGraphQlRequest
import org.springframework.graphql.server.WebGraphQlResponse
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

data class Auth(
    val user: ReadUser?,
)

const val AUTH_KEY = "auth"

@Component
class RequestHeaderInterceptor(
    private val readUserRepository: ReadUserRepository,
    private val observationRegistry: ObservationRegistry,
) : WebGraphQlInterceptor {
    override fun intercept(
        request: WebGraphQlRequest,
        chain: WebGraphQlInterceptor.Chain,
    ): Mono<WebGraphQlResponse> {
        return mono(observationRegistry.asContextElement()) {
            val user =
                request.headers.getFirst("Authorization")
                    ?.replace("Bearer ", "")
                    ?.let { readUserRepository.findByToken(it) }
            request.configureExecutionInput { _, builder ->
                builder.graphQLContext(mapOf(AUTH_KEY to Auth(user))).build()
            }
            chain.next(request).awaitSingle()
        }
    }
}
