package com.example.grpc_api;

import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;
import java.util.logging.Logger;

public class ServerGrpc {
    private static final Logger logger = Logger.getLogger(ServerGrpc.class.getName());
    private static final int PORT = 5000;

    private Server mServer;

    private void start() throws IOException {
        /* The port on which the mServer should run UploadFileServer*/

        mServer = ServerBuilder.forPort(PORT)
                .addService(new BlobKeeperImpl())
                .build()
                .start();
        logger.info("Server started, listening on " + PORT);
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                // Use stderr here since the logger may have been reset by its JVM shutdown hook.
                logger.info("*** shutting down gRPC mServer since JVM is shutting down");
                ServerGrpc.this.stop();
                logger.info("*** mServer shut down");
            }
        });
    }

    private void stop() {
        if (mServer != null) {
            mServer.shutdown();
        }
    }

    /**
     * Await termination on the main thread since the grpc library uses daemon threads.
     */
    private void blockUntilShutdown() throws InterruptedException {
        if (mServer != null) {
            mServer.awaitTermination();
        }
    }

    /**
     * Main launches the mServer from the command line.
     */
    public static void run() throws IOException, InterruptedException {
        final ServerGrpc server = new ServerGrpc();
        server.start();
        server.blockUntilShutdown();
    }
}
