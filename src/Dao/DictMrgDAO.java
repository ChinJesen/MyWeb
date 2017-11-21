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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import Dao.iml.IDictMrgDAO;
import entity.DictItem;
import util.Jdbcutil;

/**
 * @author 尛晨晨
 *
 */
public class DictMrgDAO implements IDictMrgDAO {

	/*
	 * （非 Javadoc）
	 * 
	 * @see Dao.iml.IDictMrgDAO#getDictMap()
	 */
	@Override
	public Map<String, Map<String, String>> getDictMap() {
		// Map<String,String> map = new HashMap<String,String>();
		Map<String, Map<String, String>> dictMap = new HashMap<String, Map<String, String>>();
		List<DictItem> dictList = queryAll();
		for (DictItem dictItem : dictList) {
			String grounpCode = dictItem.getGroupCode();
			Map<String, String> tempMap = null;

			if (dictMap.containsKey(grounpCode)) {
				tempMap = dictMap.get(grounpCode);
				tempMap.put(dictItem.getDictCode(), dictItem.getDictValue());
			} else {
				tempMap = new LinkedHashMap<String, String>();
				tempMap.put(dictItem.getDictCode(), dictItem.getDictValue());
				dictMap.put(grounpCode, tempMap);
			}
		}
		return dictMap;
	}

	/*
	 * （非 Javadoc）
	 * 
	 * @see Dao.iml.IDictMrgDAO#queryAll()
	 */
	@Override
	public List<DictItem> queryAll() {
		List<DictItem> list = new ArrayList<DictItem>();
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "select * from dict_item order by groupCode,sn";
		try {
			conn = Jdbcutil.getConn();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				DictItem dictItem = new DictItem();
				dictItem.setDictId(rs.getInt("dictId"));
				dictItem.setDictCode(rs.getString("dictCode"));
				dictItem.setDictValue(rs.getString("dictValue"));
				dictItem.setGroupCode(rs.getString("groupCode"));
				dictItem.setGroupName(rs.getString("groupName"));
				dictItem.setSn(rs.getInt("sn"));
				dictItem.setStatus(rs.getString("status"));
				dictItem.setRemark(rs.getString("remark"));
				dictItem.setParentId(rs.getInt("parentId"));
				list.add(dictItem);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			Jdbcutil.ColseConn(conn);
		}
		return list;
	}

	/* (non-Javadoc)
	 * @see Dao.iml.IDictMrgDAO#getGroupMap()
	 */
	@Override
	public Map<String, String> getGroupMap() {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		Map<String,String> map = new LinkedHashMap<String,String>();
		String sql = "select groupCode,groupName from dict_item group by groupCode,groupName";
		try {
			conn = Jdbcutil.getConn();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				map.put(rs.getString("groupCode"),rs.getString("groupName"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			Jdbcutil.ColseConn(conn);
		}
		return map;
	}

	/* (non-Javadoc)
	 * @see Dao.iml.IDictMrgDAO#getDictList()
	 */
	@Override
	public Map<String, List<DictItem>> getDictList() {
		Map<String, List<DictItem>>  dictMap = new LinkedHashMap<String, List<DictItem>>();
		List<DictItem> dictList = queryAll();
		
		for (DictItem dictItem : dictList) {
			String groupCode = dictItem.getGroupCode();
			if(dictMap.containsKey(groupCode))
			{
				List<DictItem> list = dictMap.get(groupCode);
				list.add(dictItem);
			}
			else
			{
				List<DictItem> list = new ArrayList<DictItem>();
				list.add(dictItem);
				dictMap.put(groupCode, list);
			}
		}
		return dictMap;
	}

	/* (non-Javadoc)
	 * @see Dao.iml.IDictMrgDAO#saveDictItems(java.util.List, java.lang.String)
	 */
	@Override
	public void saveDictItems(List<DictItem> dictList, String groupCode) {
		//1.删除
		String sql ="delete from Dict_Item where groupCode='"+groupCode+"'";
		//2.添加
		String sql1 = "insert into dict_item (dictCode,dictValue,groupCode,groupName,sn,status,remark) values (?,?,?,?,?,?,?)";
		Connection conn = null;
		PreparedStatement pstm = null;
		
		//默认情况下 JDBC是自动提交数据
		conn = Jdbcutil.getConn();
		//设置提交方式为手动提交
		try {
			conn.setAutoCommit(false);
			pstm = conn.prepareStatement(sql);
			pstm.execute();
			
			pstm = conn.prepareStatement(sql1);
			for (DictItem dictItem : dictList) {
				pstm.setString(1, dictItem.getDictCode());
				pstm.setString(2, dictItem.getDictValue());
				pstm.setString(3, dictItem.getGroupCode());
				pstm.setString(4, dictItem.getGroupName());
				pstm.setInt(5, dictItem.getSn());
				pstm.setString(6, dictItem.getStatus());
				pstm.setString(7, dictItem.getRemark());
				pstm.addBatch();
			}
			pstm.executeBatch();
			conn.commit();
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}finally{
			Jdbcutil.ColseConn(conn);
		}
	}
	@Override
	public Map<String, Map<String, String>> getSubJectMap(){
		Map<String,Map<String,String>> subjectMap = new LinkedHashMap<String,Map<String,String>>();
		List<DictItem> dictList = queryAll();
		Map<Integer,String> spMap = new HashMap<Integer,String>();
		for (DictItem dictItem : dictList) {
			if("SP".equals(dictItem.getGroupCode()))
			{
				spMap.put(dictItem.getDictId(),dictItem.getDictCode());
			}
		}
		for (Integer dictId : spMap.keySet()) {
			for (DictItem dictItem : dictList) {
				if(dictId == dictItem.getParentId()){
					String dictCode = spMap.get(dictId);
					Map<String,String> tempMap = null;
					if(subjectMap.containsKey(dictCode)){
						tempMap = subjectMap.get(dictCode);
					}else{
						tempMap = new HashMap<String,String>();
					}
					tempMap.put(dictItem.getDictCode(),dictItem.getDictValue());
					subjectMap.put(dictCode, tempMap);
				}
			}
		}
	return subjectMap;
	}

	/* (non-Javadoc)
	 * @see Dao.iml.IDictMrgDAO#getKPointTypeMap()
	 */
	@Override
	public Map<String, Map<String, String>> getKPointTypeMap() {
		Map<String, Map<String, String>> kpotionTypeMap = new LinkedHashMap<String, Map<String,String>>();
		List<DictItem> dictList = queryAll();
		Map<Integer,String> subjectMap = new HashMap<Integer,String>();
		for (DictItem dictItem : dictList) 
		{
			if("subject".equals(dictItem.getGroupCode()))
			{
				subjectMap.put(dictItem.getDictId(),dictItem.getDictCode());
			}
		}
		
		for (Integer dictId : subjectMap.keySet())
		{
			for (DictItem dictItem : dictList) {
				if(dictId == dictItem.getParentId())
				{
					String dictCode = subjectMap.get(dictId);
					Map<String,String> tempMap = null;
					if(kpotionTypeMap.containsKey(dictCode))
					{
						tempMap = kpotionTypeMap.get(dictCode);
					}
					else
					{
						tempMap = new LinkedHashMap<String,String>();
					}
					tempMap.put(dictItem.getDictCode(), dictItem.getDictValue());
					kpotionTypeMap.put(dictCode, tempMap);
				}
			}
		}
		return kpotionTypeMap;
	}
}
