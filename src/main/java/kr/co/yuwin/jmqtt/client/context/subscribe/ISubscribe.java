package kr.co.yuwin.jmqtt.client.context.subscribe;

import org.eclipse.paho.client.mqttv3.MqttException;

public interface ISubscribe {

	public void connect() throws MqttException;
	public void subscribe(String topicName) throws MqttException;
	public void disconnect() throws MqttException;
	
}
