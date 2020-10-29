package com.sunshuo.grpc.jaeger;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

/**
 * created by sunshuo
 * on 2020/10/23
 */
public class CollectorClient {

    private CollectorClient(ManagedChannelBuilder channelBuilder) {
        mChannel = channelBuilder.usePlaintext().build();
        connect();
    }

    private static CollectorClient INSTANCE;
    private ManagedChannel mChannel;

    public static CollectorClient getInstance(ManagedChannelBuilder channelBuilder) {
        if (INSTANCE == null) {
            synchronized (CollectorClient.class) {
                if (INSTANCE == null) {
                    INSTANCE = new CollectorClient(channelBuilder);
                }
            }
        }
        return INSTANCE;
    }
    private CollectorServiceGrpc.CollectorServiceBlockingStub mBlockingStub;


    public synchronized Collector.PostSpansResponse report(Collector.PostSpansRequest request) {
        try {
            Collector.PostSpansResponse response = mBlockingStub.postSpans(request);
            return response;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    private void connect() {
        mBlockingStub = CollectorServiceGrpc.newBlockingStub(mChannel);
    }
}
