package Model;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class Test implements Cloneable {
	private String name="Unsaved";
	private ArrayList<Question> Questions;
	private ArrayList<Set<Answer>> answersToShow;// fix the AmericanQuestion 
	private ArrayList<Integer>pointers;
	
	@SuppressWarnings("unchecked")
	public Test(ArrayList<ArrayList<Integer>> Man, ArrayList<Question> questionArr) throws CloneNotSupportedException {// Man
		if (Man == null)
			return;
		Questions = new ArrayList<Question>();
		pointers = new ArrayList<Integer>();
		answersToShow=new ArrayList<Set<Answer>>();
		for (int i = 0; i < Man.size(); i++) {
			Question toCopy=(Question) questionArr.get(Man.get(i).get(0)).clone();//individual Questions separation
			Questions.add(toCopy);
		}
		Collections.sort(Questions);
		for (int y = 0; y < Questions.size(); y++) {
			for (int x = 0; x < Man.size(); x++) {
				if (Questions.get(y).equals(questionArr.get(Man.get(x).get(0)))) {
					Question CastSaverQ = questionArr.get(Man.get(x).get(0));
					if (CastSaverQ.getClass().getSimpleName().equals("AmericanQuestion")) {
						AmericanQuestion CastSaverA = (AmericanQuestion) CastSaverQ;
						pointers.add(y);
						Man.get(x).remove(0);
						Man.get(x).add(0,CastSaverA.getAnswerArr().size()-2);
						Man.get(x).add(0,CastSaverA.getAnswerArr().size()-1);
						answersToShow.add((Set<Answer>)(CastSaverA.Answersarray(Man.get(x))).clone());//individual answers separation
					}
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	public Test(int questionNum, ArrayList<Question> questionArr) throws CloneNotSupportedException {// Auto
		if (questionNum > questionArr.size()) 
			return;
		Questions = new ArrayList<Question>();
		pointers = new ArrayList<Integer>();
		answersToShow=new ArrayList<Set<Answer>>();
		if (questionNum == questionArr.size()) {
			for (int i = 0; i < questionNum; i++) {
				Question toCopy=(Question) questionArr.get(i).clone();//individual Questions separation
				Questions.add(toCopy);
			}
		}
		else {
			ArrayList<Integer> usedRandoms = new ArrayList<Integer>();
			AmericanQuestion.randomGenerator(usedRandoms, questionNum, questionArr.size());
			for (int i = 0; i < questionNum; i++) {
				Question toCopy=questionArr.get(usedRandoms.get(i));
				Questions.add(toCopy);
			}
		}
		Collections.sort(Questions);
		for (int qIndex = 0; qIndex < Questions.size(); qIndex++) {
			Question CastSaverQ = Questions.get(qIndex);
			if (CastSaverQ.getClass().getSimpleName().equals("AmericanQuestion")) {
				pointers.add(qIndex);
				AmericanQuestion CastSaverA = (AmericanQuestion) CastSaverQ;
				answersToShow.add((Set<Answer>)(CastSaverA.Answersarray(CastSaverA.autoAnswerArr())).clone());//individual answers separation
			}
		}
	}
	
	public void save() throws IOException {
		int qnum = 1;
		Date date = new java.util.Date();// yy_mm_yyyy_e\
		String dateS = (date.getYear() + 1900) + "_" + (date.getMonth() + 1) + "_" + date.getDate() + "_" + date.getSeconds();
		String dirName = "Exams/Test_" + dateS;
		File f = new File(dirName);
		int d = 1;
		String dirNameCopy=dirName;
		while (!f.mkdir()) {
			dirNameCopy = dirName + "(" + d + ")";
			f = new File(dirNameCopy);
			d++;
		}
		dirName=dirNameCopy;
		PrintWriter pwExam = new PrintWriter(dirName + "/exam_" + dateS + ".txt");
		PrintWriter pwSolution = new PrintWriter(dirName + "/solution_" + dateS + ".txt");
		pwExam.println("The Test:\n\n");
		pwSolution.println("The Solution:\n\n");
		String toPrint = "";
		Question CastSaverQ;
		OpenQuestion CastSaverO;
		AmericanQuestion CastSaverA;
		this.name=f.getName();
		for (int y = 0; y < Questions.size(); y++) {
			toPrint = "Question " + (qnum) + ":\n"; // searching for the Question
			CastSaverQ = Questions.get(y);
			if (CastSaverQ.getClass().getSimpleName().equals("OpenQuestion")) {
				CastSaverO = (OpenQuestion) CastSaverQ;
				pwExam.println(toPrint + CastSaverQ.getQuestionData() + "\n");
				pwSolution.println("Answer " + (qnum) + ":\n \t" + CastSaverO.getAnswer() + "\n");
			} 
			else {
				for(int x=0;x<pointers.size();x++) {
					if(y==pointers.get(x)) {
						CastSaverA = (AmericanQuestion) CastSaverQ;
						Answer truth=CastSaverA.getRightAnswer(answersToShow.get(x));
						pwSolution.println("Answer " + (qnum) + ":\n \t" + truth + "\n");
						pwExam.println(toPrint +CastSaverA.getQuestionData()+"\n"+ answersToShow.get(x) + "\n");
						break;
					}
				}
			}
			qnum++;
		}
		pwExam.close();
		pwSolution.close();
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	@Override
	public String toString() {
		StringBuilder string=new StringBuilder();
		//string.append("---------------------------------------------------------------------------------------------- \n");
		string.append(name +"\n");
		int qnum = 1;
		for (int q = 0; q < Questions.size(); q++) {
			Question CastSaverQ = Questions.get(q);
			String toPrint = "Question " + (qnum) + ":\n";
			if (CastSaverQ.getClass().getSimpleName().equals("OpenQuestion"))
				string.append(toPrint + (OpenQuestion) CastSaverQ + "\n");
			if (CastSaverQ.getClass().getSimpleName().equals("AmericanQuestion")) {
				for(int x=0;x<pointers.size();x++) {
					if(q==pointers.get(x)) {
						AmericanQuestion CastSaverA = (AmericanQuestion) CastSaverQ;
						string.append(toPrint +CastSaverA.getQuestionData()+"\n"+ answersToShow.get(x) + "\n");
						break;
					}
				}
			}
			qnum++;
		}
		//string.append("---------------------------------------------------------------------------------------------- \n");
		return string.toString();
	}

	public String getName() {
		return name;
		
	}

	public void setName(String string) {
		this.name=string;
		
	}


}
