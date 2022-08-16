//package ro.itschool.entity;
//
//import jakarta.persistence.*;
//import lombok.Getter;
//import lombok.Setter;
//import lombok.ToString;
//import ro.itschool.enums.Style;
//
//import java.io.Serial;
//import java.io.Serializable;
//import java.util.UUID;
//
//@Getter
//@Setter
//@Entity
//@ToString
//public class Location implements Serializable {
//
//    @Serial
//    private static final long serialVersionUID = 1905122041950251207L;
//
//    @Id
//    @GeneratedValue
//    private UUID id = UUID.randomUUID();
//
//    private String name;
//    @Enumerated(EnumType.STRING)
//    private Style movement;
//
//    private Boolean isTemporary;
//
//    private Integer year;
//
//    private String country;
//
//
//    @ManyToOne
//    @JoinColumn(name = "user_id")
//    @ToString.Exclude
//    private User user;
//
//}
