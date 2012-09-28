package kr.co.yuwin.jmqtt.client.context;

import static org.junit.Assert.fail;
import kr.co.yuwin.jmqtt.client.context.publish.IPublish;
import kr.co.yuwin.jmqtt.client.context.publish.PublishContext;
import kr.co.yuwin.jmqtt.client.context.subscribe.ISubscribe;
import kr.co.yuwin.jmqtt.client.context.subscribe.SubscribeContext;

import org.junit.AfterClass;
import org.junit.BeforeClass;

public class PublishContextTest {

	@BeforeClass
	public void before() {
		ISubscribe sub = null;
		try{
			sub = new SubscribeContext("service.sub", null);
			sub.connect();
			sub.subscribe("a/b");
		} catch(Exception e) {
			fail("subscribe fail, Exception="+e.getMessage());
		} finally {
			if(sub != null)
				try{ sub.disconnect(); } catch(Exception e){}
		}
	}
	
	@AfterClass
	public void after() {
		try{
			IPublish pub = new PublishContext("service.pub", null);
			pub.publish("a/b", "hi");
		} catch(Exception e) {
			fail("publish fail, Exception="+e.getMessage());
		}
	}
}
