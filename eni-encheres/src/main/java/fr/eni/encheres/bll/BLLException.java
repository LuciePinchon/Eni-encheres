package fr.eni.encheres.bll;

public class BLLException extends Exception {


private static final long serialVersionUID = 1L;


public BLLException() {
	super();
}
public BLLException(String message) {
	super(message);
}
public BLLException(String message, Throwable exc ) {
	super(message,exc);
}


public String getMessage()
{
return "Message de la BLL - "+super.getMessage();	
}

}
