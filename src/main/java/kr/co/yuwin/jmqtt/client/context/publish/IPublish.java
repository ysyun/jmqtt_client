package kr.co.yuwin.jmqtt.client.context.publish;

import org.eclipse.paho.client.mqttv3.MqttException;

public interface IPublish {
	public void publish(String topicName, String msg) throws MqttException;
}
