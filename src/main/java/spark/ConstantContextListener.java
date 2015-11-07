package spark;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import javassist.Modifier;

public class ConstantContextListener implements ServletContextListener {
	
	private static final String CONSTANTCONTEXT = "constant";
	
	
	@Override
	public void contextInitialized(ServletContextEvent event) {
		Map<String, Object> constant = new HashMap<String, Object>();
		
		Class<Constant> declaringClass = Constant.class;
		
		for(Field field : declaringClass.getFields()) {
			int modifiers = field.getModifiers();
			
			if(Modifier.isPublic(modifiers) && Modifier.isStatic(modifiers) && Modifier.isFinal(modifiers)) {
				try {
					constant.put(field.getName(), field.get(null));
				} catch (IllegalArgumentException | IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}
		event.getServletContext().setAttribute(CONSTANTCONTEXT, constant);
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent event) {
		event.getServletContext().removeAttribute(CONSTANTCONTEXT);
	}
	
}
