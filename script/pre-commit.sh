#!/bin/sh

commit_massage_file=$1
commit_message=$(cat "$commit_massage_file")

if ! echo "$commit_message" | grep -qE "^(feat|fix|hotfix|build|style|docs|refactor|test|chore): .+ \(#\d+\)$"; then
  echo "🚨 커밋 메시지가 잘못되었습니다. 🚨"
  echo "<커밋 타입>: 커밋 내용 (#이슈번호)"
  echo "ex) feat: 예시 로직 추가 (#123)"
  exit 1
fi
