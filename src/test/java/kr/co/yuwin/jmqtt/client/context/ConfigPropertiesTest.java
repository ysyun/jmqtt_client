package kr.co.yuwin.jmqtt.client.context;

import static org.junit.Assert.*;
import kr.co.yuwin.jmqtt.client.config.ConfigProperties;
import org.junit.Test;

public class ConfigPropertiesTest {

	@Test
	public void test() {
		ConfigProperties config = new ConfigProperties();
		config.readProperties("/jmqtt.client.properties");
		
		assertNotNull(config.getString("jmqtt.broker.connect.id"));
	}
	
}
