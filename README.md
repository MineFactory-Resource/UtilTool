# PlayerJoinAndLeave
## Description
플레이어 입장 메시지를 커스텀 할 수 있도록 하는 플러그인입니다.

### Use Spigot Version:
1.17.1
### Tested Spigot Version:
1.17.1
### Required Plugin:
None

## Install Guide
1. 최신 버전의 플러그인 파일을 다운로드합니다.
2. 다운로드한 *.jar 파일을 플러그인 디렉토리에 저장합니다.
## Feature
config.yml 양식은 아래와 같습니다.
```yaml
first_time_join_message: ""
join_message: ""
leave_message: ""
```
first_time_join_message - 첫 입장 메시지  
join_message - 입장 메시지  
leave_message - 퇴장 메시지

플레이어의 이름을 가져오려면 [NAME]을 사용해 주세요.

사용 예시:
```yaml
join_message: "[NAME]님이 접속하셨습니다."
leave_message: "[NAME]님이 퇴장하셨습니다."
```
색을 사용하시려면 마인크래프트 색상 코드를 사용해 주세요.

사용 예시:
```yaml
join_message: "&e&l[NAME]&f님이 접속하셨습니다."
```
## Commands
None
## Permissions
None