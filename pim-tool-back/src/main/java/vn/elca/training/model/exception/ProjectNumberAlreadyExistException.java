package vn.elca.training.model.exception;

public class ProjectNumberAlreadyExistException extends Exception {
    private final Integer projectNumberExcept;

    public ProjectNumberAlreadyExistException(Integer projectNumberExcept) {
        super(String.format("DUPLICATE_PROJECT_NUMBER-%d", projectNumberExcept));
        this.projectNumberExcept = projectNumberExcept;
    }

    public Integer getProjectNumberExcept() {
        return projectNumberExcept;
    }
}
