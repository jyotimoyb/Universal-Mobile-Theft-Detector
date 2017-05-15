package utils;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class Publisher {


  public static void publish(String brokerUrl,String messageString,String topic,String userName, String vhost,String password) throws MqttException {

    System.out.println("== START PUBLISHER ==");


    MqttClient client=new MqttClient(brokerUrl, MqttClient.generateClientId());
    client.setCallback( new SimpleMqttCallBack() );
    MqttConnectOptions connOpts = new MqttConnectOptions();
    connOpts.setCleanSession(true);
    String name = vhost+":"+userName;
    connOpts.setUserName(name);   
    connOpts.setPassword(password.toCharArray());
    client.connect(connOpts);
    MqttMessage message = new MqttMessage();
    message.setPayload(messageString.getBytes());
    client.publish(topic, message);

    System.out.println("\tMessage '"+ messageString +"' to "+topic);

    client.disconnect();

    System.out.println("== END PUBLISHER ==");

  }


}
