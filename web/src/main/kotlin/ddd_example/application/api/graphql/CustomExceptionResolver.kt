package ddd_example.application.api.graphql

import graphql.GraphQLError
import graphql.GraphqlErrorBuilder
import graphql.schema.DataFetchingEnvironment
import org.springframework.graphql.execution.DataFetcherExceptionResolverAdapter
import org.springframework.stereotype.Component

class GraphqlCustomError(message: String) : RuntimeException(message)

@Component
class CustomExceptionResolver : DataFetcherExceptionResolverAdapter() {
    override fun resolveToSingleError(
        ex: Throwable,
        env: DataFetchingEnvironment,
    ): GraphQLError? {
        return if (ex is GraphqlCustomError) {
            GraphqlErrorBuilder.newError()
                .message(ex.message)
                .path(env.executionStepInfo.path)
                .location(env.field.sourceLocation)
                .build()
        } else {
            logger.error("Internal server error", ex)
            GraphqlErrorBuilder.newError()
                .message("Internal server error")
                .path(env.executionStepInfo.path)
                .location(env.field.sourceLocation)
                .build()
        }
    }
}
