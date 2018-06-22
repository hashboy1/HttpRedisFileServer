package JSONSOA.Service;

import Annotation.ServiceMapping;
import Common.RPCBaseService;



@ServiceMapping(Value="PrintServiceTesting",Method =0)
public class PrintServiceTesting extends RPCBaseService {

	
	
	@Override
	public String run(String Parameter1,String Parameter2) {
		
		System.out.println("welcome to access print service");
		return "print Service:" + Parameter1+Parameter2;
		
	}

}
