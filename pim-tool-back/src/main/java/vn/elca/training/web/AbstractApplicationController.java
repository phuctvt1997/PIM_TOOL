package vn.elca.training.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import vn.elca.training.util.ApplicationMapper;
@CrossOrigin
public abstract class AbstractApplicationController {
    @Autowired
    ApplicationMapper mapper;
}
