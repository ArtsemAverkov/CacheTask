package cachetask.pdfTest;

import cachetask.entity.User;
import cachetask.extension.discount.ValidParameterResolverUser;
import cachetask.pdf.PdfGenerateImpl;
import com.itextpdf.text.pdf.PdfReader;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(ValidParameterResolverUser.class)
public class PdfGenerateImplTest {


    @Test
    void testWriteToPdfFile(User user){
        PdfGenerateImpl pdfGenerator = new PdfGenerateImpl();
        PdfReader reader = mock(PdfReader.class);
        when(reader.getNumberOfPages()).thenReturn(1);
        pdfGenerator.writeToPdfFile(user);
        verify(reader).close();
    }

    @Test
    void testGetStringsUser(User user) {
        PdfGenerateImpl pdfGenerator = new PdfGenerateImpl();
        String[] userAttributes = pdfGenerator.getStringsUser(user);
        assertEquals(5, userAttributes.length);
        assertEquals("User:", userAttributes[0]);
        assertEquals("id= " + user.getId(), userAttributes[1]);
        assertEquals("name= " + user.getName(), userAttributes[2]);
        assertEquals("lastName= " + user.getLastName(), userAttributes[3]);
        assertEquals("email= " + user.getEmail(), userAttributes[4]);
    }
}


