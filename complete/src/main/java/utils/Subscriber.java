package utils;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;

public class Subscriber {

  public static void subscribe(String brokerUrl,String userName, String vhost,String password,String topic) throws MqttException {

    System.out.println("== START SUBSCRIBER ==");

    MqttClient client=new MqttClient(brokerUrl, MqttClient.generateClientId());
    client.setCallback( new SimpleMqttCallBack() );
    MqttConnectOptions connOpts = new MqttConnectOptions();
    connOpts.setCleanSession(true);
    String name = vhost+":"+userName;
    connOpts.setUserName(name);   
    connOpts.setPassword(password.toCharArray());
    client.connect(connOpts);

    client.subscribe(topic);

  }

}
