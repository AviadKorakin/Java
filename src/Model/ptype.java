package Model;

public enum ptype {
    INDEX_OUT_OF_BOUNDS("Index out of range\n"),
    ADD_SUCCESS("Added successfully\n"),
   DELETE_SUCCESS("Deleted successfully\n"),
    UPDATE_SUCCESS("Updated successfully\n"),
    QUESTION_ISNT_FOUND("The question isn't found\n"),
    ANSWER_ISNT_EXIST("Answer isnt found\n"),
    INCORRECT_TYPE("Admin:The machine can't complie incorrect type\n"),
    QUESTION_ALREADY_EXISTS("Question is already exists, cant update to the required snytax\n"),
    ANSWER_ALREADY_EXISTS("The Answer is already exist,sry..\n"),
    MINIMAL_REQUIREMENTS("Can't delete in case less than question left"),
	EMPTY_VALUE("Null value entered");
	
    private String string;

    ptype(String string) {
        this.string=string;
    }
    
    @Override
    public String toString() {
        return string;
    }

}