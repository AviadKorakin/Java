package Model;

import java.io.Serializable;

public class Answer implements Comparable<Answer>,Serializable{
	private static final long serialVersionUID = 1L;
	private String answer;
	private boolean isRight;
	
	public Answer(String answer, boolean isRight) {
		this.answer = answer;
		this.isRight = isRight;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}
	
	public void setisRight(boolean isRight) {
		this.isRight = isRight;
	}

	public String getAnswer() {
		return answer;
	}
	
	public String toString() {
		return answer +" | "+isRight;
	}

	public boolean isRight() {
		return isRight;
	}
	
	@Override
 	public boolean equals(Object obj) {
		if (this.getClass() == obj.getClass()) {
			Answer answer = (Answer) obj;
			if (this.getAnswer().equalsIgnoreCase(answer.getAnswer()))
				return true;
		}
		return false;
	}

	@Override
	public int compareTo(Answer other) {
		return answer.compareTo(other.answer);
	}
}
