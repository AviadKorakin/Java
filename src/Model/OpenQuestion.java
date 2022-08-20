package Model;

public class OpenQuestion extends Question {
	private static final long serialVersionUID = 1L;
	private String answer;
    
    public OpenQuestion(String questionData) {
        super(questionData);
    }
    
    public OpenQuestion(String questionData,String answer) { // Admin access
        this(questionData);
        updateAnswer(answer);
    }
    
    public String getAnswer() {
    	return answer;
    }
    
    public ptype updateAnswer(String answer) {
    	this.answer=answer;
    	return ptype.UPDATE_SUCCESS;
    }

    @Override
    public String toString() {
        return super.toString() + "\t" + answer +"\n";
    }
}
