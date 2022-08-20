package Model;

/**
* Project 2
*
* @author Aviad Korakin-318383544, Amit Biton-315176859
* @version 07/05/2022
*/
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Scanner;

/*
 * For multiple subjects just create a new QuestionHandler for each subject
 */

public class Main {
	private static Scanner s = new Scanner(System.in);
	public static String databaseFileName = "Database/" + "Default.txt";
	private static ArrayList<Test> testArr = new ArrayList<>();

	public static int validInt() {
		int x = 0;// set for return its will actually will never occurs only to override the
					// complier issues.
		boolean flag = false;
		do {
			try {
				x = Integer.parseInt(s.nextLine());
				flag = true;
			} catch (NumberFormatException ex) {
				System.out.println("Illegal input plz try again,Enter Integer plz");
			}
		} while (!flag);
		return x;
	}

	public static String validYesNo() {
		System.out.println(" Choose - (yes/no)");
		String a;
		a = s.nextLine();
		if (a.equalsIgnoreCase("no") || a.equalsIgnoreCase("yes"))
			return a;
		boolean flag = false;
		do {
			System.out.println("Incorrect input plz Enter (yes/no)");
			a = s.nextLine();
			if (a.equalsIgnoreCase("no") || a.equalsIgnoreCase("yes"))
				flag = true;

		} while (!flag);
		return a;
	}

	public static void save(QuestionHandler qh) {
		String a;
		boolean flag = true;
		boolean flag2 = true;
		System.out.println("Do you want to save as " + databaseFileName + " ?");
		if (validYesNo().equalsIgnoreCase("no")) {
			System.out.println("Write down the subject you want to save as");
			databaseFileName = "Database/" + s.nextLine() + ".txt";
		}
		do {
			try {
				ObjectOutputStream o = new ObjectOutputStream(new FileOutputStream(databaseFileName));
				o.writeObject(qh);
				o.close();
				System.out.println("Save sucess");
				flag = false;
			} catch (FileNotFoundException ex) {
				System.out.println("Illegal path sry..., plz enter path in proper form: name without slash or dots");
				do {
					System.out.println("Write down the subject you want to save as");
					a = s.nextLine();
					File f = new File("Database/" + a + ".txt");
					if (f.exists()) {
						System.out.println("The File is already exists do u want to override it?");
						if (validYesNo().equalsIgnoreCase("yes"))
							flag2 = false;
						else 
							System.out.println("Enter new name plz");
					} 
					else
						flag2 = false;
				} while (flag2);
				databaseFileName = "Database/" + a + ".txt";
			} catch (IOException ex) {
				System.out.println("Machine error:\n" + ex + "plz try again");
				flag = false;
			}
		} while (flag);
	}

	public static QuestionHandler read() {
		QuestionHandler qh = new QuestionHandler();
		String a;
		boolean flag = true;
		boolean newFile = false;
		String[] pathnames;
		File f = new File("Database");
		pathnames = f.list();
		if (pathnames.length != 0) {
			System.out.println("Choose one of the subjects below");
			for (String pathname : pathnames) 
				System.out.println(pathname.split(".txt")[0]);
		}
		do {
			System.out.println("Write down the subject you want to use");
			a = s.nextLine();
			if (pathnames.length != 0) {
				for (String pathname : pathnames) {
					if ((pathname.split(".txt")[0]).equalsIgnoreCase(a)) {
						a = pathname;
						flag = false;
					}
				}
			}
			if (flag) {
				System.out.println("Do you want to create new Database with this subject?");
				if (validYesNo().equalsIgnoreCase("yes")) {
					a += ".txt";
					flag = false;
					newFile = true;
				}
			}
		} while (flag);
		flag = true;
		databaseFileName = "Database/" + a;
		StringBuilder problem = new StringBuilder();
		if (!newFile) {
			do {
				try {
					ObjectInputStream i = new ObjectInputStream(new FileInputStream(databaseFileName));
					qh = (QuestionHandler) i.readObject();
					i.close();
					return qh;
				} catch (FileNotFoundException ex) {
					problem.append(ex + "\n");
					problem.append("Incorrect path" + "\n");
				} catch (IOException ex) {
					problem.append(ex + "\n");
					problem.append("Incorrect path" + "\n");
				} catch (ClassNotFoundException ex) {
					problem.append(ex + "\n");
					problem.append("Incorrect Class- Wrong file, Quitting" + "\n");
					return qh;
				}
				if (flag) {
					System.out.println(problem);
					System.out.println("Enter new Database Subject");
					a = s.nextLine();
					a += ".txt";
					databaseFileName = "Database/" + a;
				}
			} while (flag);

		} 
		else
			System.out.println("Creating new Database");
		return qh;
	}

	
	public static void main(String[] args) {
		boolean loop = true;
		int option = 0;
		QuestionHandler qh = new QuestionHandler();
		while (loop) {
			System.out.println("Choose an option:\n" 
					+ "\t1  - Show question database\n"
					+ "\t2  - Add question and answer\n" 
					+ "\t3  - Update question\n" 
					+ "\t4  - Update answer\n"
					+ "\t5  - Delete american question answer\n" 
					+ "\t6  - Add american question answer\n"
					+ "\t7  - Create test manually\n"
					+ "\t8  - Create test automaticly\n"
					+ "\t9  - duplicate exam \n" 
					+ "\t10 - Load Database\n"
					+ "\t11 - Delete Database\n" 
					+ "\t12 - Exit menu");
			option = validInt();

			switch (option) {
			case 1:
				if (!IsDatabaseEmpty(qh))
					System.out.println(qh);
				break;
			case 2:
				addQuestion(qh);
				break;
			case 3:
				if (!IsDatabaseEmpty(qh))
					updateQuestion(qh);
				break;
			case 4:
				if (!IsDatabaseEmpty(qh))
					updateAnswer(qh);
				break;
			case 5:
				if (!IsDatabaseEmpty(qh))
					deleteAnswer(qh);
				break;
			case 6:
				if (!IsDatabaseEmpty(qh))
					addAnswer(qh);
				break;
			case 7:
				if (!IsDatabaseEmpty(qh)) {
					manualTest(qh);
				}
				break;
			case 8:
				if (!IsDatabaseEmpty(qh)) {
					autoTest(qh);
				}
				break;
			case 9:
				dupTest();
				break;
			case 10:
				AddDatabase(qh);
				break;
			case 11:
				DeleteDatabase();
				break;
			case 12:
				System.out.println("Would you like to save?");
				if (validYesNo().equalsIgnoreCase("yes"))
					if (!IsDatabaseEmpty(qh))
					save(qh);
				loop = false;
				break;

			default:
				System.out.println("Not an option number!\n");
				break;
			}
		}
	}

	
	public static void dupTest() {
		if(testArr.size()==0){
			System.out.println("Tests Database is empty");
			return;
		}
		System.out.println("Tests list:");
		for (Test t : testArr) 
			System.out.println(t.getName());
		System.out.println("Choose one of these tests");
		String name = s.nextLine();
		for (Test t : testArr) {
			if (name.equals(t.getName())) {
				try {
					Test CopyTest=(Test)t.clone();
					CopyTest.setName(CopyTest.getName()+" Copy");
					testArr.add(CopyTest);
					System.out.println(t);
					System.out.println(CopyTest);
					return;
				} catch (CloneNotSupportedException e) {
					System.out.println("not clonable");
				}
			}
		}

	}

	
	public static void autoTest(QuestionHandler qh) {
		System.out.println("How many questions do you want in this test? " + "Choose 1-" + qh.getTotalQuestionNum());
		int qnum = validInt();
		try {
			Test test = qh.createAutoTest(qnum);
			testArr.add(test);
			test.save();
			System.out.println(test);
		} catch (CloneNotSupportedException e) {
			System.out.println("Not supporting clonable");
		} catch (IOException e) {
			System.out.println(e);
			System.out.println("Sry can't save the test");
		}
	}

	
	public static void DeleteDatabase() {
		String a;
		boolean flag = true;
		File f = new File("Database");
		System.out.println("Enter Database to delete");
		String[] pathnames = f.list();
		if (pathnames.length != 0) {
			System.out.println("Choose one of the subjects below");
			for (String pathname : pathnames) 
				System.out.println(pathname.split(".txt")[0]);
			do {
				System.out.println("Write down the subject you want to use");
				a = s.nextLine();
				if (pathnames.length != 0) {
					for (String pathname : pathnames) {
						if ((pathname.split(".txt")[0]).equalsIgnoreCase(a)) {
							a = pathname;
							flag = false;
						}
					}
				}
				if (flag)
					System.out.println("Make sure u choose one from the list!");
			} while (flag);
			f = new File("Database/" + a);
			f.delete();
			System.out.println("Deleted sucessfully!");
		} else
			System.out.println("Cant delete from empty directory.");
	}

	
	public static void addQuestion(QuestionHandler qh) {
		System.out.println("Enter question:");
		String qData = s.nextLine();
		System.out.println("\nIs That an American or Open question? (Input:Open/American)");
		String qType = s.nextLine();
		if (qType.equalsIgnoreCase("Open")) {
			OpenQuestion q = new OpenQuestion(qData);
			ptype A = qh.addQuestion(q);
			if (A == ptype.ADD_SUCCESS) {
				System.out.println("Enter Answer:");
				q.updateAnswer(s.nextLine());
			}
			System.out.println(A);
		} 
		else if (qType.equalsIgnoreCase("American")) {
			AmericanQuestion q = new AmericanQuestion(qData);
			ptype A = qh.addQuestion(q);
			if (A == ptype.ADD_SUCCESS) {
				String answer;
				boolean boolCorrect;
				System.out.println("To Stop Entering Answers Press q");
				do {
					System.out.println("Enter Answer: *Enter q to STOP*");
					answer = s.nextLine();
					if (answer.equalsIgnoreCase("q"))
						break;
					System.out.println("Is it correct?");
					boolCorrect = false;
					if (validYesNo().equalsIgnoreCase("yes"))
						boolCorrect = true;
					System.out.println(q.addAnswer(new Answer(answer, boolCorrect)));

				} while (!answer.equalsIgnoreCase("q"));
			}
			System.out.println(A);
		} 
		return;
	}
	

	public static void updateQuestion(QuestionHandler qh) {
		System.out.println("Which question would you like to update? " + "\n Choose 1-" + qh.getTotalQuestionNum());
		int qnum = validInt();
		if (qnum <= qh.getTotalQuestionNum() && qnum > 0) {
			System.out.println("The Question:");
			System.out.println(qh.getQuestionArr().get(qnum - 1));
			System.out.println("Enter the new query:");
			System.out.println(qh.updateQuestion(qnum, s.nextLine()));
			System.out.println("The Question After update:");
			System.out.println(qh.getQuestionArr().get(qnum - 1));
		} else
			System.out.println("Index out of range");
	}
	

	public static void deleteAnswer(QuestionHandler qh) {
		System.out.println("Which question would you like to delete an answer from?\n" + "Choose 1-" + qh.getTotalQuestionNum());
		int qnum = validInt();
		if (qnum <= qh.getTotalQuestionNum() && qnum > 0) {
			if (qh.getQuestionArr().get(qnum - 1).getClass().getSimpleName().equals("AmericanQuestion")) {
				System.out.println("The Question:");
				System.out.println(qh.getQuestionArr().get(qnum - 1));
				System.out.println("Which of the answer would you like to remove?\n");
				int anum = validInt();
				System.out.println((((AmericanQuestion) qh.getQuestionArr().get(qnum - 1)).deleteAnswer(anum)));
				System.out.println("The Question After update:");
				System.out.println(qh.getQuestionArr().get(qnum - 1));
			} else
				System.out.println("That is an open answer!");
		} else
			System.out.println("Index out of range");
	}
	

	public static void addAnswer(QuestionHandler qh) {
		System.out.println("Which question would you like to add an answer to?\n" + "Choose 1-" + qh.getTotalQuestionNum());
		int qnum = validInt();
		if (qh.getQuestionArr().get(qnum - 1).getClass().getSimpleName().equals("AmericanQuestion")) {
			System.out.println("The Question:");
			System.out.println(qh.getQuestionArr().get(qnum - 1));
			System.out.println("Enter Answer:");
			String answer = s.nextLine();
			System.out.println("Is it correct?");
			boolean boolCorrect = false;
			if (validYesNo().equalsIgnoreCase("yes"))
				boolCorrect = true;
			System.out.println(((AmericanQuestion) qh.getQuestionArr().get(qnum - 1)).addAnswer(new Answer(answer, boolCorrect)));
			System.out.println("The Question After update:");
			System.out.println(qh.getQuestionArr().get(qnum - 1));
		} else
			System.out.println("That is an open answer!");
	}
	

	@SuppressWarnings("unchecked")
	public static void manualTest(QuestionHandler qh) {
		boolean flag = true;
		System.out.println("How many questions do you want in this test? " + "Choose 1-" + qh.getTotalQuestionNum());
		int totalQnum = validInt();
		if (totalQnum > qh.getTotalQuestionNum() || totalQnum <= 0) {
			System.out.println("Out of range! \n");
			return;
		}
		ArrayList<ArrayList<Integer>> Man = new ArrayList<>();
		ArrayList<Integer> a = new ArrayList<>();
		AmericanQuestion CastSaverA;
		int totalAnum, qnum, anum;
		for (int i = 0; i < totalQnum; i++) {
			System.out.println("Question number " + (i + 1) + " will be: ");
			qnum = validInt();
			a.clear();
			a.add(--qnum);
			System.out.println("The Question:");
			System.out.println(qh.getQuestionArr().get(qnum) + "\n");
			if (qh.getQuestionArr().get(qnum).getClass().getSimpleName().equals("AmericanQuestion")) {
				System.out.println("How many answers do you want to add for this question?");
				totalAnum = validInt();
				CastSaverA = (AmericanQuestion) qh.getQuestionArr().get(qnum);
				while (totalAnum > CastSaverA.getNumOfAnswers()) {
					System.out.println("Index is out of bounds!");
					System.out.println("How many answers do you want to add for this question?");
					totalAnum = validInt();
				}
				for (int j = 0; j < totalAnum; j++) {
					flag = true;
					System.out.println("Which answer do you want to add?");
					anum = validInt();
					anum--;
					for (int u = 1; u < a.size(); u++) {
						if (a.get(u) == anum) {
							System.out.println("Answer already exist");
							flag = false;
							j--;
							break;
						}
					}
					if (flag && (anum > CastSaverA.getNumOfAnswers() - 1 || anum < 0)) {
						System.out.println("Index out of bound");
						j--;
					} 
					else if (flag) {
						a.add(anum);
						System.out.println("Answer added");
					}
				}
			}
			Man.add((ArrayList<Integer>) a.clone());
			System.out.println("Question added to test\n");
		}
		try {
			Test test = qh.createManTest(Man);
			test.save();
			testArr.add(test);
			System.out.println(test);
		} catch (CloneNotSupportedException e) {
			System.out.println("Not supporting clonable");
		} catch (IOException e) {
			System.out.println(e);
			System.out.println("Sry can't save the test");
		}
	}
	

	public static void updateAnswer(QuestionHandler qh) {
		System.out.println("Plz enter question to answer's updates " + "\n Choose 1-" + qh.getTotalQuestionNum());
		int qnum = validInt();
		if (qnum <= qh.getTotalQuestionNum() && qnum > 0) {
			System.out.println("The Question:");
			System.out.println(qh.getQuestionArr().get(qnum-1));
			String Type = qh.getQuestionArr().get(qnum-1).getClass().getSimpleName();
			if (Type.equals("OpenQuestion")) {
				System.out.println("Enter updated answer:");
				String newAnswer = s.nextLine();
				ptype A = qh.updateAnswer(qnum, newAnswer);
				System.out.println(A);
				if (A == ptype.UPDATE_SUCCESS) {
					System.out.println("The Question After update:");
					System.out.println(qh.getQuestionArr().get(qnum-1));
				}
			} 
			else {
				System.out.println("Choose answer to update: 1-"
						+ ((AmericanQuestion) qh.getQuestionArr().get(qnum-1)).getNumOfAnswers());
				int Index = validInt();
				System.out.println("\nEnter Answer:");
				String answer = s.nextLine();
				System.out.println("Is it correct?");
				boolean boolCorrect = false;
				if (validYesNo().equalsIgnoreCase("yes"))
					boolCorrect = true;
				ptype A = qh.updateAnswer(qnum, Index, new Answer(answer, boolCorrect));
				System.out.println(A);
				if (A == ptype.UPDATE_SUCCESS) {
					System.out.println("The Question After update:");
					System.out.println(qh.getQuestionArr().get(qnum-1));
					System.out.println(testArr);
				}
			}
		} 
		else
			System.out.println("Index out of bounds");
	}
	

	public static void AddDatabase(QuestionHandler qh) {
		QuestionHandler qh1 = read();
		for (int y = 0; y < qh1.getTotalQuestionNum(); y++)
			qh.addQuestion(qh1.getQuestionArr().get(y));// addQuestion will not let the user Stack the same questions.
		System.out.println("Database has being updated Sucessfully! \n");
	}
	

	public static void SetDefaultQuestion(QuestionHandler qh) {
		qh.addQuestion(new OpenQuestion("1+1?", "11"));
		qh.addQuestion(new OpenQuestion("What noises cattle make", "Moo"));
		qh.addQuestion(new OpenQuestion("How much stars are in the universe", "Around 10 bilion"));
		qh.addQuestion(new OpenQuestion("How long do bears hibernate?", "Around 7 months"));
		qh.addQuestion(new OpenQuestion("How long is a semester?", "Approximately 15 weeks"));
		qh.addQuestion(new OpenQuestion("How fast can cheetahs run", "80 to 130 KMH"));
		qh.addQuestion(new OpenQuestion("How long does earth exists(age)", "about 4.5 billion years old"));
		qh.addQuestion(new OpenQuestion("What is the biggest creature in the world", "The Antarctic blue whale"));
		qh.addQuestion(new OpenQuestion("Are some people better sprinters than others ?",
				"Yes, We even know that most of those, blessed with better genetic structure \n such ACTN3 gene"));
		qh.addQuestion(new OpenQuestion("What is the largest predator in the ocean", "Killer whales (Orcinus orca)"));
		qh.addQuestion(new AmericanQuestion("Which mammal is known to have the most powerful bite in the world?",
				new Answer("Hippopotamus", true), new Answer("Crocodile", false), new Answer("Shark", false),
				new Answer("Mouse", false)));
		qh.addQuestion(
				new AmericanQuestion("What object does a male penguin often gift to a female penguin to win her over?",
						new Answer("A beer", false), new Answer("A pebble", true), new Answer("A apple", false),
						new Answer("A selfie", false)));
		qh.addQuestion(new AmericanQuestion("Under their white fur, what color is a polar bear�s skin?",
				new Answer("Orange, like Donald Trump", false), new Answer("Black", true), new Answer("Blue", false),
				new Answer("Green", false)));
		qh.addQuestion(new AmericanQuestion("Under their white fur, what color is a polar bear�s skin?",
				new Answer("Orange, like Donald Trump", false), new Answer("Black", true), new Answer("Blue", false),
				new Answer("Green", false)));
		qh.addQuestion(new AmericanQuestion("How long is a snails lifespan?",
				new Answer("Few mins, put salt them", true), new Answer("Two to five years", true),
				new Answer("Immortal", false), new Answer("Its depends on french population", true)));
		qh.addQuestion(new AmericanQuestion("A baby goat is called what?", new Answer("Kid", true),
				new Answer("Boy", false), new Answer("Girl", false), new Answer("Moti", false)));
		qh.addQuestion(new AmericanQuestion("What animal is said to have 9 lives?", new Answer("Cat", true),
				new Answer("Human", false), new Answer("Dog", false), new Answer("Snake", true)));
		qh.addQuestion(new AmericanQuestion("What animal is known to be man best friend",
				new Answer("Your little sister", false), new Answer("Cat", false), new Answer("Dog", true),
				new Answer("The Cockroach that is under your bed", true)));
		qh.addQuestion(new AmericanQuestion("Where do a tortoise lay its eggs?", new Answer("Under your bed", false),
				new Answer("In a hole in the ground", true), new Answer("The white house", false),
				new Answer("The red house", false)));
	}
	

	public static boolean IsDatabaseEmpty(QuestionHandler qh) {
		if (qh.getTotalQuestionNum() != 0)
			return false;
		System.out.println("\nThe Database is empty, plz add questions first...\n");
		return true;
	}
}
