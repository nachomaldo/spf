package cl.ubiobio.spf.Exception;

public class FacilitoException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public FacilitoException(){
        super();
    }
    public FacilitoException(String message){
        super(message);
    }
}
