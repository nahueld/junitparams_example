package data;

import junitparams.mappers.DataMapper;
import model.ContactForm;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.Reader;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by ndealbera on 1/20/2015.
 */

/**
 * The mapper must implement DataMapper interface
 */
public class ContactFormMapper implements DataMapper {

    private static Logger logger = Logger.getLogger(ContactFormMapper.class);

    /**
     * The data mapper interface contains this only method
     * @param reader with the reference to path of the file
     * @return       an array of objects, one object per lines in the CSV file and one test iteration for each object
     */
    @Override
    public Object[] map(Reader reader) {

        //We declare the Iterable of CSVRecord, this is were we are going to hold the information of the test data file
        Iterable<CSVRecord> records = null;
        try {
            //CSVFormat allows us to easily read the file, in this case a CSV but could be and Excel file or a custom format
            //as we are going to use the default CSV format we can use the DEFAULT parser.
            records = CSVFormat.DEFAULT.parse(reader);
        } catch (IOException e) {
            logger.error("The input file for test data can't be found...");
            e.printStackTrace();
        }

        //This will be our output, a list of ContactForm objects, we will later convert this to array to match the method return type.
        List<ContactForm> contactForms = new LinkedList<ContactForm>();
        for(CSVRecord record : records) {
            ContactForm contactForm = new ContactForm();

            int recordSize = record.size();
            int numberOfFields = 4;
            if(recordSize != numberOfFields) {
                //we are throwing an exception if the test data format is wrong...
                throw new IllegalArgumentException ("The format of the input CVS is wrong, you must specify " + numberOfFields + " values");
            }else{
                logger.debug("The input CVS contains the expected number of arguments.");
                logger.debug(record.toString());
            }

            //we will create the ContactForm object based on the current CSVRecord object
            contactForm.setName(record.get(0));
            contactForm.setEmail(record.get(1));
            contactForm.setMessage(record.get(2));
            contactForm.setSubject(record.get(3));

            //We add this to our LinkedList
            contactForms.add(contactForm);
        }

        //Finally we return an array of ContectForm objects to match the return type of the method.
        return contactForms.toArray();
    }
}
