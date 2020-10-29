package com.sunshuo.grpc.jaeger;

import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.32.1)",
    comments = "Source: collector.proto")
final class CollectorServiceGrpc {

  private CollectorServiceGrpc() {}

  public static final String SERVICE_NAME = "jaeger.api_v2.CollectorService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.sunshuo.grpc.jaeger.Collector.PostSpansRequest,
      com.sunshuo.grpc.jaeger.Collector.PostSpansResponse> getPostSpansMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "PostSpans",
      requestType = com.sunshuo.grpc.jaeger.Collector.PostSpansRequest.class,
      responseType = com.sunshuo.grpc.jaeger.Collector.PostSpansResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.sunshuo.grpc.jaeger.Collector.PostSpansRequest,
      com.sunshuo.grpc.jaeger.Collector.PostSpansResponse> getPostSpansMethod() {
    io.grpc.MethodDescriptor<com.sunshuo.grpc.jaeger.Collector.PostSpansRequest, com.sunshuo.grpc.jaeger.Collector.PostSpansResponse> getPostSpansMethod;
    if ((getPostSpansMethod = CollectorServiceGrpc.getPostSpansMethod) == null) {
      synchronized (CollectorServiceGrpc.class) {
        if ((getPostSpansMethod = CollectorServiceGrpc.getPostSpansMethod) == null) {
          CollectorServiceGrpc.getPostSpansMethod = getPostSpansMethod =
              io.grpc.MethodDescriptor.<com.sunshuo.grpc.jaeger.Collector.PostSpansRequest, com.sunshuo.grpc.jaeger.Collector.PostSpansResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "PostSpans"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.lite.ProtoLiteUtils.marshaller(
                  com.sunshuo.grpc.jaeger.Collector.PostSpansRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.lite.ProtoLiteUtils.marshaller(
                  com.sunshuo.grpc.jaeger.Collector.PostSpansResponse.getDefaultInstance()))
              .build();
        }
      }
    }
    return getPostSpansMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static CollectorServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<CollectorServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<CollectorServiceStub>() {
        @Override
        public CollectorServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new CollectorServiceStub(channel, callOptions);
        }
      };
    return CollectorServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static CollectorServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<CollectorServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<CollectorServiceBlockingStub>() {
        @Override
        public CollectorServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new CollectorServiceBlockingStub(channel, callOptions);
        }
      };
    return CollectorServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static CollectorServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<CollectorServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<CollectorServiceFutureStub>() {
        @Override
        public CollectorServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new CollectorServiceFutureStub(channel, callOptions);
        }
      };
    return CollectorServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class CollectorServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public void postSpans(com.sunshuo.grpc.jaeger.Collector.PostSpansRequest request,
        io.grpc.stub.StreamObserver<com.sunshuo.grpc.jaeger.Collector.PostSpansResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getPostSpansMethod(), responseObserver);
    }

    @Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getPostSpansMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                com.sunshuo.grpc.jaeger.Collector.PostSpansRequest,
                com.sunshuo.grpc.jaeger.Collector.PostSpansResponse>(
                  this, METHODID_POST_SPANS)))
          .build();
    }
  }

  /**
   */
  public static final class CollectorServiceStub extends io.grpc.stub.AbstractAsyncStub<CollectorServiceStub> {
    private CollectorServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @Override
    protected CollectorServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new CollectorServiceStub(channel, callOptions);
    }

    /**
     */
    public void postSpans(com.sunshuo.grpc.jaeger.Collector.PostSpansRequest request,
        io.grpc.stub.StreamObserver<com.sunshuo.grpc.jaeger.Collector.PostSpansResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getPostSpansMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class CollectorServiceBlockingStub extends io.grpc.stub.AbstractBlockingStub<CollectorServiceBlockingStub> {
    private CollectorServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @Override
    protected CollectorServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new CollectorServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public com.sunshuo.grpc.jaeger.Collector.PostSpansResponse postSpans(com.sunshuo.grpc.jaeger.Collector.PostSpansRequest request) {
      return blockingUnaryCall(
          getChannel(), getPostSpansMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class CollectorServiceFutureStub extends io.grpc.stub.AbstractFutureStub<CollectorServiceFutureStub> {
    private CollectorServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @Override
    protected CollectorServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new CollectorServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.sunshuo.grpc.jaeger.Collector.PostSpansResponse> postSpans(
        com.sunshuo.grpc.jaeger.Collector.PostSpansRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getPostSpansMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_POST_SPANS = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final CollectorServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(CollectorServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_POST_SPANS:
          serviceImpl.postSpans((com.sunshuo.grpc.jaeger.Collector.PostSpansRequest) request,
              (io.grpc.stub.StreamObserver<com.sunshuo.grpc.jaeger.Collector.PostSpansResponse>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @Override
    @SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (CollectorServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .addMethod(getPostSpansMethod())
              .build();
        }
      }
    }
    return result;
  }
}
