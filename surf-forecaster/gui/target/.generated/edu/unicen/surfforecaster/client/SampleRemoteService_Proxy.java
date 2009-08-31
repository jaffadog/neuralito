package edu.unicen.surfforecaster.client;

import com.google.gwt.user.client.rpc.impl.RemoteServiceProxy;
import com.google.gwt.user.client.rpc.SerializationException;
import com.google.gwt.user.client.rpc.impl.RequestCallbackAdapter.ResponseReader;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.impl.ClientSerializationStreamWriter;

public class SampleRemoteService_Proxy extends RemoteServiceProxy implements edu.unicen.surfforecaster.client.SampleRemoteServiceAsync {
  private static final String REMOTE_SERVICE_INTERFACE_NAME = "edu.unicen.surfforecaster.client.SampleRemoteService";
  private static final String SERIALIZATION_POLICY ="5BA8A5B3E35F40698BB0BF65F390BCF2";
  private static final edu.unicen.surfforecaster.client.SampleRemoteService_TypeSerializer SERIALIZER = new edu.unicen.surfforecaster.client.SampleRemoteService_TypeSerializer();
  
  public SampleRemoteService_Proxy() {
    super(GWT.getModuleBaseURL(),
      null, 
      SERIALIZATION_POLICY, 
      SERIALIZER);
  }
  
  public void doComplimentMe(com.google.gwt.user.client.rpc.AsyncCallback callback) {
    int requestId = getNextRequestId();
    boolean toss = isStatsAvailable() && stats(timeStat("SampleRemoteService_Proxy.doComplimentMe", getRequestId(), "begin"));
    ClientSerializationStreamWriter streamWriter = createStreamWriter();
    // createStreamWriter() prepared the stream
    streamWriter.writeString(REMOTE_SERVICE_INTERFACE_NAME);
    streamWriter.writeString("doComplimentMe");
    streamWriter.writeInt(0);
    
    String payload = streamWriter.toString();
    toss = isStatsAvailable() && stats(timeStat("SampleRemoteService_Proxy.doComplimentMe", getRequestId(), "requestSerialized"));
    doInvoke(ResponseReader.STRING, "SampleRemoteService_Proxy.doComplimentMe", getRequestId(), payload, callback);
  }
}
