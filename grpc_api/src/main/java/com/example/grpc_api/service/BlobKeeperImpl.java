package com.example.grpc_api.service;

import com.example.BlobKeeperGrpc;
import com.example.PutRequest;
import com.example.PutResponse;
import com.example.grpc_api.model.Log;
import com.example.grpc_api.repository.LogRepository;
import io.grpc.stub.StreamObserver;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BlobKeeperImpl extends BlobKeeperGrpc.BlobKeeperImplBase{

    private final Logger logger = LoggerFactory.getLogger(BlobKeeperImpl.class.getName());

    private int mStatus = 200;
    private String mMessage = "";
    private BufferedOutputStream mBufferedOutputStream = null;

    @Autowired
    LogRepository logRepository;

    @Override
    public StreamObserver<PutRequest> getBlob(final StreamObserver<PutResponse> responseObserver) {
        logRepository.save(new Log("1234567890qwertyuiopasdfghjkl-11111111111111111111"));
        return new StreamObserver<PutRequest>() {
            int mmCount = 0;
            long start = 0l;
            long end = 0l;


            String name = "";

            @Override
            public void onNext(PutRequest request) {
                if(mmCount == 0){
                    start = (new Date()).getTime();

                }

                mmCount++;

                byte[] data = request.getData().toByteArray();
                long offset = request.getOffset();

                try {
                    if (mBufferedOutputStream == null || ! name.equals(request.getName())) {
                        name = request.getName();
                        mBufferedOutputStream = new BufferedOutputStream(new FileOutputStream((new Date()).getTime() + "--" + name));
                    }
                    mBufferedOutputStream.write(data);
                    mBufferedOutputStream.flush();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onCompleted() {
                responseObserver.onNext(PutResponse.newBuilder().setStatus(mStatus).setMessage(mMessage).build());
                responseObserver.onCompleted();

                end = (new Date()).getTime();

                logger.info("Time execute: " + (end-start));

                if (mBufferedOutputStream != null) {
                    try {
                        mBufferedOutputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        mBufferedOutputStream = null;
                    }
                }
            }
        };
    }
}
