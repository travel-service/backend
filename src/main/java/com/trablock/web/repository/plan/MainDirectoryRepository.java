package com.trablock.web.repository.plan;

import com.trablock.web.entity.plan.UserDirectory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MainDirectoryRepository extends JpaRepository<UserDirectory, Long> {
}
