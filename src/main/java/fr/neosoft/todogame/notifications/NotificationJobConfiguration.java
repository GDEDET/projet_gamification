//package fr.neosoft.todogame.notifications;
//
//import fr.neosoft.todogame.notifications.Notification;
//import fr.neosoft.todogame.personnes.Personne;
//import fr.neosoft.todogame.personnes.PersonneRepository;
//import fr.neosoft.todogame.taches.Tache;
//import fr.neosoft.todogame.taches.TacheRepository;
//import jakarta.persistence.EntityManagerFactory;
//import org.springframework.batch.core.*;
//import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
//import org.springframework.batch.core.configuration.support.DefaultBatchConfiguration;
//import org.springframework.batch.core.job.builder.JobBuilder;
//import org.springframework.batch.core.launch.JobLauncher;
//import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
//import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
//import org.springframework.batch.core.repository.JobRestartException;
//import org.springframework.batch.core.step.builder.StepBuilder;
//import org.springframework.batch.item.*;
//import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.scheduling.annotation.Scheduled;
//
//import java.time.LocalDate;
//import java.util.Collections;
//import java.util.Date;
//
//@Configuration
//@EnableBatchProcessing
//public class NotificationJobConfiguration extends DefaultBatchConfiguration {
//
//    private JobLauncher jobLauncher;
//    private TacheRepository tacheRepository;
//    private PersonneRepository personneRepository;
//    private NotificationService notificationService;
//
//    private EntityManagerFactory entityManagerFactory;
//
//    public void NotificationBatchConfiguration(JobLauncher jobLauncher, TacheRepository tacheRepository, PersonneRepository userRepository, NotificationService notificationService, EntityManagerFactory entityManagerFactory) {
//        this.jobLauncher = jobLauncher;
//        this.tacheRepository = tacheRepository;
//        this.personneRepository = userRepository;
//        this.notificationService = notificationService;
//        this.entityManagerFactory = entityManagerFactory;
//    }
//
//    @Scheduled(cron = "0 0 8 * * *") // Exécution quotidienne à 8h00
//    public void runNotificationJob() throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {
//        JobParameters jobParameters = new JobParametersBuilder()
//                .addDate("runDate", new Date())
//                .toJobParameters();
//        jobLauncher.run(notificationJob(), jobParameters);
//    }
//
//    @Bean
//    public Job notificationJob() {
//        return new JobBuilder()
//                .start(notificationStep())
//                .build();
//    }
//
//    @Bean
//    public Step notificationStep() {
//        return new StepBuilder("notificationStep")
//                .<Tache, Notification>chunk(10)
//                .reader(this.tacheReader())
//                .processor(tacheProcessor())
//                .writer(notificationWriter())
//                .build();
//    }
//
//    @Bean
//    public ItemReader<Tache> tacheReader() {
//        return new JpaPagingItemReaderBuilder<Tache>()
//                .name("tacheReader")
//                .entityManagerFactory(this.entityManagerFactory)
//                .pageSize(10)
//                .queryString("SELECT t FROM Tache t WHERE priorite = MOYENNE")
//                .parameterValues(Collections.singletonMap("currentDate", LocalDate.now()))
//                .build();
//    }
//
//    @Bean
//    public ItemProcessor<Tache, Notification> tacheProcessor() {
//        return tache -> {
//            Personne personne = tache.getPersonne();
//            String message = "Reminder: You have an upcoming task: " + tache.getDescription();
//            return new Notification(personne.getEmail(), message);
//        };
//    }
//
//    @Bean
//    public ItemWriter<Notification> notificationWriter() {
//        return notifications -> {
//            for (Notification notification : notifications) {
//                notificationService.sendNotification(notification);
//            }
//        };
//    }
//}
