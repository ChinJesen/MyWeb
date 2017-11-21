/**
 * 
 */
package Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Dao.iml.IQuestionMrgDAO;
import entity.AnswerInfo;
import entity.QuestionInfo;
import entity.SelectInfo;
import util.Jdbcutil;
import util.PageUtil;

/**
 * @author Œ­³¿³¿
 *
 */
public class QuestionMrgDAO extends BaseDAO<QuestionMrgDAO> implements IQuestionMrgDAO {

	/* (non-Javadoc)
	 * @see Dao.iml.IQuestionMrgDAO#saveQuestionBank(entity.QuestionInfo)
	 */
	@Override
	public Integer saveQuestionBank(QuestionInfo qsBank) {
		Integer qsId = null;
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		String sql = "INSERT INTO question_bank_info " +
				"(userId,question,specialtyType,subjectType,knowledgePoint,questionType,STATUS,createTime) " +
				" VALUES(?,?,?,?,?,?,?,?)";
		conn = Jdbcutil.getConn();
		try 
		{
			pstm = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
			pstm.setInt(1,qsBank.getUserId());
			pstm.setString(2, qsBank.getQuestion());
			pstm.setString(3, qsBank.getSpecialtyType());
			pstm.setString(4, qsBank.getSubjectType());
			pstm.setString(5, qsBank.getKnowledgePoint());
			pstm.setString(6, qsBank.getQuestionType());
			pstm.setString(7, qsBank.getStatus());
			pstm.setString(8, qsBank.getCreateTime());
			pstm.execute();
			
			rs = pstm.getGeneratedKeys();
			while(rs.next())
			{
				qsId = rs.getInt(1);
			}
			
		} 
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			Jdbcutil.ColseConn(conn);
		}
		
		return qsId;
	}

	/* (non-Javadoc)
	 * @see Dao.iml.IQuestionMrgDAO#saveContentList(java.util.Map)
	 */
	@Override
	public Map<String, Integer> saveContentList(Map<String, SelectInfo> selectMap) {
		Map<String,Integer> tempMap = new HashMap<String,Integer>();
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		String sql = "INSERT INTO select_info (qsId,content) VALUES (?,?)";
		
		conn = Jdbcutil.getConn();
		try {
			pstm = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
			for (String key : selectMap.keySet()) 
			{
				SelectInfo info = selectMap.get(key);
				pstm.setInt(1, info.getQsId());
				pstm.setString(2,info.getContent());
				pstm.execute();
				
				rs = pstm.getGeneratedKeys();
				while(rs.next())
				{
					tempMap.put(key, rs.getInt(1));
				}
			}
			
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally
		{
			Jdbcutil.ColseConn(conn);
		}
		
		return tempMap;
	}

	/* (non-Javadoc)
	 * @see Dao.iml.IQuestionMrgDAO#saveAnswerInfos(java.util.List)
	 */
	@Override
	public void saveAnswerInfos(List<AnswerInfo> asList) {
		Connection conn = null;
		PreparedStatement pstm = null;
		String sql = "INSERT INTO answer_info (qsId,selectId) VALUES (?,?)";
		
		conn = Jdbcutil.getConn();
		try {
			pstm = conn.prepareStatement(sql);
			for (AnswerInfo answerInfo : asList) 
			{
				pstm.setInt(1, answerInfo.getQsId());
				pstm.setInt(2,answerInfo.getSelectId());
				pstm.addBatch();
			}
			pstm.executeBatch();
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally
		{
			Jdbcutil.ColseConn(conn);
		}
		
	}

	/* (non-Javadoc)
	 * @see Dao.iml.IQuestionMrgDAO#queryQuestionInfos(util.PageUtil)
	 */
	@Override
	public void queryQuestionInfos(PageUtil<QuestionInfo> pageUtil) {
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		String sql = "SELECT q.qsId,q.question,q.specialtyType,q.subjectType,q.knowledgePoint,q.qusetionType," +
				" q.status,q.createTime,(SELECT userName FROM user_info WHERE userId= q.userId) userName" +
				" FROM question_bank_info q LIMIT ?,?";
		try {
			conn = Jdbcutil.getConn();
			pstm = conn.prepareStatement(sql);
			pstm.setInt(1, (pageUtil.getPageSize() - 1) * pageUtil.getPageNum());
			pstm.setInt(2,pageUtil.getPageNum());
			rs = pstm.executeQuery();
			List<QuestionInfo> tempList = new ArrayList<QuestionInfo>();
			while(rs.next())
			{
				QuestionInfo qsBank = new QuestionInfo();
				qsBank.setQsId(rs.getInt("qsId"));
				qsBank.setQuestion(rs.getString("question"));
				qsBank.setQuestionType(rs.getString("questionType"));
				qsBank.setSpecialtyType(rs.getString("specialtyType"));
				qsBank.setSubjectType(rs.getString("subjectType"));
				qsBank.setKnowledgePoint(rs.getString("knowledgePoint"));
				qsBank.setStatus(rs.getString("status"));
				qsBank.setCreateTime(rs.getString("createTime"));
				qsBank.setUserId(rs.getInt("userName"));
				tempList.add(qsBank);
			}
			pageUtil.setList(tempList);
			pageUtil.setPageNumSum(getPageNumSum(pageUtil));
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			Jdbcutil.ColseConn(conn);
		}
	}

	/**
	 * @param pageUtil
	 * @return
	 */
	private Integer getPageNumSum(PageUtil<QuestionInfo> pageUtil) {
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		String sql = "SELECT count(qsId) FROM question_bank_info";
		Integer count = 0;
		try {
			conn = Jdbcutil.getConn();
			pstm = conn.prepareStatement(sql);
			
			rs = pstm.executeQuery();
			while(rs.next())
			{
				count = rs.getInt(1);
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally
		{
			Jdbcutil.ColseConn(conn);
		}
		return count;
	}

	
}
