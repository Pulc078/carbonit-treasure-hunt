package fr.carbonit.treasurehunt.transverse.exception;

public class TreasureHuntException extends Exception{

    public TreasureHuntException() {
        super();
    }

    public TreasureHuntException(String message) {
        super(message);
    }

    public TreasureHuntException(String message, Throwable cause) {
        super(message, cause);
    }

    public TreasureHuntException(Throwable cause) {
        super(cause);
    }

    protected TreasureHuntException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
