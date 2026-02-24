# GateBridge

能让你在面板（Java Docker 环境）中运行 Gate 的项目。

## 简介

GateBridge 是专为服务器面板和 Docker 环境设计的 Minecraft 代理服务器，基于 Gate 构建。

### 特性

- ✅ 面板友好，开箱即用
- ✅ Docker 优化
- ✅ 跨平台支持（Windows/Linux/macOS）
- ✅ 使用 Gradle 专业构建

---

## 构建指南（开发者）

### 准备文件

1. **下载 Gate**：
   - 访问 [Gate GitHub Releases](https://github.com/minekube/gate/releases)
   - 下载适合你服务器系统的 Gate 二进制文件
   - Linux 下载 `gate-*-linux-amd64`，Windows 下载 `gate-*-windows-amd64.exe`
   - 重命名为 `gate`（Linux）或 `gate.exe`（Windows）

2. **放置文件**：
   ```bash
   # Linux
   cp gate src/main/resources/gate
   
   # Windows
   copy gate.exe src\main\resources\gate.exe
   ```

3. **创建配置文件**（可选）：
   ```bash
   # 将 gate.yml.example 复制到 src/main/resources/gate.yml
   cp gate.yml.example src/main/resources/gate.yml
   ```

### 构建项目

```bash
./gradlew build
```

生成的 JAR 位于 `build/libs/GateBridge-1.0.0.jar`，已包含 Gate 二进制和配置文件。

### 发布

将构建好的 JAR 上传到 GitHub Releases 或其他分发平台。

---

## 使用指南（用户）

### 快速开始

1. **下载 JAR**：
   - 从 GitHub Releases 下载最新版本的 `GateBridge-1.0.0.jar`

2. **上传到面板**：
   - 将 `GateBridge-1.0.0.jar` 上传到面板的工作目录
   - 启动命令：`java -jar GateBridge-1.0.0.jar`
   - Gate 二进制和配置会自动从 JAR 中提取到工作目录

3. **配置**（可选）：
   - 首次启动后，会自动生成 `gate.yml` 配置文件
   - 根据需要修改配置文件
   - 重启服务使配置生效

### Docker 部署

```dockerfile
FROM openjdk:11-jre-slim
WORKDIR /app
COPY GateBridge-1.0.0.jar app.jar
EXPOSE 25565
CMD ["java", "-jar", "app.jar"]
```

### 配置说明

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

---

## 常见问题

**Q: 启动失败？**
A: 确保 Java 版本 ≥ 11，JAR 文件完整，工作目录有写权限

**Q: 如何更新 Gate？**
A: 下载最新版本的 JAR 替换即可

**Q: 如何自定义配置？**
A: 首次启动后修改 `gate.yml`，然后重启服务

---

## 项目信息

- **Main Class**: com.gatebridge.ServerCore
- **Version**: 1.0.0
- **Java**: 11+

## 许可证

本项目主代码遵循 MIT 许可证。

包含的 Gate 组件遵循 Apache License, Version 2.0。

详细信息请查看 JAR 包中的 `META-INF/LICENSE` 和 `META-INF/NOTICE` 文件。

## 相关链接

- [Gate 官网](https://gate.minekube.com/)
- [Gate GitHub](https://github.com/minekube/gate)
- [Gate Releases](https://github.com/minekube/gate/releases)
