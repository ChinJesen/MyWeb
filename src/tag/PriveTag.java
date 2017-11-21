/**
 * 
 */
package tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

/**
 * @author Œ­³¿³¿
 *
 */
public class PriveTag extends SimpleTagSupport {
	private String item;

	public void setItem(String item) {
		this.item = item;
	}
	
	/* (non-Javadoc)
	 * @see javax.servlet.jsp.tagext.SimpleTagSupport#doTag()
	 */
	@Override
	public void doTag() throws JspException, IOException {
	System.out.println(item);
	}
}
