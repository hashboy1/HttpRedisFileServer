package JSONRPC.Service;

import Common.JSONRPCBaseService;

public class PrintService2 extends JSONRPCBaseService {


	@Override
	public String run(String Parameter1,String Parameter2) {	
		System.out.println("welcome to access print service2");
		return "print Service2:" + Parameter1 + Parameter2;
       	
	}

}
