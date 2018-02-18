package crossplatform.Loggers;

public class ConsoleLogger implements ILog{

    @Override
    public void logError(String text) {
        System.out.println(text);
    }
}
