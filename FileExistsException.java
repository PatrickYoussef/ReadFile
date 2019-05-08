package a3_comp249;

@SuppressWarnings("serial")
public class FileExistsException extends Exception {
	
	public FileExistsException(){
		super("Exception: There is already an existing file for that author. File will\r\n" + 
				"be renamed as BU, and older BU files will be deleted!");
	}
	
	public FileExistsException(String s) {
		super(s);
	}
	
	public String getMessage()
	{
		return super.getMessage();
	}

}
