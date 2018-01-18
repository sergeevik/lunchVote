package lunchVote.exceptions;

public class NotFoundEntity extends RuntimeException {
    public NotFoundEntity(String msg) {
        super(msg);
    }
}
