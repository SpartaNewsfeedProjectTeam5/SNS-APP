package org.example.snsapp.domain.follow.dto;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
public class FollowerResponse {

    private final List<FollowerDto> followers;

    private final long totalCount;

    private final int currentPage;

    private final int totalPage;

    @Builder
    private FollowerResponse(List<FollowerDto> followers, long totalCount, int currentPage, int totalPage) {
        this.followers = followers;
        this.totalCount = totalCount;
        this.currentPage = currentPage;
        this.totalPage = totalPage;
    }

    public static FollowerResponse create(Page<FollowerDto> page) {
        return FollowerResponse.builder()
                .followers(page.getContent())
                .totalCount(page.getTotalElements())
                .currentPage(page.getNumber() + 1)
                .totalPage(page.getTotalPages())
                .build();
    }
}
