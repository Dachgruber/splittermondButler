package Controller;


public interface Controller {

    /**
     * executes a given command in string form
     *
     * @param args string with command, f.e. !give role
     */
    void executeCommand(String cmdStr);

}
