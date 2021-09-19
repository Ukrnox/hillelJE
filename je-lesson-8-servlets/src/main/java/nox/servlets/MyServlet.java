package nox.servlets;

import lombok.extern.slf4j.Slf4j;
import nox.dao.Dao;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class MyServlet<T> extends HttpServlet {

    private final Dao<T> dao;
    private final Class<T> typeOfT;

    @SuppressWarnings("unchecked")
    public MyServlet(Dao<T> dao) {
        super();
        log.debug("public MyServlet(Dao<T> dao)");
        this.dao = dao;
        this.typeOfT = (Class<T>)
                ((ParameterizedType) getClass()
                        .getGenericSuperclass())
                        .getActualTypeArguments()[0];
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        StringBuffer requestURL = request.getRequestURL();
        String stringRequestURL = requestURL.toString();
        System.out.println(stringRequestURL);
        String[] splitRequestURL = stringRequestURL.split("/");
        System.out.println(Arrays.toString(splitRequestURL));
        String last = splitRequestURL[splitRequestURL.length - 1];
//        try (PrintWriter writer = response.getWriter()){
        if (splitRequestURL[splitRequestURL.length - 2].equals("name")) {
            List<T> cityEntities = dao.readByName(last, typeOfT);
            if (cityEntities.isEmpty()) {
                request.setAttribute("information", typeOfT.getSimpleName() + " was not found!");
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/jsp/index.jsp");
                requestDispatcher.forward(request, response);
            }
            else {
                request.setAttribute("information", dao.readByName(last, typeOfT));
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/jsp/index.jsp");
                requestDispatcher.forward(request, response);
//                    writer.println(dao.readByName(last, typeOfT));
            }
        }
        else {
            try {
                long id = Long.parseLong(last);
                T entity = dao.read(id, typeOfT).orElse(null);
                if (entity == null) {
//                    writer.println(typeOfT.getSimpleName() + " was not found!");
                    request.setAttribute("information", typeOfT.getSimpleName() + " was not found!");
                    RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/jsp/index.jsp");
                    requestDispatcher.forward(request, response);
                }
                else {
//                    writer.println(entity);
                    request.setAttribute("information", entity);
                    RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/jsp/index.jsp");
                    requestDispatcher.forward(request, response);
                }
            }
            catch (NumberFormatException e) {
                if (splitRequestURL.length == 4) {
                    StringBuilder sb = new StringBuilder();
                    dao.readAll(typeOfT).forEach(cityEntity -> sb.append(cityEntity.toString()));
                    request.setAttribute("information", sb);
                    RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/jsp/index.jsp");
                    requestDispatcher.forward(request, response);
                }
                else {
//                    writer.println("Wrong format!");
                    request.setAttribute("information", "Wrong format!");
                    RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/jsp/index.jsp");
                    requestDispatcher.forward(request, response);
                }
            }
//        }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
//        response.setContentType("text/html");
//        response.setCharacterEncoding("UTF-8");
        String body = request.getReader().lines().collect(Collectors.joining());
        String clearBody = body.replaceAll("[{:\"}]", "");
        String name = clearBody.split("name", 2)[1];
        try {
            T t = typeOfT.newInstance();
            Field nameField = typeOfT.getDeclaredField("name");
            nameField.setAccessible(true);
            nameField.set(t, name);
            dao.create(t);
            try (PrintWriter writer = response.getWriter()) {
                writer.write(t.toString());
            }
        }
        catch (InstantiationException | NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
