package com.ftne.absys.grpc

import io.grpc.ServerBuilder

fun main() {
    val helloService = HelloService()
    val server = ServerBuilder
        .forPort(15001)
        .addService(helloService)
        .build()

    Runtime.getRuntime().addShutdownHook(Thread {
        server.shutdown()
        server.awaitTermination()
    })

    server.start()
    println("Server was started")
    server.awaitTermination()
}