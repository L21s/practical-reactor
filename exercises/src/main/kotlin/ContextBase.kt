import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.time.Duration

/**
 * @author Stefan Dragisic
 */
open class ContextBase {

    val HTTP_CORRELATION_ID = "http_correlation_id"
    fun openConnection(): Mono<Void> {
        return Mono.fromRunnable<Any> { println("Opening connection!") }
            .delaySubscription(Duration.ofMillis(500))
            .then()
    }

    class Message(var correlationId: String = "", var payload: String = "")

    fun getPage(pageNumber: Int): Mono<Page> {
        return Mono.just(Page(pageNumber))
    }

    class Page(var page: Int = 0) {

        init {
            if (page == 3) {
                println("Page 3 is not available!")
                throw IllegalStateException("Page 3 is not available!")
            }
            this.page = page
        }

        val result: Flux<Int>
            get() = Flux.defer {
                if (page >= 10) {
                    return@defer Flux.empty<Int>()
                } else {
                    return@defer Flux.range(page * 10, 10)
                }
            }
    }
}
