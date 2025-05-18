# Woori\_Dream\_Car\_Dream

Woori Hackathon Spring Boot Server

# YouTube

[https://youtu.be/KcUvs8I4H-I](https://youtu.be/KcUvs8I4H-I)

# Technology

Spring Boot, Spring Webflux, MySQL

# Server Architecture & Feature Flow
```mermaid
sequenceDiagram
    participant User as User
    participant SpringBoot as Spring Boot
    participant Flask as Flask
    participant WooriBank as New Car Loan Inquiry API
    participant MySQL as MySQL

    User ->> SpringBoot: ① Enter income, loan limit, spending beliefs, and request car recommendations
    SpringBoot ->> Flask: ② Request car recommendations based on spending beliefs
    Flask -->> SpringBoot: ③ Return recommended car list
    SpringBoot ->> WooriBank: ④ Request car loan limit using user income and car price
    WooriBank -->> SpringBoot: ⑤ Return user-specific loan limit for the car
    SpringBoot ->> MySQL: ⑥ Request car details (name, price, image) for cars within loan limit
    MySQL -->> SpringBoot: ⑦ Return car details (name, price, image)
    SpringBoot -->> User: ⑧ Return list of cars within the user's loan limit
```

# Database ERD

![image](https://user-images.githubusercontent.com/53392870/120098752-ab577480-c172-11eb-9203-11517e7e205f.png)

# Screen Layout

### Start Screen

<img src="https://user-images.githubusercontent.com/53392870/117307503-982aef00-aebb-11eb-951a-b71d8efc4309.png">

### Select Basic Information

You must enter your annual income and loan limit.

<img src="https://user-images.githubusercontent.com/53392870/117307501-97925880-aebb-11eb-91da-50e4637762e9.png" width="50%"><img src="https://user-images.githubusercontent.com/53392870/117307493-95c89500-aebb-11eb-9e48-82066a7a8ee8.png" width="50%">

### What Are Your Spending Beliefs?

Select 2 or 3 spending beliefs that apply to you.

<img src="https://user-images.githubusercontent.com/53392870/117307518-99f4b280-aebb-11eb-99d1-012a13a42124.png" width="50%"><img src="https://user-images.githubusercontent.com/53392870/117307511-995c1c00-aebb-11eb-8962-b833e46fdac4.png" width="50%">

### Check the Loan Limits for the Recommended Cars

<img src="https://user-images.githubusercontent.com/53392870/117307526-9b25df80-aebb-11eb-9825-03baa82593e2.png" width="70%">

### View Details

<img src="https://user-images.githubusercontent.com/53392870/117307520-9a8d4900-aebb-11eb-9f4f-cf2758e8a6f3.png" width="70%">

---

# Woori_Dream_Car_Dream
우리은행 해커톤 Spring boot 서버

# Youtube
https://youtu.be/KcUvs8I4H-I

# 기술
Spring Boot

# 서버 구조 및 기능 흐름도
![image](https://user-images.githubusercontent.com/53392870/120098792-d17d1480-c172-11eb-9dd3-fabae2414d0d.png)


# DB ERD
![image](https://user-images.githubusercontent.com/53392870/120098752-ab577480-c172-11eb-9203-11517e7e205f.png)

# 화면 구성
### 시작화면
<img src="https://user-images.githubusercontent.com/53392870/117307503-982aef00-aebb-11eb-951a-b71d8efc4309.png">

### 기본 항목 선택하기
사용자의 연소득과 대출한도는 반드시 입력해야합니다.

<img src="https://user-images.githubusercontent.com/53392870/117307501-97925880-aebb-11eb-91da-50e4637762e9.png" width="50%"><img src="https://user-images.githubusercontent.com/53392870/117307493-95c89500-aebb-11eb-9e48-82066a7a8ee8.png" width="50%">

### 어떤 소비신념을 가지셨나요
2개 또는 3개의 사용자 소비신념을 선택합니다.

<img src="https://user-images.githubusercontent.com/53392870/117307518-99f4b280-aebb-11eb-99d1-012a13a42124.png" width="50%"><img src="https://user-images.githubusercontent.com/53392870/117307511-995c1c00-aebb-11eb-8962-b833e46fdac4.png" width="50%">

### 추천한 차들의 대출한도를 확인하세요

<img src="https://user-images.githubusercontent.com/53392870/117307526-9b25df80-aebb-11eb-9825-03baa82593e2.png" width="70%">

### 자세히 보기
<img src="https://user-images.githubusercontent.com/53392870/117307520-9a8d4900-aebb-11eb-9f4f-cf2758e8a6f3.png" width="70%">
