package com.tp.serviceley.server.model;

import com.tp.serviceley.server.model.enums.UserType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "complain_message")
public class ComplainMessage extends CreateUpdateRecord{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "complain_id")
    private Complain complain;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private UserType userType;

    @Column(name = "is_internal_message")
    private boolean isInternalMessage;
    //Internal messages will not be displayed to users.

    //'messageFor' field will be used for internal messages in case one staff wants to add message for another staff.
    @ManyToOne
    @JoinColumn(name = "message_for")
    private Staff messageFor;

    @Column(columnDefinition = "TEXT", name = "message")
    private String message;

    private String file1;
    private String file2;
    private String file3;
}
