# SNS-APP

 팔로우와 알람기능이 있는 뉴스피드 프로젝트 입니다.

## Team Members

| 이름   | 역할  | 담당 기능 |
|--------|-------|-----------|
| 김기수 | BE    | 개시물 CRUD, 알람 기능능 |
| 박혜정 | BE    | 유저 프로필, 팔로우 기능 |
| 이동재 | BE    | 댓글 CRUD |
| 정은서 | BE    | 사용자 인증 기능 |

## Tech Stack
- **Backend:** Java 17, Spring Boot, Spring Data JPA
- **Database:** MySQL
- **Security:** Session, LoginFilter
- **API 문서:** Markdown

---


## API Reference

**BaseURI** : `/api/v1`

## auth
### 회원가입 (Signup)
**Endpoint:** `POST /auth/signup`

**Description:** 비회원 사용자 회원가입 처리

**Request Body**
```json
{
  "username": "user",
  "age": 20,
  "email": "user@example.com",
  "password": "Password123@"
}
```
**Response (Success)**
```json
{
  "message": "회원가입이 완료 되었습니다.",
  "email": "user@example.com",
  "createdAt": "2025-08-22T11:59:30.625994"
}
```

---


### 로그인 (Login)
**Endpoint:** `POST /auth/signup`

**Description:** 회원 로그인 처리

**Request Body**
```json
{
  "email": "user@example.com",
  "password": "Password123@"
}

```
**Response (Success)**
```json
{
    "email": "user@example.com",
    "message": "로그인이 완료 되었습니다.",
    "loginTime": "2025-08-25T10:53:54.8075289"
}
```


---
### 회원 탈퇴 (Withdrawal)
**Endpoint:** `DELETE /auth/withdrawal`

**Description:** 로그인한 사용자가 자신의 계정을 탈퇴 처리

**Request Body**
```json
{
  "email": "user@example.com",
  "password": "Password123@"
}

```
**Response (Success)**
```json
{
    "email": "user@example.com",
    "message": "로그인이 완료 되었습니다.",
    "loginTime": "2025-08-25T10:53:54.8075289"
}
```

---

### 로그아웃 (Logout)
**Endpoint:** `POST /auth/logout`

**Description:** 로그인한 사용자의 세션 로그아웃 처리

**Request Body**
```json
없음
```
**Response (Success)**
```json
{
  "message": "로그아웃이 완료 되었습니다.",
  "loggedOutAt": "2025-08-25T10:59:23.228615"
}
```

---
## user

### 프로필 조회 (Get Profile)
**Endpoint:** `GET /users/profile`

**Description:** 로그인한 사용자가 특정 사용자의 프로필 조회

**Query Parameters**
| 파라미터  | 타입     | 필수여부 | 설명          |
| ----- | ------ | ---- | ----------- |
| email | String | 필수   | 조회할 사용자 이메일 |


**Request Example**
```json
GET /api/v1/users/profile?email=user@example.com
```
**Response (Success)**
```json
{
    "email": "user@example.com",
    "username": "user",
    "age": 20,
    "profileImage": null,
    "followerCount": 0,
    "followingCount": 0
}
```


---

### 프로필 수정 (Update Profile)
**Endpoint:** `PUT /users/me/profile`

**Description:** 로그인한 사용자가 자신의 프로필 정보를 수정

**Request Body**
```json
{
  "username": "유저",
  "age": 25,
  "profileImage": "https://user@example.com/profiles/newimage.jpg"
}

```
**Response (Success)**
```json
{
    "email": "user@example.com",
    "username": "유저",
    "age": 25,
    "profileImage": "https://user@example.com/profiles/newimage.jpg",
    "followerCount": 0,
    "followingCount": 0,
    "modifiedAt": "2025-08-25T11:15:48.4352501"
}
```

---

### 비밀번호 수정 (Update Password)
**Endpoint:** `PUT /users/me/password`

**Description:** 로그인한 사용자가 자신의 비밀번호를 변경

**Request Body**
```json
{
  "currentPassword": "Password123@",
  "newPassword": "Newpassword123@"
}
```
**Response (Success)**
```json
{
    "message": "비밀번호가 성공적으로 변경 되었습니다.",
    "modifiedAt": "2025-08-22T15:14:07.391953"
}
```

----

## posts

### 게시물 작성 (Create Post)
**Endpoint:** `POST /posts`

**Description:** 로그인한 사용자가 새 게시물을 작성

**Request Body**
```json
{
  "title": "새로운 포스트",
  "content": "이것은 새로운 포스트의 내용입니다."
}
```
**Response (201 Created)**
```json
{
    "id": 1,
    "email": "user@example.com",
    "title": "새로운 포스트",
    "content": "이것은 새로운 포스트의 내용입니다.",
    "createdAt": "2025-08-25T11:20:04.16561",
    "modifiedAt": "2025-08-25T11:20:04.16561",
    "commentCount": 0,
    "likeCount": 0
}
```

---

### 로그인 유저 게시물 조회 (Get My Posts)
**Endpoint:** `GET /posts/myposts`

**Description:** 로그인한 사용자가 본인이 작성한 게시물 목록 조회

**Query Parameters**
| 파라미터      | 타입     | 기본값       | 설명                |
| --------- | ------ | --------- | ----------------- |
| page      | int    | 0         | 조회할 페이지 번호        |
| size      | int    | 10        | 한 페이지당 게시물 수      |
| sort      | String | createdAt | 정렬 기준             |
| direction | String | desc      | 정렬 방향 (ASC, DESC) |


**Request Example**
```json
GET /api/v1/posts/myposts?page=0&size=10&sort=createdAt&direction=desc
```
**Response (Success)**
```json
[
    {
        "id": 101,
        "email": "user@example.com",
        "title": "제목1",
        "content": "내용1",
        "createdAt": "2025-08-19T10:00:00",
        "modifiedAt": "2025-08-19T10:30:00",
        "commentCount": 3,
        "likeCount": 5
    },
    {
        "id": 102,
        "email": "user@example.com",
        "title": "제목2",
        "content": "내용2",
        "createdAt": "2025-08-18T12:00:00",
        "modifiedAt": "2025-08-18T12:30:00",
        "commentCount": 2,
        "likeCount": 3
    }
]
```

---

### 팔로잉 게시물 조회 (Get Following Posts)
**Endpoint:** `GET /posts/following`

**Description:** 로그인한 사용자가 팔로잉 중인 사용자의 게시물 목록 조회

**Query Parameters**
| 파라미터      | 타입     | 기본값       | 설명                |
| --------- | ------ | --------- | ----------------- |
| page      | int    | 0         | 조회할 페이지 번호        |
| size      | int    | 10        | 한 페이지당 게시물 수      |
| sort      | String | createdAt | 정렬 기준             |
| direction | String | desc      | 정렬 방향 (ASC, DESC) |


**Request Example**
```json
GET /api/v1/posts/following?page=0&size=10&sort=createdAt&direction=desc
```
**Response (Success)**
```json
[
    {
        "id": 3,
        "email": "h@example.com",
        "title": "모닝 커피",
        "content": "모닝엔 커피 한 잔과 함께 힘찬 시작",
        "createdAt": "2025-08-25T11:26:15.450153",
        "modifiedAt": "2025-08-25T11:26:15.450153",
        "commentCount": 0,
        "likeCount": 0
    },
    {
        "id": 2,
        "email": "h@example.com",
        "title": "즐거운 월요일",
        "content": "안녕하세요. 즐거운 월요일입니다.",
        "createdAt": "2025-08-25T11:25:45.009571",
        "modifiedAt": "2025-08-25T11:25:45.009571",
        "commentCount": 0,
        "likeCount": 0
    }
]
```

---


### 게시물 검색 (Search Posts)
**Endpoint:** `GET /posts/search`

**Description:** 로그인한 사용자가 키워드 기반으로 게시물 검색

**Query Parameters**
| 파라미터       | 타입     | 기본값       | 설명                                          |
| ---------- | ------ | --------- | ------------------------------------------- |
| keyword    | String | -         | 검색 키워드 (제목, 내용, 작성자 이메일 등)                  |
| searchType | String | -         | 검색 대상 (`title`, `content`, `email`, `date`) |
| page       | int    | 0         | 조회할 페이지 번호                                  |
| size       | int    | 10        | 한 페이지당 게시물 수                                |
| sort       | String | createdAt | 정렬 기준                                       |
| direction  | String | desc      | 정렬 방향 (ASC, DESC)                           |


**Request Example**
email 검색 예시:
```json
GET /api/v1/posts/search?keyword=user@example.com&searchType=email
```
Date 타입 검색 예시:
```json
GET /api/v1/posts/search?keyword=20250825~20250825&searchType=date
```
**Response (Success)**
```json
[
    {
        "id": 3,
        "email": "h@example.com",
        "title": "모닝 커피",
        "content": "모닝엔 커피 한 잔과 함께 힘찬 시작",
        "createdAt": "2025-08-25T11:26:15.450153",
        "modifiedAt": "2025-08-25T11:26:15.450153",
        "commentCount": 0,
        "likeCount": 0
    },
    {
        "id": 2,
        "email": "h@example.com",
        "title": "즐거운 월요일",
        "content": "안녕하세요. 즐거운 월요일입니다.",
        "createdAt": "2025-08-25T11:25:45.009571",
        "modifiedAt": "2025-08-25T11:25:45.009571",
        "commentCount": 0,
        "likeCount": 0
    },
    {
        "id": 1,
        "email": "user@example.com",
        "title": "새로운 포스트",
        "content": "이것은 새로운 포스트의 내용입니다.",
        "createdAt": "2025-08-25T11:20:04.16561",
        "modifiedAt": "2025-08-25T11:20:04.16561",
        "commentCount": 0,
        "likeCount": 0
    }
]
```

---

### 게시글 수정 (Update Post)
**Endpoint:** `PUT /posts/{postId}`

**Description:** 로그인한 사용자가 본인의 게시글을 수정

**Path Variables**
| 파라미터   | 타입   | 설명         |
| ------ | ---- | ---------- |
| postId | Long | 수정할 게시물 ID |


**Request Body**
```json
{
  "title": "수정된 게시물 제목",
  "content": "수정된 게시물 내용"
}
```
**Response (Success)**
```json
{
    "id": 1,
    "email": "user@example.com",
    "title": "수정된 게시물 제목",
    "content": "수정된 게시물 내용",
    "createdAt": "2025-08-25T11:20:04.16561",
    "modifiedAt": "2025-08-25T11:20:04.16561",
    "commentCount": 0,
    "likeCount": 0
}
```

---

### 게시글 삭제 (Delete Post)
**Endpoint:** `DELETE /posts/{postId}`

**Description:** 로그인한 사용자가 본인의 게시글 삭제

**Path Variables**
| 파라미터   | 타입   | 설명         |
| ------ | ---- | ---------- |
| postId | Long | 삭제할 게시물 ID |


**Request Example**
```json
DELETE /posts/1
```
**Response (Success)**
- 200 0K 

---

### 게시글 좋아요 생성 (Like Post)
**Endpoint:** `POST /posts/{postId}/likes`

**Description:** 로그인한 사용자가 특정 게시글에 좋아요를 생성

**Path Variables**
| 파라미터   | 타입   | 설명             |
| ------ | ---- | -------------- |
| postId | Long | 좋아요를 누를 게시물 ID |


**Request Example**
```json
POST /posts/2/likes
```
**Response (Success)**
```json
{
    "id": 2,
    "email": "h@example.com",
    "title": "즐거운 월요일",
    "content": "안녕하세요. 즐거운 월요일입니다.",
    "createdAt": "2025-08-25T11:25:45.009571",
    "modifiedAt": "2025-08-25T11:25:45.009571",
    "commentCount": 0,
    "likeCount": 1
}
```

---


### 게시글 좋아요 삭제 (Unlike Post)
**Endpoint:** `DELETE /posts/{postId}/likes`

**Description:** 로그인한 사용자가 특정 게시글의 좋아요를 취소

**Path Variables**
| 파라미터   | 타입   | 설명              |
| ------ | ---- | --------------- |
| postId | Long | 좋아요를 취소할 게시물 ID |


**Request Example**
```json
DELETE /posts/1/likes
```
**Response (Success)**
- 200 0K 


---

## comments

### 댓글 작성 (Create Comment)
**Endpoint:** `POST /posts/{postId}/comments`

**Description:** 로그인한 사용자가 특정 게시글에 댓글 작성

**Path Variables**
| 파라미터   | 타입   | 설명             |
| ------ | ---- | -------------- |
| postId | Long | 댓글을 작성할 게시물 ID |


**Request Body**
```json
{
  "content": "댓글 내용입니다."
}
```
**Response (201 Created)**
```json
{
    "id": 1,
    "postId": 2,
    "email": "user@example.com",
    "content": "댓글 내용입니다.",
    "likeCount": 0,
    "createdAt": "2025-08-25T11:41:09.4944126",
    "modifiedAt": "2025-08-25T11:41:09.4944126"
}
```

---


### 게시물 댓글 조회 (Get Post Comments)
**Endpoint:** `GET /posts/{postId}/comments`

**Description:** 로그인한 사용자가 특정 게시글의 댓글 목록 조회

**Query Parameters**
| 파라미터      | 타입     | Default   | 설명                |
| --------- | ------ | --------- | ----------------- |
| page      | int    | 0         | 조회할 페이지 번호        |
| size      | int    | 10        | 한 페이지당 댓글 수       |
| sort      | String | createdAt | 정렬 기준             |
| direction | String | desc      | 정렬 방향 (ASC, DESC) |


**Request Example**
```json
GET /posts/2/comments
```
**Response (200 OK)**
```json
[
    {
        "id": 2,
        "postId": 2,
        "email": "user@example.com",
        "content": "두 번째 댓글 작성",
        "likeCount": 0,
        "createdAt": "2025-08-25T11:45:15.680191",
        "modifiedAt": "2025-08-25T11:45:15.680191"
    },
    {
        "id": 1,
        "postId": 2,
        "email": "user@example.com",
        "content": "댓글 내용입니다.",
        "likeCount": 0,
        "createdAt": "2025-08-25T11:41:09.494413",
        "modifiedAt": "2025-08-25T11:41:09.494413"
    }
]
```

---

### 댓글 수정 (Update Comment)
**Endpoint:** `PUT /posts/{postId}/comments/{commentId}`

**Description:** 로그인한 사용자가 본인이 작성한 댓글을 수정

**Path Variables**
| 파라미터      | 타입   | 설명             |
| --------- | ---- | -------------- |
| postId    | Long | 댓글이 작성된 게시물 ID |
| commentId | Long | 수정할 댓글 ID      |


**Request Body**
```json
{
  "content": "수정된 댓글 내용입니다."
}
```
**Response (200 OK)**
```json
{
    "id": 1,
    "postId": 2,
    "email": "user@example.com",
    "content": "수정된 댓글 내용입니다.",
    "likeCount": 0,
    "createdAt": "2025-08-25T11:41:09.494413",
    "modifiedAt": "2025-08-25T11:41:09.494413"
}
```

---

### 댓글 삭제 (Delete Comment)
**Endpoint:** `DELETE /posts/{postId}/comments/{commentId}`

**Description:** 로그인한 사용자가 본인이 작성한 댓글을 삭제

**Path Variables**
| 파라미터      | 타입   | 설명             |
| --------- | ---- | -------------- |
| postId    | Long | 댓글이 작성된 게시물 ID |
| commentId | Long | 삭제할 댓글 ID      |


**Request Example**
```json
DELETE /posts/2/comments/1
```
**Response**
- 204 No Content

---

### 댓글 좋아요 생성 (Like Comment)
**Endpoint:** `POST /posts/{postId}/comments/{commentId}/likes`

**Description:** 로그인한 사용자가 다른 사용자가 작성한 댓글에 좋아요를 누름

**Path Variables**
| 파라미터      | 타입   | 설명             |
| --------- | ---- | -------------- |
| postId    | Long | 댓글이 작성된 게시물 ID |
| commentId | Long | 좋아요를 누를 댓글 ID  |


**Request Example**
```json
POST /posts/2/comments/2/likes
```
**Response (200 OK)**
```json
{
    "id": 2,
    "postId": 2,
    "email": "user@example.com",
    "content": "두 번째 댓글 작성",
    "likeCount": 1,
    "createdAt": "2025-08-25T11:45:15.680191",
    "modifiedAt": "2025-08-25T11:45:15.680191"
}
```

---

### 댓글 좋아요 삭제 (Delete Comment Like)
**Endpoint:** `DELETE /posts/{postId}/comments/{commentId}/likes`

**Description:** 로그인한 사용자가 이전에 좋아요를 누른 댓글의 좋아요를 취소

**Path Variables**
| 파라미터      | 타입   | 설명             |
| --------- | ---- | -------------- |
| postId    | Long | 댓글이 작성된 게시물 ID |
| commentId | Long | 좋아요를 취소할 댓글 ID |


**Request Example**
```json
POST /posts/2/comments/2/likes
```
**Response (200 OK)**
```json
{
    "id": 2,
    "postId": 2,
    "email": "user@example.com",
    "content": "두 번째 댓글 작성",
    "likeCount": 0,
    "createdAt": "2025-08-25T11:45:15.680191",
    "modifiedAt": "2025-08-25T11:52:59.5377244"
}
```

---


## follows


### 팔로우 생성 (Follow User)
**Endpoint:** `POST /follows`

**Description:** 로그인한 사용자가 다른 사용자를 팔로우

**Request Body**
```json
{
"targetUserEmail": "h@example.com"
}
```
**Response (200 OK)**
```json
{
    "message": "팔로우가 완료 되었습니다.",
    "createdAt": "2025-08-25T11:26:30.0577068"
}
```

---


### 팔로우 취소 (Unfollow User)
**Endpoint:** `POST /follow/unfollow`

**Description:** 로그인한 사용자가 팔로우한 사용자를 언팔로우

**Request Body**
```json
{
  "targetUserEmail": "targetUserId@example.com"
}
```
**Response (200 OK)**
```json
{
  "message": "언팔로우가 완료되었습니다"
}
```

---

### 로그인 유저 팔로워 조회 (Get Followers)
**Endpoint:** `GET /follows/followers`

**Description:** 로그인한 사용자의 팔로워 목록 조회

**Query Parameters**
| 파라미터      | 타입     | Default   | 설명                |
| --------- | ------ | --------- | ----------------- |
| page      | int    | 0         | 페이지 번호            |
| size      | int    | 10        | 페이지 크기            |
| sort      | String | createdAt | 정렬 기준             |
| direction | String | desc      | 정렬 방향 (ASC, DESC) |


**Request Example**
```json
GET /api/v1/follow/followers
```
**Response (200 OK)**
```json
{
    "followers": [
        {
            "email": "j@example.com",
            "username": "전우치"
        },
        {
            "email": "user@example.com",
            "username": "유저"
        }
    ],
    "totalCount": 2,
    "currentPage": 1,
    "totalPage": 1
}
```

---

## notifications


### 전체 알림 조회 (Get All Notifications)
**Endpoint:** `GET /notifications`

**Description:** 로그인한 사용자의 전체 알림 목록 조회

**Request Example**
```json
GET /api/v1/notifications
```
**Response (200 OK)**
```json
[
    {
        "message": "전우치님이 팔로우 하셨습니다.",
        "createdAt": "2025-08-25T11:59:06.453789"
    },
    {
        "message": "유저님이 팔로우 하셨습니다.",
        "createdAt": "2025-08-25T11:57:10.657367"
    },
    {
        "message": "유저님이 즐거운 월요일 게시글에 댓글을 남기셨습니다.",
        "createdAt": "2025-08-25T11:45:15.686311"
    },
    {
        "message": "유저님이 즐거운 월요일 게시글에 댓글을 남기셨습니다.",
        "createdAt": "2025-08-25T11:41:09.541012"
    },
    {
        "message": "유저님이 즐거운 월요일 게시글에 좋아요를 남기셨습니다.",
        "createdAt": "2025-08-25T11:38:26.961729"
    },
    {
        "message": "유저님이 팔로우 하셨습니다.",
        "createdAt": "2025-08-25T11:26:30.065732"
    }
]
```

---

### 알림 확인 삭제 (Delete Notifications)
**Endpoint:** `DELETE /api/notifications`

**Description:** 로그인한 사용자의 확인된 알림 삭제

**Request Example**
```json
GET /api/v1/notifications/1
```
**Response (200 OK)**
- No Content

---


## 에러 코드
| 구분   | HTTP Status      | 코드     | 메시지                                                  |
|--------|------------------|----------|---------------------------------------------------------|
| 값     | 400 Bad Request  | VAL-001  | 입력값이 유효하지 않습니다.                             |
| 게시물 | 404 NOT_FOUND    | PST-001  | 게시물을 찾을 수 없습니다.                              |
|        | 403 FORBIDDEN    | PST-002  | 자신의 게시물에는 좋아요를 누를 수 없습니다.            |
| 댓글   | 404 NOT_FOUND    | CMT-001  | 댓글을 찾을 수 없습니다.                                |
|        | 403 FORBIDDEN    | CMT-002  | 댓글에 대한 권한이 없습니다.                            |
|        | 403 FORBIDDEN    | CMT-003  | 자신의 댓글에는 좋아요를 누를 수 없습니다.              |
| 유저   | 409 CONFLICT     | USR-001  | 이미 가입된 이메일입니다.                               |
|        | 401 UNAUTHORIZED | USR-002  | 아이디 또는 비밀번호가 잘못되었습니다.                  |
|        | 404 NOT_FOUND    | USR-003  | 유저를 찾을 수 없습니다.                                |
|        | 401 UNAUTHORIZED | USR-004  | 로그인이 필요한 서비스 입니다.                          |
|        | 403 FORBIDDEN    | USR-005  | 권한이 없어 요청을 수행할 수 없습니다.                  |
|        | 400 Bad Request  | USR-006  | 현재 비밀번호와 동일한 비밀번호로는 변경할 수 없습니다. |
|        | 400 Bad Request  | USR-007  | 현재 비밀번호가 일치하지 않습니다.                      |
|        | 404 NOT_FOUND    | USR-008  | 탈퇴한 회원입니다.                                      |
| 좋아요 | 409 CONFLICT     | LIKE-001 | 이미 좋아요를 눌렀습니다.                               |
|        | 404 NOT_FOUND    | LIKE-002 | 좋아요를 누르지 않았습니다.                             |
| 팔로우 | 400 Bad Request  | FOW-001  | 자기 자신을 팔로우 할 수 없습니다.                      |
|        | 400 Bad Request  | FOW-002  | 자기 자신을 언팔로우 할 수 없습니다.                    |
|        | 400 Bad Request  | FOW-003  | 이미 팔로우 중인 사용자 입니다.                         |
|        | 400 Bad Request  | FOW-004  | 팔로우하지 않는 사용자 입니다.                          |
| 알람   | 404 NOT_FOUND    | NTF-001  | 알람을 찾을 수 없습니다.                                |
