package servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import Dao.UserMrgDAO;
import Dao.iml.IUserMsgDAO;
import entity.UserInfo;
import util.PageUtil;

public class UserMrgServlet extends HttpServlet {
	private static final long serialVersionUID = -6381456126118663189L;
	/**
	 * 
	 */
	private IUserMsgDAO userDAO = new UserMrgDAO();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String flag = req.getParameter("flag");
		if ("login".equals(flag)) {
			login(req, resp);
		} else if ("save".equals(flag)) {
			save(req, resp);
		} else if ("queryAll".equals(flag)) {
			queryAll(req, resp);
		} else if ("queryById".equals(flag)) {
			queryUserInfo(req, resp);
		} else if ("delete".equals(flag)) {
			delete(req, resp);
		} else if ("ajaxFileDownload".equals(flag)) {
			ajaxFileDownload(req, resp);
		}
	}

	/**
	 * @param req
	 * @param resp
	 * @throws Exception
	 */
	private void ajaxFileDownload(HttpServletRequest req, HttpServletResponse resp) {
		String photoName = req.getParameter("fileName");
		String path = "D:/test/" + photoName;
		try {
			FileInputStream fis = new FileInputStream(path);
			ServletOutputStream sos = resp.getOutputStream();
			byte[] bbuf = new byte[2048];
			int hasRead = 0;
			while ((hasRead = fis.read(bbuf)) > 0) {
				sos.write(bbuf, 0, hasRead);
			}
			fis.close();
			sos.flush();
			sos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 */
	private void delete(HttpServletRequest req, HttpServletResponse resp) {
		String userid = req.getParameter("userid");
		userDAO.deleteUserInfoById(Integer.parseInt(userid));
		queryAll(req, resp);
	}

	/**
	 * @param req
	 * @param resp
	 */
	private void queryUserInfo(HttpServletRequest req, HttpServletResponse resp) {
		try {
			String userid = req.getParameter("userid");
			UserInfo userInfo = null;
			if (!"".equals(userid)) {
				userInfo = userDAO.queryById(Integer.parseInt(userid));
			}
			req.setAttribute("userInfo", userInfo);
			req.getRequestDispatcher("jsp/editUserInfo.jsp").forward(req, resp);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * ��ѯ����
	 *
	 */

	private void queryAll(HttpServletRequest req, HttpServletResponse resp) {
		try {
			PageUtil<UserInfo> pageUtil = new PageUtil<UserInfo>();
			String pageSize = req.getParameter("pageSize");
			String pageNum = req.getParameter("pageNum");
			if (pageSize != null && !"".equals(pageSize)) {
				pageUtil.setPageSize(Integer.parseInt(pageSize));
			}
			if (pageNum != null && !"".equals(pageNum)) {
				pageUtil.setPageNum(Integer.parseInt(pageNum));
			}
			// ��������ѯ
			// �õ�����
			String userno = req.getParameter("userno");
			String usernm = req.getParameter("usernm");
			String sex = req.getParameter("sex");
			// װ��user info�е�user
			UserInfo user = null;
			if (userno != null || usernm != null || sex != null) {
				user = new UserInfo();
				user.setUserno(userno);
				user.setUsernm(usernm);
				user.setSex(sex);
			}
			// �ٰ�user�ŵ�pageutil���entity
			pageUtil.setEntity(user);
			// pageutil����queryall����
			userDAO.queryAll(pageUtil);
			req.setAttribute("pageSize", pageUtil.getPageSize());
			req.setAttribute("pageNum", pageUtil.getPageNum());
			req.setAttribute("pageNumSum", pageUtil.getPageNumSum());
			req.setAttribute("pageSizeSum", pageUtil.getPageSizeSum());
			req.setAttribute("userlist", pageUtil.getList());// ��jsp���ͼ��ϣ�showallusers.jsp��get�������õ��ü��ϣ�
			req.setAttribute("user", user);
			req.getRequestDispatcher("jsp/showallusers.jsp").forward(req, resp);// ����ת����������ҳ�档forward��req��resp����jsp��
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * ���淽��
	 *
	 */
	private void save(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String msg = "����ɹ�";
		String userno = req.getParameter("userno");// �˺�
		String userpw = req.getParameter("userpw");// ����
		String usernm = req.getParameter("usernm");// ����
		String userag = req.getParameter("userag");// ����
		String sex = req.getParameter("sex"); // �Ա�
		String ah = "";
		String[] ahs = req.getParameterValues("ah"); // ����
		if(ahs!= null){
			for (String s : ahs) {
				ah += s +"_";
			}
			if (ah.contains("_")){
				ah = ah.substring(0,ah.length()-1);
			}
		}
		String jg = req.getParameter("jg"); // ����
		String photo = req.getParameter("photo"); //��Ƭ
		String birthday = req.getParameter("birthday");
		String createTime = req.getParameter("createTime");
		String status = req.getParameter("status");
		String roleId = req.getParameter("roleId");
		String jj = req.getParameter("jj");
		UserInfo userinfo = new UserInfo();
		userinfo.setUserno(userno);
		userinfo.setUserpw(userpw);
		userinfo.setUsernm(usernm);
		userinfo.setUserag(userag);
		userinfo.setSex(sex);
		userinfo.setAh(ah);
		userinfo.setJg(jg);
		userinfo.setPhoto(photo);
		userinfo.setBirthday(birthday);
		userinfo.setCreateTime(createTime);
		userinfo.setStatus(status);
		userinfo.setRoleId(roleId);
		userinfo.setJj(jj);
		// ����ҵ�񷽷�
		UserMrgDAO userDAO = new UserMrgDAO();
		if (!userDAO.saveUserInfo(userinfo)) {
			msg = "����ʧ��";
		}
		req.setAttribute("user", userinfo);
		req.setAttribute("msg", msg);
		req.getRequestDispatcher("/jsp/registerUserInfo.jsp").forward(req, resp);
	}

	/**
	 * ��¼�󷽷�
	 *
	 */
	private void login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String userno = req.getParameter("userno");
		String userpw = req.getParameter("userpw");
		String msg = "";
		String url = "main1.jsp";
		UserInfo userInfo = userDAO.login(userno, userpw);
		if (userInfo == null) {
			msg = "�˻����������";
			url = "Index.jsp";
		}
		req.getSession().setAttribute("userInfo",userInfo);
		req.setAttribute("msg", msg);
		req.getRequestDispatcher(url).forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String flag = req.getParameter("flag");
		if ("validUserNo".equals(flag)) {
			validUserNo(req, resp);
		} else if ("ajaxfileupload".equals(flag)) {
			ajaxfileupload(req, resp);
		}

	}

	/**
	 * @param req
	 * @param resp
	 * @throws IOException
	 */
	private void ajaxfileupload(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		try {
			DiskFileItemFactory factoy = new DiskFileItemFactory();
			factoy.setSizeThreshold(1024 * 1024);
			factoy.setRepository(new File("D:\\test"));
			ServletFileUpload upload = new ServletFileUpload(factoy);
			upload.setFileSizeMax(1021 * 1024 * 2);
			upload.setSizeMax(1024 * 1024 * 10);
			@SuppressWarnings("unchecked")
			List<FileItem> list = upload.parseRequest(req);
			for (FileItem fileItem : list) {
				if (!fileItem.isFormField()) {
					InputStream in = fileItem.getInputStream();
					File file = new File("D:\\test");
					if (!file.exists()) {
						file.mkdirs();
					}
					file.setWritable(true);
					String filePath = file.getAbsolutePath() + "\\" + fileItem.getName();
					FileOutputStream out = new FileOutputStream(filePath);
					byte[] b = new byte[1024];
					int len = 0;
					while ((len = in.read(b)) != -1) {
						out.write(b, 0, len);
					}
					out.flush();
					out.close();
					in.close();

					// ����ͼƬ���ͻ���
					PrintWriter out1 = resp.getWriter();
					// out1.print("{\"filePath\":"+"\"http://127.0.0.1:8080//FileImages//"+fileItem.getName()+"\"}");
					out1.print("{\"filePath\":\"" + fileItem.getName() + "\"}");
					out1.flush();
					out1.close();
				}
			}

		} catch (FileUploadException e) {
			e.printStackTrace();
		}
	}

	/**
	 * ����˻��Ƿ����
	 * @param req
	 * @param resp
	 */
	private void validUserNo(HttpServletRequest req, HttpServletResponse resp) {
		String userno = req.getParameter("userno");
		boolean flag = userDAO.validUserNO(userno);
		PrintWriter out = null;
		try {
			out = resp.getWriter();
			out.print(flag);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
	}
}
