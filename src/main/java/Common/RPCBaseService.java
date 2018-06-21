package Common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class RPCBaseService {
	
	//protected Logger logger = LoggerFactory.getLogger(getClass());
	
	public  abstract String run(String Parameter1,String Parameter2);   //key function for Service
	

}
