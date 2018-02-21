package crossplatform.Loggers;

import crossplatform.FileUtil;

public class FileLogger implements ILog{
    private String _filePath;

    public FileLogger(String filePath){
        _filePath=filePath;
    }

    /**
     * logs error to file
     * @param text message for logging
     */
    @Override
    public void logError(String text) {
        FileUtil.writeLine(text,_filePath);
    }
}
