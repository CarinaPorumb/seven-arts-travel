//package ro.itschool.scheduler;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import ro.itschool.repository.UserRepository;
//import ro.itschool.service.UserService;
//import ro.itschool.service.email.EmailBodyService;
//import ro.itschool.service.email.EmailSender;
//
//@Component
//public class MonthlyScheduler {
//
//    @Autowired
//    EmailBodyService emailBodyService;
//    @Autowired
//    EmailSender emailSender;
//
//    @Autowired
//    UserRepository userRepository;
//
//    @Autowired
//    UserService userService;

//    @Scheduled(cron = "30 10 ? * * 5L")   //At 10:30 a.m. on the last Thursday of every month
//    public void monthlyEmail() {

//        emailSender.sendEmail(user.getEmail(), "Discover the latest art destinations!", emailBodyService.emailBodyScheduler(user));
//}

//
//}
//
