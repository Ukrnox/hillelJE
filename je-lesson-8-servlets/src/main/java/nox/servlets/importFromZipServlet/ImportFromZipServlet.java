package nox.servlets.importFromZipServlet;

import nox.cvs.ImportCSVFromZIPIntoDB;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/importFromZip", loadOnStartup = 1)
public class ImportFromZipServlet extends HttpServlet {

    private ImportCSVFromZIPIntoDB importCSVFromZIPIntoDB;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(config.getServletContext());
        importCSVFromZIPIntoDB = (ImportCSVFromZIPIntoDB) webApplicationContext.getBean("importCSVFromZIPIntoDB");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        importCSVFromZIPIntoDB.importCSVFromFileINCurrentDir("import.zip");
        resp.sendRedirect(req.getContextPath() + "/");
    }
}
