# 🧭 Git 브랜치 관리 매뉴얼 (Main + Feature 전략)

## 개요
이 문서는 프로젝트의 Git 브랜치 전략과 작업 절차를 설명합니다.  
본 프로젝트는 **단순성과 안정성**을 중시하며, 다음 두 개의 주요 브랜치를 기반으로 합니다.

| 브랜치 | 역할 |
|---------|------|
| **main** | 배포 가능한 안정 버전 |
| **feature/*** | 새로운 기능이나 수정 작업용 임시 브랜치 |

## 기본 원칙
1. **`main` 브랜치는 항상 안정 상태**를 유지해야 합니다.
2. 메인 브랜치는 직접 커밋하지 않고, PR(Pull Request) 또는 merge를 통해 변경하여 히스토리를 추척할 수 있도록 합니다.
3. 새로운 작업은 반드시 **feature 브랜치**에서 진행하고, PR을 통하여 병합하는 작업이 필요합니다.
4. 하나의 feature 브랜치는 **하나의 목적(기능, 버그 수정 등)** 만 수행합니다. 
5. 브랜치 이름은 명확하고 일관성 있게 작성합니다. ( 예: `feature/login`, `feature/api-refactor`, `fix/crash-on-startup` )

## 브랜치 생성 절차
> 새 작업을 시작하기 전에 항상 main 브랜치를 최신화합니다.

```bash
# step 1. main 최신화
git checkout main
git pull origin main

# step 2. 새 feature 브랜치 생성
git checkout -b feature/<작업명>

ex) git checkout -b feature/user-auth
```

## 작업 및 커밋 규칙
> 커밋은 작은 단위로 나누고, 의미 있는 메시지를 남깁니다.
> 커밋 메시지는 다음 형식을 따릅니다:

```bash

feat: add login form
fix: resolve null pointer in user manager
refactor: simplify API call logic
docs: update setup guide

```

## 작업 완료 후 병합 절차
> 기능 구현이 완료되면 main에 병합하기 전, 다음 단계를 따릅니다.

### 1. main 최신화
```bash
git checkout main
git pull origin main
```

### 2. feature 브랜치 병합
```bash
git merge --no-ff feature/<작업명> -m "Merge feature/<작업명> into main"
```

### 3. 병합 결과 확인
```bash
git log --oneline --graph --decorate
```

### 4. 원격 저장소에 푸시
```bash
git push origin main
```

### 5. 병합 후, feature 브랜치는 더 이상 필요하지 않으므로 삭제합니다.

```bash
git branch -d feature/<작업명>
git push origin --delete feature/<작업명>
```

## 권장 워크플로 요약

| 단계| 명령 예시 | 설명|
|---|---|---|
| 1 | git pull origin main | main 최신화|
| 2 | git checkout -b feature/<작업명> | 새 브랜치 생성 |
| 3 | git add . → git commit -m "<메시지> | 작업 및 커밋 |
| 4 | git push origin feature/<작업명> | 원격에 feature 브랜치 푸시 |
| 5 | PR 또는 수동 병합| main에 병합 |
| 6 | git branch -d feature/<작업명> | 로컬 브랜치 정리|

---

* 본 문서는 깃허브 프로젝트의 코드 베이스를 표준화하고, 변경 이력을 투명하게 관리하기 위해 고안되었습니다.


