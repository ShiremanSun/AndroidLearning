package com.genshuixue.student.jaeger.util;

import android.view.Display;

import com.genshuixue.student.jaeger.Model;

import java.util.ArrayList;
import java.util.List;

import io.jaegertracing.thriftjava.Batch;
import io.jaegertracing.thriftjava.Process;
import io.jaegertracing.thriftjava.Span;

/**
 * created by sunshuo
 * on 2020/10/28
 */
public class ConvertUtil {

    public static Model.Batch convertBatch(List<Span> spans, Process process) {
        Model.Batch.Builder myBatch = Model.Batch.newBuilder();
        myBatch.addAllSpans(convertSpans(spans));
        myBatch.setProcess(convertProcess(process));
        return myBatch.build();
    }

    private static List<Model.Span> convertSpans(List<Span> spans) {
        List<Model.Span> mySpans = new ArrayList<>();
        for (Span span : spans) {
            Model.Span.Builder mySpan = Model.Span.newBuilder();
//            mySpan.addAllLogs(span.logs);
//            mySpan.addAllReferences(span.references);
//            mySpan.addAllTags(span.tags);
//            mySpan.setDuration(span.duration);
//            mySpan.setFlags(span.flags);
//            mySpan.setOperationName(span.operationName);
//            mySpan.setSpanId(span.spanId);
//            mySpan.setStartTime(span.startTime);
//            mySpan.setTraceId(span.traceIdHigh);
            mySpan.setOperationName(span.operationName);
            mySpans.add(mySpan.build());
        }

        return mySpans;
    }

    private static Model.Process convertProcess(Process process) {
        Model.Process myProcess = Model.Process.newBuilder().build();
        return myProcess;
    }
}
