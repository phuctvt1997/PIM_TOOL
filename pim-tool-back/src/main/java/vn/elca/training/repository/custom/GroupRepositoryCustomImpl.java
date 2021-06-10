package vn.elca.training.repository.custom;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class GroupRepositoryCustomImpl implements GroupRepositoryCustom{
    @PersistenceContext
    private EntityManager em;

}
