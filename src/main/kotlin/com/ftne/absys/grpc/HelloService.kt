package com.ftne.absys.grpc

class HelloService : HelloServiceGrpcKt.HelloServiceCoroutineImplBase() {
    override suspend fun hello(request: HelloRequest): HelloResponse =
        HelloResponse.newBuilder()
            .setMessage("Hello, ${request.name}")
            .build()
}
