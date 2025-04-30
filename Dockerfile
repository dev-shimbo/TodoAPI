#  Java 実行環境JRE Java 開発環境 JDK
# ベースイメージを指定します
FROM eclipse-temurin:17-jdk-jammy AS builder
# ワーキングディレクトリを設定します
WORKDIR /app
# Maven Wrapperを含むすべてのファイルをコピーします
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
# Maven依存関係をダウンロードします
RUN ./mvnw dependency:go-offline
# アプリケーションのソースコードをコピーします
COPY src ./src
# アプリケーションをビルドします
RUN ./mvnw clean package
# ランタイムステージの定義
FROM eclipse-temurin:17-jre-jammy
# ワーキングディレクトリを設定します
WORKDIR /app
# アプリケーションのビルド結果をコピーします
# app.jarとして扱う
COPY --from=builder /app/target/*.jar app.jar
# コンテナが起動する際に実行されるコマンドを指定します
ENTRYPOINT ["java", "-jar", "app.jar"]
