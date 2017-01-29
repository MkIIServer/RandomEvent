package tw.mics.spigot.plugin.randomevent.exception;

public class RandomEventException extends Exception {
    private static final long serialVersionUID = 3277198675183258390L;
    private String error_msg;
    public RandomEventException(String string) {
        error_msg = string;
    }
    
    public String getErrorMessage(){
        return error_msg;
    }
}
