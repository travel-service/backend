# JWT Token 

현재 AccessToken 유효시간 : 1m \
현재 RefreshToken 유효시간 : 2m


## 1. /api/signup 

![image](https://user-images.githubusercontent.com/76652908/163234084-ac18bc6d-f8fd-459b-911d-95a56fc7df99.png)

## 2. /api/login
1. response Header 확인
2. accessToken value, refreshToken Set-Cookie value 확인

![image](https://user-images.githubusercontent.com/76652908/163234178-9caaec72-4c65-4070-8ed2-050d895c9424.png)

## 3. /api/user/test
(현재)
1. Reqeust Header에 AccessToeken, Cookie에 refreshToken 심어서 요청

AccessToken 유효 = TOKEN OK 메시지 \
AccessToken 만료, RefreshToken 유효 = response Header에 newAccessToken 발행, TOKEN OK 메시지 \
AccessToken 만료, RefreshToken 만료 = Denied

![image](https://user-images.githubusercontent.com/76652908/163234501-b3c9c1a8-191f-4816-a1d7-54bb3e9050b2.png)


# 이미지 업로드, 다운로드
### 네이밍 규칙 생성 필요
## 1. /upload 
![file1](https://user-images.githubusercontent.com/76652908/168602996-1a6bfa12-3f24-4129-b340-41623daf9cd5.PNG)

## 2. /download/파일명
![file2](https://user-images.githubusercontent.com/76652908/168603062-c8fa11ed-ca84-4722-a078-40d92d2a7d22.PNG)

### 파일명을 어떻게 알아내나요?
upload 요청 시 return 값으로 /경로/파일명 돌려줍니다.
