package com.sunshuo.grpc.jaeger;

import com.sunshuo.grpc.jaeger.util.ThriftGrpcSpanConverter;

import java.util.List;

import io.grpc.ManagedChannelBuilder;
import io.jaegertracing.internal.exceptions.SenderException;
import io.jaegertracing.thrift.internal.senders.ThriftSender;
import io.jaegertracing.thriftjava.Process;
import io.jaegertracing.thriftjava.Span;

/**
 * created by sunshuo
 * on 2020/10/23
 */
public class GrpcSender extends ThriftSender {

    private static final int ONE_MB_IN_BYTES = 1048576;

    private String mIp;
    private int mPort;
    private ManagedChannelBuilder mChannelBuilder;

    /**
     * @param ip
     * @param port
     *
     */
    public GrpcSender(String ip, int port) {
        super(ProtocolType.Binary, ONE_MB_IN_BYTES);
        mChannelBuilder = ManagedChannelBuilder.forAddress(ip, port);
    }

    @Override
    public void send(Process process, List<Span> spans) throws SenderException {
        Collector.PostSpansRequest request = Collector.PostSpansRequest.newBuilder().setBatch(ThriftGrpcSpanConverter.convertBatch(spans, process)).build();
        CollectorClient.getInstance(mChannelBuilder).report(request);
    }
}
