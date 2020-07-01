package com.example.demo.utility;

import com.example.demo.dto.TaskDTO;
import com.example.demo.entity.Task;

import java.util.ArrayList;
import java.util.List;

public class TaskUtil {

    public static List<TaskDTO> convertTaskIntoTaskDTO(List<Task> tasks){
        List<TaskDTO> listTaskDTO = new ArrayList<>();

        tasks.forEach(task -> {
            TaskDTO _taskDTO = TaskDTO.builder()
                    .taskId(task.getTaskId())
                    .taskIdParent(task.getTaskIdparent())
                    .taskName(task.getTaskName())
                    .nameCreate(task.getNameCreate())
                    .dateCreate(task.getDateCreate())
                    .dateStart(task.getDateStart())
                    .deadlineDate(task.getDeadlineDate())
                    .taskState(task.getTaskState())
                    .discription(task.getDiscription())
                    .taskOutput(task.getTaskOutput())
                    .build();

            if (task.getStaffId() != null) {
                _taskDTO.setStaffName(task.getStaffId().getName());
                _taskDTO.setStaffId(task.getStaffId().getStaffId());
            }
            if (task.getProjectId() != null) {
                _taskDTO.setProjectId(task.getProjectId().getProjectId());
            }
            listTaskDTO.add(_taskDTO);
        });
        return listTaskDTO;
    }
}
