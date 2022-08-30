# UtilTool
## Description
서버에 유용한 기능들을 추가할 수 있게 해주는 플러그인입니다.

### Use Spigot Version:
1.17.1
### Tested Spigot Version:
1.17.1, 1.18.2
### Required Plugin:
PlaceholderAPI (Optional)

## Install Guide
1. 최신 버전의 플러그인 파일을 다운로드합니다.
2. 다운로드한 *.jar 파일을 플러그인 디렉토리에 저장합니다.
## Feature

### 첫 입장, 입장, 퇴장 메시지 기능
해당 기능은 PlaceholderAPI를 지원합니다.  
config.yml 양식은 아래와 같습니다.
```yaml
first_time_join_message: ""
join_message: ""
leave_message: ""
```
first_time_join_message - 첫 입장 메시지  
join_message - 입장 메시지  
leave_message - 퇴장 메시지

색을 사용하시려면 마인크래프트 색상 코드를 사용해 주세요.

사용 예시:
```yaml
join_message: "&e&l환영합니다!"
```
이 메시지 기능은 접속한 플레이어가 첫 입장인지 아닌지를 구별하기 위해
World 폴더의 playerdata 폴더에서 파일을 읽어오는 것이 아닌,
UtilTool 플러그인 폴더안에 존재하는 players.yml의 UUIDs를 읽어옵니다.  
따라서, UtilTool의 버전을 업데이트 할 시에 다음 과정을 따라야합니다.:
1. UtilTool 플러그인을 업데이트 하기 전, players.yml을 백업해 둡니다.
2. 새로운 버전으로 업데이트 합니다.
3. 서버를 열고 닫습니다.
4. 백업해둔 players.yml 파일을 새 players.yml 파일에 붙여넣기합니다.
5. 마지막으로, 서버를 열어 players.yml의 UUIDs가 정상적으로 백업 되었는지 확인합니다.
### 스폰 기능
/setspawn 명령어를 통해 현재 플레이어가 위치한 곳을 스폰 지점으로 지정할 수 있습니다.  
/spawn, /tmvhs, /스폰, /넴주 명령어를 통해 지정한 스폰 지점으로 이동할 수 있습니다.  
### VOID 방지 기능
플레이어가 세계 밖(y: 0)으로 떨어졌을 때 스폰으로 텔레포트됩니다.
### 채팅청소 기능
/채팅청소 명령어를 통해 자신의 채팅창을 청소합니다. (타인의 채팅창은 청소되지 않음.)
/전체채팅청소 명령어를 통해 전체의 채팅창을 청소합니다. (관리자 전용)
### 확성기 기능
/확성기 명령어를 통해 자신의 채팅을 강조를 할 수 있습니다. (쿨타임 5분)  
### 커스텀 메시지 명령어 기능
commands.yml을 통해 설정한 특정 명령어를 입력할 경우 할당된 메시지를 전송합니다.  
커스텀 메시지 명령어 기능은 PlaceholderAPI를 지원합니다.
### 귓속말 기능
/귓속말 명령어를 통해 다른 사람에게 귓속말을 전달할 수 있습니다.
### 귓속말 차단,차단해제 기능
/차단 명령어를 통해 다른 사람의 귓속말을 차단할 수 있습니다.  
/차단해제 명령어를 통해 귓속말 차단을 해제할 수 있습니다.

사용 예시:
```yaml
Commands:
  마인리스트:
    - "https://minelist.kr/"
  디스코드:
    - "디스코드는 현재 개발 중에 있습니다."
  카페:
    - "현재 운영 중인 카페가 없습니다."  
```
메시지에 색을 사용하시려면 마인크래프트 색상 코드를 사용해 주세요.

사용 예시:
```yaml
Commands:
  마인리스트:
    - "&lhttps://minelist.kr/"
  디스코드:
    - "&9디스코드는 현재 개발 중에 있습니다."
  카페:
    - "&3현재 운영 중인 카페가 없습니다."  
```

### 쉬프트+우클릭 명령어 기능
다른 플레이어에게 웅크린채로 우클릭을 하면 설정한 명령어가 실행되는 기능입니다.
config.yml 양식은 아래와 같습니다.
```yaml
shift_right_click_command: ""
enable_world: ""
```
shift_right_click_command - 다른 플레이어에게 웅크린채로 우클릭을 했을때 실행되는 명령어.
enable_world - 해당 기능을 쓸 수 있는 월드 리스트.
클릭당한 플레이어의 이름을 가져오려면 %player%를 사용해 주세요.

사용 예시:
```yaml
shift_right_click_command: "msg %player% Hello!"
enable_world:
  - "world"
  - "world_nether"
```


## Commands
플러그인 리로드 하기: /utiltool reload  
스폰 지점 정하기: /setspawn  
스폰으로 이동하기: /spawn, /tmvhs, /스폰, /넴주  
개인 채팅 청소: /채팅청소  
전체 채팅 청소: /전체채팅청소
## Permissions
```yaml
permissions:
  utiltool.reload:
    default: op
  utiltool.spawn:
    default: true
  utiltool.setspawn:
    default: op
  utiltool.mychatclear:
    default: true
  utiltool.allchatclear:
    default: op
  utiltool.broadcaster:
    default: true
  utiltool.whisper:
    default: true
```
