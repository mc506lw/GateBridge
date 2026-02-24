# GateBridge

能让你在面板（Java Docker 环境）中运行 Gate 的项目。

## 简介

GateBridge 是专为服务器面板和 Docker 环境设计的 Minecraft 代理服务器，基于 Gate 构建。

### 特性

- ✅ 面板友好，开箱即用
- ✅ Docker 优化
- ✅ 跨平台支持（Windows/Linux/macOS）
- ✅ 使用 Gradle 专业构建

## 快速开始

### 准备文件

1. **下载 Gate**：
   - 访问 [Gate GitHub Releases](https://github.com/minekube/gate/releases)
   - 下载适合你服务器系统的 Gate 二进制文件
   - Linux 服务器下载 `gate-*-linux-amd64`
   - 重命名为 `gate`（Linux）或 `gate.exe`（Windows）

2. **放置文件**：
   - 将 Gate 二进制文件放入 `src/main/resources/` 目录
   - 复制 `gate.yml.example` 为 `gate.yml` 并修改配置

3. **构建项目**：
   ```bash
   ./gradlew build
   # 生成的 JAR 位于 build/libs/GateBridge-1.0.0.jar
   ```

4. **上传到面板**：
   - 将 `GateBridge-1.0.0.jar` 上传到面板
   - 启动命令：`java -jar GateBridge-1.0.0.jar`
   - Gate 二进制和配置会自动从 JAR 中提取

## 配置

主要配置项（`gate.yml`）：

```yaml
config:
  bind: 0.0.0.0:25565
  lite:
    enabled: true
    routes:
      - host: localhost
        backend: localhost:25566
```

详细配置：[Gate 官方文档](https://gate.minekube.com/)

## Docker 部署

```dockerfile
FROM openjdk:11-jre-slim
WORKDIR /app
COPY build/libs/GateBridge-1.0.0.jar app.jar
EXPOSE 25565
CMD ["java", "-jar", "app.jar"]
```

## 项目信息

- **Main Class**: com.gatebridge.ServerCore
- **Version**: 1.0.0
- **Java**: 11+

## 常见问题

**Q: 启动失败？**
A: 确保 Gate 二进制文件已正确放入 `src/main/resources/`，配置文件存在，Java 版本 ≥ 11

**Q: 如何更新 Gate？**
A: 替换 `src/main/resources/gate` 或 `gate.exe` 文件，重新构建即可

## 相关链接

- [Gate 官网](https://gate.minekube.com/)
- [Gate GitHub](https://github.com/minekube/gate)
- [Gate Releases](https://github.com/minekube/gate/releases)
