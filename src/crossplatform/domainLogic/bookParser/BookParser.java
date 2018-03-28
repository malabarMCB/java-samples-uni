package crossplatform.domainLogic.bookParser;

import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import crossplatform.domainLogic.books.Book;
import org.apache.commons.lang3.StringUtils;

import java.time.Year;

public class BookParser{

    private static final String ERRORMESSAGE="Invalid book data! ";

    /**
     * parses csv string to Book
     * @param bookStr csv string
     * @return Book instance
     * @throws Exception exception that appears during parsing
     */
    public static Book parse(String bookStr) throws Exception {
        CsvMapper mapper = new CsvMapper();
        CsvSchema schema = CsvSchema.builder()
                .addColumn("udc")
                .addColumn("author")
                .addColumn("name")
                .addColumn("publishYear")
                .addColumn("instanceCount")
                .build();
        Book book=null;
        try{
            book = mapper
                    .reader(Book.class)
                    .with(schema)
                    .readValue(bookStr);
        }
        catch (Exception e){
            throw new Exception(ERRORMESSAGE+bookStr);
        }

        if(StringUtils.isBlank(book.getUdc()) || StringUtils.isBlank(book.getName())|| StringUtils.isBlank(book.getAuthor())
                || book.getInstanceCount()<0)
            throw new Exception(ERRORMESSAGE+bookStr);

        if(book.getPublishYear()!=null && (book.getPublishYear()> Year.now().getValue() || book.getPublishYear()<0))
            throw new Exception(ERRORMESSAGE+bookStr);

        return book;
    }
}
