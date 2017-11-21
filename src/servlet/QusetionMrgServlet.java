package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Dao.QuestionMrgDAO;
import Dao.iml.IQuestionMrgDAO;
import entity.AnswerInfo;
import entity.QuestionInfo;
import entity.SelectInfo;
import util.CommUtil;
import util.PageUtil;
import util.StringUtil;


/**
 * Servlet implementation class QusetionMrgServlet
 */
public class QusetionMrgServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private IQuestionMrgDAO qsDAO;
	
	public QusetionMrgServlet()
	{
		qsDAO = new QuestionMrgDAO();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		String flag = request.getParameter("flag");
		if("getSubJect".equals(flag))
		{
			String spCode = request.getParameter("spCode");
			Map<String,String> subJectMap = CommUtil.newInstance().getSubjectMap(spCode);
			resultJson(subJectMap,response);
		}
		else if("getKPointType".equals(flag))
		{
			String subCode = request.getParameter("subCode");
			Map<String,String> kpointMap = CommUtil.newInstance().getKpotionTypeMap(subCode);
			resultJson(kpointMap,response);
		}
		else if ("save".equals(flag))
		{
			save(request,response);
		}
		else if ("queryAll".equals(flag))
		{
			queryAll(request,response);
		}
	}

	/**
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws ServletException 
	 */
	private void queryAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String pageSize = request.getParameter("pageSize");
		String pageNum = request.getParameter("pageNum");
		PageUtil<QuestionInfo> pageUtil = new PageUtil<QuestionInfo>();
		
		if (pageSize != null && !"".equals(pageSize))
		{
			pageUtil.setPageSize(Integer.parseInt(pageSize));
		}
		if(pageNum != null && !"".equals(pageNum))
		{
			pageUtil.setPageNum(Integer.parseInt(pageNum));
		}
		qsDAO.queryQuestionInfos(pageUtil);
		request.setAttribute("pageUtil",pageUtil);
		request.getRequestDispatcher("jsp/ExamManager/showExam.jsp").forward(request, response);
	}

	/**
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws ServletException 
	 */
	private void save(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		String msg = "保存失败！";
		String optionContent = "";
		String answerStr = "";
		String question = request.getParameter("question");
		String specialtyType = request.getParameter("specialtyType");
		String subjectType = request.getParameter("subjectType");
		String knowledgePointType = request.getParameter("knowledgePointType");
		String questionType = request.getParameter("questionType");
		String userid = request.getParameter("userid");
		
		QuestionInfo qsBank = new QuestionInfo();
		qsBank.setUserId(Integer.parseInt(userid));
		qsBank.setQuestion(question);
		qsBank.setQuestionType(questionType);
		qsBank.setSpecialtyType(specialtyType);
		qsBank.setSubjectType(subjectType);
		qsBank.setKnowledgePoint(knowledgePointType);
		qsBank.setStatus("0");
		qsBank.setCreateTime(StringUtil.getSysTime());
		//保存题库信息
		Integer qsId = qsDAO.saveQuestionBank(qsBank);
		String[] answers = request.getParameterValues("content");
		//保存选项
		if(qsId != null)
		{
			int i=1;
			Map<String,SelectInfo> selectMap = new HashMap<String,SelectInfo>();
			while (true) 
			{
				SelectInfo info = new SelectInfo();
				String name = "option"+i;
				String content = request.getParameter(name);
				if("".equals(optionContent))
					optionContent = content;
				else
					optionContent += "@@@" +content;
				if(content == null)
					break;
				info.setQsId(qsId);
				info.setContent(content);
				selectMap.put(name, info);
				i++;
			}
			Map<String,Integer> tempMap = qsDAO.saveContentList(selectMap);
			//保存答案
			List<AnswerInfo> asList = new ArrayList<AnswerInfo>();
			for (String answer : answers)//空指针
			{
				AnswerInfo answerInfo = new AnswerInfo();
				Integer selectId = tempMap.get(answer);
				answerInfo.setQsId(qsId);
				answerInfo.setSelectId(selectId);
				asList.add(answerInfo);
			}
			qsDAO.saveAnswerInfos(asList);
			msg = "保存成功！";
		}
		
		request.setAttribute("msg",msg);
		request.setAttribute("optionContent",optionContent);
		request.setAttribute("answerStr",answerStr);
		request.getRequestDispatcher("jsp/ExeaManager/register.jsp").forward(request, response);
		
		
		
	}

	/**
	 * @param kpointMap
	 * @param response
	 */
	private void resultJson(Map<String, String> subJectMap, HttpServletResponse response) {
		String jsonStr = StringUtil.getJsonStr(subJectMap);

		response.setContentType("text/html; charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.print(jsonStr);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		
	}

}
