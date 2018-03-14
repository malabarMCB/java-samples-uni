package crossplatform.Loggers;

import crossplatform.FileUtil;

import java.io.File;

public class FileLogger implements ILog{
    private String file;

    public void setFile(String file) {
        this.file = file;
    }

    /**
     * logs error to file
     * @param text message for logging
     */
    @Override
    public void logError(String text) {
        FileUtil.writeLine(text, file);
    }
}
