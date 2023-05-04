package com.mateuszcer.socialmediaapp.service;

import com.mateuszcer.socialmediaapp.model.*;
import com.mateuszcer.socialmediaapp.payload.request.SignupRequest;
import com.mateuszcer.socialmediaapp.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mateuszcer.socialmediaapp.exceptions.UserAlreadyExistException;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService implements IUserService{
    private final UserRepository userRepository;

    private final RoleService roleService;

    private final PasswordEncoder passwordEncoder;

    private final FollowersService followersService;

    private final LikesService likesService;

    public UserService(UserRepository userRepository, RoleService roleService, PasswordEncoder passwordEncoder, FollowersService followersService, LikesService likesService) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
        this.followersService = followersService;
        this.likesService = likesService;
    }
    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }
    @Override
    public Boolean existsById(Long id) {
        return userRepository.existsById(id);
    }
    @Override
    public Optional<User> findByUsername(String username) {
        Optional<User> userOpt = userRepository.findByUsername(username);
        if(userOpt.isPresent() && userOpt.get().getEnabled())
            return userOpt;
        return Optional.empty();
    }
    @Override
    public Boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public Boolean followUser(String username, String toFollowUsername) {
        if(Objects.equals(username, toFollowUsername)) {
            return Boolean.FALSE;
        }
        Optional<User> userOpt = userRepository.findByUsername(username);
        Optional<User> toFollowOpt = userRepository.findByUsername(toFollowUsername);

        if(userOpt.isEmpty() || toFollowOpt.isEmpty() || !toFollowOpt.get().getEnabled()) {
            return Boolean.FALSE;
        }

        User user = userOpt.get();
        User toFollow = toFollowOpt.get();
        Set<Followers> following = user.getFollowing();

        Followers follow = new Followers(user, toFollow);
        if(following.contains(follow)) {
            return Boolean.FALSE;
        }
        followersService.update(follow);
        return Boolean.TRUE;
    }

    public Boolean unFollowUser(String username, String toFollowUsername) {
        Optional<User> userOpt = userRepository.findByUsername(username);
        Optional<User> toUnFollowOpt = userRepository.findByUsername(toFollowUsername);

        if(userOpt.isEmpty() || toUnFollowOpt.isEmpty()) {
            return false;
        }

        User user = userOpt.get();
        User toUnFollow = toUnFollowOpt.get();

        Optional<Followers> followersOpt = followersService.getByToAndFrom(user, toUnFollow);
        if(followersOpt.isEmpty())
            return Boolean.FALSE;
        Followers followers = followersOpt.get();

        followersService.delete(followers);
        return Boolean.TRUE;


    }

    @Override
    public User registerUser(SignupRequest signupRequest) throws UserAlreadyExistException {

        String username = signupRequest.getUsername();
        String email = signupRequest.getEmail();
        String password = signupRequest.getPassword();
        String firstname = signupRequest.getFirstname();
        String lastname = signupRequest.getLastname();
        String genderString = signupRequest.getGender();
        EGender gender;
        if(genderString.equals("Male")) {
            gender = EGender.GENDER_MALE;
        }
        else if(genderString.equals("Female")) {
            gender = EGender.GENDER_FEMALE;
        }
        else {
            gender = EGender.GENDER_OTHER;
        }

        if (userRepository.existsByEmail(email)) {
            throw new UserAlreadyExistException("Email is already in use!");
        }

        if (userRepository.existsByUsername(username)) {
            throw new UserAlreadyExistException("Username is already in use!");
        }

        User newUser = User
                .builder()
                .email(email)
                .username(username)
                .firstname(firstname)
                .lastname(lastname)
                .password(passwordEncoder.encode(password))
                .gender(gender)
                .enabled(Boolean.FALSE)
                .profilePicture(signupRequest.getPictureId())
                .build();

        Set<Role> roles = new HashSet<>();
        Role userRole = roleService.findByName(ERole.ROLE_USER)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        roles.add(userRole);

        newUser.setRoles(roles);
        newUser = userRepository.save(newUser);

        return newUser;
    }

    @Override
    public void saveRegisteredUser(User user) {
        userRepository.save(user);
    }

    public Set<User> getFollowers(String username) {
        Optional<User> userOpt = userRepository.findByUsername(username);
        if(userOpt.isEmpty()) {
            return new HashSet<>();
        }
        User user = userOpt.get();
        List<Followers> followers = followersService.getAllByTo(user);
        return followers.stream().map(Followers::getFrom).collect(Collectors.toSet());
    }

    public Set<User> getFollowing(String username) {
        Optional<User> userOpt = userRepository.findByUsername(username);
        if(userOpt.isEmpty()) {
            return new HashSet<>();
        }
        User user = userOpt.get();
        List<Followers> followers = followersService.getAllByFrom(user);
        return followers.stream().map(Followers::getTo).collect(Collectors.toSet());
    }

    public Set<UserPost> getLiked(String username) {
        Optional<User> userOpt = userRepository.findByUsername(username);
        if(userOpt.isEmpty()) {
            return new HashSet<>();
        }
        User user = userOpt.get();
        List<Likes> likes = likesService.getAllByFrom(user);

        return likes.stream().map(Likes::getTo).collect(Collectors.toSet());
    }

    public List<User> findAllByPattern(String pattern) {
        List<User> users = userRepository.findByUsernameLike(pattern);
        users.addAll(userRepository.findByFirstnameLike(pattern));
        users.addAll(userRepository.findByLastnameLike(pattern));
        return users.stream().filter(User::getEnabled).collect(Collectors.toList());
    }

    public List<User> getNewUsers() {

        return userRepository.findFirst15ByOrderByCreationDateTime().stream()
                .filter(User::getEnabled).collect(Collectors.toList());
    }

    public Boolean updateBio(String username, String bio) {
        Optional<User> userOpt = userRepository.findByUsername(username);

        if(userOpt.isEmpty()) {
            return Boolean.FALSE;
        }

        User user = userOpt.get();

        user.setBio(bio);
        return Boolean.TRUE;

    }


    public boolean updatePicture(String username, Integer id) {
        Optional<User> userOpt = userRepository.findByUsername(username);

        if(userOpt.isEmpty()) {
            return Boolean.FALSE;
        }

        User user = userOpt.get();

        user.setProfilePicture(id);
        return Boolean.TRUE;

    }

    public List<User> findAll() {
        return userRepository.findAll().stream().filter(User::getEnabled).collect(Collectors.toList());
    }
}
