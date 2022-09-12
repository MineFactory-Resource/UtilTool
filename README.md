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
UtilTool의 버전을 업데이트 할 시에 다음 과정을 따라야합니다 :
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

### 커스텀 명령어 기능
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
### 커스텀 메세지 기능  
UtilTool의 명령어 등을 실행했을 때 출력되는 메세지를  
사용자 임의로 변경할 수 있습니다.  

예시는 다음과 같습니다  
기본 message.yml 내용 :  
```
# first_time_join_message - 첫 입장 메시지
# join_message - 입장 메시지
# leave_message - 퇴장 메시지
# teleport_message - 플레이어가 텔레포트 될 때 출력되는 메시지
# create_mode_message - 플레이어가 크리에이티브 모드로 변경되었을 때 출력되는 메세지
# survival_mode_message - 플레이어가 서바이벌 모드로 변경되었을 때 출력되는 메세지
# adventure_mode_message - 플레이어가 모험 모드로 변경되었을 때 출력되는 메세지
# specter_mode_message - 플레이어가 관전자 모드로 변경되었을 때 출력되는 메세지
# target_create_mode_message - 플레이어가 크리에이티브 모드로 변경되었을 때 출력되는 메세지
# target_survival_mode_message - 플레이어가 크리에이티브 모드로 변경되었을 때 출력되는 메세지
# my_chat_clear_message - 플레이어가 자신의 채팅을 청소했을 때 출력되는 메세지
# all_chat_clear_message - 권한이 있는사람이나, 관리자가 전체 채팅을 청소했을 때 출력되는 메세지
# broadcast_command_message - 확성기 관련 명령어를 사용했을 때 출력되는 메세지
# broadcast_cooldown_message - 확성기의 쿨타임 메세지
# unknown_command_message - 알 수 없는 명령어를 사용했을 때 출력되는 메세지
# message_gm_0 - 게임모드 설명 메세지(서바이벌)
# message_gm_1 - 게임모드 설명 메세지(크리에이티브)
# message_gm_2 - 게임모드 설명 메세지(모험)
# message_gm_3 - 게임모드 설명 메세지(관전자)

# 색을 사용하시려면 마인크래프트 색상 코드를 사용해 주세요.
# 사용 예시:
# join_message: '&e&l환영합니다!'

first_time_join_message: '&e%player_name% &f님, 서버에 처음 오신걸 환영합니다!'
join_message: '&e%player_name% &f님이 서버에 입장하셨습니다!'
leave_message: '&e%player_name% &f님이 서버에 퇴장하셨습니다!'
set_spawn_message: '&f스폰 위치가 설정되었습니다!'
teleport_message: '&f텔레포트 되었습니다!'
create_mode_message: '&f게임모드가 크리에이티브 모드로 변경되었습니다!'
survival_mode_message: '&f게임모드가 서바이벌 모드로 변경되었습니다!'
adventure_mode_message: '&f게임모드가 모험 모드로 변경되었습니다!'
specter_mode_message: '&f게임모드가 관전자 모드로 변경되었습니다!'
target_create_mode_message: '&e%target% &f님의 게임모드가 크리에이티브 모드로 변경되었습니다!'
target_survival_mode_message: '&e%target% &f님의 게임모드가 서바이벌 모드로 변경되었습니다!'
my_chat_clear_message: '&f자신의 채팅창이 청소되었습니다!'
all_chat_clear_message: '&f모든 채팅창이 청소되었습니다!'
broadcast_command_message: '&f/확성기 [메세지] - [메세지]를 전체채팅으로 전달합니다.'
broadcast_cooldown_message: '&f확성기 재사용 시간까지 %time% 초 남았습니다!'
unknown_command_message: '&f올바르지 않은 명령어입니다.'
message_gm_0: '&f/gm 0 - 게임모드를 서바이벌 모드로 변경합니다.'
message_gm_1: '&f/gm 1 - 게임모드를 크리에이티브 모드로 변경합니다.'
message_gm_2: '&f/gm 2 - 게임모드를 모험 모드로 변경합니다.'
message_gm_3: '&f/gm 3 - 게임모드를 관전자 모드로 변경합니다.'
```


## Commands
플러그인 리로드 하기: /utiltool reload  
스폰 지점 정하기: /setspawn  
스폰으로 이동하기: /spawn, /tmvhs, /스폰, /넴주  
개인 채팅 청소: /채팅청소  
전체 채팅 청소: /전체채팅청소  
게임 모드 변경 : /gm [0~3] , /gmc , /gms  
확성기 기능 : /확성기 [할말]  

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
  utiltool.gamemode:
    default: op
```
