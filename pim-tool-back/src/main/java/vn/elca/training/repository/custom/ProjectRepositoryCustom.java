
package vn.elca.training.repository.custom;

import vn.elca.training.model.entity.Project;

import java.util.List;

public interface ProjectRepositoryCustom {
    Project findProjectById(Long id);

    List<Project> findProjectByCondition(String condition, String status);

    List<Project> findAllProject();

    List<Project> findProjectByProjectNumber(Integer projectNumber);

    List<Project> findMultipleProject(List<Integer> projects);

    Project findProjectByNumber(Integer projectNumber);

}
