# FeMeHaeJo - BE

## 🙌 Branch Naming Convention

| 이름 | 규칙 | 설명 | 분기점 | 병합점 |
|---|---|---|---|---|
| main | `main` | 배포 가능한 최종 상태의 브랜치 | - | develop |
| develop | `develop` | 기능 개발을 위한 분기 및 병합 지점으로 사용하는 브랜치 | main | feat |
| hotfix | `hotfix/v<hotfix-version>` | 서비스 중 긴급 수정 건에 대한 처리 | main | main, develop |
| feat | `feat/<feature-name>` | 기능 개발 브랜치 | develop | develop |
| refactor | `refactor/<feature-name>` | 리팩토링 브랜치 | develop | develop |

<details>
<summary>예시</summary>
<div markdown="1">

- <이름>/<기능설명>-#<이슈번호> 의 형식으로 작성
- 브랜치명은 kebab-case를 따름
- 예) feat/create-login-#3

</div>
</details>

## 🙌 Commit Message Convention

| 머릿말           | 설명                                                             |
| ---------------- | --------------------------------------------------------------|
| feat             | 새로운 기능 추가                                                  |
| fix              | 버그 수정                                                       |
| design           | CSS 등 사용자 UI 디자인 변경                                       |
| !BREAKING CHANGE | 커다란 API 변경의 경우                                            |
| !HOTFIX          | 코드 포맷 변경, 세미 콜론 누락, 코드 수정이 없는 경우                     |
| refactor         | 프로덕션 코드 리팩토링업                                            |
| comment          | 필요한 주석 추가 및 변경                                            |
| docs             | 문서 수정                                                       |
| test             | 테스트 추가, 테스트 리팩토링(프로덕션 코드 변경 X)                       |
| setting          | 패키지 설치, 개발 설정                                             |
| chore            | 빌드 테스트 업데이트, 패키지 매니저를 설정하는 경우(프로덕션 코드 변경 X)      |
| rename           | 파일 혹은 폴더명을 수정하거나 옮기는 작업만인 경우                         |
| remove           | 파일을 삭제하는 작업만 수행한 경우                                     |

<details>
<summary>예시</summary>
<div markdown="1">

- 양식: <머릿말>: <제목> - #<이슈번호> 의 형식으로 작성
- 예시: feat: 로그인 기능 추가 - #3

</div>
</details>
