package controller;

import java.util.Arrays;

import org.apache.commons.lang3.ArrayUtils;

/**
 * a command is a custom data set that includes a prefix, a actual order and an string array of arguments
 *
 * @author Cornelius
 */
public class Command {
    private String prefix = "!";
    private String cmd;
    private String[] args;

    /**
     * creates a new command and assign every part of it
     *
     * @param fullCommand Command
     */
    public Command(String fullCommand) {
        String[] parts = this.splitCommand(fullCommand);
        this.cmd = parts[1];
        this.args = Arrays.copyOfRange(parts, 2, parts.length);
    }

    /**
     * splits the command-string into an array with every word
     *
     * @param command Command
     * @return StringArray with words
     */
    private String[] splitCommand(String command) {
        String[] parts = command.split("[! ]");
        System.out.println(parts.length);

        for (String str : parts) {
            System.out.println(str);
        }

        return parts;
    }

    // getter and setter
    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public String[] getArgs() {
        return args;
    }

    // set the full args array
    public void setArgs(String[] args) {
        this.args = args;
    }

    // only set one specific argument
    public void setArgs(int pos, String arg) {
        this.args[pos] = arg;
    }

    public String getRawCommand() {
        return prefix + cmd + ArrayUtils.toString(args);
    }
}