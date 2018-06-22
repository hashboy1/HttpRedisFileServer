package JSONSOA.Service.test;

import Annotation.ServiceMapping;
import Common.RPCBaseService;



@ServiceMapping(Value="printService4",Method =0)   //Service Register
public class printService4 extends RPCBaseService {


	@Override
	public String run(String Parameter1,String Parameter2) {	
		System.out.println("welcome to access print service4");
		return "print Service4:" + Parameter1 + Parameter2;
       	
	}

}