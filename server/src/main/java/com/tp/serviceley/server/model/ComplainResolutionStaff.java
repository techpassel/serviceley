package com.tp.serviceley.server.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "complain_resolution_activity")
public class ComplainResolutionStaff {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Complain complain;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "staff_id")
    private Staff staff;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "added_by")
    private Staff invitedBy;
    //Any staff can use this field to invite some other staff for the resolution of the complaint.

    @Column(name = "adding_message")
    private String invitationMessage;
    //Staff will use this field to explain why he/she have invited the other staff.

    @Column(name = "is_invitation_accepted")
    private boolean isInvitationAccepted;

    private String remark;
    //Staff can use this field in situations like suppose he/she is invited by some staff and
    //he/she can't handle the complaint then he can use this field for explaining why he/she can't do so.
}
