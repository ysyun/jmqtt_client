JMQTT Client Library Usage
===

# Files
    1. jmqtt_client-1.0.jar
    2. jmqtt.client.properties
    * This library imports the paho client package for mqtt.
     
# API
    <IPublish.java> interface class
    * 토픽(key)와 전송하려는 메세지를 전달한다
      public void publish(String topicName, String message) throws MqttException;
    
    <ISubscribe.java> interface class
    * MQTT Broker에 연결한다. 연결 정보는 properties 파일 설정값을 참조함 
      public void connect() throws MqttException;
    * 받아 보길 원하는 토픽(key)을 등록한다.
      public void subscribe(String topicName) throws MqttException;
    * MQTT Broker로부터 연결을 끊는다.
      public void disconnect() throws MqttException;
    
# Config
    <jmqtt.client.properties>
    환경설정 파일 : -D옵션을 주거나 jmqtt_client-1.0.jar 파일의 root에 있으면 됨
    # JMQTT broker server ip:port
    # default value ip 127.0.0.1, port 1883
    #jmqtt.broker.ip=222.231.25.95
    jmqtt.broker.ip=127.0.0.1
    jmqtt.broker.port=1883

    # id/password realm 파일을 별도 작성해야 함
    # 적용여부 default is false 
    jmqtt.client.auth.enable=false
    jmqtt.broker.connect.id=jmqtt-client
    jmqtt.broker.connect.pwd=jmqtt-client-12345

    # network
    # send ping message to broker (second)
    jmqtt.client.keepalive.interval=6000
    # connect time out (second)
    jmqtt.client.connect.timeout=6000

    # publish Quality of Service (QoS)
    # level 0 : at most once - 메세지가 한번이 갈 수도 않갈 수도 있음 (상호체크 없음)
    # level 1 : at least once - 메세지가 한번이나 두번 중복해서 갈 수도 있음 (상호체크 1회) 
    # level 2 : confirm once - 반드시 한번만 메세지가 전달됨 (상호체크 2회)
    jmqtt.qos.level=2
    
# Testing
    <SUBSCRIBE>
    * Usage
     1. open new cmd console window (Subscribe console)
     2. java -classpath jmqtt_client-1.0.jar -Djmqtt.config=c:/jmqtt.client.properties MqttServiceMain sub <topicName>
    
    * Example in Subscribe console
     java -classpath jmqtt_client-1.0.jar -Djmqtt.config=c:/jmqtt.client.properties MqttServiceMain sub a/b/c
     Connected to tcp://xxx.xxx.xxx.xxx:1883
     Subscribing to topic "a/b/c" qos 2
     Press <Enter> to exit
    
    <PUBLISH>
    * Usage
     1. open new cmd console window (Publish console)
     2. java -classpath jmqtt_client-1.0.jar -Djmqtt.config=c:/jmqtt.client.properties MqttServiceMain pub <topicName> <Message>
    
    * Example in Publish console
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
    
    
  Thanks for reading this manual written by yun youngsik(dowon)
    