package lunchVote.util;

import lunchVote.exceptions.IdNotEqualsException;
import lunchVote.exceptions.NotFoundEntity;
import lunchVote.model.HasId;

import java.util.Objects;

public class ValidateUtil {
    public static void checkIdEquals(HasId object, int id){
        Objects.requireNonNull(object);
        if (object.getId() == null || !object.getId().equals(id))
            throw new IdNotEqualsException();
    }

    public static void checkEntityNotNull(HasId object, int id){
        if (object == null)
            throw new NotFoundEntity("Not found entity with id=" + id);
    }

    public static void checkNew(HasId bean) {
        if (!bean.isNew()) {
            throw new IllegalArgumentException(bean + " must be new (id=null)");
        }
    }


}
