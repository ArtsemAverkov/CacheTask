package cachetask.pdf;

import cachetask.entity.User;

public interface PdfGenerate {
    void writeToPdfFile(User user);
}
