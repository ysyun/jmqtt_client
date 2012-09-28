package kr.co.yuwin.jmqtt.client.context;

import kr.co.yuwin.jmqtt.client.config.ConfigProperties;

public abstract class AbsContext {

	private ConfigProperties config = null;
	
	public AbsContext() {
		init();
	}
	
	private void init() {
		config = new ConfigProperties();

		String filePath = System.getProperty("jmqtt.config");
		if (filePath == null || filePath.trim().equals(""))
			filePath = "/jmqtt.client.properties";
		config.readProperties(filePath);
	}
	
	protected String getBrokerUrl() {
		return "tcp://" + config.getString("jmqtt.broker.ip") + ":"
				+ config.getString("jmqtt.broker.port");
	}

	protected int getQoS() {
		return config.getInt("jmqtt.qos.level");
	}
	
	protected boolean isAuthEnable() {
		return config.getBoolean("jmqtt.client.auth.enable");
	}
	
	protected String getUserName() {
		return config.getString("jmqtt.broker.connect.id");
	}
	
	protected char[] getPassword() {
		return config.getString("jmqtt.broker.connect.pwd").toCharArray();
	}
		
	protected int getKeepAliveInterval()
	{
		return config.getInt("jmqtt.client.keepalive.interval");
	}
	
	protected int getConnectTimeout()
	{
		return config.getInt("jmqtt.client.connect.timeout");
	}
}
