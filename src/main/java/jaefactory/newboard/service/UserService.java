package jaefactory.newboard.service;


import jaefactory.newboard.domain.user.User;
import jaefactory.newboard.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public User join(User user){
        String rawPassword = user.getPassword();
        String encPassword = bCryptPasswordEncoder.encode(rawPassword);

        user.changePassword(encPassword);
        user.setRole("Role_USER");
        User userEntity = userRepository.save(user);
        return userEntity;
    }

    @Transactional(readOnly = true)
    public User findUser(String username){
        User user = userRepository.findByUsername(username).orElseThrow(() ->{
            throw new NoSuchElementException("조회 실패");
        });
        return user;
    }

    @Transactional
    public void userUpdate(User user) {
        User persistence = userRepository.findById(user.getId()).orElseThrow(()->{
            return new IllegalArgumentException("회원 찾기 실패");
        });

        // Validate 체크 => oauth 필드에 값이 없으면 수정 가능
        if(persistence.getOauth() == null || persistence.getOauth().equals("")) {
            String rawPassword = user.getPassword();
            String encPassword = bCryptPasswordEncoder.encode(rawPassword);
            persistence.changePassword(encPassword);
            persistence.updateName(user.getName());
        }
    }
}
