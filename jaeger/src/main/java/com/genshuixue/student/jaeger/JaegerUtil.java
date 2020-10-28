package com.genshuixue.student.jaeger;

import io.jaegertracing.internal.JaegerTracer;
import io.jaegertracing.internal.metrics.Metrics;
import io.jaegertracing.internal.metrics.NoopMetricsFactory;
import io.jaegertracing.internal.propagation.B3TextMapCodec;
import io.jaegertracing.internal.reporters.RemoteReporter;
import io.jaegertracing.internal.samplers.ConstSampler;
import io.jaegertracing.spi.Reporter;
import io.jaegertracing.spi.Sampler;
import io.jaegertracing.spi.Sender;
import io.jaegertracing.thrift.internal.senders.HttpSender;
import io.opentracing.Tracer;
import io.opentracing.propagation.Format;
import io.opentracing.util.GlobalTracer;

/**
 * created by sunshuo
 * on 2020/7/17
 */
public class JaegerUtil {
    public static void getTracer() {
        //sender.withAgentHost("http://localhost");
        //sender.withAgentPort(16686);
        new Thread(new Runnable() {
            @Override
            public void run() {
                String endPoint = "http://172.20.116.30:14268/api/traces";
                Sender sender = new GrpcSender("172.20.116.62", 14250);
                //Sender sender = new HttpSender.Builder(endPoint).build();
                Sampler sampler = new ConstSampler(true);
                Reporter reporter = new RemoteReporter.Builder()
                        .withFlushInterval(RemoteReporter.DEFAULT_FLUSH_INTERVAL_MS)
                        .withMaxQueueSize(RemoteReporter.DEFAULT_MAX_QUEUE_SIZE)
                        .withMetrics(new Metrics(new NoopMetricsFactory()))
                        .withSender(sender)
                        .build();
                B3TextMapCodec b3Codec = new B3TextMapCodec.Builder().build();
                JaegerTracer tracer = new JaegerTracer.Builder("haoke-student")
                        .withSampler(sampler)
                        .withReporter(reporter)
                        .registerInjector(Format.Builtin.HTTP_HEADERS, b3Codec)
                        .registerExtractor(Format.Builtin.HTTP_HEADERS, b3Codec)
                        .build();
                GlobalTracer.registerIfAbsent(tracer);
            }
        }).start();

//        String endPoint = "http://172.20.116.30:14268/api/traces";
//        Sender sender = new GrpcSender("127.0.0.1", 14250);
//        //Sender sender = new HttpSender.Builder(endPoint).build();
//        Sampler sampler = new ConstSampler(true);
//        Reporter reporter = new RemoteReporter.Builder()
//                .withFlushInterval(RemoteReporter.DEFAULT_FLUSH_INTERVAL_MS)
//                .withMaxQueueSize(RemoteReporter.DEFAULT_MAX_QUEUE_SIZE)
//                .withMetrics(new Metrics(new NoopMetricsFactory()))
//                .withSender(sender)
//                .build();
//        B3TextMapCodec b3Codec = new B3TextMapCodec.Builder().build();
//        JaegerTracer tracer = new JaegerTracer.Builder("haoke-student")
//                .withSampler(sampler)
//                .withReporter(reporter)
//                .registerInjector(Format.Builtin.HTTP_HEADERS, b3Codec)
//                .registerExtractor(Format.Builtin.HTTP_HEADERS, b3Codec)
//                .build();
//        GlobalTracer.registerIfAbsent(tracer);

    }
}
