package telnet.user.commands;

public class Getopt {
	/*
	The getopt function gets the next option argument from the argument list specified by the argv and
	argc arguments. Normally these values come directly from the arguments received by main. 

	The options argument is a string that specifies the option characters that are valid for this
	program. An option character in this string can be followed by a colon (‘:’) to indicate that it
	takes a required argument. If an option character is followed by two colons (‘::’), its argument
	is optional; this is a GNU extension. 

	getopt has three ways to deal with options that follow non-options argv elements.
	The special argument ‘--’ forces in all cases the end of option scanning. 

	The default is to permute the contents of argv while scanning it so that eventually all the
	non-options are at the end. This allows options to be given in any order, even with programs that
	were not written to expect this. 
	If the options argument string begins with a hyphen (‘-’), this is treated specially. It permits
	arguments that are not options to be returned as if they were associated with option character ‘\1’. 
	POSIX demands the following behavior: The first non-option stops option processing.
	This mode is selected by either setting the environment variable POSIXLY_CORRECT or beginning the
	options argument string with a plus sign (‘+’). 
	The getopt function returns the option character for the next command line option. When no
	more option arguments are available, it returns -1. There may still be more non-option arguments;
	you must compare the external variable optind against the argc parameter to check this. 

	If the option has an argument, getopt returns the argument by storing it in the variable optarg.
	You don't ordinarily need to copy the optarg string, since it is a pointer into the original argv
	array, not into a static area that might be overwritten. 

	If getopt finds an option character in argv that was not included in options, or a missing option
	argument, it returns ‘?’ and sets the external variable optopt to the actual option character.
	If the first character of options is a colon (‘:’), then getopt returns ‘:’ instead of ‘?’ to
	indicate a missing option argument. In addition, if the external variable opterr is nonzero (which
	is the default), getopt prints an error message. 
	
	*/
	protected String[] argv;
	protected String opts;
	protected int optind = 0;
	protected int optchar = 0;
	protected String argument;
	protected boolean optoptional = false;
	protected boolean error = false;
	
	
	public static void main(String[] args) {
        // TODO code application logic here
		//String[] args = { "-c", "-b", "-a", "test" };
		Getopt g = new Getopt(args, "-abcd");
		//g.setOpterr(false); // We'll do our own error handling
		int c;
		while ((c = g.getopt()) != -1)
		{
			 switch(c)
			 {
			 case 'a':
			 case 'd':
				 System.out.print("You picked " + (char)c + "\n");
				 break;
			 //
			 case 'b':
			 case 'c':
				  
				  System.out.print("You picked " + (char)c + 
						  		" with an argument of " +
						  		((args != null) ? args : "null") + "\n");
				  break;
				  //
			 case '?':
				 break; // getopt() already printed an error
				 //
			 default:
				 System.out.print("getopt() returned " + c + "\n");	
			 }
		 }
	}
	Getopt(String[] argv, String opts)
	{
		this.argv = argv;
		this.opts = opts;
	}
	
	public int getopt()
	{
		error = false;
		argument = null;
		int c = 0;
		optchar = 0;
		
		while (optind < argv.length)
		{
			System.out.print("argv[optind]" + argv[optind]);
			  	
			c = argv[optind].charAt(optchar);
			if (c == '-')
			{
				optchar++;
				c = argv[optind].charAt(optchar);
				if (matchOption(c) == true)
				{
					optind++;
					return c;
				}
			}
			else
			{
				argument = argv[optind++];
				return '?';
			}
		}
		return -1;
	}
	boolean matchOption(int o)
	{
		boolean optionalarg = false;
		
		for (int i = 0; i < opts.length(); i++)
		{
			if (opts.charAt(i) == o)
			{
				i++;
				if (opts.charAt(i) == ':')
				{
					i++;
					// Argument follows
					if (opts.charAt(i) == ':')
					{
						// Optional argument follows
						optionalarg = true;
					}
					
					if (optind >= argv.length)
					{
						if (optionalarg == true)
						{
							return true;
						}
						error = true;
						return false;
						//
					}
					argument = argv[++optind];
					    
				}
				return true;
			}
		}
		return false;
	}
	String getOptarg()
	{
		return argument;
	}
}
