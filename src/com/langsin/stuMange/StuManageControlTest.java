package com.langsin.stuMange;

import static org.junit.Assert.*;
import static org.junit.Assert.fail;

import org.junit.Ignore;
import org.junit.Test;

public class StuManageControlTest {

	StuManageControl control = new StuManageControl();
	
	@Test
	public void testLogin() throws Exception {
		control.login();
		assertTrue(!control.isLogined);
	}

	@Ignore
	public void testSearch() throws Exception {
			
	}

	@Ignore
	public void testModify() {
	}

}
