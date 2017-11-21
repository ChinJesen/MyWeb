package servlet;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import Dao.UserMrgDAO;
import Dao.iml.IUserMsgDAO;
import entity.UserInfo;
import util.ConfigUtil;

/**
 * Servlet implementation class FileMrgServlet
 */
public class FileMrgServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static String filepath;
	
	static {
		ConfigUtil configUtil = ConfigUtil.newInstance(null);
		filepath = configUtil.getVal("filepath");
	}
	
	private IUserMsgDAO userDAO;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FileMrgServlet() {
        userDAO = new UserMrgDAO();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			DiskFileItemFactory factoy = new DiskFileItemFactory();
			factoy.setSizeThreshold(1024*1024);
			factoy.setRepository(new File(filepath));
			ServletFileUpload upload = new ServletFileUpload(factoy);
			upload.setFileSizeMax(1021*1024*2);
			upload.setSizeMax(1024*1024*10);
			@SuppressWarnings("unchecked")
			List<FileItem> list = upload.parseRequest(request);
			for (FileItem fileItem : list) {
				if(!fileItem.isFormField()){
					List<UserInfo> userList = readExcelFile(fileItem.getInputStream());
					int[] len = userDAO.saveBatch(userList);
					int scsCount = 0;//成功数量
					int fidCount = 0;//失败数量
					for(int i:len){
						if(i>0)
							scsCount++;
					}
					fidCount = len.length - scsCount;
					
					request.setAttribute("scsCount",scsCount);
					request.setAttribute("fidCount",fidCount);
					request.setAttribute("len",len);
					request.setAttribute("userList",userList);
					request.getRequestDispatcher("jsp/fileManager/showResult.jsp").forward(request, response);
				}else{
					String name = fileItem.getFieldName();
					String value = fileItem.getString();
					System.out.println(name+"-----"+value);
				}
			}		
			
		} catch (FileUploadException e) {
			e.printStackTrace();
		}
		//request.getRequestDispatcher("jsp/importFile.jsp").forward(request, response);// 请求转发发送连接页面。forward把req，resp传到jsp中

	}

	/**
	 * @param inputStream
	 * @return
	 * @throws IOException 
	 */
	private List<UserInfo> readExcelFile(InputStream inputStream) throws IOException {
		List<UserInfo> userList = new ArrayList<UserInfo>();
		
		@SuppressWarnings("resource")
		HSSFWorkbook work = new HSSFWorkbook(inputStream);
		HSSFSheet sheet = work.getSheetAt(0);
		for(int i = 2;i<=sheet.getLastRowNum();i++){
			HSSFRow row = sheet.getRow(i);
			UserInfo user = new UserInfo();
			for (int j = 0; j < row.getLastCellNum(); j++) {
				HSSFCell cell = row.getCell(j);
				int cellType = cell.getCellType();
				Object val = null;
				switch (cellType) {
				case 0:
					val = cell.getNumericCellValue();
					break;
				case 1:
					val = cell.getStringCellValue();
					break;
				default:
					break;
				}
				switch (j) {
				case 1:
					user.setUserno(val.toString());
					break;
				case 2:
					user.setUsernm(val.toString());
					break;
				case 3:
					user.setUserag(val.toString());
					break;
				case 4:
					user.setSex(val.toString());
					break;
				case 5:
					user.setAh(val.toString());
					break;
				case 6:
					user.setJg(val.toString());
					break;
				default:
					break;
				}
			}
			userList.add(user);
		}
		return userList;
	}

}
