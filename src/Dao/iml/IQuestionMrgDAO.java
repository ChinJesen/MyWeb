/**
 * 
 */
package Dao.iml;

import java.util.List;
import java.util.Map;

import Dao.QuestionMrgDAO;
import entity.AnswerInfo;
import entity.QuestionInfo;
import entity.SelectInfo;
import util.PageUtil;

/**
 * @author Œ­³¿³¿
 *
 */
public interface IQuestionMrgDAO extends IBaseDAO<QuestionMrgDAO> {

	/**
	 * @param qsBank
	 * @return
	 */
	Integer saveQuestionBank(QuestionInfo qsBank);

	/**
	 * @param selectMap
	 * @return
	 */
	Map<String, Integer> saveContentList(Map<String, SelectInfo> selectMap);

	/**
	 * @param asList
	 */
	void saveAnswerInfos(List<AnswerInfo> asList);

	/**
	 * @param pageUtil
	 */
	void queryQuestionInfos(PageUtil<QuestionInfo> pageUtil);

}
