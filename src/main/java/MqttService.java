import kr.co.yuwin.jmqtt.client.context.publish.IPublish;
import kr.co.yuwin.jmqtt.client.context.publish.PublishContext;
import kr.co.yuwin.jmqtt.client.context.subscribe.ISubscribe;
import kr.co.yuwin.jmqtt.client.context.subscribe.SubscribeContext;

import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttTopic;


public class MqttService implements MqttCallback {
	
	public void publish(String topicName, String msg) {
		try{
			IPublish pub = new PublishContext("service.pub", this);
			pub.publish(topicName, msg);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void subscribe(String topicName) {
		ISubscribe sub = null;
		try{
			sub = new SubscribeContext("service.sub", this);
			sub.connect();
			sub.subscribe(topicName);
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			if(sub != null)
				try{ sub.disconnect(); } catch(Exception e){}
		}
	}
	
	/****************************************************************/
	/* Methods to implement the MqttCallback interface */
	/****************************************************************/
	/**
	 * @see MqttCallback#connectionLost(Throwable)
	 */
	public void connectionLost(Throwable cause) {
		// Called when the connection to the server has been lost.
		// An application may choose to implement reconnection
		// logic at this point.
		// This sample simply exits.
		System.out.println("Connection to lost! : " + cause.getMessage());
		System.exit(1);
	}

	/**
	 * @see MqttCallback#deliveryComplete(MqttDeliveryToken)
	 */
	public void deliveryComplete(MqttDeliveryToken token) {
		// Called when a message has completed delivery to the
		// server. The token passed in here is the same one
		// that was returned in the original call to publish.
		// This allows applications to perform asychronous
		// delivery without blocking until delivery completes.

		// This sample demonstrates synchronous delivery, by
		// using the token.waitForCompletion() call in the main thread.
	}

	/**
	 * @see MqttCallback#messageArrived(MqttTopic, MqttMessage)
	 */
	public void messageArrived(MqttTopic topic, MqttMessage message)
			throws MqttException {
		// Called when a message arrives from the server.

		System.out.println("Time:\t" + System.currentTimeMillis()
				+ "  Topic:\t" + topic.getName() + "  Message:\t"
				+ new String(message.getPayload()) + "  QoS:\t"
				+ message.getQos());
	}

	/****************************************************************/
	/* End of MqttCallback methods */
	/****************************************************************/

}
