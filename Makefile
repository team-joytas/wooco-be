# 커밋 컨벤션용 pre-commit 훅

setup:
	@echo "pre-commit 훅 등록을 시작합니다..."
	@if command -v pip3 > /dev/null; then \
    		pip3 install pre-commit --quiet; \
    	else \
    		pip install pre-commit --quiet; \
    	fi
	@mkdir -p .git/hooks
	@touch .git/hooks/commit-msg
	@ln -sf ../../script/pre-commit.sh .git/hooks/commit-msg
	@chmod +x .git/hooks/commit-msg
	@echo "✅  pre-commit 훅 등록 완료."

clean:
	@echo "pre-commit 훅 제거를 시작합니다..."
	@rm -f .git/hooks/commit-msg
	@echo "✅  pre-commit 훅 삭제 완료."
