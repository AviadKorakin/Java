package Model;

import java.util.ArrayList;
import java.util.Random;
public class AmericanQuestion extends Question { // American American1
	private static final long serialVersionUID = 1L;
	public static final String A1="More than one correct", A2="None of the above";
	private Set<Answer> answerArr;

	public AmericanQuestion(String questionData) {
		super(questionData);
		answerArr=new Set<>();
		addtheDefaultAnswers();
	}

	public AmericanQuestion(String questionData, Answer A, Answer B, Answer C, Answer D) {// Admin access
		this(questionData);
		addtheDefaultAnswers();
	    addAnswer(A);
		addAnswer(B);
		addAnswer(C);
		addAnswer(D);
	}
	
	public void setValueDefaults()
	{
		settheDefaultAnswers();
		int numOfCorrect=0;
		for(int x=0;x<answerArr.Length();x++)
		{
			if (answerArr.get(x).isRight())
			{
			     numOfCorrect++;
			}
		}
		if(numOfCorrect>1)
		{
			answerArr.update(new Answer(A1,true));
			answerArr.update(new Answer(A2,false));
			return;
		}
		if(numOfCorrect==0)
		{
			answerArr.update(new Answer(A1,false));
			answerArr.update(new Answer(A2,true));
			return;
		}
			answerArr.update(new Answer(A1,false));
			answerArr.update(new Answer(A2,false));
		
	}
	
	public void addtheDefaultAnswers() {
		addAnswer(new Answer(A1,false));
		addAnswer(new Answer(A2,false));
	}
	
	public void settheDefaultAnswers() {
		answerArr.update(new Answer(A1,false));
		answerArr.update(new Answer(A2,false));
	}
	
	public void setValueDefaults(ArrayList<Integer> answersToShow) {
		settheDefaultAnswers();
		int numOfCorrect=0;
		for(int x=0;x<answersToShow.size();x++) {
				if (answerArr.get(answersToShow.get(x)).isRight()) 
				     numOfCorrect++;
		}
		if(numOfCorrect>1) {
			answerArr.update(new Answer(A1,true));
			answerArr.update(new Answer(A2,false));
			return;
		}
		if(numOfCorrect==0) {
			answerArr.update(new Answer(A1,false));
			answerArr.update(new Answer(A2,true));
			return;
		}
		answerArr.update(new Answer(A1,false));
		answerArr.update(new Answer(A2,false));
	}

	public ArrayList<Answer> getAnswerArr() {
        return answerArr.setToArrayList();
    }

	public ptype addAnswer(Answer answer) {
		if(answer.getAnswer()=="")return ptype.EMPTY_VALUE;
		if(answerArr.add(answer))
			return ptype.ADD_SUCCESS;
		return ptype.ANSWER_ALREADY_EXISTS;
	}

	public ptype deleteAnswer(int index) {
		index--;
		if(answerArr.delete(index))
			return ptype.DELETE_SUCCESS;
		return ptype.INDEX_OUT_OF_BOUNDS;
	}

	public ptype updateAnswer(int index, Answer updAnswer) {
		index--;
		if(answerArr.update(index, updAnswer))
			return ptype.UPDATE_SUCCESS;
		return ptype.ANSWER_ALREADY_EXISTS; 
	}
	
	@Override
	public String toString() {
		setValueDefaults();
		StringBuilder buff = new StringBuilder();
		for (int i = 0; i < answerArr.Length(); i++)
			buff.append("\t" + (i + 1) + ") " + (answerArr.get(i)).toString() + "\n");
		return super.toString() + "\n" + buff.toString();
	}
	
	public Answer getRightAnswer(Set<Answer> a) {
		Answer truth=null;
		ArrayList<Answer> answersToShow = a.setToArrayList();
		for(int x=answersToShow.size()-1;x>=0;x--) {
			if (answersToShow.get(x).isRight()) {
				truth = answersToShow.get(x);
				break;
			}
		}
		return truth;
	  }
	
	public Set<Answer> Answersarray(ArrayList<Integer> answersToShow) {
		setValueDefaults(answersToShow);
		Set<Answer> arr = new Set<Answer>();
		if(answersToShow.size()==0) return null;
		for(int x=0;x<answersToShow.size();x++)
			arr.add(answerArr.get(answersToShow.get(x)));
		return arr;
	}

	public int getNumOfAnswers() {
		return answerArr.Length()-2;
	}
	
	public  ArrayList<Integer> autoAnswerArr() {
		ArrayList<Integer> rightArr = new ArrayList<Integer>();
		ArrayList<Integer> wrongArr = new ArrayList<Integer>();
		int r=0, w=0;
		ArrayList<Integer> autoArr = new ArrayList<Integer>();
		autoArr.add(answerArr.Length()-2);
		autoArr.add(answerArr.Length()-1);
		for (int i=0; i<answerArr.Length()-2; i++) {
			if (answerArr.get(i).isRight()) {
				rightArr.add(i);
				r++;
			}
			else {
				wrongArr.add(i);
				w++;
			}
		}
		if ((r == 0) && (w <= 4)) {
			for (int x=0;x<wrongArr.size();x++)
				autoArr.add(wrongArr.get(x));// system output -- less than 4 incorrect , and no true value
		}
		else if ((r > 0) && (w < 4)) { // system output -- less than 4 incorrect , there are true values
			autoArr.add(rightArr.get((new Random()).nextInt(r)));
			for (int i=0; i<w; i++) 
				autoArr.add(wrongArr.get(i));
		}
		else if ((r == 0) && (w > 4)) { // system output -- more than 4 incorrect , and no true value
			ArrayList<Integer> ChosenRandoms = new ArrayList<Integer>();
			randomGenerator(ChosenRandoms,4,w);
			for (int i=0; i<4; i++) {
				autoArr.add(wrongArr.get(ChosenRandoms.get(i)));//error have to fix
			}
		}
		else if ((r > 0) && (w > 3)) { // system output -- more than 3 incorrect , there are true values
			int includeOneCorrect = (new Random()).nextInt(2); // 1 for true
			ArrayList<Integer> ChosenRandoms = new ArrayList<Integer>();
			randomGenerator(ChosenRandoms,4-includeOneCorrect,w);
			if (includeOneCorrect == 1)
				autoArr.add(rightArr.get((new Random()).nextInt(r)));
			for (int i=0; i<ChosenRandoms.size(); i++)
				autoArr.add(wrongArr.get(ChosenRandoms.get(i))); 
		}
		return autoArr;
	}
	
	public static void randomGenerator(ArrayList<Integer> chosenRandoms,int size,int limit) {// randomize input array to int in range 0-limit (both limits excluded)
		 if(size==0)return;
    	 int n;
    	 Random rnd = new Random();
		 do {
			 n = rnd.nextInt(limit);
		 } while(chosenRandoms.contains(n));
	    chosenRandoms.add(n);
	    randomGenerator(chosenRandoms,--size,limit);
     }   
}