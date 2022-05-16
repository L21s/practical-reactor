import reactor.core.publisher.Flux
import reactor.core.publisher.SynchronousSink
import reactor.test.publisher.TestPublisher
import java.util.*

/**
 * @author Stefan Dragisic
 */
open class BackpressureBase {

    var pub1 = TestPublisher.create<String>()
    fun messageStream1(): Flux<String> = pub1.flux()

    var pub2 = TestPublisher.create<String>()
    fun messageStream2(): Flux<String> = pub2.flux()

    var pub3 = TestPublisher.createNoncompliant<String>(TestPublisher.Violation.REQUEST_OVERFLOW)
    fun messageStream3(): Flux<String> = pub3.flux()

    var pub4 = TestPublisher.createNoncompliant<String>(TestPublisher.Violation.REQUEST_OVERFLOW)
    fun messageStream4(): Flux<String> = pub4.flux()

    fun remoteMessageProducer(): Flux<String?> = Flux.generate { s: SynchronousSink<String?> -> s.next("MESSAGE#" + UUID.randomUUID()) }
}