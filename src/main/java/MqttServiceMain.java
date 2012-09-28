
public class MqttServiceMain {

	public static void main(String[] args)
	{
		if(args.length<2) {
			System.out.println("Subscribe : java -classpath ./jmqtt_client-1.0.jar -Djmqtt.config=d:/jmqtt.client.properties MqttServiceMain sub <topic string>");
			System.out.println("Publish   : java -classpath ./jmqtt_client-1.0.jar -Djmqtt.config=d:/jmqtt.client.properties MqttServiceMain pub <topic string> <message>");
		}
		
		try {
			MqttService service = new MqttService(); 
			
			if(args[0].equals("pub")) 
			{
				service.publish(args[1], args[2]);
			} 
			else if(args[0].equals("sub")) 
			{		
				service.subscribe(args[1]);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
