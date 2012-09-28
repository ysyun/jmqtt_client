package kr.co.yuwin.jmqtt.client.context.publish;

import kr.co.yuwin.jmqtt.client.context.AbsContext;

import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttDefaultFilePersistence;
import org.eclipse.paho.client.mqttv3.MqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttTopic;

public class PublishContext extends AbsContext implements MqttCallback, IPublish {

	private MqttClient client = null;

	public PublishContext(String clientId, MqttCallback callback) {
		// initialize config properties
		super();

		try {
			// get message store directory
			String tmpDir = System.getProperty("java.io.tmpdir");
			MqttDefaultFilePersistence dataStore = new MqttDefaultFilePersistence(
					tmpDir);

			// Construct the object that contains connection parameters
			// such as cleansession and LWAT
			MqttConnectOptions conOpt = new MqttConnectOptions();
			conOpt.setCleanSession(false);
			if (isAuthEnable()) {
				conOpt.setUserName(getUserName());
				conOpt.setPassword(getPassword());
			}
			conOpt.setKeepAliveInterval(getKeepAliveInterval());
			conOpt.setConnectionTimeout(getConnectTimeout());

			// Construct the MqttClient instance
			client = new MqttClient(getBrokerUrl(), clientId, dataStore);

			// Set this wrapper as the callback handler
			if (callback != null)
				client.setCallback(callback);
			else
				client.setCallback(this);

		} catch (MqttException e) {
			e.printStackTrace();
			System.out.println("can't create PublishContext, Exception="
					+ e.getMessage());
			System.exit(1);
		}
	}

	public void publish(String topicName, String msg) throws MqttException {
		try {
			// Connect to the server
			client.connect();
			System.out.println("Connected to " + getBrokerUrl());

			// Get an instance of the topic
			MqttTopic topic = client.getTopic(topicName);

			byte[] payload = msg.getBytes();
			MqttMessage message = new MqttMessage(payload);
			message.setQos(getQoS());

			// Publish the message
			System.out.println("Publishing at: " + System.currentTimeMillis()
					+ " to topic \"" + topicName + "\" qos " + getQoS());
			MqttDeliveryToken token = topic.publish(message);

			// Wait until the message has been delivered to the server
			token.waitForCompletion();
		} catch (Exception e) {
			throw new MqttException(e);
		} finally {
			// Disconnect the client
			client.disconnect();
			System.out.println("Disconnected");
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
		System.out.println("Connection to " + getBrokerUrl() + " lost!");
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
