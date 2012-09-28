JMQTT Client Library Usage
===

# Files
    1. jmqtt_client-1.0.jar
    2. jmqtt.client.properties
    * This library imports the paho client package for mqtt.
     
# API
    <IPublish.java>
    public void publish(String topicName, String message) throws MqttException;
    
    <ISubscribe.java>
    public void connect() throws MqttException;
    public void subscribe(String topicName) throws MqttException;
    public void disconnect() throws MqttException;
    
# Config
    <jmqtt.client.properties>
    # JMQTT broker server ip:port
    # default value ip 127.0.0.1, port 1883
    #jmqtt.broker.ip=222.231.25.95
    jmqtt.broker.ip=127.0.0.1
    jmqtt.broker.port=1883

    # id/password realm 파일을 별도 작성해야 함
    # default is false 
    jmqtt.client.auth.enable=false
    jmqtt.broker.connect.id=jmqtt-client
    jmqtt.broker.connect.pwd=jmqtt-client-12345

    # network
    # send ping message to broker (second)
    jmqtt.client.keepalive.interval=6000
    # connect time out (second)
    jmqtt.client.connect.timeout=6000

    # publish Quality of Service (QoS)
    # level 0 : at most once - 한번이 갈 수도 않갈 수도 있음 (상호체크 없음)
    # level 1 : at least once - 한번이나 두번 중복해서 갈 수도 있음 (상호체크 1회) 
    # level 2 : confirm once - 반드시 한번만 감 (상호체크 2회)
    jmqtt.qos.level=2
    
# Testing
    <SUBSCRIBE>
    * Usage
    1. open new cmd console window (Sub console)
    2. java -classpath jmqtt_client-1.0.jar -Djmqtt.config=c:/jmqtt.client.properties MqttServiceMain sub <topicName>
    
    * Example in Sub console
    java -classpath jmqtt_client-1.0.jar -Djmqtt.config=c:/jmqtt.client.properties MqttServiceMain sub a/b/c
    Connected to tcp://xxx.xxx.xxx.xxx:1883
    Subscribing to topic "a/b/c" qos 2
    Press <Enter> to exit
    
    <PUBLISH>
    * Usage
    1. open new cmd console window (Pub console)
    2. java -classpath jmqtt_client-1.0.jar -Djmqtt.config=c:/jmqtt.client.properties MqttServiceMain pub <topicName> <Message>
    
    *Example in Pub console
    java -classpath jmqtt_client-1.0.jar -Djmqtt.config=c:/jmqtt.client.properties MqttServiceMain pub a/b/c hidowon
    Connected to tcp://xxx.xxx.xxx.xxx:1883
    Publishing at: 13488232283200 to topic "a/b/c" qos 2
    Disconnected
    
    ....
    and then you can see "hidowon" like this message in Sub console

# MqttServiceMain Example
    1. MqttCallback interface 상속받아 구현(implementation)
        연결 유실 콜백 : public void connectionLost(Throwable cause);
        Publish 메세지 송신 결과   : public void deliveryComplete(MqttDeliveryToken token);
        Subscribe 메세지 수신 결과 : public void messageArrived(MqttTopic topic, MqttMessage message)throws MqttException;
    
    2. Subscribe 호출
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

    3. Publish 호출 
        try{
			IPublish pub = new PublishContext("service.pub", this);
			pub.publish(topicName, msg);
		} catch(Exception e) {
			e.printStackTrace();
		}
    
# Adaptation in your application
    1. MqttCallback interface 상속받아 구현(implementation)
    2. use ISubscribe interface
    3. use IPublish interface
    * check the sources for details
    
# Thanks for reading this manual. 2012.09.28 by yun dowon
    