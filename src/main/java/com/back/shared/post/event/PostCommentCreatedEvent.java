package com.back.shared.post.event;

import com.back.boundedContext.post.entity.Post;
import com.back.boundedContext.post.entity.PostComment;
import com.back.shared.post.dto.PostCommentDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostCommentCreatedEvent {

    private final PostCommentDto postCommentDto;
}
