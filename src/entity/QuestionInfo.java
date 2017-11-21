/**
 * 
 */
package entity;

/**
 * @author Œ­³¿³¿
 *
 */
public class QuestionInfo {
		private Integer qsId;
		private Integer use_userid;
		private Integer userId;
		private String question;
		private String specialtyType;
		private String subjectType;
		private String knowledgePoint;
		private String questionType;
		private String createTime;
		private String status;
		
		public String getStatus() {
			return status;
		}
		public void setStatus(String status) {
			this.status = status;
		}
		public Integer getQsId() {
			return qsId;
		}
		public void setQsId(Integer qsId) {
			this.qsId = qsId;
		}
		public Integer getUse_userid() {
			return use_userid;
		}
		public void setUse_userid(Integer use_userid) {
			this.use_userid = use_userid;
		}
		public Integer getUserId() {
			return userId;
		}
		public void setUserId(Integer userId) {
			this.userId = userId;
		}
		public String getQuestion() {
			return question;
		}
		public void setQuestion(String question) {
			this.question = question;
		}
		public String getSpecialtyType() {
			return specialtyType;
		}
		public void setSpecialtyType(String specialtyType) {
			this.specialtyType = specialtyType;
		}
		public String getSubjectType() {
			return subjectType;
		}
		public void setSubjectType(String subjectType) {
			this.subjectType = subjectType;
		}
		public String getKnowledgePoint() {
			return knowledgePoint;
		}
		public void setKnowledgePoint(String knowledgePoint) {
			this.knowledgePoint = knowledgePoint;
		}
		public String getQuestionType() {
			return questionType;
		}
		public void setQuestionType(String questionType) {
			this.questionType = questionType;
		}
		public String getCreateTime() {
			return createTime;
		}
		public void setCreateTime(String createTime) {
			this.createTime = createTime;
		}
		
		
}
