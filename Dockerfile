# 로컬에서 빌드

FROM ghcr.io/graalvm/graalvm-community:21 AS builder
WORKDIR /app
COPY . .
RUN ./gradlew -Dorg.gradle.jvmargs="-Xmx8g" nativeCompile
# 바이너리 정보 출력 및 실행 권한 확인
RUN file build/native/nativeCompile/demo-jpa || echo "file command not found"
RUN ls -la build/native/nativeCompile/

# 아키텍처 명시적 지정 (개발 환경과 일치하도록)
FROM debian:bookworm-slim
WORKDIR /app
COPY --from=builder /app/build/native/nativeCompile/demo-jpa .
# 실행 권한 부여
RUN chmod +x ./demo-jpa
# 실행 파일 존재 확인
RUN ls -la
EXPOSE 8080
ENTRYPOINT ["./demo-jpa"]
