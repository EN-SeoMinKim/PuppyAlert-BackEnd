package seominkim.puppyAlert.domain.host.service;
import jakarta.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import seominkim.puppyAlert.domain.host.entity.Host;
import seominkim.puppyAlert.global.dto.LoginRequestDTO;
import seominkim.puppyAlert.global.dto.SignUpRequestDTO;
import seominkim.puppyAlert.global.entity.Location;

import java.time.LocalDate;

@SpringBootTest
public class HostServiceTest {

    // 일반적으로, 테스트 코드에서 서비스 클래스를 테스트할 때 해당 서비스 클래스에서 사용하는
    // 리포지토리나 다른 의존성을 명시적으로 주입하지 않아도 됩니다.
    // 서비스 클래스는 스프링의 의존성 주입(DI)에 의해 자동으로 리포지토리 빈을 주입받기 때문입니다.
    @Autowired HostService hostService;
    @Autowired EntityManager em;

    @Test
    @Transactional
    @Rollback
    public void signUpTest(){
        // given
        String testHostId = "Havertz";

        SignUpRequestDTO signUpDTO = new SignUpRequestDTO();
        signUpDTO.setId(testHostId);
        signUpDTO.setPassword("29");
        signUpDTO.setName("하베르츠");
        signUpDTO.setBirth(LocalDate.now());
        signUpDTO.setLocation(new Location(100.135135, 135.12435));
        signUpDTO.setPhoneNumber("010-4822-3636");

        // when
        String findId = hostService.signUp(signUpDTO);

        // then
        Host findHost = hostService.findById(findId);
        Assertions.assertThat(findHost.getHostId()).isEqualTo(testHostId);
    }

    @Test
    @Transactional
    @Rollback
    public void loginTest(){
        // given
        Host host = new Host();
        host.setHostId("mbappe");
        host.setPassword("7");
        host.setName("음바페");
        host.setBirth(LocalDate.now());
        host.setLocation(new Location(133.4135,137.58357));
        host.setPhoneNumber("010-4822-3636");

        em.persist(host);

        // when
        LoginRequestDTO loginRequestDTO = new LoginRequestDTO();
        loginRequestDTO.setId(host.getHostId());
        loginRequestDTO.setPassword(host.getPassword());

        boolean isValidLogin = hostService.checkLogin(loginRequestDTO);

        // then
        Assertions.assertThat(isValidLogin).isTrue();
    }
}