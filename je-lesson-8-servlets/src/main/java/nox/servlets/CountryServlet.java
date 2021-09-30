package nox.servlets;

import nox.dao.CountryDao;
import nox.entities.CountryEntity;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.annotation.WebServlet;

@WebServlet(urlPatterns = "/countries/*", loadOnStartup = 1)
public class CountryServlet extends MyServlet<CountryEntity> {

    @Autowired
    public CountryServlet(CountryDao countryDao) {
        super(countryDao);
    }
}
