/**
 * 
 */
package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Dao.ClassMrgDAO;
import Dao.UserMrgDAO;
import Dao.iml.IClassMrgDAO;
import Dao.iml.IUserMsgDAO;
import entity.ClassInfo;
import entity.UserInfo;
import util.CommUtil;
import util.PageUtil;

/**
 * @author 尛晨晨
 *
 */
public class ClassMrgServlet extends HttpServlet {
	private IClassMrgDAO classDAO = new ClassMrgDAO();
	private IUserMsgDAO userDAO = new UserMrgDAO();
	/**
	 * 
	 */
	private static final long serialVersionUID = 8831058097035445038L;

	/*
	 * （非 Javadoc）
	 * 
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.
	 * HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String flag = req.getParameter("flag");
		if ("add".equals(flag)) {
			add(req, resp);
		} else if ("queryAllclass".equals(flag)) {
			queryAllclass(req, resp);
		} else if ("deleteclass".equals(flag)) {
			deleteclass(req, resp);
		} else if ("queryById".equals(flag)){
			queryById(req,resp);
		} else if ("update".equals(flag)){
			update(req,resp);
		} else if ("queryTeahcer".equals(flag)){
			queryTeahcers(req,resp);
		} else if ("queryClassInfo".equals(flag)){
			queryClassInfo(req,resp);
		}
	}

	/**
	 * 查询班级名称时候可用
	 * @param req
	 * @param resp
	 * @throws IOException 
	 */
	private void queryClassInfo(HttpServletRequest req, HttpServletResponse resp) throws IOException {
			String className = req.getParameter("className");
			boolean flag = classDAO.queryByName(className);
			PrintWriter out = resp.getWriter();
			out.print(flag);
			out.flush();
			out.close();
	}

	/**
	 * @param req
	 * @param resp
	 * @throws IOException 
	 */
	private void queryTeahcers(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String teacherName = req.getParameter("teacherName");
		List<UserInfo> userList = userDAO.queryTeacherInfos(teacherName);
		//System.out.println("查询结果:"+userList.size());
		if(userList != null && userList.size() > 0)
		{
			StringBuffer s = new StringBuffer();
			s.append("{\"item\":[");
			for (UserInfo userInfo : userList) {
				s.append("{");
				s.append("\"userId\":\"").append(userInfo.getUserid()).append("\",");
				s.append("\"userName\":\"").append(userInfo.getUsernm()).append("\"");
				s.append("},");
			}
			if(s.lastIndexOf(",") != -1)
			{
				s = new StringBuffer(s.substring(0, s.length() - 1));
			}
			s.append("]}");
			resp.setContentType("text/html;charset=UTF-8");
			resp.setCharacterEncoding("UTF-8");
			PrintWriter out = resp.getWriter();
			out.print(s);
			out.flush();
			out.close();
		}
	}

	/**
	 * 修改，更新信息
	 * @param req
	 * @param resp
	 * @throws IOException 
	 * @throws ServletException 
	 */
	private void update(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		req.getRequestDispatcher("jsp/classManager/showClassInfo.jsp").forward(req, resp);
	}

	/**
	 * 通过id查询信息
	 * @param req
	 * @param resp
	 */
	private void queryById(HttpServletRequest req, HttpServletResponse resp) {
	try {
		String classId = req.getParameter("classId");
		ClassInfo classInfo = null;
		if(!"".equals(classId)){
			classInfo = classDAO.queryById(classId);
		}
		req.setAttribute("classInfo",classInfo);
		req.getRequestDispatcher("jsp/classManager/editClassInfo.jsp").forward(req, resp);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 删除班级信息
	 * @param req
	 * @param resp
	 */
	private void deleteclass(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String classId = req.getParameter("classId");
		classDAO.deleteClassInfoById(classId);
		queryAllclass(req, resp);
	}

	/**
	 * 条件查询
	 * @param req
	 * @param resp
	 */
	private void queryAllclass(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		PageUtil<ClassInfo> pageUtil = new PageUtil<ClassInfo>();
		String pageSize = req.getParameter("pageSize");
		String pageNum = req.getParameter("pageNum");
		if (pageSize != null && !"".equals(pageSize)) {
			pageUtil.setPageSize(Integer.parseInt(pageSize));
		}
		if (pageNum != null && !"".equals(pageNum)) {
			pageUtil.setPageNum(Integer.parseInt(pageNum));
		}

		// 条件查询
		String classId = req.getParameter("classId");
		String className = req.getParameter("className");
		ClassInfo classinfo = null;
		if (classId != null || className != null) {
			classinfo = new ClassInfo();
			classinfo.setClassId(classId);
			classinfo.setClassName(className);
		}
		pageUtil.setEntity(classinfo);
		classDAO.queryAll(pageUtil);
		req.setAttribute("pageSize", pageUtil.getPageSize());
		req.setAttribute("pageNum", pageUtil.getPageNum());
		req.setAttribute("pageNumSum", pageUtil.getPageNumSum());
		req.setAttribute("pageSizeSum", pageUtil.getPageSizeSum());
		req.setAttribute("classList", pageUtil.getList());// 向jsp发送集合（showallusers.jsp的get方法来得到该集合）
		req.setAttribute("clsInfo", classinfo);
		req.getRequestDispatcher("jsp/classManager/showClassInfos.jsp").forward(req, resp);
	}

	/**
	 * 添加班级信息
	 * @param req
	 * @param resp
	 */
	private void add(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String msg = "保存失败";
		String className = req.getParameter("className");
		String special = req.getParameter("special");
		String createTime = req.getParameter("createTime");
		String endTime = req.getParameter("endTime");
		String remark = req.getParameter("remark");
		String teacherIds = req.getParameter("teacherIds");
		String spTeachter = req.getParameter("spTeachter");
		
		ClassInfo classInfo = new ClassInfo();
		classInfo.setClassName(className);
		classInfo.setSpecial(special);
		classInfo.setCreateTime(createTime);
		classInfo.setEndTime(endTime);
		classInfo.setRemark(remark);
		classInfo.setStatus("0");

		Integer classId = classDAO.saveClassInfo(classInfo);
		if (classId != null) {
			msg = "保存成功";
			//保存班级的老师信息
			if(teacherIds != null && !"".equals(teacherIds)){
			String[] tcherIds = teacherIds.split(","); 
			classDAO.saveClassTeachers(tcherIds,classId);
			}
			CommUtil commUtil = CommUtil.newInstance();
			commUtil.refeshClassMap();
			this.getServletContext().setAttribute("clsMap",commUtil.getClassMap());
		}
		req.setAttribute("msg", msg);
		req.setAttribute("spTeachter",spTeachter);
		req.setAttribute("teacherIds",teacherIds);
		req.getRequestDispatcher("/jsp/classManager/registerClassInfo.jsp").forward(req, resp);
	}

	/*
	 * （非 Javadoc）
	 * 
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.
	 * HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO 自动生成的方法存根
	}
}
