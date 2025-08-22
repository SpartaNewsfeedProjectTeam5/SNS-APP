package org.example.snsapp.domain.notification.service;


import lombok.RequiredArgsConstructor;
import org.example.snsapp.domain.notification.dto.NotificationResponse;
import org.example.snsapp.domain.notification.repository.NotificationRepository;
import org.example.snsapp.domain.notification.entity.Notification;
import org.example.snsapp.domain.user.entity.User;
import org.example.snsapp.domain.user.service.UserDomainService;
import org.example.snsapp.global.enums.ErrorCode;
import org.example.snsapp.global.enums.NotificationContentType;
import org.example.snsapp.global.exception.CustomException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService{
    private final NotificationRepository notificationRepository;
    private final UserDomainService userDomainService;

    @Transactional
    @Override
    public void create(User fromUser, User toUser, NotificationContentType type,Long typeId,String message) {
        // 자신의 게시물에 댓글을 달았을 때 방지
        if(Objects.equals(fromUser,toUser)) return;

        Notification notification = Notification.create(
                fromUser,
                toUser,
                type,
                typeId,
                message);

        notificationRepository.save(notification);
    }

    @Transactional(readOnly = true)
    @Override
    public List<NotificationResponse> findAllNotificationReceivedByEmail(String loginUserEmail) {
        User user = userDomainService.getUserByEmail(loginUserEmail);

        List<Notification> notifications = notificationRepository.findByToUser(user);

        return notifications.stream().map(NotificationResponse::create).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void delete(Long notificationId, String loginUserEmail) {
        Notification notification = notificationRepository.findById(notificationId).orElseThrow(
                ()-> new CustomException(ErrorCode.NOTIFICATION_NOT_FOUND));

        if(!MatchAuthorEmail(notification,loginUserEmail))
            throw new CustomException(ErrorCode.NO_PERMISSION);

        notificationRepository.delete(notification);
    }

    /**
     * 알람을 받은 유저의 이메일과 {@code email}을 비교해 반환
     *
     * @param notification  알람 엔티티
     * @param email 이메일
     * @return 같다면 true, 틀리다면 false
     */
    private boolean MatchAuthorEmail(Notification notification, String email) {
        return Objects.equals(notification.getToUser().getEmail(), email);
    }
}
