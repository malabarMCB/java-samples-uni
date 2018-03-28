package crossplatform.domainLogic.loggers;

public class ConsoleLogger implements ILog{

    /**
     * logs error to console
     * @param text message for logging
     */
    @Override
    public void logError(String text) {
        System.out.println(text);
    }
}
