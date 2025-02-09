# Clapper Board
![ClapperBoard_1](https://github.com/104735K/ClapperBoard/assets/170151340/de87d966-1db2-4ed6-9c72-396094d9cace)

JAVA + Spring Boot JDBC게시판 입니다.

## 💻 PROJECT INTRODUCTION
Clapper Board는 영화를 좋아하는 이들을 위한 공간으로, 다양한 영화 정보를 공유하고 소통할 수 있는 커뮤니티 입니다.

사용자들이 자유롭게 영화에 관한 이야기를 나누고, 다양한 영화를 추천하며 관련 정보를 공유할 수 있습니다.

<br>

✓ 자유게시판

정해진 양식없이 자유롭게 글을 작성할 수 있습니다. 댓글 기능으로 다른 사용자들과 소통하고, 여러 정보와 의견을 공유할 수 있습니다.

✓ 영화 추천 및 리뷰게시판

사용자들은 자신이 좋아하는 영화를 추천하고, 리뷰를 작성할 수 있습니다.

직접 영화에 평점을 주어 본인의 영화에 대한 시각과 의견을 공유할 수 있습니다.                    

## ⚙️ DEVELOPMENT ENVIRONMENT
- JAVA 17
- Framework : Spring Boot (version 3.2.5)
- Database : MySQL (version 8.3.0) / MySQL Workbench 
- Front : HTML/CSS, JavaScript
- Intellij IDEA, GitHub

## ✅ FEATURE
- 기본 CRUD (작성, 조회, 수정, 삭제)
- 게시글의 댓글 및 대댓글 
- 영화의 평점 및 리뷰 작성

## 🗓️ DEVELOPMENT PERIOD
2024.05.22 - 2024.06 ~ ing

## 📑 DEMO

|                               메인 페이지                               |                                자유게시판                             |                               영화추천게시판                               |
| :---------------------------------------------------------------------: | :---------------------------------------------------------------------: |:---------------------------------------------------------------------: |
|![ClapperBoard_1](https://github.com/104735K/ClapperBoard/assets/170151340/de87d966-1db2-4ed6-9c72-396094d9cace)|![ClapperBoard_2](https://github.com/104735K/ClapperBoard/assets/170151340/63e793b1-ef1e-4c03-b4d9-8322295ba89e) | ![ClapperBoard_3](https://github.com/104735K/ClapperBoard/assets/170151340/ee6324a3-9587-45c5-b66d-76ac392aba28)|

|                               영화리뷰 상세 페이지                               |                                댓글 및 대댓글                             |                  
| :---------------------------------------------------------------------: | :---------------------------------------------------------------------: |
|![ClapperBoard_4](https://github.com/104735K/ClapperBoard/assets/170151340/5b69ac8f-744c-44e1-8e43-76c05b4dfefc)|![ClapperBoard_4](https://github.com/104735K/ClapperBoard/assets/170151340/7a7c03e4-1bdf-4a2b-8755-7b3858982678)| 



## 📂 PROJECT STRUCTURE
```
./src
├── main
│   ├── java
│   │   └── com
│   │       └── movieboard
│   │           └── mboard
│   │               ├── MboardApplication.java
│   │               ├── controller
│   │               ├── dao
│   │               ├── dto
│   │               ├── service
│   │               ├── serviceImpl
│   │               ├── util
│   │               └── vo
│   └── resources
│       ├── application.yml
│       ├── static
│       │   └── css
│       └── templates
└── test

```
