package Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

public class QuestionHandler implements Serializable {
	private static final long serialVersionUID = 1L;
	protected ArrayList<Question> questionArr;

	public QuestionHandler() {
		questionArr = new ArrayList<Question>();
	}

	public ptype addQuestion(Question question) {
		if(question.getQuestionData()=="")return ptype.EMPTY_VALUE;
		if (isQuestionExist(question)) {
			Question.questionID--;
			return ptype.QUESTION_ALREADY_EXISTS;
		}
		questionArr.add(question);
		return ptype.ADD_SUCCESS;
	}

	public ptype updateQuestion(int index, String updateSyntax) {
		index--;
		if (index > questionArr.size() || index < 0)
			return ptype.INDEX_OUT_OF_BOUNDS;
		if (!isQuestionExist(questionArr.get(index)))
			return ptype.QUESTION_ISNT_FOUND;
		Question q=questionArr.get(index);
		if (q.getClass().getSimpleName().equals("OpenQuestion")) {
			if (isQuestionExist(new OpenQuestion(updateSyntax))) {
				Question.questionID--;
				return ptype.QUESTION_ALREADY_EXISTS;
			}
		}
		if (q.getClass().getSimpleName().equals("AmericanQuestion")) {
			if (isQuestionExist(new AmericanQuestion(updateSyntax))) {
				Question.questionID--;
				return ptype.QUESTION_ALREADY_EXISTS;
			}
		}
		questionArr.get(index).setQuestionData(updateSyntax);
		return ptype.UPDATE_SUCCESS;
	}

	public ptype updateAnswer(int index, String updateSyntax) {
		index--;
		if (index > questionArr.size() || index < 0)
			return ptype.INDEX_OUT_OF_BOUNDS;
		if (!isQuestionExist(questionArr.get(index)))
			return ptype.QUESTION_ISNT_FOUND;
		Question q=questionArr.get(index);
		if (q.getClass().getSimpleName().equals("OpenQuestion")) {
			((OpenQuestion)q).updateAnswer(updateSyntax);
			return ptype.UPDATE_SUCCESS;
		}
		return ptype.INCORRECT_TYPE;
	}

	public ptype updateAnswer(int index, int indexAnswer, Answer updatedAnswer) {
		index--;
		if (index > questionArr.size() || index < 0)
			return ptype.INDEX_OUT_OF_BOUNDS;
		if (!isQuestionExist(questionArr.get(index)))
			return ptype.QUESTION_ISNT_FOUND;
		Question q=questionArr.get(index);
		if (q.getClass().getSimpleName().equals("AmericanQuestion")) {
			ptype Messege = ((AmericanQuestion)q).updateAnswer(indexAnswer, updatedAnswer);
			return Messege;
		}
		return ptype.INCORRECT_TYPE;
	}

	public ptype deleteQuestion(int index) {
		index--;
		if (index > questionArr.size() || index < 0)
			return ptype.INDEX_OUT_OF_BOUNDS;
		if (!isQuestionExist(questionArr.get(index)))
			return ptype.QUESTION_ISNT_FOUND;
		questionArr.remove(index);
		return ptype.DELETE_SUCCESS;
	}
	public ptype addAmericanAnswer(int index,String answer,boolean a) {
		index--;
		Question q=questionArr.get(index);
		if(q.getClass().getSimpleName().equals("AmericanQuestion"))
		{
			ptype A=((AmericanQuestion)q).addAnswer(new Answer(answer,a));
			return A;
		}
		return ptype.INCORRECT_TYPE;
	}

	@Override
	public String toString() {
		Collections.sort(questionArr);
		StringBuilder buff = new StringBuilder();
		//buff.append("----------------------------------------------------------------------------------- \n");
		for (int i = 0; i < questionArr.size(); i++)
			buff.append("Question " + (i + 1) + ":\n" + questionArr.get(i).toString() + "\n");
		//buff.append("----------------------------------------------------------------------------------- \n");
		return buff.toString();
	}

	public ArrayList<Question> getQuestionArr() {
		return questionArr;
	}

	public int getTotalQuestionNum() {
		return questionArr.size();
	}

	public void setQuestionArr(ArrayList<Question> questionArr) {
		this.questionArr = questionArr;
	}

	public boolean isQuestionExist(Question question) {
		for (int i = 0; i < questionArr.size(); i++) {
			if (questionArr.contains(question))
				return true;
		}
		return false;
	}
	
	public Test createManTest(ArrayList<ArrayList<Integer>> Man) throws CloneNotSupportedException {
		return new Test(Man,questionArr);
	}
	
	public Test createAutoTest(int numofchoises) throws CloneNotSupportedException {
		return new Test(numofchoises,questionArr);
	}
}
