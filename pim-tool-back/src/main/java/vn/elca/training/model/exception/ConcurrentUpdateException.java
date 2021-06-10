package vn.elca.training.model.exception;

public class ConcurrentUpdateException extends Exception {
    private final Integer projectNumberExcept;

    public ConcurrentUpdateException(Integer projectNumberExcept) {
        super(String.format("CONCURRENT_UPDATE-%d", projectNumberExcept));
        this.projectNumberExcept = projectNumberExcept;
    }
}
