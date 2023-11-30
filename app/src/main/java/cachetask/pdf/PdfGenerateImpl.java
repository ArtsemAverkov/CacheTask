package cachetask.pdf;

import cachetask.entity.User;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;


import java.io.FileOutputStream;
import java.io.IOException;


public class PdfGenerateImpl implements PdfGenerate {

    @Override
    public void writeToPdfFile(User user) {
        try {
            PdfReader reader = new PdfReader("Clevertec_Template.pdf");
            PdfStamper stamper = new PdfStamper(reader, new FileOutputStream("updated_Clevertec_Template.pdf"));
            PdfContentByte canvas = stamper.getUnderContent(1);

            BaseFont font = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
            canvas.setFontAndSize(font, 40);

            String[] userAttributes = getStringsUser(user);

            int yOffset = 500;
            for (String s : userAttributes) {
                canvas.beginText();
                canvas.setTextMatrix(100, yOffset);
                canvas.showText(s);
                canvas.endText();
                yOffset -= 50;
                }
            stamper.close();
            reader.close();

        } catch (IOException | DocumentException e) {
            e.printStackTrace();
        }
    }

    public String[] getStringsUser(User user) {
        String[] userAttributes = {
                "User:",
                "id= " + user.getId(),
                "name= " + user.getName(),
                "lastName= " + user.getLastName(),
                "email= " + user.getEmail()
        };
        return userAttributes;
    }
}
