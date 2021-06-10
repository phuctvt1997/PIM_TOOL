package vn.elca.training.validator;

import org.springframework.stereotype.Component;
import vn.elca.training.model.exception.ConcurrentUpdateException;

@Component
public class ConcurrentValidator {
    public void validate(Long versionInDatabase, Long versionFromFrontEnd,Integer projectNumberExcept) throws ConcurrentUpdateException {
        if (!versionInDatabase.equals(versionFromFrontEnd) ) {
            throw new ConcurrentUpdateException(projectNumberExcept);
        }
    }
}
