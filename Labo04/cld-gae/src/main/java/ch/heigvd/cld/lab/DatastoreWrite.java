package ch.heigvd.cld.lab;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

@WebServlet(name = "DatastoreWrite", value = "/datastorewrite")
public class DatastoreWrite extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/plain");
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

        // Retrieve field-value pairs from the query part of the URI
        Enumeration<String> parameterNames = req.getParameterNames();

        String kind = getKind(req);
        String key = getKey(req);

        if (kind == null) {
            resp.getWriter().println("Error: _kind field is mandatory.");
            return;
        }

        Entity entity;
        if (key != null) {
            entity = new Entity(kind, key);
        } else {
            entity = new Entity(kind);
        }

        // Iterate over each field-value pair and add them as properties to the entity
        while (parameterNames.hasMoreElements()) {
            String fieldName = parameterNames.nextElement();
            String fieldValue = req.getParameter(fieldName);
            // Skip _kind and _key fields
            if (!fieldName.equals("_kind") && !fieldName.equals("_key")) {
                entity.setProperty(fieldName, fieldValue);
            }
        }

        // Write the entity to the Datastore
        datastore.put(entity);

        // Send response
        resp.getWriter().println("Entity written to datastore.");
    }

    // Method to retrieve the kind of the entity from the request
    private String getKind(HttpServletRequest req) {
        return req.getParameter("_kind");
    }

    // Method to retrieve the key of the entity from the request, or null if not provided
    private String getKey(HttpServletRequest req) {
        return req.getParameter("_key");
    }
}
