package tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import javax.servlet.jsp.tagext.TagSupport;

public class MyTag extends SimpleTagSupport {
    private String type;  
    private String value;  
      
    public String getType() {  
        return type;  
    }  
  
    public void setType(String type) {  
        this.type = type;  
    }  
  
    public String getValue() {  
        return value;  
    }  
  
    public void setValue(String value) {  
        this.value = value;  
    } 
    
    @Override
    public void doTag() throws JspException, IOException {
    	String name = "";
    	if(type != null && value != null){
    		name = type + "is" + value;
    	}
    	JspWriter out = this.getJspContext().getOut();
    	out.print(name);
    }
}
