import reactor.core.publisher.Flux
import reactor.core.publisher.SynchronousSink
import java.time.Duration
import java.util.concurrent.atomic.AtomicInteger

/**
 * @author Stefan Dragisic
 */
open class BroadcastingBase {
    fun systemUpdates(): Flux<String> = Flux.just("RESTARTED", "UNHEALTHY", "HEALTHY", "DISK_SPACE_LOW", "OOM_DETECTED", "CRASHED", "UNKNOWN")
        .delayElements(Duration.ofSeconds(1))
        .doOnNext { n: String -> println("Broadcast update: $n") }

    private val counter = AtomicInteger(0)
    fun messageStream(): Flux<Message> = Flux.generate { sink: SynchronousSink<Int> ->
        val id = counter.getAndIncrement()
        sink.next(id)
    }
        .map { i: Int -> Message("user#$i", "payload#$i") }
        .delayElements(Duration.ofMillis(250))
        .take(5)

    class Message(var user: String = "", var payload: String = "")
}