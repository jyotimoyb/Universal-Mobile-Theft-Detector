package hello;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import javax.inject.Inject;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.util.JSON;

import utils.Publisher;

@Service
@Transactional
public class ReceiverService {
	
	@Inject
	MongoTemplate mongoTemplate;
	
	public String getOneUser(String userId) {       

		DBCollection collection = (mongoTemplate.getCollection("MobileUsers"));

		BasicDBObject searchQuery = new BasicDBObject();
		searchQuery.put("userId", userId);
		DBCursor cursor = collection.find(searchQuery);	        
		String a = "";
		while (cursor.hasNext()) {
			a=a+(cursor.next());        
		}        
		return a;		
	}
	public String updateOneUser(String userId,JsonObject userDetails) {       

		DBCollection collection = (mongoTemplate.getCollection("MobileUsers"));

		BasicDBObject searchQuery = new BasicDBObject();
		searchQuery.put("userId", userId);
		BasicDBObject document = new BasicDBObject();
		userDetails.remove("userId");
		userDetails.addProperty("userId", userId);
		document = (BasicDBObject)JSON.parse(userDetails.toString());
		collection.update(searchQuery, document);
		return userDetails.toString();				
	}
	

public void pubSample(String userId,String cmd) {

    String topic        = "MQTT Examples";
    String content      = "Message from MqttPublishSample";
    int qos             = 1;
    String broker       = "tcp://localhost:1883";
    String clientId     = "JavaSample";
    String clientId2     = "JavaSample2";
    JsonObject payload1 = new JsonObject();
    payload1.addProperty("userId",userId);
    payload1.addProperty("cmd",cmd);
    

    try {
    	Publisher.publish(broker, payload1.toString(), topic, "mqtt", "test", "mqtt");    	
        
        
    } catch(MqttException me) {
        System.out.println("reason "+me.getReasonCode());
        System.out.println("msg "+me.getMessage());
        System.out.println("loc "+me.getLocalizedMessage());
        System.out.println("cause "+me.getCause());
        System.out.println("excep "+me);
        me.printStackTrace();
    }
 }
/*
 * mqttClient.setCallback(new MqttCallback() { //1
@Override
public void connectionLost(Throwable throwable) {
//Called when connection is lost.
}
@Override
public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {
System.out.println("Topic: " + topic);
System.out.println(new String(mqttMessage. getPayload()));
System.out.println("QoS: " + mqttMessage. getQos());
System.out.println("Retained: " + mqttMessage. isRetained());
}
  @Override
public void deliveryComplete(final IMqttDeliveryToken iMqttDeliveryToken) {
//When message delivery was complete
}
});
mqttClient.connect();
mqttClient.subscribe("my/topic", 2); //2
 */
}

