package JSONRPC.Service;

import Common.JSONRPCBaseService;

public class PrintService extends JSONRPCBaseService {

	
	
	@Override
	public String run(String Parameter1,String Parameter2) {
		
		System.out.println("welcome to access print service");
		return "print Service:" + Parameter1+Parameter2;
		
	}

}
