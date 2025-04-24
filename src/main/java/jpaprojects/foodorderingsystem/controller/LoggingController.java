package jpaprojects.foodorderingsystem.controller;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class LoggingController {

    @GetMapping("/log-test")
    public String logTest() {
        log.info("INFO log testi: Tətbiq düzgün işləyir!");
        log.error("ERROR log testi: Tətbiqdə xəta baş verdi!");
        return "Loglar yazıldı!";
    }
}
