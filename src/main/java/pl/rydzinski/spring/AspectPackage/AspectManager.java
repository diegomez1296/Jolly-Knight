package pl.rydzinski.spring.AspectPackage;

import javafx.util.converter.LocalDateTimeStringConverter;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import pl.rydzinski.spring.Repositories.CharacterRepository;
import pl.rydzinski.spring.Repositories.InventoryRepository;
import pl.rydzinski.spring.Repositories.ItemRepository;
import pl.rydzinski.spring.Repositories.UserRepository;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Aspect
@Controller
public class AspectManager {

    private UserRepository userRepository;
    private CharacterRepository characterRepository;
    private InventoryRepository inventoryRepository;
    private ItemRepository itemRepository;

    private DateTimeFormatter dateTimeFormatter;
    private LocalDateTime localDateTime;

    private Log log;

    @Autowired
    public AspectManager(UserRepository userRepository, CharacterRepository characterRepository,
                         InventoryRepository inventoryRepository, ItemRepository itemRepository) {
        this.userRepository = userRepository;
        this.characterRepository = characterRepository;
        this.inventoryRepository = inventoryRepository;
        this.itemRepository = itemRepository;

        log = LogFactory.getLog(AspectManager.class);
    }


    @Pointcut("execution(* pl.rydzinski.spring.*.*.*(..))")
    private void generalPointcut() {}

    @Before("generalPointcut()")
    public void infoLog(JoinPoint joinPoint) throws FileNotFoundException {

        log.info(joinPoint.getSignature());
    }

    // @After("@annotation(AOPAdvice)")
    public void copyOfDatabase() throws FileNotFoundException {

        dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH_mm_ss");
        localDateTime = LocalDateTime.now();

        System.out.println(localDateTime.toString());
        PrintWriter saveUsers = new PrintWriter("users_" + localDateTime.format(dateTimeFormatter) + ".txt");
        saveUsers.println(userRepository.findAll().toString() + "---|||---" + characterRepository.findAll().toString() + "---|||---" + inventoryRepository.findAll().toString() + "---|||---" + itemRepository.findAll().toString());
        saveUsers.close();
    }
}
