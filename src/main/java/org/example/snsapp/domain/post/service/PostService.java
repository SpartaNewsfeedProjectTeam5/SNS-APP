package org.example.snsapp.domain.post.service;

import jakarta.validation.Valid;
import org.example.snsapp.domain.post.dto.PostBaseRequest;
import org.example.snsapp.domain.post.dto.PostBaseResponse;

import java.net.URI;

public interface PostService {
    PostBaseResponse create(String loginUserEmail, @Valid PostBaseRequest postBaseRequest);
}
