package Commands;

public class User extends Command {
	private String Reply = "331 Password required for ";
	private String name = null;
	public String Name()
	{
		return "User";
	}
	public void ParseArgs(String args)
	{
		name = args;
		// User iain qwerty
	}
	public void process()
	{
	}
	public String Reply()
	{
		return ("331 Password required for " + name);
	}
	
	public Command Next()
	{
		return null;
	}
}
