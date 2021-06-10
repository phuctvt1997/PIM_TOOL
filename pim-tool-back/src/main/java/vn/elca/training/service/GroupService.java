package vn.elca.training.service;

import vn.elca.training.model.entity.Group;

import java.util.List;

public interface GroupService  {
    Group getOneGroup(Long id);

    List<Group> findAllGroup();
}
