package nox.servlets;

import nox.dao.RegionDao;
import nox.entities.RegionEntity;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.annotation.WebServlet;

@WebServlet(urlPatterns = "/regions/*", loadOnStartup = 1)
public class RegionServlet extends MyServlet<RegionEntity>{

    @Autowired
    public RegionServlet(RegionDao regionDao) {
        super(regionDao);
    }
}
