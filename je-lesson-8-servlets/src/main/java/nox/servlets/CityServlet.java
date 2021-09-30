package nox.servlets;

import nox.dao.CityDao;
import nox.entities.CityEntity;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.annotation.WebServlet;

@WebServlet(urlPatterns = "/cities/*", loadOnStartup = 1)
public class CityServlet extends MyServlet<CityEntity> {

    @Autowired
    public CityServlet(CityDao cityDao) {
        super(cityDao);
    }
}
