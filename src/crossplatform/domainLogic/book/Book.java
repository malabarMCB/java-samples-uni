package crossplatform.domainLogic.book;

import org.apache.commons.lang3.StringUtils;

import java.time.Year;

public class Book{
    protected String udc;
    protected String author;
    protected String name;
    protected Short publishYear;
    protected int instanceCount;

    @Override
    public String toString() {
        return String.format("UDC: %s; author is %s; book name is %s; was publilshed in %d; number of instance: %d"
                ,udc,author,name,publishYear,instanceCount);
    }

    public String getUdc() {
        return udc;
    }

    public void setUdc(String udc) throws Exception {
        if(StringUtils.isBlank(udc))
            throw new Exception("Empty udc!");
        this.udc = udc;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) throws Exception {
        if(StringUtils.isBlank(author))
            throw new Exception("Empty author!");
        this.author = author;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) throws Exception {
        if(StringUtils.isBlank(name))
            throw new Exception("Empty name!");
        this.name = name;
    }

    public Short getPublishYear() {
        return publishYear;
    }

    public void setPublishYear(Short publishYear) throws Exception {
        if(publishYear> Year.now().getValue())
            throw new Exception("Year value is bigger than now!");
        this.publishYear = publishYear;
    }

    public int getInstanceCount() {
        return instanceCount;
    }

    public void setInstanceCount(int instanceCount) throws Exception {
        if(instanceCount<0)
            throw new Exception("Negative instance count!");
        this.instanceCount = instanceCount;
    }
}
