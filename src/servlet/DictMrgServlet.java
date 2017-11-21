package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Dao.DictMrgDAO;
import Dao.iml.IDictMrgDAO;
import entity.DictItem;
import util.CommUtil;

/**
 * Servlet implementation class DictMrgServlet
 */
public class DictMrgServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
	private IDictMrgDAO dictDAO;
    public DictMrgServlet() {
    	dictDAO = new DictMrgDAO();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String flag = req.getParameter("flag");
		if("queryDict".equals(flag)){
			queryDict(req,resp);
		}else if("save".equals(flag)){
			save(req,resp);
		}
	}

	/**
	 * @param req
	 * @param resp
	 * @throws IOException 
	 * @throws ServletException 
	 */
	private void save(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String groupCode = req.getParameter("jg");
		String groupName = CommUtil.newInstance().getGroupMap().get(groupCode);
		if(groupName == null || "".equals(groupName)){
			groupName = groupCode.split("_")[1];
			groupCode = groupCode.split("_")[0];
		}
		List<DictItem> dictList = new ArrayList<DictItem>();
		
		List<DictItem> oldDictList = CommUtil.newInstance().getDictList(groupCode);
		String[] dictCodes = req.getParameterValues("dictCode");
		if(dictCodes != null){
		for (String dictCode : dictCodes) {
			for (DictItem dictItem : oldDictList) {
				if(dictItem.getDictCode().equals(dictCode)){
					dictList.add(dictItem);
					break;
				}
			}
		}
	}
		String[] dictCodeNews = req.getParameterValues("dictCodeNew");
		String[] dictValueNews = req.getParameterValues("dictValue");
		String[] sns = req.getParameterValues("sn");
		String[] status = req.getParameterValues("status");
		String[] remarks = req.getParameterValues("remark");
		
		for (int i = 0; i < dictCodeNews.length; i++) {
			DictItem dictItem = new DictItem();
			dictItem.setDictCode(dictCodeNews[i]);
			dictItem.setDictValue(dictValueNews[i]);
			dictItem.setSn(Integer.parseInt(sns[i]));
			dictItem.setStatus(status[i]);
			dictItem.setRemark(remarks[i]);
			dictItem.setGroupCode(groupCode);
			dictItem.setGroupName(groupName);
			dictList.add(dictItem);
		}
		dictDAO.saveDictItems(dictList,groupCode);
		//刷新页面数据
		CommUtil.newInstance().refush();
		ServletContext application = this.getServletContext();
		application.setAttribute("test", CommUtil.newInstance().getDictMap());
		application.setAttribute("groupMap", CommUtil.newInstance().getGroupMap());
		req.getRequestDispatcher("jsp/sysManager/dictItemMrg.jsp").forward(req, resp);
	}

	/**
	 * @param req
	 * @param resp
	 */
	private void queryDict(HttpServletRequest req, HttpServletResponse resp) {
		String groupCode = req.getParameter("groupCode");
		List<DictItem> dictList = CommUtil.newInstance().getDictList(groupCode);
		String s = getJsonStr(dictList);
		try {
			resp.setContentType("text/html;chatset=utf-8");
			resp.setCharacterEncoding("utf-8");
			PrintWriter out = resp.getWriter();
			out.print(s);
			out.flush();
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @param dictList
	 * @return
	 */
	private String getJsonStr(List<DictItem> dictList) {
		//{item:[{dictId:"",dictCode:""}]}
				StringBuffer s = new StringBuffer();
				s.append("{item:[");
				for (DictItem dictItem : dictList)
				{
					s.append("{");
					s.append("dictId:\"").append(dictItem.getDictId()).append("\",");
					s.append("dictCode:\"").append(dictItem.getDictCode()).append("\",");
					s.append("dictValue:\"").append(dictItem.getDictValue()).append("\",");
					s.append("groupCode:\"").append(dictItem.getGroupCode()).append("\",");
					s.append("groupName:\"").append(dictItem.getGroupName()).append("\",");
					s.append("sn:\"").append(dictItem.getSn()).append("\",");
					s.append("status:\"").append(dictItem.getStatus()).append("\",");
					s.append("remark:\"").append(dictItem.getRemark()).append("\"");
					s.append("},");
				}
				if(s.toString().contains(","))
					s = new StringBuffer(s.substring(0,s.length() - 1));
				
				s.append("]}");
				return s.toString();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
