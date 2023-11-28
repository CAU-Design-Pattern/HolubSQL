![image](https://github.com/CAU-Design-Pattern/HolubSQL/assets/110660221/8860f990-d9ff-441c-9475-2400c6a8c2c1)## 개발환경
### IDE
Eclipse, IntelliJ
### Language
Java 8 (jdk1.8)
Junit 5

## 에러 시 해결 방법
이클립스에서 에러나는 경우 해결 방법

프로젝트 우클릭 properties 클릭 후 다음과 같이 진행
![284037291-fa77afc5-1242-4957-85f7-6c2aaa0340ec](https://github.com/CAU-Design-Pattern/HolubSQL/assets/39735744/bc0cf32b-9f78-4dcb-98d6-a3454fbcf024)
![284037387-900210e7-6d70-4443-81a1-2a4c11c5c05e](https://github.com/CAU-Design-Pattern/HolubSQL/assets/39735744/2e04559f-47d3-440c-92b7-638abac2ff37)
![284037398-5d88a017-649e-4580-a4d8-14d4d8a7bc7e](https://github.com/CAU-Design-Pattern/HolubSQL/assets/39735744/c07d204f-61ec-4df9-accd-1c2b2786a623)
![284037408-77aed6fb-7879-4d26-80fa-23ff9f3e4359](https://github.com/CAU-Design-Pattern/HolubSQL/assets/39735744/3e1074e6-bfe6-4cf1-a28f-59c181f95652)
![284037415-01d17c59-6cdb-4187-a7f8-a3b61aaf41b8](https://github.com/CAU-Design-Pattern/HolubSQL/assets/39735744/947c4163-d415-429b-af5a-69d34c6cdf85)
![284037420-99b0f44b-2b3d-4c50-8413-75e93f50ea7a](https://github.com/CAU-Design-Pattern/HolubSQL/assets/39735744/750a78fa-11db-4e14-8fee-aa20caf33b1f)
![284037439-23f3a946-7b6b-4360-9c81-e7cca534d944](https://github.com/CAU-Design-Pattern/HolubSQL/assets/39735744/7d159a0d-6990-46da-a1ae-a828589aeaca)


## HTMLExporterTest 문제
### Jsoup 설치
HTMLExporterTest가 JSoup라이브러리를 사용해서 추가 설치해야합니다.  
https://jsoup.org/download  
jsoup-1.17.1.jar core library 클릭해서 jar 파일 다운  
![스크린샷 2023-11-28 210429](https://github.com/CAU-Design-Pattern/HolubSQL/assets/110660221/d679c82c-2ae2-4810-a949-be376dc53b5f)  
HolubSQL폴더 우클릭 -> Properties -> Libraries  
![스크린샷 2023-11-28 210406](https://github.com/CAU-Design-Pattern/HolubSQL/assets/110660221/b77d0134-ed75-453e-8221-8a5e539d449d)  
Libraries -> Add External JARs -> 다운받은 Jsoup jar 파일 다운  
그래도 문제있으면 HTMLExporterTest Delete하고 빌드

