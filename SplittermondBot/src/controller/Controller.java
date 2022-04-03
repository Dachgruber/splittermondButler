package controller;

public interface Controller {

	/**
	 * executes a given command in string form
	 *
	 * @param cmdStr string with command, f.e. !give role
	 */
	void executeCommand(String cmdStr);
}
