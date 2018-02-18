package crossplatform.BookParser;

import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import crossplatform.Books.Book;
import org.apache.commons.lang3.StringUtils;

import java.time.Year;

public class BookParser{

    private static final String ERRORMESSAGE="Invalid book data! ";

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

        if(StringUtils.isBlank(book.udc) || StringUtils.isBlank(book.name)|| StringUtils.isBlank(book.author)
                || book.instanceCount<0)
            throw new Exception(ERRORMESSAGE+bookStr);

        if(book.publishYear!=null && book.publishYear> Year.now().getValue())
            throw new Exception(ERRORMESSAGE+bookStr);

        return book;
    }
}
