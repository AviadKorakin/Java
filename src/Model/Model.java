package Model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class Model   {
	private static final String databaseFileName = "Database/" + "Default.txt";
	private ArrayList<Test> testArr = new ArrayList<>();	
	private QuestionHandler Questions=new QuestionHandler();
	
	public void addTest(Test test) {
		testArr.add(test);
	}
	
	public int getNumOfTests() {
		return testArr.size();
	}
	
	public ArrayList<Test> getTestArr() {
		return testArr;
	}
	
	public void setTestArr(ArrayList<Test> testArr) {
		this.testArr = testArr;
	}
	
	public QuestionHandler getQuestions() {
		return Questions;
	}
	
	public void setQuestions(QuestionHandler questions) {
		Questions = questions;
	}
	
	public String save(QuestionHandler qh) {
		try {
			ObjectOutputStream o = new ObjectOutputStream(new FileOutputStream(databaseFileName));
			o.writeObject(qh);
			o.close();
			return "Save sucess";
		} catch (FileNotFoundException ex) {
			return "Illegal path sry..., plz enter path in proper form: name without slash or dots" ;
				} 
		 catch (IOException ex) {
			return "Machine error:\n" + ex + "plz try again";
		}
	}
	
	public String read() {
		StringBuilder query= new StringBuilder();
		QuestionHandler qh = new QuestionHandler();
		try {
			ObjectInputStream i = new ObjectInputStream(new FileInputStream(databaseFileName));
			qh = (QuestionHandler) i.readObject();
			i.close();
			setQuestions(qh);
			query.append("Sucess");
		} catch (FileNotFoundException ex) {
			query.append(ex + "\n");
			query.append("Incorrect path" + "\n");
		} catch (IOException ex) {
			query.append(ex + "\n");
			query.append("Incorrect path" + "\n");
		} catch (ClassNotFoundException ex) {
			query.append(ex + "\n");
			query.append("Incorrect Class- Wrong file, Quitting" + "\n");
			
		}
		return query.toString();
	}

	public void dupTest(int index) throws CloneNotSupportedException, IOException {
		Test copyTest=(Test)testArr.get(index).clone();
		copyTest.setName(copyTest.getName()+" Copy");
		testArr.add(copyTest);
	}
	
	
	
	
	
	
	

}
