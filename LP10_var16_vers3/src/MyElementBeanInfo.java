/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.beans.*;
import java.awt.*;

public class MyElementBeanInfo extends SimpleBeanInfo {
    
    public Image getIcon(int kind) { return loadImage("pic.gif"); }
    
    /** This is a convenience method for creating PropertyDescriptor objects */
    static PropertyDescriptor prop(String name, String description) {
	try {
	    PropertyDescriptor p =
		new PropertyDescriptor(name, MyElement.class);
	    p.setShortDescription(description);
	    return p;
	}
	catch(IntrospectionException e) { return null; } 
    }

    // Initialize a static array of PropertyDescriptor objects that provide
    // additional information about the properties supported by the bean.
    // By explicitly specifying property descriptors, we are able to provide
    // simple help strings for each property; these would not be available to
    // the beanbox through simple introspection.  We are also able to register
    // a special property editors for the messageText property
    static PropertyDescriptor[] props = {
	prop("backgroundText", "The text that appears in the element body"),
	prop("buttonText", "The text that appears in the button"),
	prop("chooserElements", "The array of elements of JComboBox"),
	prop("acceptingSymbol","The symbol which generate the event"),
	
    };
  
    /** Return the property descriptors for this bean */
    public PropertyDescriptor[] getPropertyDescriptors() { return props; }
    
    /** The message property is most often customized; make it the default */
    public int getDefaultPropertyIndex() { return 0; }
    
}
