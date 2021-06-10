package vn.elca.training.service.impl;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import vn.elca.training.model.entity.Task;
import vn.elca.training.model.entity.User;
import vn.elca.training.model.exception.DeadlineAfterFinishingDateException;
import vn.elca.training.repository.TaskRepository;
import vn.elca.training.repository.UserRepository;
import vn.elca.training.service.UserService;

import java.util.List;

/**
 * @author gtn
 */
@Service
@org.springframework.transaction.annotation.Transactional(rollbackFor = DeadlineAfterFinishingDateException.class,
        propagation = Propagation.REQUIRED)
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    TaskRepository taskRepository;

    @Override
    public User findOne(Long id) {
        User user = userRepository.findById(id).orElse(null);
        if (user != null) {
            Hibernate.initialize(user.getTasks());
        } else {
            throw new NullPointerException();
        }
        return user;
    }

    @Override
    public User findOne(String usename) {
        return userRepository.findUserByUsername(usename);
    }

    @Override
    public User addTasksToUser(List<Long> taskIds, String username) {
        List<Task> tasks = taskRepository.findAllById(taskIds);
        User user = findOne(username);
        user.setTasks(tasks);
        for (int i = 0; i < tasks.size(); i++) {
            tasks.get(i).setUser(user);
        }
        return user;
    }

    @Override
    public User update(User user) {
        return userRepository.save(user);
    }
}
