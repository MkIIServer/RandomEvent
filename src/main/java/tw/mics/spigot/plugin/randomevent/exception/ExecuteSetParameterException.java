package tw.mics.spigot.plugin.randomevent.exception;

public class ExecuteSetParameterException extends Exception {
    private String error_msg;
    public ExecuteSetParameterException(String string) {
        error_msg = string;
    }
    
    public String getErrorMessage(){
        return error_msg;
    }

    private static final long serialVersionUID = 8222397623987113370L;
}
