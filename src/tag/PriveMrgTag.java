/**
 * 
 */
package tag;

import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import entity.UserInfo;
import util.CommUtil;

/**
 * @author 尛晨晨
 *
 */
@SuppressWarnings("serial")
public class PriveMrgTag extends TagSupport {
	 private String value;

	public void setValue(String value) {
		this.value = value;
	}
	
	/* (non-Javadoc)
	 * @see javax.servlet.jsp.tagext.TagSupport#doStartTag()
	 */
	@Override
	public int doStartTag() throws JspException {
		Object obj = pageContext.getSession().getAttribute("userInfo");
		if(obj != null){
			UserInfo user = (UserInfo)obj;
			List<String> priveList = CommUtil.newInstance().getPriveList(user.getRoleId());
			if(priveList.contains(value))
				return EVAL_BODY_INCLUDE;		//返回标签主体
		}
			return SKIP_BODY;
	}
}
