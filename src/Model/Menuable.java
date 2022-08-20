package Model;

public  interface Menuable {
		// representing the basic functions that needs to be inside main,to run the program.
		public  String addQuestion();
		public String updateQuestion();
		public String addAnswer();
		public String deleteAnswer();
		public String updateAnswer();
		public String manualTest();
		public  String autoTest();
		public String isDatabaseEmpty();
		public String addDatabase();
		public String deleteDatabase();
		public String readData();
		public String saveData();
	}

