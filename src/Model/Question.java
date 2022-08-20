package Model;

import java.io.Serializable;
import java.util.ArrayList;

import Model.Question;

public abstract class Question implements Comparable<Question> , Serializable,Cloneable {
	private static final long serialVersionUID = 1L;
	public static int questionID=1;
    private int questionNum;
    private String questionData;

    public Question(String questionData) {
        this.questionNum = questionID;
        this.questionData = questionData;
        questionID++;
    }
    
    public int getQuestionNum() {
        return questionNum;
    }

    @Override
    public int compareTo(Question other) {
		int length1 = 0, length2 = 0;
		if (this.getClass().getSimpleName().equals("OpenQuestion"))
			length1 = ((OpenQuestion) this).getAnswer().length();
		else {
			ArrayList<Answer> arr = ((AmericanQuestion) this).getAnswerArr();
			for (Answer i : arr) 
				length1 += i.getAnswer().length();
		}
		if (other.getClass().getSimpleName().equals("OpenQuestion"))
			length2 = ((OpenQuestion) other).getAnswer().length();
		else {
			ArrayList<Answer> arr = ((AmericanQuestion) other).getAnswerArr();
			for (Answer i : arr) 
				length2 += i.getAnswer().length();
		}
		return length1 - length2;	
    }
    
    public void updateQuestion(String questionData) {
        this.questionData = questionData;
    }
    
    public void setQuestionData(String questionData) {
    	this.questionData = questionData;
    }
    
    @Override
	public boolean equals(Object obj) {
    	if (this.getClass() == obj.getClass()) {
			Question question = (Question) obj;
			if (this.getQuestionData().equalsIgnoreCase(question.getQuestionData())) 
				return true;
		}
		return false;
	}
    
    public String getQuestionData() {
        return questionData;
    }
    
	@Override
    public String toString() {
        return questionData + "\n";
    }
	
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
}

