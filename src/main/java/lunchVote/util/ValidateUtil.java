package lunchVote.util;

import lunchVote.exceptions.IdNotEqualsException;
import lunchVote.exceptions.NotFoundEntity;
import lunchVote.model.HasId;

import java.util.Objects;

public class ValidateUtil {
    public static void checkIdEquals(HasId object, int id){
        Objects.requireNonNull(object, "Entity ");
        if (!object.getId().equals(id))
            throw new IdNotEqualsException();
    }

    public static <T extends HasId> void checkEntityNotNull(T object, int id){
        if (object == null)
            throw new NotFoundEntity("Not found entity with id=" + id);
    }

    public static void checkNew(HasId bean) {
        if (!bean.isNew()) {
            throw new IllegalArgumentException(bean + " must be new (id=null)");
        }
    }

    public static void checkDeleteSuccess(boolean delete, int id) {
        if (!delete) {
            throw new NotFoundEntity("Not found entity with id=" + id);
        }
    }


}
