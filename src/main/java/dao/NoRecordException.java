package dao;

public class NoRecordException extends Exception{

    public NoRecordException (String message){
        super(message);
    }

    public NoRecordException() {
        super();
    }

}
