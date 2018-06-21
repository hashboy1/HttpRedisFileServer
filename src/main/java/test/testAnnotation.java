package test;

import Annotation.ServiceMapping;

@Person(name="xingoo",age=25)
@ServiceMapping(Value="testAnnotation",Method =0)   //Service Register
public class testAnnotation {

	public static void print(Class c){
		         System.out.println(c.getName());
		        
		         //java.lang.Class的getAnnotation方法，如果有注解，则返回注解。否则返回null
		         Person person = (Person)c.getAnnotation(Person.class);
		         
		         if(person != null){
		            System.out.println("name:"+person.name()+" age:"+person.age());
	        }else{
		            System.out.println("person unknown!");
		        }
		     }
		     public static void main(String[] args){
		    	 testAnnotation.print(testAnnotation.class);
		     }
}
