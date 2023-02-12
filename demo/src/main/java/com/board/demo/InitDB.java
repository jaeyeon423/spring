package com.board.demo;

import com.board.demo.domain.Role;
import com.board.demo.domain.RoleType;
import com.board.demo.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
@Profile("local")
public class InitDB {
    private final RoleRepository roleRepository;

    @PostConstruct
    public void initDB(){
        log.info("initialize database");
        initRole();
    }

    private void initRole(){
        roleRepository.saveAll(
                List.of(RoleType.values()).stream().map(roleType -> new Role(roleType)).collect(Collectors.toList())
        );
    }
}
