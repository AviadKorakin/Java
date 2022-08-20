package View;

import Controller.Controller;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class View {
	private int counter=0;
	private  MenuBar menu=new MenuBar();
	private BorderPane mainPane;
	private Button enter=new Button();
	private Button intoController=new Button();
	private Text textOnTop=new Text();
	private Text textOnMiddle=new Text();
	private Text textOnBottom=new Text();
	private TextField inputOnTop=new TextField();
	private TextField inputOnMiddle=new TextField();
	private RadioButton trueRadio=new RadioButton();
	private RadioButton falseRadio=new RadioButton();
	private ComboBox<String> select=new ComboBox<>();
	private ComboBox<String> selector=new ComboBox<>();
	private GridPane pane;
	private MenuButton answersBox= new MenuButton();
	private TextArea bigMsg=new TextArea();
	private TextArea output=new TextArea();
	private TextArea data=new TextArea();
	private Controller c;
	
	public void setController(Controller c) {
		this.c = c;
		c.uploadDatabase();
	}
	
	public void setMenubar()
	{
		 Menu Questions=new Menu("Questions");
		 Menu Answers=new Menu("Answers");
		 Menu Test=new Menu("Tests Creation");
		 menu.getMenus().add(Questions);
		 menu.getMenus().add(Answers);
		 menu.getMenus().add(Test);
		 
		 MenuItem addOpenQuestion=new MenuItem("Add Open Question");
		 MenuItem addAmericanQuestion=new MenuItem("Add American Question");
		 MenuItem UpdateQuestion=new MenuItem("Update Question");
		 MenuItem DeleteQuestion=new MenuItem("Delete Question");
		 Questions.getItems().add(addOpenQuestion);
		 Questions.getItems().add(addAmericanQuestion);
		 Questions.getItems().add(UpdateQuestion);
		 Questions.getItems().add(DeleteQuestion);
		 
		 
		 addOpenQuestion.setOnAction(e -> {
			 refreshDatabaseOutput();
			 globalOutput();
			 inputOpen();
			 pane.getChildren().clear();
			 pane.requestFocus();
			 textOnTop.setText("Enter The Question:");
			 textOnMiddle.setText("Enter The Answer:");
			 enter.setText("Check availability");
			 intoController.setText("Add");
			 inputOnTop.setText("Enter Question inquiry here");
			 inputOnMiddle.setText("Enter Answer inquiry here");
			 pane.add(textOnTop, 0, 0);
			 pane.add(inputOnTop, 0, 1);
			 pane.add(enter, 0, 2);
			 pane.add(textOnMiddle, 0, 3);
			 pane.add(inputOnMiddle, 0, 4);
			 pane.add(intoController, 0, 5);
			 enter.setOnAction(ex -> {
				c.checkOpenAvalibility(inputOnTop.getText());
			 });
			 intoController.setOnAction(ex->
			 {
				 c.addOpenQuestion(inputOnTop.getText(),inputOnMiddle.getText(),output);
			 });
		 });
		 addAmericanQuestion.setOnAction(e -> {
			 refreshDatabaseOutput();
			 counter=0;
			 globalOutput();
			 inputAmerican();
			 pane.getChildren().clear();
			 pane.requestFocus();
			 textOnTop.setText("Enter The Question:");
			 textOnMiddle.setText("Enter The Answer number " +(counter+1)+" :");
			 enter.setText("Check availability");
			 intoController.setText("Add");
			 inputOnTop.setText("Enter Question inquiry here");
			 inputOnMiddle.setText("Enter Answer number inquiry here");
			 trueRadio.setText("true");
			 falseRadio.setText("false");
			 pane.add(textOnTop, 0, 0);
			 pane.add(inputOnTop, 0, 1);
			 pane.add(enter, 0, 2);
			 pane.add(textOnMiddle, 0, 3);
			 pane.add(inputOnMiddle, 0, 4);
			 pane.add(trueRadio, 1, 4);
			 trueRadio.setSelected(true);
			 falseRadio.setSelected(false);
			 pane.add(falseRadio, 2, 4);
			 pane.add(intoController, 0, 5);
			 
			 enter.setOnAction(ex -> {
					c.checkAmericanAvalibility(inputOnTop.getText());
				 });
			 intoController.setOnAction(ex->
			 {
				 if(trueRadio.isSelected())
				 {
					
				 c.addAmericanAnswer(inputOnTop.getText(),inputOnMiddle.getText(),true, output,counter);
				 }
				 else
				 {
				 c.addAmericanAnswer(inputOnTop.getText(),inputOnMiddle.getText(),false, output,counter);
				 }
				 counter++;
				 
				 textOnMiddle.setText("Enter The Answer number " +(counter+1)+" :");
			 });
		 });
	
		 UpdateQuestion.setOnAction(e -> {
			 refreshDatabaseOutput();
			 c.updateSelect(select);
			 globalOutput();
			 questionInput();
			 pane.getChildren().clear();
			 pane.requestFocus();
			 textOnTop.setText("Mark the desired question");
			 textOnMiddle.setText("");
			 textOnBottom.setText("Enter The Update Question query:");
			 inputOnMiddle.setText("Enter new Question inquiry here");
			 intoController.setText("Update");
			 pane.add(textOnTop, 0, 0);
			 pane.add(select, 0, 1);
			 pane.add( textOnMiddle, 0, 2);
			 pane.add( textOnBottom, 0, 3);
			 pane.add( inputOnMiddle, 0,4);
			 pane.add(intoController, 0,5);
			 
			 intoController.setOnAction(ex -> {
				 c.updateQuestion(select,inputOnMiddle.getText(),output);
			 });
			 
		 });
	
		 DeleteQuestion.setOnAction(e -> {
			 refreshDatabaseOutput();
			 globalOutput();
			 questionInput();
			 c.updateSelect(select);
			 pane.getChildren().clear();
			 pane.requestFocus();
			 textOnTop.setText("Mark the desired question number");
			 textOnMiddle.setText("");
			 intoController.setText("Delete");
			 pane.add(textOnTop, 0, 0);
			 pane.add(select, 0, 1);
			 pane.add( textOnMiddle, 0, 2);
			 pane.add(intoController, 0, 3);
			 intoController.setOnAction(ex->
			 {
			 c.deleteQuestion(select,output);
			 });
		 });
			  
		 MenuItem AddAnswer=new MenuItem("Add Answer");
		 MenuItem UpdateAnswer=new MenuItem("Update Answer");
		 MenuItem DeleteAnswer=new MenuItem("Delete Answer");
		 Answers.getItems().add(AddAnswer);
		 Answers.getItems().add(UpdateAnswer);
		 Answers.getItems().add(DeleteAnswer);
		 
		 
		 AddAnswer.setOnAction(e -> {
			 refreshDatabaseOutput();
			 globalOutput();
			 addOpenAnswerInput();
			 c.updateSelect(select);
			 pane.getChildren().clear();
			 pane.requestFocus();
			 textOnTop.setText("Mark the desired question number");
			 textOnMiddle.setText("");
			 intoController.setText("Add");
			 inputOnMiddle.setText("Add Answer inquiry here");
			 pane.add(textOnTop, 0, 0);
			 pane.add(select, 0, 1);
			 pane.add(textOnMiddle, 0, 2);
			 pane.add(inputOnMiddle, 0, 3);
			 pane.add(trueRadio, 1,3);
			 trueRadio.setSelected(true);
			 falseRadio.setSelected(false);
			 pane.add(falseRadio, 2, 3);
			 pane.add(intoController, 0, 4);
			 
			 select.setOnAction(ex -> {
				 int qindex = select.getSelectionModel().getSelectedIndex();
				 if (c.selectedAmerican(qindex)) {
					 addAmericanAnswerInput();
					 c.answerSelector(selector, qindex);
				 }
				 else {
					 addOpenAnswerInput();
				 }
			 });
			 
			 intoController.setOnAction(ex -> {
				 if(trueRadio.isSelected())
				 {
					
					 c.addAmericanAnswer(select,output,inputOnMiddle,true);
				 }
				 else
				 {
					 c.addAmericanAnswer(select,output,inputOnMiddle,false);
				 }
			 });
		 });
		 
		 UpdateAnswer.setOnAction(e -> {
			 refreshDatabaseOutput();
			 globalOutput();
			 updateOpenAnswerInput();
			 intoController.setDisable(true);
			 inputOnMiddle.setDisable(true);
			 c.updateSelect(select);
			 pane.getChildren().clear();
			 pane.requestFocus();
			 textOnTop.setText("Mark the desired question number");
			 textOnMiddle.setText("");
			 textOnBottom.setText("The Answers are:");
			 intoController.setText("Update");
			 inputOnMiddle.setText("Updated Answer inquiry here");
			 pane.add(textOnTop, 0, 0);
			 pane.add(select, 0, 1);
			 pane.add(textOnMiddle, 0, 2);
			 pane.add(textOnBottom, 0, 3);
			 pane.add(selector, 0, 4);
			 pane.add(inputOnMiddle, 0, 5);
			 pane.add(trueRadio, 2, 5);
			 trueRadio.setSelected(true);
			 falseRadio.setSelected(false);
			 pane.add(falseRadio, 3, 5);
			 pane.add(intoController, 0, 6);

			 select.setOnAction(ex -> {
				 int qindex = select.getSelectionModel().getSelectedIndex();
				 if (c.selectedAmerican(qindex)) {
					 updateAmericanAnswerInput();
					 c.answerSelector(selector, qindex);
				 }
				 else {
					 updateOpenAnswerInput();
				 }
			 });
			 
			 intoController.setOnAction(ex -> {
				c.updateAnswer(select, selector, output, inputOnMiddle, trueRadio); 
			 });
			 
		 });
		 
		 DeleteAnswer.setOnAction(e -> {
			 refreshDatabaseOutput();
			 globalOutput();
			 deleteAnswerInputOpen();
			 c.updateSelect(select);
			 pane.getChildren().clear();
			 pane.requestFocus();
			 textOnTop.setText("Mark the desired question number");
			 textOnMiddle.setText("");
			 textOnBottom.setText("The Answers are:");
			 intoController.setText("Delete");
			 pane.add(textOnTop, 0, 0);
			 pane.add(select, 0, 1);
			 pane.add(textOnMiddle, 0, 2);
			 pane.add(textOnBottom, 0, 3);
			 pane.add(selector, 0, 4);
			 pane.add(intoController, 1, 4);
			 
			 select.setOnAction(ex -> {
				 int qindex = select.getSelectionModel().getSelectedIndex();
				 if (c.selectedAmerican(qindex)) {
					 deleteAnswerInputAmerican();
					 c.answerSelector(selector, qindex);
				 }
				 else {
					 deleteAnswerInputOpen();
				 }
			 });
			 
			 intoController.setOnAction(ex -> {
				 c.deleteAnswer(select, selector, output); 
			 });
		 });
		 
		 
		 MenuItem CreateAuto=new MenuItem("Create Automatic Test");
		 MenuItem CreateMan=new MenuItem("Create Manual Test");
		 MenuItem DupeTests=new MenuItem("Duplicate Test");
		 Test.getItems().add(CreateAuto);
		 Test.getItems().add(CreateMan);
		 Test.getItems().add(DupeTests);
		 
		 CreateAuto.setOnAction(e -> {
			 refreshDatabaseOutput();
			 globalOutput();
			 autoTestInput();
			 pane.getChildren().clear();
			 pane.requestFocus();
			 textOnTop.setText("Enter Amount of Questions u want in your test");
			 intoController.setText("Create");
			 bigMsg.setText("");
			 bigMsg.setPrefHeight(300);
			 pane.add(textOnTop, 0, 0);
			 pane.add(select, 0, 1);
			 pane.add(intoController, 0, 2);
			 pane.add(bigMsg, 0, 3);
			 
			 select.setOnAction(ex -> {
				 
			 });
			 intoController.setOnAction(ex -> {
				int qnum = select.getSelectionModel().getSelectedIndex()+1;
				c.createAutoTest(bigMsg, qnum, output); 
			 });
		 });
		 
		 CreateMan.setOnAction(e -> {
			 refreshDatabaseOutput();
			 c.clearMan();
			 globalOutput();
			 ManTestInput();
			 pane.getChildren();
			 pane.getChildren().clear();
			 pane.requestFocus();
			 textOnTop.setText("Select Questions for Man Test, when finish press Submit");
			 textOnMiddle.setText("Select Answers");
			 enter.setText("Add");
			 intoController.setText("Submit");
			 bigMsg.setText("");
			 pane.add(textOnTop, 0, 0);
			 pane.add(select, 0, 1);
			 pane.add(textOnMiddle, 0,2);
			 pane.add(answersBox, 0, 4);
			 pane.add(enter, 0, 5);
			 pane.add(intoController, 0, 7);
			 pane.add(bigMsg, 0, 8);
			 
			 select.setOnAction(ex -> {
				 globalOutput();
				 int qindex = select.getSelectionModel().getSelectedIndex();
				 if (c.selectedAmerican(qindex)) {
					 c.answerSelector(answersBox, qindex);
					 answersBox.setDisable(false);
				 }
				 else
				 {
					 answersBox.setDisable(true);
				 }
				 enter.setDisable(false);
			 });
			 enter.setOnAction(ex -> {
				 int qindex = select.getSelectionModel().getSelectedIndex();
				 if (c.selectedAmerican(qindex)) {
					c.addtoManTest(answersBox, qindex,output);
				 }
				 else
				 {
					 c.addtoManTest(qindex,output);
				 }
				 intoController.setDisable(false);
				 
			 });
			 intoController.setOnAction(ex -> {
				 c.createManTest(bigMsg, output);
				 c.clearMan();
			 });
			 
		 });
		 DupeTests.setOnAction(e -> {
			 refreshDatabaseOutput();
			 globalOutput();
			 dupTestInput();
			 pane.getChildren().clear();
			 pane.requestFocus();
			 textOnTop.setText("Select Test");
			 intoController.setText("Duplicate");
			 bigMsg.setText("");
			 pane.add(textOnTop, 0, 0);
			 pane.add(select, 0, 1);
			 pane.add(intoController, 0, 2);
			 c.testSelection(select);
			 
			 select.setOnAction(ex -> {
				 c.showTestOnScreen(select, data);
			});
			 intoController.setOnAction(ex -> {
				 c.duplicateSelectedTest(select, output);
			 });
		 });
		 
		 
		 
		 bigMsg.setEditable(false);
		 bigMsg.setStyle("-fx-text-fill:#00008b");
		 trueRadio.setText("true");
		 falseRadio.setText("false");
		 
		 
		 inputOnTop.setOnMouseClicked(ex -> {
			 inputOnTop.setText("");
		 });
		 answersBox.setOnMouseEntered(ex -> {
			 answersBox.show();
		 });
		 answersBox.setOnMousePressed(ex -> {
			 answersBox.show();
		 });
		 inputOnMiddle.setOnMouseClicked(ex -> {
			 inputOnMiddle.setText("");
		 });
		 trueRadio.setOnMouseClicked(ex -> {
			 falseRadio.setSelected(false);
		 });
		 falseRadio.setOnMouseClicked(ex -> {
			 trueRadio.setSelected(false);
		 });
		 inputOnTop.setMinWidth(500);
		 inputOnMiddle.setMinWidth(500);
		 
		
	}


	public View(Stage primaryStage)
	{
		 pane = new GridPane();
		 pane.setHgap(5);
		 pane.setVgap(5);
        mainPane = new BorderPane();
        mainPane.setCenter(pane);
		setMenubar();
		mainPane.setTop(menu);
		mainPane.setRight(data);
		data.setEditable(false);
		data.setMaxWidth(300);
		mainPane.setBottom(output);
		output.setMaxHeight(100);
		//output.setMaxWidth(200);
		output.setEditable(false);
		output.setStyle("-fx-text-fill: rgb(255,0,0)");
		data.setStyle("-fx-background-color:black;");
		    Scene scene = new Scene(mainPane);
		    primaryStage.setTitle("Test Generator"); // Set the stage title
		    primaryStage.setScene(scene); // Place the scene in the stage
		    primaryStage.setWidth(1000);
		    primaryStage.setHeight(600);
		    primaryStage.setResizable(false);
		    // Display the stage
		    primaryStage.show();
		    
		primaryStage.setOnCloseRequest(e -> {
			 c.saveDatabase();
		});
	}

	public void updateOpeninput()
	{
		inputOnTop.setDisable(true);
		enter.setDisable(true);
		inputOnMiddle.setDisable(false);
		intoController.setDisable(false);
	}
	
	public void inputOpen()
	{
		inputOnTop.setDisable(false);
		enter.setDisable(false);
		inputOnMiddle.setDisable(true);
		intoController.setDisable(true);
	}

	public void inputAmerican()
	{
		inputOpen();
		trueRadio.setDisable(true);
		falseRadio.setDisable(true);
	}

	public void updateInputAmerican()
	{
		updateOpeninput();
		trueRadio.setDisable(false);
		falseRadio.setDisable(false);
		
	}

	public void questionInput()
	{
		inputOnTop.setDisable(false);
		enter.setDisable(false);
		inputOnMiddle.setDisable(false);
		intoController.setDisable(false);
		trueRadio.setDisable(false);
		falseRadio.setDisable(false);
	}

	public void deleteAnswerInputOpen() {
		select.setDisable(false);
		selector.setDisable(true);
		intoController.setDisable(true);
	}
	
	public void deleteAnswerInputAmerican() {
		select.setDisable(false);
		selector.setDisable(false);
		intoController.setDisable(false);
	}

	public void updateOpenAnswerInput() {
		select.setDisable(false);
		inputOnMiddle.setDisable(false);
		intoController.setDisable(false);
		selector.setDisable(true);
		trueRadio.setDisable(true);
		falseRadio.setDisable(true);
		trueRadio.setSelected(true);
	}
	
	public void updateAmericanAnswerInput() {
		select.setDisable(false);
		inputOnMiddle.setDisable(false);
		intoController.setDisable(false);
		selector.setDisable(false);
		trueRadio.setDisable(false);
		falseRadio.setDisable(false);
		trueRadio.setSelected(true);
	}

	public void addOpenAnswerInput() {
		select.setDisable(false);
		inputOnMiddle.setDisable(true);
		intoController.setDisable(true);
		trueRadio.setDisable(true);
		falseRadio.setDisable(true);
	}

	public void addAmericanAnswerInput() {
		select.setDisable(false);
		inputOnMiddle.setDisable(false);
		intoController.setDisable(false);
		trueRadio.setDisable(false);
		falseRadio.setDisable(false);
	}
	
	public void globalOutput()
	{
		output.setText("");
	}
	
	public void refreshDatabaseOutput() {
		data.setText(c.refreshDatabase());
	}
	
	public void autoTestInput() {
		intoController.setDisable(false); 
		select.setDisable(false);
		bigMsg.clear();
		c.qnumSelection(select);
	}

	public void dupTestInput() {
		select.setDisable(false);
		intoController.setDisable(false);
		c.testSelection(select);
	}
	
	public void ManTestInput() {
		answersBox.setDisable(true);
		 enter.setDisable(true);
		 intoController.setDisable(true);
		c.updateSelect(select);
		
	}
}
