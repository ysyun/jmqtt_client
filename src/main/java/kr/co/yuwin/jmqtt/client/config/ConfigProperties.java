package kr.co.yuwin.jmqtt.client.config;

import java.io.*;
import java.util.*;

public class ConfigProperties {

	private Properties props = null;

	public void readProperties(String filePath) {		
		InputStream input = null;
		try {
			File file = new File(filePath);
			if (!file.exists()) {
				input = ConfigProperties.class.getResourceAsStream(filePath);
			} else {
				input = new FileInputStream(file);
			}
			
			props = new Properties();
			props.load(input);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (input != null)
				try {
					input.close();
				} catch (IOException e) {}
		}
	}

	public Properties getProperties() {
		return props;
	}

	public String getProperty(String key) {
		String value = System.getProperty(key);
		if (value != null)
			return value;
		value = props.getProperty(key);
		if (value != null)
			return value;
		return System.getenv(key);
	}

	public String getString(String key) {
		// TODO Auto-generated method stub
		return getProperty(key);
	}

	public boolean getBoolean(String key) {
		String value = getProperty(key);
		if (value == null) {
			return false;
		} else {
			try {
				return Boolean.getBoolean(value.trim());
			} catch (Exception e) {
				return false;
			}
		}
	}

	public int getInt(String key) {
		String value = getProperty(key);
		if (value == null) {
			return -1;
		} else {
			try {
				return Integer.parseInt(value.trim());
			} catch (Exception e) {
				return -1;
			}
		}
	}

	public float getFloat(String key) {
		String value = getProperty(key);
		if (value == null) {
			return -1.0F;
		} else {
			try {
				return Float.parseFloat(value.trim());
			} catch (Exception e) {
				return -1.0F;
			}
		}
	}

	public double getDouble(String key) {
		String value = getProperty(key);
		if (value == null) {
			return -1.0F;
		} else {
			try {
				return Double.parseDouble(value.trim());
			} catch (Exception e) {
				return -1.0F;
			}
		}
	}	
}
