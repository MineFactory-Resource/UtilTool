# UtilTool
## Description
서버에 유용한 기능들을 추가할 수 있게 해주는 플러그인입니다.

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

/setspawn 명령어를 통해 현재 플레이어가 위치한 곳을 스폰 지점으로 지정할 수 있습니다.  
/spawn 을 통해 지정한 스폰 지점으로 이동할 수 있습니다.
## Commands
플러그인 리로드 하기: /utiltoolreload  
스폰 지점 정하기: /setspawn  
스폰으로 이동하기: /spawn
## Permissions
```yaml
utiltool.reload:
  default: op
utiltool.spawn:
  default: true
utiltool.setspawn:
  default: op
```