package Commands;

public abstract class Command {
	public abstract String Name();
	public abstract void ParseArgs(String args);
	public abstract void process();
	public abstract String Reply();
	public abstract Command Next();	// chain the next command/responce
}
