package Redis.Service;

import Annotation.ServiceMapping;
import Common.RPCBaseService;




@ServiceMapping(Value="PrintService3",Method =0)   //Service Register
public class printService3 extends RPCBaseService {


	@Override
	public String run(String Parameter1,String Parameter2) {	
		System.out.println("welcome to access print service3");
		return "print Service3:" + Parameter1 + Parameter2;
       	
	}

}