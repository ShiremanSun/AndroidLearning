package com.sunshuo.grpc.jaeger.util;

import com.sunshuo.grpc.jaeger.Model;
import com.google.protobuf.ByteString;
import com.google.protobuf.Duration;
import com.google.protobuf.Timestamp;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import io.jaegertracing.thriftjava.Log;
import io.jaegertracing.thriftjava.Process;
import io.jaegertracing.thriftjava.Span;
import io.jaegertracing.thriftjava.SpanRef;
import io.jaegertracing.thriftjava.Tag;

/**
 * created by sunshuo
 * on 2020/10/28
 * 把SDK中类型转换成proto中定义的类型
 */
public class ThriftGrpcSpanConverter {

    public static Model.Batch convertBatch(List<Span> spans, Process process) {
        Model.Batch.Builder myBatch = Model.Batch.newBuilder();
        Model.Process myProcess = convertProcess(process);
        myBatch.addAllSpans(convertSpans(spans, myProcess));
        myBatch.setProcess(convertProcess(process));
        return myBatch.build();
    }

    private static List<Model.Span> convertSpans(List<Span> spans, Model.Process process) {
        List<Model.Span> mySpans = new ArrayList<>();
        for (Span span : spans) {
            Model.Span.Builder mySpan = Model.Span.newBuilder();
            mySpan.setProcess(process);
            mySpan.setOperationName(span.operationName);
            mySpan.setTraceId(convertLongToBS(span.traceIdLow));
            mySpan.addAllLogs(convertLogs(span.logs));
            mySpan.addAllTags(convertTags(span.tags));
            mySpan.addAllReferences(convertReferences(span));
            mySpan.setSpanId(convertLongToBS(span.spanId));
            long durationSe = span.duration / 1000000;
            int durationNa = (int) (span.duration % 1000000) * 1000;
            mySpan.setDuration(Duration.newBuilder().setSeconds(durationSe).setNanos(durationNa).build());
            long startTimeSe = span.startTime / 1000000;
            int startTimeNa = (int) (span.startTime % 1000000) * 1000;
            mySpan.setStartTime(Timestamp.newBuilder().setSeconds(startTimeSe).setNanos(startTimeNa).build());
            mySpan.setFlags(span.flags);
            mySpans.add(mySpan.build());
        }
        return mySpans;
    }

    private static ByteString convertLongToBS(long value) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(8);
        byteBuffer.putLong(value);
        return ByteString.copyFrom(byteBuffer.array());
    }

    private static List<Model.SpanRef> convertReferences(Span span) {
        List<Model.SpanRef> myReferences = new ArrayList<>();
        if (span.parentSpanId != 0 && span.traceIdLow != 0) {
            Model.SpanRef.Builder spanRef = Model.SpanRef.newBuilder();
            spanRef.setRefType(Model.SpanRefType.CHILD_OF)
                    .setTraceId(convertLongToBS(span.traceIdLow)).setSpanId(convertLongToBS(span.parentSpanId));
            myReferences.add(spanRef.build());
        }
        if (span.references == null || span.references.size() == 0) return myReferences;
        for (SpanRef ref : span.references) {
            Model.SpanRef.Builder myRef = Model.SpanRef.newBuilder();
            myRef.setSpanId(convertLongToBS(ref.spanId));
            myRef.setTraceId(convertLongToBS(ref.traceIdLow));
            switch (ref.refType) {
                case CHILD_OF:
                    myRef.setRefType(Model.SpanRefType.CHILD_OF);
                    break;
                case FOLLOWS_FROM:
                    myRef.setRefType(Model.SpanRefType.FOLLOWS_FROM);
                    break;
            }
            myReferences.add(myRef.build());
        }
        return myReferences;
    }

    /**
     * @param process
     * @return
     */
    private static Model.Process convertProcess(Process process) {
        Model.Process.Builder myProcess = Model.Process.newBuilder();
        myProcess.setServiceName(process.serviceName);
        myProcess.addAllTags(convertTags(process.tags));
        return myProcess.build();
    }

    /**
     * @param tags
     * @return
     */
    private static List<Model.KeyValue> convertTags(List<Tag> tags) {
        List<Model.KeyValue> myTags = new ArrayList<>();
        if (tags == null) return myTags;
        for (Tag tag : tags) {
            Model.KeyValue.Builder myTag = Model.KeyValue.newBuilder();
            myTag.setKey(tag.key);
            switch (tag.vType) {
                case BOOL:
                    myTag.setVType(Model.ValueType.BOOL);
                    myTag.setVBool(tag.vBool);
                    break;
                case LONG:
                    myTag.setVType(Model.ValueType.INT64);
                    myTag.setVInt64(tag.vLong);
                    break;
                case STRING:
                    myTag.setVType(Model.ValueType.STRING);
                    myTag.setVStr(tag.vStr);
                    break;
                case DOUBLE:
                    myTag.setVType(Model.ValueType.FLOAT64);
                    myTag.setVFloat64(tag.vDouble);
                    break;
                case BINARY:
                    myTag.setVType(Model.ValueType.BINARY);
                    myTag.setVBinary(ByteString.copyFrom(tag.vBinary));
                    break;
            }
            myTags.add(myTag.build());
        }
        return myTags;
    }

    private static List<Model.Log> convertLogs(List<Log> logs) {
        List<Model.Log> myLogs = new ArrayList<>();
        if (logs == null) return myLogs;
        for (Log log : logs) {
            Model.Log.Builder myLog = Model.Log.newBuilder();
            long timestampSe = log.timestamp / 1000000;
            int timestampNa = (int) (log.timestamp % 1000000) * 1000;
            myLog.setTimestamp(Timestamp.newBuilder().setSeconds(timestampSe).setNanos(timestampNa).build());
            myLog.addAllFields(convertTags(log.fields));
            myLogs.add(myLog.build());
        }
        return myLogs;
    }
}

