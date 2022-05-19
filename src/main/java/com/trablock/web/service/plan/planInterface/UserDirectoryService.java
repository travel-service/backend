package com.trablock.web.service.plan.planInterface;

import com.trablock.web.controller.form.StateChangeForm;
import com.trablock.web.controller.form.UserDirectoryForm;
import com.trablock.web.entity.plan.Plan;
import com.trablock.web.entity.plan.UserDirectory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface UserDirectoryService {

    void saveUserDirectory(UserDirectory userDirectory);

    void createUserDirectory(HttpServletRequest request, UserDirectoryForm userDirectoryForm, HttpServletResponse response);

    void deleteUserDirectory(Long userDirectoryId);

    List<UserDirectory> findMainUserDirectoryMain(HttpServletRequest request);

}
