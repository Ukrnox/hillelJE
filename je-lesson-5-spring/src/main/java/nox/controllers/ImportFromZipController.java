package nox.controllers;

import nox.cvs.ImportCSVFromZIPIntoDB;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping(path = "/importFromZip")
public class ImportFromZipController {
    private final ImportCSVFromZIPIntoDB importCSVFromZIPIntoDB;

    public ImportFromZipController(ImportCSVFromZIPIntoDB importCSVFromZIPIntoDB) {
        this.importCSVFromZIPIntoDB = importCSVFromZIPIntoDB;
    }

    @PostMapping
    public void importCSVFromZIPIntoDB(HttpServletResponse response) throws IOException {
        response.sendRedirect("/");
        importCSVFromZIPIntoDB.importCSVFromFileINCurrentDir("import.zip");
    }

    @ExceptionHandler
    public String handleElementNotFound(Exception ex) {
        return ex.getMessage();
    }
}
