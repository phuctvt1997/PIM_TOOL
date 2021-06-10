package vn.elca.training.web;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import vn.elca.training.model.dto.UserDto;
import vn.elca.training.model.entity.User;
import vn.elca.training.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/Tasks")
public class TaskController extends AbstractApplicationController {
    @Autowired
    UserService userService;

    @PostMapping("/{username}/addTasks")
    public UserDto addTasks(@RequestBody List<Long> taskIds, @PathVariable String username) {
        if (CollectionUtils.isEmpty(taskIds)) {
            throw new IllegalArgumentException("Invalid request! List taskIds is empty");
        } else if (StringUtils.isBlank(username)) {
            throw new IllegalArgumentException("Invalid request! Username is blank");
        }
        User user = userService.addTasksToUser(taskIds, username);
        return mapper.userToUserDto(user);
    }
}
