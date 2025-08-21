package org.example.snsapp.domain.post.service;

import jakarta.validation.Valid;
import org.example.snsapp.domain.post.dto.PostBaseRequest;
import org.example.snsapp.domain.post.dto.PostBaseResponse;
import org.example.snsapp.domain.post.dto.PostPageResponse;
import org.example.snsapp.global.enums.SearchType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatusCode;

import java.net.URI;

public interface PostService {
    PostBaseResponse create(String loginUserEmail, @Valid PostBaseRequest postBaseRequest);

    Page<PostPageResponse> search(String keyword, SearchType searchType, Pageable pageable);
}
