# JWT Token 가이드라인 

AccessToken = 짧은 유효기간, 사용자 검증용
RefreshToken = 긴 유효기간, AccessToken 재발급용

## 1. Token 사용법  
### Token 유효기간 설정
```
config/jwt/jwtTokenProvider 상단 값 변경, 60 * 1000L = 1분
```
![image](https://user-images.githubusercontent.com/76652908/169291173-dd69dfa9-1662-4f49-a732-986f9bc5cf0c.png)

### Token 으로 사용자 구분하기
```
1. Token은 헤더에 실려오기 때문에 HttpServletRequest 이용
2. jwtTokenProvider.resolveAccessToken(request) => AccessToken 흭득
3. AccessToken 으로 userId, userName(아이디) 등 흭득, 사용자 구분 가능
```
![image](https://user-images.githubusercontent.com/76652908/169294071-f0511b4e-a9f4-4c49-bf3f-1ace16e1f99f.png)  
![image](https://user-images.githubusercontent.com/76652908/169294984-8d0831d1-c800-458e-97c3-6f406a63d445.png)




## 2. PostMan 에서 Token 사용하기

### 1. /api/signup 회원가입
![image](https://user-images.githubusercontent.com/76652908/169292226-bbf3d5ad-5873-4b9d-95bd-05a781df5af6.png)


```
    "userName": "test",
    "password": "1234",
    "realName": "KDP",
    "birthday": "970825",
    "gender": "MALE",
    "phoneNum": "01012345678",
    "email": "test@naver.com",
    "nickName": "test"
```

## 2. /api/login 로그인
![image](https://user-images.githubusercontent.com/76652908/169292427-484e724a-6d13-4a63-911a-ed53a1e7b0fc.png)
```
    "userName": "test",
    "password": "1234"
```

흭득한 2개의 토큰 값은 **전체복사** 하여 아래 사진과 같이 **/api/user** 로 시작하는 API 요청에 필수 삽입  
Cookie = refreshToken  
AccessToken = accessToken


![image](https://user-images.githubusercontent.com/76652908/169292716-f013b142-f322-410b-9b55-d1bdc1fe5204.png)


# 이미지 업로드, 다운로드
### 네이밍 규칙 생성 필요
## 1. /upload 
![file1](https://user-images.githubusercontent.com/76652908/168602996-1a6bfa12-3f24-4129-b340-41623daf9cd5.PNG)

## 2. /download/파일명
![file2](https://user-images.githubusercontent.com/76652908/168603062-c8fa11ed-ca84-4722-a078-40d92d2a7d22.PNG)

### 파일명을 어떻게 알아내나요?
현재 프로필 사진 기능만 구현, 따라서 사용자이름.png 로 자동저장. 
