package Services;

import Dao.PartDao;
import Models.Part;
import org.skife.jdbi.v2.sqlobject.CreateSqlObject;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Objects;

public abstract class PartService {

    //definicion de todos los tipos de mensajes que puedan surgir en la ejecucion
    private static final String PART_NOT_FOUND = "Part id %s not found.";
    private static final String DATABASE_REACH_ERROR =
            "Could not reach the MySQL database. The database may be down or there may be network connectivity issues. Details: ";
    private static final String DATABASE_CONNECTION_ERROR =
            "Could not create a connection to the MySQL database. The database configurations are likely incorrect. Details: ";
    private static final String DATABASE_UNEXPECTED_ERROR =
            "Unexpected error occurred while attempting to reach the database. Details: ";
    private static final String SUCCESS = "Success...";
    private static final String UNEXPECTED_ERROR = "An unexpected error occurred while deleting part.";

    @CreateSqlObject
    abstract PartDao partsDao();

    public List<Part> getParts() {
        return partsDao().getParts();
    }

    public Part getPart(int id) {
        Part part = partsDao().getPart(id);
        if (Objects.isNull(part)) {
            throw new WebApplicationException(String.format(PART_NOT_FOUND, id), Response.Status.NOT_FOUND);
        }
        return part;
    }

    public Part createPart(Part part) {
        partsDao().createPart(part);
        return partsDao().getPart(partsDao().lastInsertId());
    }

    public Part editPart(Part part) {
        if (Objects.isNull(partsDao().getPart(part.getId()))) {
            throw new WebApplicationException(String.format(PART_NOT_FOUND, part.getId()),
                    Response.Status.NOT_FOUND);
        }
        partsDao().editPart(part);
        return partsDao().getPart(part.getId());
    }

    public String deletePart(final int id) {
        int result = partsDao().deletePart(id);
        switch (result) {
            case 1:
                return SUCCESS;
            case 0:
                throw new WebApplicationException(String.format(PART_NOT_FOUND, id), Response.Status.NOT_FOUND);
            default:
                throw new WebApplicationException(UNEXPECTED_ERROR, Response.Status.INTERNAL_SERVER_ERROR);
        }
    }
}
