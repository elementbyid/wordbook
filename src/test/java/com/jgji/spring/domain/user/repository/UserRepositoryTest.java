package com.jgji.spring.domain.user.repository;

import com.jgji.spring.domain.user.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@TestPropertySource("classpath:application-test.properties")
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void findByUserName() {
        //given
        User user = new User();
        user.setUsername("jgji");
        user.setPassword("qwe123");

        userRepository.save(user);

        //when
        User when = userRepository.findByUsername("jgji");

        //than
        assertEquals(user.getUsername(), when.getUsername());
    }
}