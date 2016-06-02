package hellospring;

import java.util.List;
import java.util.Map;
import java.util.Properties;


public class HelloImpl implements IHello{
	
	private String msg;
	private int i;
	private List<String> values;
	private String [] arr;
	private Map<String,String> map;
	private Properties prop;
	
	
	
	public void setProp(Properties prop) {
		this.prop = prop;
	}
	public void setMap(Map<String, String> map) {
		this.map = map;
	}
	public void setArr(String[] arr) {
		this.arr = arr;
	}
	public void setValues(List<String> values) {
		this.values = values;
	}
	public HelloImpl()
	{
		this.msg = "Hello World!";
	}
	public HelloImpl(String msg){
		this.msg = msg;
	}
	
	public HelloImpl(int i){
		this.i = i;
	}
	
	@java.beans.ConstructorProperties({"msg", "i"})
	public HelloImpl(String msg,int i){
		this.i = i;
		this.msg = msg;
	}
	
	@Override
	public void sayHello() {
		System.out.println(msg);
		System.out.println(i);
	}
	
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public void setI(int i) {
		this.i = i;
	}
	
}
