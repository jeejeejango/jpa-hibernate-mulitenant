package org.jeejeejango.repository;

import org.jeejeejango.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author jeejeejango
 * @since 10/01/2019 01:20
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
