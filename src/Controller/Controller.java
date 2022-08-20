package Controller;

import java.io.IOException;
import java.util.ArrayList;

import Model.AmericanQuestion;
import Model.Answer;
import Model.Model;
import Model.OpenQuestion;
import Model.Question;
import Model.QuestionHandler;
import Model.Test;
import Model.ptype;
import View.View;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuButton;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class Controller {
	private Model model;
	private View view;
	private AmericanQuestion temp;
	private ArrayList<ArrayList<Integer>> Man=new ArrayList<>();

	public Controller(Model model, View view) {
		this.model = model;
		this.view = view;
		view.setController(this);
	}

	public void checkOpenAvalibility(String inputOnTop) {
		OpenQuestion q = new OpenQuestion(inputOnTop);
		if (!model.getQuestions().isQuestionExist(q)) {
			view.updateOpeninput();

		} else {
			view.inputOpen();
		}

	}

	public void addOpenQuestion(String inputOnTop, String inputOnMiddle, TextArea Output) {
		OpenQuestion q = new OpenQuestion(inputOnTop);
		ptype A = model.getQuestions().addQuestion(q);
		if (A != ptype.ADD_SUCCESS) {
			view.inputOpen();
			Output.setText(A.toString());
			return;
		}
		q.updateAnswer(inputOnMiddle);
		Output.setText(q.toString());
		view.inputOpen();
		view.refreshDatabaseOutput();
		return;

	}

	public void checkAmericanAvalibility(String inputOnTop) {
	AmericanQuestion q = new AmericanQuestion(inputOnTop);
	if (!model.getQuestions().isQuestionExist(q)) {
		view.updateInputAmerican();
		return ;
		
	} 
		view.inputAmerican();
	
}

	public void addAmericanQuestion(String inputOnTop, TextArea Output) {
		AmericanQuestion q = new AmericanQuestion(inputOnTop);
		ptype A = model.getQuestions().addQuestion(q);
		if (A != ptype.ADD_SUCCESS) {
			view.inputAmerican();
			temp= null;
		}

		Output.setText(A.toString());
		temp= q;
	}

	public void addAmericanAnswer(String inputOnTop,String inputOnMiddle,boolean truth, TextArea output,int counter)
	{
		if(counter==0)addAmericanQuestion(inputOnTop,output);
		if(counter>=0)
		{
	    if(temp!=null)
	    {
	    	Answer a=new Answer(inputOnMiddle,truth);
	    	ptype A= temp.addAnswer(a);
			if (A == ptype.ADD_SUCCESS) {
				output.setText(temp.toString());
				view.refreshDatabaseOutput();
				return;
			}

			output.setText(A.toString());
	    }
		view.refreshDatabaseOutput();
		}
	}

	public void updateSelect(ComboBox<String> cmb) {
		cmb.getItems().clear();
		QuestionHandler qh=model.getQuestions();
		ArrayList<Question> a=qh.getQuestionArr();
		for(int x=0;x<qh.getTotalQuestionNum();x++)
			cmb.getItems().add(a.get(x).toString());
	}

	public void updateQuestion(ComboBox<String> select,String inputOnMiddle, TextArea output) {
		ptype A=model.getQuestions().updateQuestion(select.getSelectionModel().getSelectedIndex()+1, inputOnMiddle);
		if(A==ptype.UPDATE_SUCCESS)
		{
			updateSelect(select);
		}
		output.setText(A.toString());
		view.refreshDatabaseOutput();
	}

	public void deleteQuestion(ComboBox<String> select, TextArea output) {
		ptype A=model.getQuestions().deleteQuestion(select.getSelectionModel().getSelectedIndex()+1);
		if(A==ptype.DELETE_SUCCESS)
		{
			updateSelect(select);
		}
		output.setText(A.toString());
		view.refreshDatabaseOutput();
	}

	public void addAmericanAnswer(ComboBox<String> select, TextArea output, TextField inputOnMiddle, boolean b) {
		ptype A=model.getQuestions().addAmericanAnswer(select.getSelectionModel().getSelectedIndex()+1,inputOnMiddle.getText(),b);
		if(A==ptype.ADD_SUCCESS)
		{
			updateSelect(select);
		}
		output.setText(A.toString());
		view.refreshDatabaseOutput();
	}

	public boolean selectedAmerican(int qindex) { 
		if (qindex < 0)
			return false;
		if (model.getQuestions().getQuestionArr().get(qindex).getClass().getSimpleName().equals("AmericanQuestion"))
			return true;
		return false;
	}
	
	public void answerSelector(ComboBox<String> cmb, int qindex) {
		cmb.getItems().clear();
		QuestionHandler qh = model.getQuestions();
		ArrayList<Question> questions = qh.getQuestionArr();
		AmericanQuestion q = (AmericanQuestion) questions.get(qindex);
		ArrayList<Answer> answers = q.getAnswerArr();
		for(int i=0; i<q.getNumOfAnswers(); i++)
			cmb.getItems().add(answers.get(i).toString());
	}
	
	public void answerSelector(MenuButton answersBox, int qindex) {
		answersBox.getItems().clear();
		QuestionHandler qh = model.getQuestions();
		ArrayList<Question> questions = qh.getQuestionArr();
		AmericanQuestion q = (AmericanQuestion) questions.get(qindex);
		ArrayList<Answer> answers = q.getAnswerArr();
		for(int i=0; i<q.getNumOfAnswers(); i++)
			answersBox.getItems().add(new CheckMenuItem(answers.get(i).toString()));
			
		}
		
	public void addtoManTest(MenuButton answersBox,int qindex,TextArea output)
	{
		if(checkForExistColMan(qindex))
		{
			output.setText(model.getQuestions().getQuestionArr().get(qindex)+ " is already in use!");
			return;
		}
		ArrayList<Integer>a= new ArrayList<>();
		a.add(qindex);
		for(int i=0; i<answersBox.getItems().size(); i++)
		{
			if(((CheckMenuItem)answersBox.getItems().get(i)).isSelected())
				a.add(i);
		}
		Man.add(a);
		output.setText("Question added");
	}
	
	public void addtoManTest(int qindex,TextArea output) {
		if(checkForExistColMan(qindex))
		{
			output.setText(model.getQuestions().getQuestionArr().get(qindex)+ " is already in use!");
			return;
		}
		ArrayList<Integer>a= new ArrayList<>();
		a.add(qindex);
		Man.add(a);
		output.setText("Question added");
	}
	
	public void clearMan() {
		Man.clear();
	}
	
	public boolean checkForExistColMan(int qindex) {
		for(int x=0; x<Man.size();x++)
		{
			if(Man.get(x).get(0)==qindex)
			{
				return true;
			}
		}
		return  false;
	}

	public void deleteAnswer(ComboBox<String> select, ComboBox<String> selector, TextArea output) {
		int qindex = select.getSelectionModel().getSelectedIndex();
		int aindex = selector.getSelectionModel().getSelectedIndex() +1;
		
		if (qindex<0 || aindex<0) 
			return;
		
		ptype A=((AmericanQuestion) model.getQuestions().getQuestionArr().get(qindex)).deleteAnswer(aindex);
		if (A == ptype.DELETE_SUCCESS) {
			updateSelect(select);
			answerSelector(selector, qindex);
		}
		output.setText(A.toString());
		view.refreshDatabaseOutput();
	}

	public void updateAnswer(ComboBox<String> select, ComboBox<String> selector, TextArea output, TextField inputOnMiddle, RadioButton trueRadio) {
		int qindex = select.getSelectionModel().getSelectedIndex();
		int aindex = selector.getSelectionModel().getSelectedIndex() +1;
		ptype A;
		
		if (selector.isDisabled()) {
			A = ((OpenQuestion) model.getQuestions().getQuestionArr().get(qindex)).updateAnswer(inputOnMiddle.getText());
			output.setText(A.toString());
		}
		else if (aindex != 0) {
			Answer a = new Answer(inputOnMiddle.getText(), trueRadio.isSelected());
			A = ((AmericanQuestion) model.getQuestions().getQuestionArr().get(qindex)).updateAnswer(aindex, a);
			output.setText(A.toString());
		}
		else {
			output.setText("No answer selected");
		}
		updateSelect(select);
		view.refreshDatabaseOutput();
	}

	public String refreshDatabase() {
		return model.getQuestions().toString();
	}
	
	public void saveDatabase() {
		model.save(model.getQuestions());
	}
	
	public void uploadDatabase() {
		model.read();
		view.refreshDatabaseOutput();
	}
	public void qnumSelection(ComboBox<String> select) {
		select.getItems().clear();
		int qnum = model.getQuestions().getTotalQuestionNum();
		
		for (int i=1; i<=qnum; i++)
			select.getItems().add(String.valueOf(i));
	}

	public void createAutoTest(TextArea bigMsg, int qnum, TextArea output) {
		if (qnum == 0) {
			output.setText("not enough questions");
			return;
		}
		try {
			Test test = model.getQuestions().createAutoTest(qnum);
			model.addTest(test);
			test.save();
			bigMsg.setText(test.toString());
			output.setText("test created successfully");
		} catch (CloneNotSupportedException | IOException e) {
			e.printStackTrace();
			output.setText("an error occurred");
		}
	}
	
	public void createManTest(TextArea bigMsg, TextArea output) {
		try {
			Test test = model.getQuestions().createManTest(Man);
			model.addTest(test);
			test.save();
			bigMsg.setText(test.toString());
			output.setText("test created successfully");
		} catch (CloneNotSupportedException | IOException e) {
			e.printStackTrace();
			output.setText("an error occurred");
		}
	}
	
	public void testSelection(ComboBox<String> select) {
		select.getItems().clear();
		if (model.getNumOfTests() == 0)
			return;
		
		for (int i=0; i<model.getNumOfTests(); i++) 
			select.getItems().add(model.getTestArr().get(i).getName());
	}

	public void showTestOnScreen(ComboBox<String> select, TextArea data) {
		int index = select.getSelectionModel().getSelectedIndex();
		if (index >= 0)
			data.setText(model.getTestArr().get(index).toString());
	}
	
	public void duplicateSelectedTest(ComboBox<String> select, TextArea output) {
		int index = select.getSelectionModel().getSelectedIndex();
		if (index >= 0) {
			try {
				model.dupTest(index);
				output.setText("duplcate successfully");
				testSelection(select);
			} catch (CloneNotSupportedException e) {
				output.setText("duplication failed");
			} catch (IOException e) {
				output.setText("duplicated test not saved");
			}
		}	
	}

}














