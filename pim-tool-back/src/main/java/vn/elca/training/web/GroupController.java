package vn.elca.training.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.elca.training.model.dto.GroupDto;
import vn.elca.training.service.GroupService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/Groups")
public class GroupController extends AbstractApplicationController {
    @Autowired
    private GroupService groupService;

    @GetMapping("/")
    public List<GroupDto> GroupList() {
        return this.groupService.findAllGroup().stream().map(mapper::groupToGroupDto).collect(Collectors.toList());
    }
}
