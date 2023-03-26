package com.mateuszcer.socialmediaapp.repository;

import com.mateuszcer.socialmediaapp.model.Followers;
import com.mateuszcer.socialmediaapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FollowersRepository extends JpaRepository<Followers, Long> {
    public List<Followers> findAllByTo(User user);
    public List<Followers> findAllByFrom(User user);
}
